package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.capability.TCRCapabilityProvider;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.clientbound.PortalBlockScreenPacket;
import com.gaboj1.tcr.util.DataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 传送石碑
 */
public class PortalBlock extends BaseEntityBlock{

    public PortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {

        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof PortalBlockEntity portalBlockEntity){
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent((tcrPlayer -> tcrPlayer.setLastPortalBlockPos(pos)));
                //创造且潜行的情况下，按下即为切换传送锚点的类型。
                if(serverPlayer.isCreative() && player.isShiftKeyDown()){
                    portalBlockEntity.changeId();
                    player.sendSystemMessage(Component.literal("ID changed to: "+portalBlockEntity.getId()));
                }else {
                    if(!portalBlockEntity.isPlayerUnlock(player) || !portalBlockEntity.isActivated()){
                        portalBlockEntity.setPlayerUnlock(player);//解锁传送石！
                        portalBlockEntity.activateAnim();
                        serverPlayer.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_unlock"));
                        level.playSound(null , player.getX(),player.getY(),player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS,1,1);//播放末地传送门开启的音效
                    }else{
                        CompoundTag data = new CompoundTag();
                        data.putBoolean("isVillage", portalBlockEntity.getId() < 4);
                        data.putBoolean("isFromPortalBed", false);
                        data.putBoolean("isSecondEnter", false);
                        data.putInt("bedPosX", pos.getX());
                        data.putInt("bedPosY", pos.getY());
                        data.putInt("bedPosZ", pos.getZ());
                        for(DataManager.BoolData boolData : DataManager.portalPointUnlockData){
                            data.putBoolean(boolData.getKey(), boolData.get(player));
                        }
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PortalBlockScreenPacket(data), serverPlayer);
                    }
                }
            }
            portalBlockEntity.unlock();//客户端也同步 TODO 换个传输法
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> components, @NotNull TooltipFlag p_49819_) {
        components.add(Component.translatable(this.getDescriptionId()+".usage"));
        super.appendHoverText(p_49816_, p_49817_, components, p_49819_);
    }

//    @Override
//    @SuppressWarnings("deprecation")
//    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos, @NotNull CollisionContext p_60558_) {
//        return Block.box(0,0,0,32,32,32);
//    }

    /**
     * 粒子特效
     */
    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        VoxelShape $$4 = this.getShape(pState, pLevel, pPos, CollisionContext.empty());
        Vec3 $$5 = $$4.bounds().getCenter();
        double $$6 = (double)pPos.getX() + $$5.x;
        double $$7 = (double)pPos.getZ() + $$5.z;

        for(int $$8 = 0; $$8 < 3; ++$$8) {
            if (pRandom.nextBoolean()) {
                pLevel.addParticle(ParticleTypes.PORTAL, $$6 + pRandom.nextDouble() / 5.0, (double)pPos.getY() + (0.5 - pRandom.nextDouble()), $$7 + pRandom.nextDouble() / 5.0, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        if(entity instanceof Player player){
            return player.isCreative();
        }
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState state) {
        return new PortalBlockEntity(blockPos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
