package com.p1nero.dote.archive;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.entity.LevelableEntity;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.SyncArchivePacket;
import com.p1nero.dote.network.packet.clientbound.BroadcastMessagePacket;
import com.p1nero.dote.network.packet.clientbound.OpenEndScreenPacket;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 保存游戏进度，这玩意儿应该所有人统一，所以用了自己的数据管理。
 */
public class DOTEArchiveManager {

    private static boolean alreadyInit = false;

    /**
     * 以服务端数据为准，如果已经被服务端同步过了，则不能读取客户端的数据，用于{@link DOTEArchiveManager#read(String)}
     */
    public static void setAlreadyInit() {
        DOTEArchiveManager.alreadyInit = true;
    }

    public static boolean isAlreadyInit() {
        return alreadyInit;
    }

    private static int worldLevel = 0;
    public static final BiomeProgressData BIOME_PROGRESS_DATA = new BiomeProgressData();
    private static boolean noPlotMode = false;

    public static void setNoPlotMode() {
        noPlotMode = true;
    }

    public static boolean isNoPlotMode() {
        return noPlotMode;
    }

    public static int getWorldLevel(){
        return worldLevel;
    }

    public static boolean setWorldLevel(int worldLevel) {
        if(worldLevel >= 0 && worldLevel <= 3){
            DOTEArchiveManager.worldLevel = worldLevel;
            return true;
        } else {
            DuelOfTheEndMod.LOGGER.info("failed to set world level. world level should between [0, 2]");
            return false;
        }
    }

    public static String getWorldLevelName(){
        return switch (worldLevel){
            case 0 -> "N";
            case 1 -> "Ⅰ";
            case 2 -> "Ⅱ";
            case 3 -> "Ⅲ";
            case 4 -> "Ⅳ";
            default -> throw new IllegalStateException("Unexpected worldLevel value: " + worldLevel);
        };
    }

    public static boolean isFinished(){
        return worldLevel >= 3;
    }

    public static final List<Task> DIALOG_LIST = new ArrayList<>();
    public static final HashSet<Task> DIALOG_SET = new HashSet<>();//优化用的，但是不知道能优化多少（
    public static final TaskSet TASK_SET = new TaskSet();
    public static void addTask(Task task){
        TASK_SET.add(task);
    }
    public static void finishTask(Task task){
        TASK_SET.remove(task);
    }

    /**
     * 把对话添加到列表里
     */
    public static void addDialog(Component name, Component content){
        if(name == null || content == null){
            return;
        }
        Task task = new Task(name, content);
        if(!DIALOG_SET.contains(task)){
            DIALOG_LIST.add(task);
            DIALOG_SET.add(task);
        }
    }

    public static List<Task> getDialogList() {
        return DIALOG_LIST;
    }

    public static CompoundTag getDialogListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        for(int i = 0; i < DIALOG_LIST.size(); i++){
            dialogListNbt.put("dialog"+i, DIALOG_LIST.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setDialogListFromNbt(CompoundTag DialogListTag, int size){
        DIALOG_LIST.clear();
        for(int i = 0; i < size; i++){
            Task task = Task.fromNbt(DialogListTag.getCompound("dialog"+i));
            DIALOG_LIST.add(task);
            DIALOG_SET.add(task);
        }
    }

    public static CompoundTag getTaskListNbt(){
        CompoundTag dialogListNbt = new CompoundTag();
        List<Task> tasks = TASK_SET.stream().toList();
        for(int i = 0; i < tasks.size(); i++){
            dialogListNbt.put("task" + i, tasks.get(i).toNbt());
        }
        return dialogListNbt;
    }

    public static void setTaskListFromNbt(CompoundTag taskListTag, int size){
        TASK_SET.clear();
        for(int i = 0; i < size; i++){
            TASK_SET.add(Task.fromNbt(taskListTag.getCompound("task"+i)), false);
        }
    }

    /**
     * 完成轮回时调用, 提升怪物等级并记录选择
     */
    public static void worldLevelUp(ServerLevel level, boolean chooseKnight){
        if(level.dimension() != DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
            level = level.getServer().getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY);
        }
        if(level == null){
            return;
        }
        worldLevel++;
        BIOME_PROGRESS_DATA.clearData();
        switch (worldLevel){
            case 1:
                BIOME_PROGRESS_DATA.setChoice1(chooseKnight);
                //广播盔甲可用
                PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new BroadcastMessagePacket(DuelOfTheEndMod.getInfo("tip14"), false));
                break;
            case 2:
                BIOME_PROGRESS_DATA.setChoice2(chooseKnight);
                break;
            case 3:
                BIOME_PROGRESS_DATA.setChoice3(chooseKnight);
        }

        for(Entity entity : level.getAllEntities()){
            if(entity instanceof LevelableEntity levelableEntity){
                levelableEntity.levelUp(worldLevel);
            }
        }

        //完成一次轮回
        for(ServerPlayer player : level.getServer().getPlayerList().getPlayers()){
            //全部遣返，重置状态
            if(player.serverLevel().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
                ServerLevel overworld = level.getServer().overworld();
                player.changeDimension(overworld, new DOTETeleporter(level.getSharedSpawnPos()));
                //重置重生点
                if(player.getRespawnDimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
                    player.setRespawnPosition(Level.OVERWORLD, overworld.getSharedSpawnPos(), 0.0F, false, true);
                }
                //重置能否进炼狱群系
                player.getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(dotePlayer -> dotePlayer.setCanEnterPBiome(false));
//                //清空物品栏中的非法物品
//                if(!player.isCreative()){
//                    for(NonNullList<ItemStack> list :ItemUtil.getCompartments(player)){
//                        for(ItemStack stack : list){
//                            if(!(stack.getItem() instanceof IDOTEKeepableItem doteKeepableItem && doteKeepableItem.shouldKeepWhenExitDim())){
//                                stack.setCount(0);
//                            }
//                        }
//                    }
//                }
            }
            if(isFinished()){
                if(BIOME_PROGRESS_DATA.isEnd1()){
                    DOTEAdvancementData.getAdvancement("loyal", player);
                } else if(BIOME_PROGRESS_DATA.isEnd3()){
                    DOTEAdvancementData.getAdvancement("star", player);
                    PacketRelay.sendToPlayer(DOTEPacketHandler.INSTANCE, new OpenEndScreenPacket(), player);//播终末之诗
                    ItemUtil.addItem(player, DOTEItems.BALMUNG.get().getDefaultInstance());
                } else {
                    DOTEAdvancementData.getAdvancement("unfinished", player);
                }
            }
        }
        //全局广播
//        Component message = DuelOfTheEndMod.getInfo("level_up", getWorldLevelName());
//        PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new BroadcastMessagePacket(message, false));
        //同步客户端数据
        syncToClient();

    }

    public static class BiomeProgressData {

        private boolean guideSummoned;
        private boolean boss1fought;
        private boolean boss2fought;
        private boolean boss3fought;
        private boolean senbaiFought;
        private boolean goldenFlameFought;
        private boolean choice1, choice2, choice3;

        public boolean isGuideSummoned() {
            return guideSummoned;
        }

        public boolean isBoss1fought() {
            return boss1fought;
        }

        public boolean isBoss2fought() {
            return boss2fought;
        }

        public boolean isSenbaiFought() {
            return senbaiFought;
        }

        public boolean isGoldenFlameFought() {
            return goldenFlameFought;
        }

        public void setGuideSummoned(boolean guideSummoned) {
            this.guideSummoned = guideSummoned;
        }

        public void setBoss1fought(boolean boss1fought) {
            this.boss1fought = boss1fought;
        }

        public void setBoss2fought(boolean boss2fought) {
            this.boss2fought = boss2fought;
        }
        public void setBoss3fought(boolean boss2fought) {
            this.boss3fought = boss2fought;
        }

        public void setSenbaiFought(boolean senbaiFought) {
            this.senbaiFought = senbaiFought;
        }

        public void setGoldenFlameFought(boolean goldenFlameFought) {
            this.goldenFlameFought = goldenFlameFought;
        }

        /**
         * 结局1： 忠诚，全选骑士
         */
        public boolean isEnd1(){
            return isFinished() && choice1 && choice2 && choice3;
        }

        /**
         * 结局3：碎星者，最终必选final，且前面有一次选final
         */
        public boolean isEnd3(){
            return isFinished() && (!choice1 || !choice2) && !choice3;
        }

        public boolean isChoice1() {
            return choice1;
        }

        public void setChoice1(boolean choice1) {
            this.choice1 = choice1;
        }

        public boolean isChoice2() {
            return choice2;
        }

        public void setChoice2(boolean choice2) {
            this.choice2 = choice2;
        }

        public boolean isChoice3() {
            return choice3;
        }

        public void setChoice3(boolean choice3) {
            this.choice3 = choice3;
        }

        public BiomeProgressData(){

        }

        /**
         * 轮回的时候用
         */
        public void clearData(){
            boss1fought = false;
            boss2fought = false;
            boss3fought = false;
            senbaiFought = false;
            goldenFlameFought = false;
        }

        public CompoundTag toNbt(CompoundTag tag) {
            tag.putBoolean("guideSummoned", guideSummoned);
            tag.putBoolean("boss1fought", boss1fought);
            tag.putBoolean("boss2fought", boss2fought);
            tag.putBoolean("boss3fought", boss3fought);
            tag.putBoolean("senbaiFought", senbaiFought);
            tag.putBoolean("goldenFlameFought", goldenFlameFought);
            tag.putBoolean("choice1", choice1);
            tag.putBoolean("choice2", choice2);
            tag.putBoolean("choice3", choice3);
            return tag;
        }

        public void fromNbt(CompoundTag serverData){
            guideSummoned = serverData.getBoolean("guideSummoned");
            boss1fought = serverData.getBoolean("boss1fought");
            boss2fought = serverData.getBoolean("boss2fought");
            boss3fought = serverData.getBoolean("boss3fought");
            senbaiFought =  serverData.getBoolean("senbaiFought");
            goldenFlameFought = serverData.getBoolean("goldenFlameFought");
            choice1 = serverData.getBoolean("choice1");
            choice2 = serverData.getBoolean("choice2");
            choice3 = serverData.getBoolean("choice3");
        }

    }

    public static Task buildTask(String task){
        return new Task(Component.translatable("task." + DuelOfTheEndMod.MOD_ID + "." + task), Component.translatable("task_content." + DuelOfTheEndMod.MOD_ID + "." + task));
    }

    public static Task buildTask(String task, Object... objects){
        return new Task(Component.translatable("task." + DuelOfTheEndMod.MOD_ID + "." + task), Component.translatable("task_content." + DuelOfTheEndMod.MOD_ID + "." + task, objects));
    }


    public static File getFile(String worldName){
        return new File("config/"+ DuelOfTheEndMod.MOD_ID+"/"+ worldName +".nbt");
    }

    public static void save(String worldName){

        try {
            CompoundTag saveData = toNbt();
            File file = getFile(worldName);
            DuelOfTheEndMod.LOGGER.info("saving data to {} :\n"+saveData, file.getAbsolutePath());
            if(!file.exists()){
                file.createNewFile();
            }
            NbtIo.write(saveData, file);
            DuelOfTheEndMod.LOGGER.info("over.");
        } catch (Exception e) {
            DuelOfTheEndMod.LOGGER.error("Can't save serverData", e);
        }

    }

    public static void read(String worldName){
        if(alreadyInit){
            return;
        }
        try {
            File save = getFile(worldName);
            CompoundTag saveData = NbtIo.read(save);
            if(saveData == null){
                DuelOfTheEndMod.LOGGER.info("save data not found. created new save data: {}" + save.createNewFile(), save.getAbsolutePath());
            } else {
                DuelOfTheEndMod.LOGGER.info("reading data {} :\n"+saveData, save.getAbsolutePath());
                fromNbt(saveData);
                DuelOfTheEndMod.LOGGER.info("over.");
            }
        } catch (Exception e) {
            DuelOfTheEndMod.LOGGER.error("Can't read save serverData", e);
        }
    }

    public static boolean deleteCache(String fileName){
        fromNbt(new CompoundTag());
        return getFile(fileName).delete();
    }

    /**
     * 清空数据
     */
    public static void clear(){
        fromNbt(new CompoundTag());
        alreadyInit = false;
        syncToClient();
    }

    /**
     * 同步数据
     * 请在服务端使用
     */
    public static void syncToClient(){
        PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new SyncArchivePacket(toNbt()));
    }

    /**
     * 把服务端的所有数据转成NBT方便发给客户端
     * @return 所有数据狠狠塞进NBT里
     */
    public static CompoundTag toNbt(){
        CompoundTag serverData = new CompoundTag();
        serverData.putInt("worldLevel", worldLevel);
        serverData.putBoolean("noPlotMode", noPlotMode);
//        serverData.putInt("dialogLength", DIALOG_LIST.size());
//        serverData.put("dialogList", getDialogListNbt());
//        serverData.putInt("taskLength", TASK_SET.size());
//        serverData.put("taskList", getTaskListNbt());
        serverData.put("biome_progress_data", BIOME_PROGRESS_DATA.toNbt(new CompoundTag()));
        return serverData;
    }

    /**
     * 把服务端发来的nbt转成客户端的SaveUtil调用
     * @param serverData 从服务端发来的nbt
     */
    public static void fromNbt(CompoundTag serverData){
        DuelOfTheEndMod.LOGGER.info("reading from: \n" + serverData);
        alreadyInit = true;
        worldLevel = serverData.getInt("worldLevel");
        noPlotMode = serverData.getBoolean("noPlotMode");
//        setDialogListFromNbt(serverData.getCompound("dialogList"), serverData.getInt("dialogLength"));
//        setTaskListFromNbt(serverData.getCompound("taskList"), serverData.getInt("taskLength"));
        BIOME_PROGRESS_DATA.fromNbt(serverData.getCompound("biome_progress_data"));
    }

}
