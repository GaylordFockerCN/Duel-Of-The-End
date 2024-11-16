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
public class Modelgdh<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "modelgdh"), "main");
    public final ModelPart Head;

    public Modelgdh(ModelPart root) {
        this.Head = root.getChild("Head");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition Head = partdefinition.addOrReplaceChild("Head",
                CubeListBuilder.create().texOffs(64, 12).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 72).addBox(-3.5F, -7.5F, 3.5F, 7.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-3.5F, -8.5F, -3.5F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(28, 42).addBox(3.5F, -8.5F, -3.5F, 1.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(44, 18)
                        .addBox(-4.5F, -8.5F, -3.5F, 1.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));
        PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 88).addBox(-2.7486F, -5.0081F, 1.7593F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.0706F, -0.0052F, 0.3054F));
        PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 14).addBox(-2.6833F, -4.2219F, 0.5822F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.4633F, -0.0052F, 0.3054F));
        PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 78).addBox(-0.0807F, -2.3027F, 0.1245F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.9257F, 0.5444F, 0.6801F));
        PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(90, 33).addBox(0.8222F, -4.4785F, -0.0443F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -1.0271F, 0.1069F, 0.373F));
        PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(84, 7).addBox(-2.362F, 2.6036F, -1.1774F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.6379F, -0.0052F, 0.3054F));
        PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(84, 0).addBox(-0.5427F, -1.9748F, -0.0347F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -1.0306F, 0.0052F, -0.3054F));
        PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(90, 39).addBox(-2.8222F, -4.4785F, -0.0443F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -1.0271F, -0.1069F, -0.373F));
        PartDefinition cube_r8 = Head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 85).addBox(-1.9193F, -2.3027F, 0.1245F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.9257F, -0.5444F, -0.6801F));
        PartDefinition cube_r9 = Head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(88, 20).addBox(-0.3167F, -4.2219F, 0.5822F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.4633F, 0.0052F, -0.3054F));
        PartDefinition cube_r10 = Head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(84, 47).addBox(-0.638F, 2.6036F, -1.1774F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.6379F, 0.0052F, -0.3054F));
        PartDefinition cube_r11 = Head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(38, 88).addBox(-0.2514F, -5.0081F, 1.7593F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.0706F, 0.0052F, -0.3054F));
        PartDefinition cube_r12 = Head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(80, 83).addBox(-2.4573F, -1.9748F, -0.0347F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -1.0306F, -0.0052F, 0.3054F));
        PartDefinition cube_r13 = Head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(14, 50).addBox(1.9101F, -6.0075F, -1.0826F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.4631F, 1.584F, 0.844F, 0.2146F, -0.2492F, -0.1203F));
        PartDefinition cube_r14 = Head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 72).addBox(-3.9686F, -6.0075F, -0.1179F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 2.6F, -3.5F, 0.2182F, -0.3054F, 0.0F));
        PartDefinition cube_r15 = Head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(68, 73).addBox(-3.9101F, -6.0075F, -1.0826F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.4631F, 1.584F, 0.844F, 0.2146F, 0.2492F, 0.1203F));
        PartDefinition cube_r16 = Head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(44, 68).addBox(1.9686F, -6.0075F, -0.1179F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 2.6F, -3.5F, 0.2182F, 0.3054F, 0.0F));
        PartDefinition cube_r17 = Head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(82, 75).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.1603F, -5.45F, -4.5381F, 0.0114F, -0.0865F, 0.174F));
        PartDefinition cube_r18 = Head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(65, 95).addBox(0.5F, 1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.1313F, -6.55F, -4.5381F, -0.0114F, -0.0865F, 0.2187F));
        PartDefinition cube_r19 = Head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(95, 62).addBox(-1.5F, 1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.1313F, -6.55F, -4.5381F, -0.0114F, 0.0865F, -0.2187F));
        PartDefinition cube_r20 = Head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(82, 79).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.2313F, -5.45F, -4.5381F, 0.0114F, 0.0865F, -0.174F));
        PartDefinition cube_r21 = Head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 66).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0893F, -8.6F, -4.0F, 0.0F, 0.1745F, -0.3054F));
        PartDefinition cube_r22 = Head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(28, 18).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.3366F, -8.6F, -4.0F, 0.0F, -0.1745F, 0.3054F));
        PartDefinition cube_r23 = Head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(90, 83).addBox(-1.0F, -8.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.1764F, 0.1048F, -1.1083F, 0.3116F, -0.0403F, 0.1246F));
        PartDefinition cube_r24 = Head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(80, 90).addBox(-2.0F, -8.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0709F, 0.1048F, -1.1083F, 0.3116F, 0.0403F, -0.1246F));
        PartDefinition cube_r25 = Head.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 50).addBox(0.0F, -4.5F, -3.5F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.7872F, -9.0273F, 0.75F, -0.4363F, 0.0F, -0.2618F));
        PartDefinition cube_r26 = Head.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, -4.5F, -3.5F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.7872F, -9.0273F, 0.75F, -0.4363F, 0.0F, 0.2618F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
