package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.serverbound.ControlLlamaPacket;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class PlayerEventListener {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity().level() instanceof ServerLevel level){
            //补录，以防新玩家没有成就
            for(ServerPlayer player : level.getPlayers((serverPlayer -> true))){
                if(SaveUtil.biome1.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_1", player);
                }
                if(SaveUtil.biome2.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_2", player);
                }
                if(SaveUtil.biome3.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_3", player);
                }
                if(SaveUtil.biome4.isFinished()){
                    TCRAdvancementData.getAdvancement("finish_biome_4", player);
                }
            }
            //动态调整怪物血量
            if(TCRConfig.BOSS_HEALTH_AND_LOOT_MULTIPLE.get()){
                for(Entity entity : level.getEntities().getAll()){
                    if(entity instanceof MultiPlayerBoostEntity multiPlayerBoostEntity){
                        multiPlayerBoostEntity.whenPlayerCountChange();
                    }
                }
            }

        }

        //初始化玩家数据
        DataManager.init(event.getEntity());

        //检测是否是快速模式
        if(TCRConfig.FAST_MOD.get() && !DataManager.getFastModLoot.get(event.getEntity())){
            DataManager.getFastModLoot.put(event.getEntity(), true);
            event.getEntity().addItem(Items.NETHER_STAR.getDefaultInstance());
            event.getEntity().addItem(Items.WITHER_ROSE.getDefaultInstance());
            event.getEntity().displayClientMessage(TheCasketOfReveriesMod.getInfo("fast_mod_tip").withStyle(ChatFormatting.BLUE), false);
        }

        //主世界没假身就召唤假身，注意主世界和维度的区别
        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY && serverPlayer.getServer() != null){
            serverPlayer.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> {
                ServerLevel overworld = serverPlayer.getServer().overworld();
                if(tcrPlayer.getFakePlayerUuid() == null || !(overworld.getEntity(tcrPlayer.getFakePlayerUuid()) instanceof TCRFakePlayer)){
                    //召唤假人
                    BlockPos bedBlockPos = tcrPlayer.getBedPointBeforeEnter();
                    TCRFakePlayer fakePlayer = new TCRFakePlayer(serverPlayer, overworld, bedBlockPos);
                    overworld.addFreshEntity(fakePlayer);
                    fakePlayer.setSleepingPos(bedBlockPos);
                }
            }));
        }
    }

    /**
     * 玩家退出事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){

    }

    /**
     * 前面的区域以后再来探索吧~
     */
    @SubscribeEvent
    public static void enterBiome(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        //维度内可骑羊驼
        if(player.level().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
            if(player.isLocalPlayer() && player.getVehicle() instanceof Llama llama){
                LocalPlayer localPlayer = (LocalPlayer) player;
                if(llama.isTamed() && localPlayer.input.up){
                    TCRPacketHandler.INSTANCE.sendToServer(new ControlLlamaPacket(llama.getId(), player.getViewVector(1.0f).scale(20.0).toVector3f()));
                }
            }
        }

        //禁止进入第三第四群系
        if(!player.isCreative()){
            Level level = player.level();
            Holder<Biome> currentBiome = level.getBiome(player.getOnPos());
            if (currentBiome.is(TCRBiomeTags.FORBIDDEN_BIOME)) {
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.enter_forbidden_biome"), true);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 1, false, true));
            }
        }

    }


    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event){
    }

    @SubscribeEvent
    public static void onPlayerWakeup(PlayerWakeUpEvent event){
        if(event.getEntity() instanceof ServerPlayer serverPlayer){
            if(serverPlayer.serverLevel().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
                serverPlayer.serverLevel().setDayTime(1000);
            }
        }
    }

    /**
     * @author LZY_Pinero
     * 利用原版物品冷却的特性使沙鹰双持状态下可以左右开火（不要小看这段代码，没有这段代码无法实现交替开火而且会变成一起开火）
     * 原版右键空气时左右两边都会触发use，但是主手会更快点，所以只要判断沙鹰双持且两物品不同（物品共用冷却）时让没冷却的那个物品迅速冷却1刻（防止一只手use完立马被调用）
     * 有一点很关键，开始是没有判断canFire的，会导致左手可以开火但是由于右手先点击而导致左手武器被禁用
     *
     */
    @SubscribeEvent
    public static void doubleHoldCoolDownController(PlayerInteractEvent event) {

        Player player = event.getEntity();
        ItemStack offHandStack = player.getOffhandItem();
        ItemStack mainHandStack = player.getMainHandItem();
        if (offHandStack.getItem().getClass() == mainHandStack.getItem().getClass())return;
        if(mainHandStack.getItem() instanceof GunCommon mainHandItem&& offHandStack.getItem() instanceof GunCommon offHandItem){

            if(event.getHand() == InteractionHand.MAIN_HAND){
                if(!player.getCooldowns().isOnCooldown(offHandItem)&& !mainHandItem.isReloading){
                    player.getCooldowns().addCooldown(offHandItem,1);
                }
            }else{
                if((!player.getCooldowns().isOnCooldown(mainHandItem)) && !offHandItem.isReloading){
                    player.getCooldowns().addCooldown(mainHandItem,1);
                }
            }

        }
    }

}
