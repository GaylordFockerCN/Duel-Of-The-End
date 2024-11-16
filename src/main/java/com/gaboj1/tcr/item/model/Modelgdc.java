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
public class Modelgdc<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "modelgdc"), "main");
    public final ModelPart Body;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;

    public Modelgdc(ModelPart root) {
        this.Body = root.getChild("Body");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 95).addBox(0.7491F, 0.1849F, -2.0559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.7946F, 0.5147F, 0.8984F));

        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(82, 94).addBox(-0.2929F, -0.7625F, -1.9139F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.696F, 0.5868F, 0.8986F));

        PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(94, 65).addBox(-1.2044F, -1.8114F, -2.1455F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.5457F, 0.6891F, 0.8829F));

        PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(94, 4).addBox(-0.4611F, -0.6464F, -0.5503F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.3624F, 0.3001F, 0.7514F));

        PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(94, 0).addBox(-1.4505F, -1.6311F, -0.8023F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.2216F, 0.3863F, 0.778F));

        PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(94, 8).addBox(-1.5322F, 0.341F, -0.7067F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.4604F, -0.2423F, -0.7259F));

        PartDefinition cube_r7 = Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(94, 45).addBox(0.4505F, -1.6311F, -0.8023F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.2216F, -0.3863F, -0.778F));

        PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(94, 49).addBox(-0.5389F, -0.6464F, -0.5503F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.3624F, -0.3001F, -0.7514F));

        PartDefinition cube_r9 = Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(94, 68).addBox(0.2044F, -1.8114F, -2.1455F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.5457F, -0.6891F, -0.8829F));

        PartDefinition cube_r10 = Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 96).addBox(-0.7071F, -0.7625F, -1.9139F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.696F, -0.5868F, -0.8986F));

        PartDefinition cube_r11 = Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(6, 96).addBox(-1.7491F, 0.1849F, -2.0559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, 2.1135F, -2.5012F, 0.7946F, -0.5147F, -0.8984F));

        PartDefinition cube_r12 = Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(90, 93).addBox(0.5322F, 0.341F, -0.7067F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, 2.1135F, -2.5012F, 0.4604F, 0.2423F, 0.7259F));

        PartDefinition cube_r13 = Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(62, 16).addBox(-4.5F, -6.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.9171F, 13.0566F, 0.001F, -3.1416F, 0.0F, 0.3927F));

        PartDefinition cube_r14 = Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(30, 59).addBox(2.5F, -6.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.9171F, 13.0566F, 0.001F, -3.1416F, 0.0F, -0.3927F));

        PartDefinition cube_r15 = Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 42).addBox(-4.5F, -1.5F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.45F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r16 = Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -1.5F, -7.0F, 9.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 6.25F, -4.5F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r17 = Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(20, 91).addBox(3.0F, -9.5F, -7.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(-2.0F, -9.5F, -7.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 13.4806F, -3.3902F, -2.7053F, 0.0F, 0.0F));

        PartDefinition cube_r18 = Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(60, 73).addBox(-7.0F, -9.5F, -7.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 72).addBox(-2.0F, -9.5F, -7.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 7.3668F, -3.2649F, -2.9671F, 0.0F, 0.0F));

        PartDefinition cube_r19 = Body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -5.5F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-3.2F, -5.5F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 6.5F, -5.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r20 = Body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -5.5F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.5F, -4.7F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r21 = Body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -5.5F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.5F, -5.3F, 3.1416F, 0.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(24, 24).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r22 = RightArm.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(28, 93).addBox(-1.4171F, -1.3638F, -1.7136F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, -0.0767F, 0.0516F, -1.0324F));

        PartDefinition cube_r23 = RightArm.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(36, 93).addBox(-0.5109F, -0.518F, -1.4905F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, 0.073F, -0.0382F, -1.0314F));

        PartDefinition cube_r24 = RightArm.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(94, 71).addBox(0.6189F, 0.5591F, -1.1757F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.4518F, 0.2075F, -0.9694F));

        PartDefinition cube_r25 = RightArm.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(76, 94).addBox(-0.4865F, -0.4843F, -1.0102F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.3472F, 0.2754F, -0.9945F));

        PartDefinition cube_r26 = RightArm.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(76, 26).addBox(-1.4817F, -1.678F, -1.2306F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.1905F, 0.3671F, -1.0441F));

        PartDefinition cube_r27 = RightArm.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(48, 92).addBox(0.4232F, 0.3706F, -1.6502F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, 0.1783F, -0.1006F, -1.0387F));

        PartDefinition cube_r28 = RightArm.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(62, 55).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(-1.576F, -4.0001F, -0.9168F, 0.3491F, 0.0F, -0.2182F));

        PartDefinition cube_r29 = RightArm.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(62, 61).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, -2.6F, 2.5F, -0.3491F, 0.0F, -0.4451F));

        PartDefinition cube_r30 = RightArm.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(64, 0).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(-2.4472F, -2.9087F, -0.9168F, 0.3491F, 0.0F, -0.4451F));

        PartDefinition cube_r31 = RightArm.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(62, 29).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5019F, -3.6662F, 2.5F, -0.3491F, 0.0F, -0.2182F));

        PartDefinition cube_r32 = RightArm.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(60, 89).addBox(-2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, 7.25F, -1.0568F, -0.7854F, 0.0F, -0.2094F));

        PartDefinition cube_r33 = RightArm.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(90, 87).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2709F, 5.245F, 0.0039F, -0.7244F, 0.004F, -0.5115F));

        PartDefinition cube_r34 = RightArm.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(70, 47).addBox(-2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, 7.25F, 1.0645F, 0.7854F, 0.0F, -0.2094F));

        PartDefinition cube_r35 = RightArm.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(40, 78).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.55F, 3.7F, -1.7376F, 0.0F, -0.3927F, 0.1571F));

        PartDefinition cube_r36 = RightArm.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(76, 16).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.55F, 3.7F, 1.9F, 0.0F, 0.3927F, 0.1571F));

        PartDefinition cube_r37 = RightArm.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(40, 75).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1495F, 10.4012F, 1.8616F, 0.2433F, 0.0232F, -0.4859F));

        PartDefinition cube_r38 = RightArm.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(0, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8994F, 11.6008F, 2.2572F, 0.2167F, 0.1138F, -0.869F));

        PartDefinition cube_r39 = RightArm.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(40, 22).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8994F, 11.6008F, -2.2572F, -0.2167F, -0.1138F, -0.869F));

        PartDefinition cube_r40 = RightArm.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(40, 72).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1495F, 10.4012F, -1.8616F, -0.2433F, -0.0232F, -0.4859F));

        PartDefinition cube_r41 = RightArm.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(12, 85).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.45F, 10.0F, 0.2F, -0.2443F, 0.0F, -0.3927F));

        PartDefinition cube_r42 = RightArm.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(28, 84).addBox(-0.5F, -2.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.45F, 10.0F, -0.2F, 0.2443F, 0.0F, -0.3927F));

        PartDefinition cube_r43 = RightArm.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(82, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.582F, 11.9567F, 0.0F, 0.0F, 0.0F, -0.9163F));

        PartDefinition cube_r44 = RightArm.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(48, 88).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, 10.5F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r45 = RightArm.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(12, 88).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0F, 9.2F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(30, 0).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.2094F, 0.0F, 0.0F));

        PartDefinition cube_r46 = LeftArm.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(94, 57).addBox(0.4171F, -1.3638F, -1.7136F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.1197F, -4.9998F, 0.6784F, -0.0767F, -0.0516F, 1.0324F));

        PartDefinition cube_r47 = LeftArm.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(56, 94).addBox(-0.4891F, -0.518F, -1.4905F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.1197F, -4.9998F, 0.6784F, 0.073F, 0.0382F, 1.0314F));

        PartDefinition cube_r48 = LeftArm.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(96, 29).addBox(-1.6189F, 0.5591F, -1.1757F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9583F, -4.8024F, -1.2128F, 0.4518F, -0.2075F, 0.9694F));

        PartDefinition cube_r49 = LeftArm.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(96, 26).addBox(-0.5135F, -0.4843F, -1.0102F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9583F, -4.8024F, -1.2128F, 0.3472F, -0.2754F, 0.9945F));

        PartDefinition cube_r50 = LeftArm.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(70, 94).addBox(0.4817F, -1.678F, -1.2306F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9583F, -4.8024F, -1.2128F, 0.1905F, -0.3671F, 1.0441F));

        PartDefinition cube_r51 = LeftArm.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(94, 53).addBox(-1.4232F, 0.3706F, -1.6502F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.1197F, -4.9998F, 0.6784F, 0.1783F, 0.1006F, 1.0387F));

        PartDefinition cube_r52 = LeftArm.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(70, 41).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(1.576F, -4.0001F, -0.9168F, 0.3491F, 0.0F, 0.2182F));

        PartDefinition cube_r53 = LeftArm.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(70, 35).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, -2.6F, 2.5F, -0.3491F, 0.0F, 0.4451F));

        PartDefinition cube_r54 = LeftArm.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(62, 67).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(2.4472F, -2.9087F, -0.9168F, 0.3491F, 0.0F, 0.4451F));

        PartDefinition cube_r55 = LeftArm.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(64, 6).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5019F, -3.6662F, 2.5F, -0.3491F, 0.0F, 0.2182F));

        PartDefinition cube_r56 = LeftArm.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(70, 89).addBox(0.5F, -1.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, 7.25F, -1.0568F, -0.7854F, 0.0F, 0.2094F));

        PartDefinition cube_r57 = LeftArm.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(12, 91).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2709F, 5.245F, 0.0039F, -0.7244F, -0.004F, 0.5115F));

        PartDefinition cube_r58 = LeftArm.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(82, 26).addBox(0.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, 7.25F, 1.0645F, 0.7854F, 0.0F, 0.2094F));

        PartDefinition cube_r59 = LeftArm.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(0, 82).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(3.55F, 3.7F, -1.7376F, 0.0F, 0.3927F, -0.1571F));

        PartDefinition cube_r60 = LeftArm.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(16, 81).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(3.55F, 3.7F, 1.9F, 0.0F, -0.3927F, -0.1571F));

        PartDefinition cube_r61 = LeftArm.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(12, 82).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1495F, 10.4012F, 1.8616F, 0.2433F, -0.0232F, 0.4859F));

        PartDefinition cube_r62 = LeftArm.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(8, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8994F, 11.6008F, 2.2572F, 0.2167F, -0.1138F, 0.869F));

        PartDefinition cube_r63 = LeftArm.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(4, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8994F, 11.6008F, -2.2572F, -0.2167F, 0.1138F, 0.869F));

        PartDefinition cube_r64 = LeftArm.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(28, 81).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1495F, 10.4012F, -1.8616F, -0.2433F, 0.0232F, 0.4859F));

        PartDefinition cube_r65 = LeftArm.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(48, 96).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.45F, 10.0F, 0.2F, -0.2443F, 0.0F, 0.3927F));

        PartDefinition cube_r66 = LeftArm.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(44, 96).addBox(-0.5F, -2.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.45F, 10.0F, -0.2F, 0.2443F, 0.0F, 0.3927F));

        PartDefinition cube_r67 = LeftArm.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(84, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.582F, 11.9567F, 0.0F, 0.0F, 0.0F, 0.9163F));

        PartDefinition cube_r68 = LeftArm.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(44, 93).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6F, 10.5F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r69 = LeftArm.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(8, 92).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0F, 9.2F, 0.0F, 0.0F, 0.0F, 0.3491F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
