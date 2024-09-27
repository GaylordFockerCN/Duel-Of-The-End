package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.NPCDialoguePacket;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/**
 * 不受彼此伤害，可以被玩家挑战
 */
public abstract class Master extends TCRVillager implements NpcDialogue, LevelableEntity, MultiPlayerBoostEntity {
    protected Player conversingPlayer;
    boolean waiting;
    public static final EntityDataAccessor<Integer> BOSS_ID = SynchedEntityData.defineId(Master.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_TRAIL = SynchedEntityData.defineId(Master.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_FIGHTING = SynchedEntityData.defineId(Master.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_BOSS_SUMMONED = SynchedEntityData.defineId(Master.class, EntityDataSerializers.BOOLEAN);
    private double damageFromBoss;
    public Master(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 142857);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(BOSS_ID, -1);
        getEntityData().define(IS_TRAIL, false);
        getEntityData().define(IS_FIGHTING, false);
        getEntityData().define(IS_BOSS_SUMMONED, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        getEntityData().set(BOSS_ID, tag.getInt("boss_id"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("boss_id", getEntityData().get(BOSS_ID));
    }

    public void setBossId(int id){
        getEntityData().set(BOSS_ID, id);
        getEntityData().set(IS_BOSS_SUMMONED, true);
    }

    public void setSummonedByBoss(boolean summonedByBoss) {
        getEntityData().set(IS_BOSS_SUMMONED, summonedByBoss);
    }

    /**
     * waiting就是不能动的状态
     */
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public boolean isSummonedByBoss() {
        return getEntityData().get(IS_BOSS_SUMMONED);
    }

    public void startFighting(LivingEntity entity){
        getEntityData().set(IS_FIGHTING, true);
        setTarget(entity);
        setWaiting(false);
    }

    public void stopFighting(){
        getEntityData().set(IS_FIGHTING, false);
        setTarget(null);
    }

    @Override
    public void tick() {
        super.tick();
        //如果没有绑定的boss就紫砂
        if(!level().isClientSide && isSummonedByBoss()){
            int bossId = getEntityData().get(BOSS_ID);
            if(bossId != -1 && !(level().getEntity(bossId) instanceof SecondBossEntity)){
                this.setHealth(0);
            }
        }

        if(conversingPlayer != null){
            this.getLookControl().setLookAt(conversingPlayer);
            this.getNavigation().stop();
        }

        if(!level().isClientSide && isWaiting()){
            this.getNavigation().stop();
        }

        if(isSummonedByBoss()){
            setTarget(level().getNearestPlayer(this, 64));
        }

    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightningBolt) {}

    /**
     * 免疫除了来自玩家和第二boss以外的伤害，防止互相攻击。若是试炼状态也可受伤
     */
    @Override
    public boolean hurt(DamageSource source, float v) {
        if(source.getEntity() instanceof SecondBossEntity entity){
            damageFromBoss += v;
            //还想boss帮你打架？做梦
            if(damageFromBoss > getMaxHealth() / 3){
                return false;
            }
        }
        if((source.getEntity() instanceof Player && SaveUtil.biome2.choice == SaveUtil.BiomeProgressData.BOSS) || source.getEntity() instanceof SecondBossEntity || getEntityData().get(IS_TRAIL)){
            return super.hurt(source, v);
        }
        return false;
    }

    /**
     * 死后从boss移除
     * 或者进入对话给予挑战奖励
     */
    @Override
    public void die(@NotNull DamageSource source) {
        if(isSummonedByBoss()){
            super.die(source);
            if(level().getEntity(getEntityData().get(BOSS_ID)) instanceof SecondBossEntity secondBoss){
                secondBoss.removeMaster(this);
            }
        } else if(source.getEntity() instanceof ServerPlayer player){
            sendDialoguePacket(player);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.add(new AnimationController<>(this, "Summon", 10, state -> PlayState.STOP)
                .triggerableAnim("summon", RawAnimation.begin().thenPlay("summon")));
    }

    @Override
    public boolean isFriendly() {
        return !isAngry();
    }

    @Override
    public boolean isAngry() {
        return SaveUtil.BiomeProgressData.BOSS == SaveUtil.biome2.choice || getEntityData().get(IS_FIGHTING);
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        if(senderData.getBoolean("isBossDie")){
            //TODO 历战模式，
        }
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {
        this.conversingPlayer = player;
    }

    @Nullable
    @Override
    public Player getConversingPlayer() {
        return this.conversingPlayer;
    }

    public void sendDialoguePacket(ServerPlayer serverPlayer){
        CompoundTag serverData = new CompoundTag();
        serverData.putBoolean("isElderTalked",SaveUtil.biome2.isElderTalked);
        serverData.putBoolean("isBossDie",SaveUtil.biome2.isBossDie);
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new NPCDialoguePacket(this.getId(), serverData), serverPlayer);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide()) {
                this.lookAt(player, 180.0F, 180.0F);
                if (player instanceof ServerPlayer serverPlayer) {
                    if (this.getConversingPlayer() == null) {
                        this.setConversingPlayer(serverPlayer);
                        sendDialoguePacket(serverPlayer);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

}
