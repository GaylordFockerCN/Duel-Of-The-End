package com.gaboj1.tcr.block.custom;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.entity.PortalBedEntity;
import com.gaboj1.tcr.datagen.DOTEAdvancementData;
import com.gaboj1.tcr.worldgen.biome.BiomeMap;
import com.gaboj1.tcr.worldgen.dimension.DOTEDimension;
import com.gaboj1.tcr.worldgen.portal.DOTETeleporter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PortalBed extends BedBlock {
    public PortalBed(DyeColor pColor, Properties pProperties) {
        super(pColor, pProperties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(!pPlayer.getItemInHand(pHand).is(Items.NETHER_STAR) && !pPlayer.isCreative()){
            pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("before_enter"), true);
            return InteractionResult.CONSUME;
        }
        //统一Pos到床头
        if (pState.getValue(PART) != BedPart.HEAD) {
            pPos = pPos.relative(pState.getValue(FACING));
            pState = pLevel.getBlockState(pPos);
            if (!pState.is(this)) {
                return InteractionResult.CONSUME;
            }
        }
        if (pLevel.isClientSide)
            return InteractionResult.CONSUME;
        if (pPlayer.canChangeDimensions()) {
            if (pLevel instanceof ServerLevel serverlevel && canSetSpawn(serverlevel) && !(Boolean)pState.getValue(OCCUPIED) ) {

                MinecraftServer minecraftserver = serverlevel.getServer();
                ResourceKey<Level> destinationResourcekey = (pLevel.dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY ?
                                Level.OVERWORLD : DOTEDimension.P_SKY_ISLAND_LEVEL_KEY);

                ServerLevel portalDimension = minecraftserver.getLevel(destinationResourcekey);
                if (portalDimension != null && !pPlayer.isPassenger()) {
                    if(destinationResourcekey == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY) {
                        pPlayer.changeDimension(portalDimension, new DOTETeleporter(new BlockPos(BiomeMap.getInstance().getCenter1().x, 170, BiomeMap.getInstance().getCenter1().y)));
                        pPlayer.level().playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
                    } else {
                        //爆炸并且获得成就
                        pLevel.removeBlock(pPos, false);
                        BlockPos $$6 = pPos.relative(pState.getValue(FACING).getOpposite());
                        if (pLevel.getBlockState($$6).is(this)) {
                            pLevel.removeBlock($$6, false);
                        }
                        Vec3 $$7 = pPos.getCenter();
                        pLevel.explode(null, pLevel.damageSources().badRespawnPointExplosion($$7), null, $$7, 5.0F, true, Level.ExplosionInteraction.BLOCK);

                        DOTEAdvancementData.getAdvancement("try_wake_up",(ServerPlayer) pPlayer);

                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

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
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PortalBedEntity(pos, state);
    }

}
