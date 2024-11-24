package com.p1nero.dote.block.entity;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.DOTEBlocks;
import com.google.common.collect.Lists;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BetterStructureBlockEntity extends StructureBlockEntity {

    public static final int DETECT_SIZE = 80;

    //用于判断有没有加载过，否则会一直重复加载
    private boolean generated = false;
    public boolean isGenerated() {
        return generated;
    }

    public BetterStructureBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return DOTEBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get();
    }

    @Override
    public boolean detectSize() {
        if (this.getMode() != StructureMode.SAVE) {
            return false;
        } else {
            BlockPos blockpos = this.getBlockPos();
            BlockPos blockPos1 = new BlockPos(blockpos.getX() - DETECT_SIZE, 0, blockpos.getZ() - DETECT_SIZE);
            BlockPos blockPos2 = new BlockPos(blockpos.getX() + DETECT_SIZE, DETECT_SIZE -1, blockpos.getZ() + DETECT_SIZE);
            List<StructureBlockEntity> list = this.getRelatedCorners(blockPos1, blockPos2);
            List<StructureBlockEntity> list1 = this.filterRelatedCornerBlocks(list);
            if (list1.isEmpty()) {
                return false;
            } else {

                BoundingBox boundingBox = this.calculateEnclosingBoundingBox(blockpos, list1);
                if (boundingBox.maxX() - boundingBox.minX() > 1 &&
                        boundingBox.maxY() - boundingBox.minY() > 1 &&
                        boundingBox.maxZ() - boundingBox.minZ() > 1) {

                    this.setStructurePos(new BlockPos(
                            (boundingBox.minX() - blockpos.getX() + 1),
                            (boundingBox.minY() - blockpos.getY() + 1),
                            (boundingBox.minZ() - blockpos.getZ() + 1)));

                    this.setStructureSize(new BlockPos(
                            (boundingBox.maxX() - boundingBox.minX() - 1),
                            (boundingBox.maxY() - boundingBox.minY() - 1),
                            (boundingBox.maxZ() - boundingBox.minZ() - 1)));

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
            if (blockstate.is(Blocks.STRUCTURE_BLOCK) || blockstate.is(DOTEBlocks.BETTER_STRUCTURE_BLOCK.get())) {
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
        Predicate<StructureBlockEntity> predicate = (structureBlock) -> structureBlock.getMode() == StructureMode.CORNER && this.getStructureName().equals(structureBlock.getStructureName());
        return structureBlocks.stream().filter(predicate).collect(Collectors.toList());
    }

    private BoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<StructureBlockEntity> structureBlocks) {
        BoundingBox boundingBox;
        if (structureBlocks.size() > 1) {
            BlockPos blockpos = structureBlocks.get(0).getStructurePos();
            boundingBox = new BoundingBox(blockpos);
        } else {
            boundingBox = new BoundingBox(p_184416_1_);
        }

        for(StructureBlockEntity entity : structureBlocks) {
            BlockPos blockPos1 = entity.getBlockPos();
            boundingBox.encapsulate(blockPos1);
        }

        return boundingBox;
    }

    /**
     * 自毁
     */
    @Override
    public boolean loadStructure(@NotNull ServerLevel level, boolean p_59849_, @NotNull StructureTemplate template) {
        if(generated){
            return false;
        }
        boolean ret = super.loadStructure(level, p_59849_, template);
        if(DOTEConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.get()){
            level.destroyBlock(this.getBlockPos(), false);
        }
        if(ret){
            generated = true;
        }
        return ret;
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
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

        generated = nbt.getBoolean("generated");

        //当加载的时候强制加载一下区块，为了在结构内包含结构方块时以生成结构，省的调用红石。
        if(this.level != null && !generated && DOTEConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.get()){
            if(this.level.isClientSide){
                HandleStructureBlockLoad.load(this);
                generated = true;
            }else {
                //不知道为毛没用？
                DuelOfTheEndMod.LOGGER.info("try to load custom structure block on server: {}", getStructureName());
                loadStructure(((ServerLevel) level));
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("generated", generated);
    }

    @Override
    public boolean saveStructure(boolean writeToDisk) {

        if (this.getMode() == StructureMode.SAVE && !Objects.requireNonNull(this.level).isClientSide) {
            BlockPos $$1 = this.getBlockPos().offset(this.getStructurePos());
            ServerLevel $$2 = (ServerLevel)this.level;
            StructureTemplateManager $$3 = $$2.getStructureManager();

            StructureTemplate $$6;
            try {
                $$6 = $$3.getOrCreate(new ResourceLocation(getStructureName()));
            } catch (ResourceLocationException var8) {
                return false;
            }

            $$6.fillFromWorld(this.level, $$1, this.getStructureSize(), !this.isIgnoreEntities(), Blocks.STRUCTURE_VOID);
//          在独立的版本用at解决了↓
            $$6.setAuthor("Sorry It's Private");
            if (writeToDisk) {
                try {
                    return $$3.save(new ResourceLocation(getStructureName()));
                } catch (ResourceLocationException var7) {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

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
