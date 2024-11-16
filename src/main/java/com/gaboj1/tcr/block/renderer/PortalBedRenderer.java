package com.gaboj1.tcr.block.renderer;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.entity.PortalBedEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

/**
 * 床实在不会拼，只能翻看看哪些mod有床，于是找到了这个，并且发现DivineRPG的梦魇床也是传送作用。。
 * @author DivineRPG
 * RenderNightmareBed.java
 */
public class PortalBedRenderer implements BlockEntityRenderer<PortalBedEntity> {
    private final ModelPart headRoot;
    private final ModelPart footRoot;
    public static final ModelLayerLocation HEAD = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "head"), "main");
    public static final ModelLayerLocation FOOT = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "foot"), "main");

    public PortalBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(HEAD);
        this.footRoot = context.bakeLayer(FOOT);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(50, 6).addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation(((float)Math.PI / 2F), 0.0F, ((float)Math.PI / 2F)));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(50, 18).addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation(((float)Math.PI / 2F), 0.0F, (float)Math.PI));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createFootLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation(((float)Math.PI / 2F), 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(50, 12).addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation(((float)Math.PI / 2F), 0.0F, ((float)Math.PI * 1.5F)));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void render(PortalBedEntity tile, float p_112206_, PoseStack poseStack, MultiBufferSource buffer, int light, int p_112210_) {
        Level level = tile.getLevel();
        if (level != null) {
            BlockState blockstate = tile.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> neighborcombineresult = DoubleBlockCombiner.combineWithNeigbour(BlockEntityType.BED, BedBlock::getBlockType, BedBlock::getConnectedDirection, ChestBlock.FACING, blockstate, level, tile.getBlockPos(), (p_112202_, p_112203_) -> false);
            int i = neighborcombineresult.apply(new BrightnessCombiner<>()).get(light);
            this.renderPiece(poseStack, buffer, blockstate.getValue(BedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, blockstate.getValue(BedBlock.FACING), null, i, p_112210_, false);
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, null, light, p_112210_, false);
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, null, light, p_112210_, true);
        }
    }

    private void renderPiece(PoseStack stack, MultiBufferSource bufferSource, ModelPart part, Direction direction, Material material, int p_173547_, int p_173548_, boolean p_173549_) {
        stack.pushPose();
        stack.translate(0.0F, 0.5625F, p_173549_ ? -1.0F : 0.0F);
        stack.mulPose(Axis.XP.rotationDegrees(90.0F));
        stack.translate(0.5F, 0.5F, 0.5F);
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
        stack.translate(-0.5F, -0.5F, -0.5F);

        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/block/portal_bed.png")));
        part.render(stack, vertexconsumer, p_173547_, p_173548_);
        stack.popPose();
    }

}
