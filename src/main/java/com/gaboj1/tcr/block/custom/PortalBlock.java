package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.PortalBlockScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 右键传送石碑
* */
public class PortalBlock extends BaseEntityBlock{

    public PortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {

        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof PortalBlockEntity portalBlockEntity){
            if (player instanceof ServerPlayer serverPlayer) {
                if(serverPlayer.isCreative()){
                    portalBlockEntity.changeId(player);
                }else {
                    if(!serverPlayer.getPersistentData().getBoolean(portalBlockEntity.getID())){
                        serverPlayer.getPersistentData().putBoolean(portalBlockEntity.getID(),true);//解锁传送石！
                        serverPlayer.sendSystemMessage(Component.translatable("info.the_casket_of_reveries.teleport_unlock"));
                        level.playSound(player,pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS,1,1);//播放末地传送门开启的音效 TODO 有bug
                        portalBlockEntity.unlock();
                    }else{
                        portalBlockEntity.activateAnim();
                        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PortalBlockScreenPacket(serverPlayer.getPersistentData().copy()), serverPlayer);
                    }
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> components, TooltipFlag p_49819_) {
        components.add(Component.translatable(this.getDescriptionId()+".usage"));
        super.appendHoverText(p_49816_, p_49817_, components, p_49819_);
    }

    //    @Override
//    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
//        super.onBlockStateChange(level, pos, oldState, newState);
//    }

    private String findNearestStone(BlockPos pos){
        int x = pos.getX();
        int z = pos.getZ();

        return "true";
    }

    //粒子特效
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
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
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new PortalBlockEntity(blockPos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
