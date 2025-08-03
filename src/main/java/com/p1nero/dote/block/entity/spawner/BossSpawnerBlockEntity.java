package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Objects;

public abstract class BossSpawnerBlockEntity<T extends DOTEBoss> extends BlockEntity implements GeoBlockEntity {

    protected final EntityType<T> entityType;
    @Nullable
    protected DOTEBoss myBoss;
    public int tickCount;
    private boolean inBossFight;
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation IN_FIGHT = RawAnimation.begin().thenLoop("in_fight");
    protected static final RawAnimation START = RawAnimation.begin().then("start", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation END = RawAnimation.begin().then("end", Animation.LoopType.PLAY_ONCE);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.entityType = entityType;
    }

    public EntityType<T> getEntityType() {
        return entityType;
    }

    public void setMyBoss(@Nullable DOTEBoss myBoss) {
        this.myBoss = myBoss;
    }

    public @Nullable DOTEBoss getMyBoss() {
        return myBoss;
    }

    public void startBossFight() {
        inBossFight = true;
        playStartAnimation();
        syncAndSave();
    }

    public void endBossFight() {
        this.inBossFight = false;
        playEndAnimation();
        this.syncAndSave();
    }

    public boolean isInBossFight() {
        return inBossFight;
    }

    public void onPlayerInteract(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (checkItem(pPlayer, pPlayer.getItemInHand(pHand), pPos, pHit)) {
            if (pLevel instanceof ServerLevel serverLevel) {
                this.spawnMyBoss(serverLevel);
            }
        }
    }

    /**
     * 检查手上物品是否合法
     */
    protected boolean checkItem(Player player, ItemStack itemStack, BlockPos pos, BlockHitResult hitResult) {
        return true;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState state) {
        searchBoss();
    }

    /**
     * 当重进游戏或某种原因导致处于boss战而没boss时，尝试搜索boss
     */
    protected void searchBoss() {
        if (this.inBossFight && (this.myBoss == null || this.myBoss.isRemoved()) && level instanceof ServerLevel serverLevel) {
            List<? extends DOTEBoss> entities = serverLevel.getEntities(entityType, LivingEntity::isAlive);
            if(!entities.isEmpty()) {
                myBoss = entities.get(0);
            } else {
                inBossFight = false;
                myBoss = null;
                syncAndSave();
            }
        }
    }

    public boolean spawnMyBoss(ServerLevelAccessor accessor) {
        if (inBossFight) {
            return false;
        }
        myBoss = this.makeMyCreature();
        myBoss.setHomePos(getBlockPos());
        BlockPos spawnPos = accessor.getBlockState(this.getBlockPos().above()).getCollisionShape(accessor, this.getBlockPos().above()).isEmpty() ? this.getBlockPos().above() : this.getBlockPos();
        myBoss.moveTo(spawnPos, accessor.getLevel().getRandom().nextFloat() * 360F, 0.0F);
        ForgeEventFactory.onFinalizeSpawn(myBoss, accessor, accessor.getCurrentDifficultyAt(spawnPos), MobSpawnType.SPAWNER, null, null);
        boolean success = accessor.addFreshEntity(myBoss);
        if (success) {
            startBossFight();
        }
        return success;
    }

    @NotNull
    protected T makeMyCreature() {
        return Objects.requireNonNull(this.entityType.create(Objects.requireNonNull(this.getLevel())));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
        controllers.add(new AnimationController<>(this, "Transition", 5, state -> PlayState.CONTINUE)
                .triggerableAnim("start", START)
                .triggerableAnim("end", END));
    }

    public void playStartAnimation() {
        triggerAnim("Transition", "start");
    }

    public void playEndAnimation() {
        triggerAnim("Transition", "end");
    }

    protected <E extends BossSpawnerBlockEntity<?>> PlayState deployAnimController(final AnimationState<E> state) {
        return inBossFight ? state.setAndContinue(IN_FIGHT) : state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void sync() {
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void syncAndSave() {
        this.sync();
        setChanged();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putBoolean("inBossFight", inBossFight);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        inBossFight = tag.getBoolean("inBossFight");
        super.load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        saveAdditional(compoundTag);
        return compoundTag;
    }

    /**
     * 获取角斗场的半径，限制玩家离开，同时也是boss的搜索范围
     */
    public abstract float getArenaRadius();

}
