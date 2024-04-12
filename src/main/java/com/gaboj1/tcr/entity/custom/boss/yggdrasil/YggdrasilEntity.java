package com.gaboj1.tcr.entity.custom.boss.yggdrasil;

import com.gaboj1.tcr.init.TCRModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;


public class YggdrasilEntity extends PathfinderMob implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS);

    private boolean canBeHurt;
    private int hurtTimer;

    public YggdrasilEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    public ServerBossEvent getBossBar() {
        return this.bossInfo;
    }
    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.getBossBar().setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.level().isClientSide()) {
            this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.getBossBar().addPlayer(player);
    }


    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.getBossBar().removePlayer(player);
    }

    @Override
    public void die(DamageSource p_21014_) {
        super.die(p_21014_);
        if (this.level() instanceof ServerLevel server){
            this.getBossBar().setProgress(0.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float v) {
        if(damageSource.isCreativePlayer()){
            super.hurt(damageSource, v);
        }
        if(!canBeHurt){
            if(damageSource.getEntity() instanceof Player player){
                player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.boss1invincible"),true);
            }
            return false;
        }

        return super.hurt(damageSource, v);
    }

    //时间过了就恢复无敌状态
    @Override
    public void tick() {
        super.tick();
        if(canBeHurt){
            hurtTimer--;
        }
        if(hurtTimer<0){
            canBeHurt = false;
        }
    }

    //特定机制下（比如树爪被破坏）才能被打
    public void setCanBeHurt() {
        this.canBeHurt = true;
        hurtTimer = 200;
    }

    public static AttributeSupplier setAttributes() {//生物属性
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)//最大血量
                .add(Attributes.ATTACK_DAMAGE, 6.0f)//单次攻击伤害
                .add(Attributes.ATTACK_SPEED, 1.0f)//攻速
                .add(Attributes.MOVEMENT_SPEED, 0.35f)//移速
                .build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //非攻击状态动画
        controllers.add(new AnimationController<>(this, "controller",
                0, this::predicate));

        //攻击状态动画
        controllers.add(new AnimationController<>(this, "attackController",
                0, this::attackPredicate));

    }

public static class spawnTreeClawAtPointPositionGoal extends Goal {
    private final YggdrasilEntity yggdrasil;
    private int shootInterval;
    public final int shootIntervalMax = 200;
    public static final int attackRange = 10;

    public spawnTreeClawAtPointPositionGoal(YggdrasilEntity yggdrasil) {
        this.yggdrasil = yggdrasil;
//        shootInterval = shootIntervalMax;//开局就发射一个树爪，方便调试
    }

    @Override
    public boolean canUse() {
        return --this.shootInterval <= 0;
    }


    @Override
    public void start() {
        List<Player> players = this.yggdrasil.level().getNearbyPlayers(TargetingConditions.DEFAULT, yggdrasil, getPlayerAABB(yggdrasil.getOnPos(),attackRange));
        for(Player target : players){
            TreeClawEntity treeClaw = new TreeClawEntity(this.yggdrasil.level(), this.yggdrasil, target);
            treeClaw.setPos(target.getX(),target.getY()+1,target.getZ());
            yggdrasil.level().addFreshEntity(treeClaw);//树爪继承自Mob，和平模式无法召唤！！
            treeClaw.catchPlayer();
            yggdrasil.level().playSound(null,treeClaw.getOnPos(), SoundEvents.PLAYER_HURT, SoundSource.BLOCKS,1.0f,1.0f);
        }

        this.shootInterval = shootIntervalMax;
    }

    /**
     * 返回一个范围
     * @param pos 中心位置
     * @param offset 半径
     * @return 以pos为中心offset的两倍为边长的一个正方体
     */
    private AABB getPlayerAABB(BlockPos pos, int offset){
        return new AABB(pos.offset(offset,offset,offset),pos.offset(-offset,-offset,-offset));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {//播放移动动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("wander", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(isDeadOrDying()){//播放死亡动画
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.LOOP));
            return PlayState.STOP;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.STOP;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return TCRModSounds.YGGDRASIL_AMBIENT_SOUND.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return TCRModSounds.YGGDRASIL_CRY.get();
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying();
    }

    private PlayState attackPredicate(AnimationState state) {
        if(state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    protected void registerGoals() {//设置生物行为
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new spawnTreeClawAtPointPositionGoal(this));
//       this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));


        /*
         * 下面设置攻击目标：（按需修改）
         * 首先寻找攻击源
         * 如果是玩家，且树怪被玩家激怒，则优先攻击玩家
         * 如果是村民，不处于被激怒状态则也被攻击，优先级低于玩家（按需修改）
         * 如果是Creeper，则与村民逻辑相同，但优先级低于村民（按需修改）
         * */
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,AbstractVillager.class,true));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
