package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.11.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelgdb<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "modelgdb"), "main");
    public final ModelPart RightLeg;
    public final ModelPart LeftLeg;

    public Modelgdb(ModelPart root) {
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(46, 45).addBox(-3.284F, 7.6642F, -2.874F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(68, 82).addBox(2.716F, 6.7842F, -2.874F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 68).addBox(-3.104F, 6.7842F, -2.874F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 40).addBox(-3.284F, 5.7842F, -2.874F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(50, 16).addBox(-3.284F, 5.7842F, 2.946F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(46, 35).addBox(-2.896F, 7.6642F, -2.874F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 54).addBox(-2.716F, 6.7842F, -2.874F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 61).addBox(3.104F, 7.0267F, -2.874F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 22).addBox(-2.896F, 5.7842F, -2.874F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-2.896F, 5.7842F, 2.946F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
