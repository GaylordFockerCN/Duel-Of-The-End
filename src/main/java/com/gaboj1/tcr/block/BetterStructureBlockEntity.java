package com.gaboj1.tcr.block;

import com.gaboj1.tcr.init.TCRModBlocks;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BetterStructureBlockEntity extends StructureBlockEntity {

    public BetterStructureBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public boolean detectSize() {
        if (this.getMode() != StructureMode.SAVE) {
            return false;
        } else {
            BlockPos blockpos = this.getBlockPos();
            BlockPos blockpos1 = new BlockPos(blockpos.getX() - 256, 0, blockpos.getZ() - 256);
            BlockPos blockpos2 = new BlockPos(blockpos.getX() + 256, 255, blockpos.getZ() + 256);
            List<StructureBlockEntity> list = this.getRelatedCorners(blockpos1, blockpos2);
            List<StructureBlockEntity> list1 = this.filterRelatedCornerBlocks(list);
            if (list1.size() < 1) {
                return false;
            } else {

                AABB structureboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);
                if (structureboundingbox.maxX - structureboundingbox.minX > 1 &&
                        structureboundingbox.maxY - structureboundingbox.minY > 1 &&
                        structureboundingbox.maxZ - structureboundingbox.minZ > 1) {

                    this.setStructurePos(new BlockPos(
                            (int) (structureboundingbox.minX - blockpos.getX() + 1),
                            (int) (structureboundingbox.minY - blockpos.getY() + 1),
                            (int) (structureboundingbox.minZ - blockpos.getZ() + 1)));

                    this.setStructureSize(new BlockPos(
                            (int) (structureboundingbox.maxX - structureboundingbox.minX - 1),
                            (int) (structureboundingbox.maxY - structureboundingbox.minY - 1),
                            (int) (structureboundingbox.maxZ - structureboundingbox.minZ - 1)));

                    this.setChanged();
                    BlockState iblockstate = this.level.getBlockState(blockpos);
                    this.level.sendBlockUpdated(blockpos, iblockstate, iblockstate, 3);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private List<StructureBlockEntity> getRelatedCorners(BlockPos minPos, BlockPos maxPos) {
        List<StructureBlockEntity> list = Lists.newArrayList();

        for(BlockPos blockpos : BlockPos.withinManhattan(minPos, maxPos.getX()-minPos.getX(), maxPos.getY()-minPos.getY(), maxPos.getZ()-minPos.getZ())) {
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.STRUCTURE_BLOCK) || blockstate.is(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get())) {
                assert this.level != null;
                BlockEntity entity = this.level.getBlockEntity(blockpos);
                if (entity instanceof StructureBlockEntity) {
                    list.add((StructureBlockEntity)entity);
                }
            }
        }

        return list;
    }

    private List<StructureBlockEntity> filterRelatedCornerBlocks(List<StructureBlockEntity> structureBlocks) {
        Predicate<StructureBlockEntity> predicate = (structureBlock) -> {
            return structureBlock.getMode() == StructureMode.CORNER && this.getStructureName().equals(structureBlock.getStructureName());
        };
        return structureBlocks.stream().filter(predicate).collect(Collectors.toList());
    }

    private AABB calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<StructureBlockEntity> structureBlocks) {
        AABB mutableboundingbox;
        if (structureBlocks.size() > 1) {
            BlockPos blockpos = structureBlocks.get(0).getStructurePos();
            mutableboundingbox = new AABB(blockpos, blockpos);
        } else {
            mutableboundingbox = new AABB(p_184416_1_, p_184416_1_);
        }

        for(StructureBlockEntity entity : structureBlocks) {
            BlockPos blockpos1 = entity.getBlockPos();
            if (blockpos1.getX() < mutableboundingbox.minX) {
                mutableboundingbox.setMinX(blockpos1.getX());
            } else if (blockpos1.getX() > mutableboundingbox.maxX) {
                mutableboundingbox.setMaxX(blockpos1.getX());
            }

            if (blockpos1.getY() < mutableboundingbox.minY) {
                mutableboundingbox.setMinY(blockpos1.getY());
            } else if (blockpos1.getY() > mutableboundingbox.maxY) {
                mutableboundingbox.setMaxY(blockpos1.getY());
            }

            if (blockpos1.getZ() < mutableboundingbox.minZ) {
                mutableboundingbox.setMinZ(blockpos1.getZ());
            } else if (blockpos1.getZ() > mutableboundingbox.maxZ) {
                mutableboundingbox.setMaxZ(blockpos1.getZ());
            }
        }

        return mutableboundingbox;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        int i = nbt.getInt("posX");
        int j = nbt.getInt("posY");
        int k = nbt.getInt("posZ");
        setStructurePos(new BlockPos(i, j, k));
        int l = Math.max(nbt.getInt("sizeX"), 0);
        int i1 = Math.max(nbt.getInt("sizeY"), 0);
        int j1 = Math.max(nbt.getInt("sizeZ"), 0);
        setStructureSize(new BlockPos(l, i1, j1));
        this.updateBlockState();
    }

    @Override
    public boolean saveStructure(boolean writeToDisk) {

        if (this.getMode() == StructureMode.SAVE && this.level.isClientSide ) {
            BlockPos blockpos = this.getBlockPos();
            ServerLevel serverLevel = (ServerLevel) this.level;
            StructureTemplateManager templatemanager = serverLevel.getStructureManager();
            StructureTemplate template = templatemanager.getOrCreate(new ResourceLocation(getStructureName()));
            template.fillFromWorld(this.level, blockpos, this.getStructureSize(), !this.isIgnoreEntities(), Blocks.STRUCTURE_VOID);
//          变成private方法了没法沿用了qwq
//          template.setAuthor(this.author);
            return !writeToDisk || templatemanager.save(new ResourceLocation(getStructureName()));
        } else {
            return false;
        }
    }

//    @Override
//    public BlockEntityType<?> getType() {
//        return ;
//    }

    private void updateBlockState() {
        if (this.level != null) {
            BlockPos blockpos = this.getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.STRUCTURE_BLOCK)) {
                this.level.setBlock(blockpos, blockstate.setValue(StructureBlock.MODE, this.getMode()), 2);
            }

        }
    }

}
