package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.minecraft.world.entity.LivingEntity;
public class GoldenDragonArmorModel extends HumanoidModel<LivingEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "golden_dragon_armor"), "main");

	public GoldenDragonArmorModel(ModelPart root) {
        super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(64, 12).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(16, 72).addBox(-3.5F, -7.5F, 3.5F, 7.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 0).addBox(-3.5F, -8.5F, -3.5F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(28, 42).addBox(3.5F, -8.5F, -3.5F, 1.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(44, 18).addBox(-4.5F, -8.5F, -3.5F, 1.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 88).addBox(-2.7486F, -5.0081F, 1.7593F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.0706F, -0.0052F, 0.3054F));

		PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 14).addBox(-2.6833F, -4.2219F, 0.5822F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.4633F, -0.0052F, 0.3054F));

		PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(52, 78).addBox(-0.0807F, -2.3027F, 0.1245F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.9257F, 0.5444F, 0.6801F));

		PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(90, 33).addBox(0.8222F, -4.4785F, -0.0443F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -1.0271F, 0.1069F, 0.373F));

		PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(84, 7).addBox(-2.362F, 2.6036F, -1.1774F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -0.6379F, -0.0052F, 0.3054F));

		PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(84, 0).addBox(-0.5427F, -1.9748F, -0.0347F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -1.0306F, 0.0052F, -0.3054F));

		PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(90, 39).addBox(-2.8222F, -4.4785F, -0.0443F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -1.0271F, -0.1069F, -0.373F));

		PartDefinition cube_r8 = Head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 85).addBox(-1.9193F, -2.3027F, 0.1245F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.9257F, -0.5444F, -0.6801F));

		PartDefinition cube_r9 = Head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(88, 20).addBox(-0.3167F, -4.2219F, 0.5822F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.4633F, 0.0052F, -0.3054F));

		PartDefinition cube_r10 = Head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(84, 47).addBox(-0.638F, 2.6036F, -1.1774F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.6379F, 0.0052F, -0.3054F));

		PartDefinition cube_r11 = Head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(38, 88).addBox(-0.2514F, -5.0081F, 1.7593F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(-4.1451F, -11.621F, 2.2392F, -0.0706F, 0.0052F, -0.3054F));

		PartDefinition cube_r12 = Head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(80, 83).addBox(-2.4573F, -1.9748F, -0.0347F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.1451F, -11.621F, 2.2392F, -1.0306F, -0.0052F, 0.3054F));

		PartDefinition cube_r13 = Head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(14, 50).addBox(1.9101F, -6.0075F, -1.0826F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4631F, 1.584F, 0.844F, 0.2146F, -0.2492F, -0.1203F));

		PartDefinition cube_r14 = Head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 72).addBox(-3.9686F, -6.0075F, -0.1179F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.6F, -3.5F, 0.2182F, -0.3054F, 0.0F));

		PartDefinition cube_r15 = Head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(68, 73).addBox(-3.9101F, -6.0075F, -1.0826F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4631F, 1.584F, 0.844F, 0.2146F, 0.2492F, 0.1203F));

		PartDefinition cube_r16 = Head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(44, 68).addBox(1.9686F, -6.0075F, -0.1179F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.6F, -3.5F, 0.2182F, 0.3054F, 0.0F));

		PartDefinition cube_r17 = Head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(82, 75).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1603F, -5.45F, -4.5381F, 0.0114F, -0.0865F, 0.174F));

		PartDefinition cube_r18 = Head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(64, 94).addBox(0.5F, 1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1313F, -6.55F, -4.5381F, -0.0114F, -0.0865F, 0.4368F));

		PartDefinition cube_r19 = Head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(94, 61).addBox(-1.5F, 1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1313F, -6.55F, -4.5381F, -0.0114F, 0.0865F, -0.4368F));

		PartDefinition cube_r20 = Head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(82, 79).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2313F, -5.45F, -4.5381F, 0.0114F, 0.0865F, -0.174F));

		PartDefinition cube_r21 = Head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 66).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0893F, -8.6F, -4.0F, 0.0F, 0.1745F, -0.3054F));

		PartDefinition cube_r22 = Head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(28, 18).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3366F, -8.6F, -4.0F, 0.0F, -0.1745F, 0.3054F));

		PartDefinition cube_r23 = Head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(90, 83).addBox(-1.0F, -8.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1764F, 0.1048F, -1.1083F, 0.3116F, -0.0403F, 0.1246F));

		PartDefinition cube_r24 = Head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(80, 90).addBox(-2.0F, -8.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0709F, 0.1048F, -1.1083F, 0.3116F, 0.0403F, -0.1246F));

		PartDefinition cube_r25 = Head.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 50).addBox(0.0F, -4.5F, -3.5F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7872F, -9.0273F, 0.75F, -0.4363F, 0.0F, -0.2618F));

		PartDefinition cube_r26 = Head.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, -4.5F, -3.5F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7872F, -9.0273F, 0.75F, -0.4363F, 0.0F, 0.2618F));

		PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r27 = Body.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(20, 95).addBox(0.7491F, 0.1849F, -2.0559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.7946F, 0.5147F, 0.8984F));

		PartDefinition cube_r28 = Body.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(82, 94).addBox(-0.2929F, -0.7625F, -1.9139F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.696F, 0.5868F, 0.8986F));

		PartDefinition cube_r29 = Body.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(94, 65).addBox(-1.2044F, -1.8114F, -2.1455F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.5457F, 0.6891F, 0.8829F));

		PartDefinition cube_r30 = Body.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(94, 4).addBox(-0.4611F, -0.6464F, -0.5503F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.3624F, 0.3001F, 0.7514F));

		PartDefinition cube_r31 = Body.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(94, 0).addBox(-1.4505F, -1.6311F, -0.8023F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.2216F, 0.3863F, 0.778F));

		PartDefinition cube_r32 = Body.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(94, 8).addBox(-1.5322F, 0.341F, -0.7067F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.4604F, -0.2423F, -0.7259F));

		PartDefinition cube_r33 = Body.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(94, 45).addBox(0.4505F, -1.6311F, -0.8023F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.2216F, -0.3863F, -0.778F));

		PartDefinition cube_r34 = Body.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(94, 49).addBox(-0.5389F, -0.6464F, -0.5503F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.3624F, -0.3001F, -0.7514F));

		PartDefinition cube_r35 = Body.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(94, 68).addBox(0.2044F, -1.8114F, -2.1455F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.5457F, -0.6891F, -0.8829F));

		PartDefinition cube_r36 = Body.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(0, 96).addBox(-0.7071F, -0.7625F, -1.9139F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.696F, -0.5868F, -0.8986F));

		PartDefinition cube_r37 = Body.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(6, 96).addBox(-1.7491F, 0.1849F, -2.0559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0417F, -21.8865F, -2.5012F, 0.7946F, -0.5147F, -0.8984F));

		PartDefinition cube_r38 = Body.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(90, 93).addBox(0.5322F, 0.341F, -0.7067F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0417F, -21.8865F, -2.5012F, 0.4604F, 0.2423F, 0.7259F));

		PartDefinition cube_r39 = Body.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(62, 16).addBox(-4.5F, -6.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.9171F, -10.9434F, 0.001F, -3.1416F, 0.0F, 0.3927F));

		PartDefinition cube_r40 = Body.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(30, 59).addBox(2.5F, -6.5F, -2.5F, 2.0F, 8.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.9171F, -10.9434F, 0.001F, -3.1416F, 0.0F, -0.3927F));

		PartDefinition cube_r41 = Body.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 42).addBox(-4.5F, -1.5F, -2.5F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.55F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r42 = Body.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -1.5F, -7.0F, 9.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -17.75F, -4.5F, 3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r43 = Body.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(20, 91).addBox(3.0F, -9.5F, -7.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 92).addBox(-2.0F, -9.5F, -7.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -10.5194F, -3.3902F, -2.7053F, 0.0F, 0.0F));

		PartDefinition cube_r44 = Body.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(60, 73).addBox(-7.0F, -9.5F, -7.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(32, 72).addBox(-2.0F, -9.5F, -7.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -16.6332F, -3.2649F, -2.9671F, 0.0F, 0.0F));

		PartDefinition cube_r45 = Body.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -5.5F, -7.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.5F, -5.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 24).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition cube_r46 = RightArm.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(28, 93).addBox(-1.4171F, -1.3638F, -1.7136F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, -0.0767F, 0.0516F, -1.0324F));

		PartDefinition cube_r47 = RightArm.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(36, 93).addBox(-0.5109F, -0.518F, -1.4905F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, 0.073F, -0.0382F, -1.0314F));

		PartDefinition cube_r48 = RightArm.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(94, 71).addBox(0.6189F, 0.5591F, -1.1757F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.4518F, 0.2075F, -0.9694F));

		PartDefinition cube_r49 = RightArm.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(76, 94).addBox(-0.4865F, -0.4843F, -1.0102F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.3472F, 0.2754F, -0.9945F));

		PartDefinition cube_r50 = RightArm.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(76, 26).addBox(-1.4817F, -1.678F, -1.2306F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, -4.8024F, -1.2128F, 0.1905F, 0.3671F, -1.0441F));

		PartDefinition cube_r51 = RightArm.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(48, 92).addBox(0.4232F, 0.3706F, -1.6502F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.1197F, -4.9998F, 0.6784F, 0.1783F, -0.1006F, -1.0387F));

		PartDefinition cube_r52 = RightArm.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(62, 55).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(-1.576F, -4.0001F, -0.9168F, 0.3491F, 0.0F, -0.2182F));

		PartDefinition cube_r53 = RightArm.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(62, 61).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, -2.6F, 2.5F, -0.3491F, 0.0F, -0.4451F));

		PartDefinition cube_r54 = RightArm.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(64, 0).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(-2.4472F, -2.9087F, -0.9168F, 0.3491F, 0.0F, -0.4451F));

		PartDefinition cube_r55 = RightArm.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(62, 29).addBox(-3.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5019F, -3.6662F, 2.5F, -0.3491F, 0.0F, -0.2182F));

		PartDefinition cube_r56 = RightArm.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(60, 89).addBox(-2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, 7.25F, -1.0568F, -0.7854F, 0.0F, -0.2094F));

		PartDefinition cube_r57 = RightArm.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(90, 87).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2709F, 5.245F, 0.0039F, -0.7244F, 0.004F, -0.5115F));

		PartDefinition cube_r58 = RightArm.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(70, 47).addBox(-2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, 7.25F, 1.0645F, 0.7854F, 0.0F, -0.2094F));

		PartDefinition cube_r59 = RightArm.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(40, 78).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.55F, 3.7F, -1.7376F, 0.0F, -0.3927F, 0.1571F));

		PartDefinition cube_r60 = RightArm.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(76, 16).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.55F, 3.7F, 1.9F, 0.0F, 0.3927F, 0.1571F));

		PartDefinition cube_r61 = RightArm.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(40, 75).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1495F, 10.4012F, 1.8616F, 0.2433F, 0.0232F, -0.4859F));

		PartDefinition cube_r62 = RightArm.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(0, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8994F, 11.6008F, 2.2572F, 0.2167F, 0.1138F, -0.869F));

		PartDefinition cube_r63 = RightArm.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(40, 22).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8994F, 11.6008F, -2.2572F, -0.2167F, -0.1138F, -0.869F));

		PartDefinition cube_r64 = RightArm.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(40, 72).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1495F, 10.4012F, -1.8616F, -0.2433F, -0.0232F, -0.4859F));

		PartDefinition cube_r65 = RightArm.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(12, 85).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.45F, 10.0F, 0.2F, -0.2443F, 0.0F, -0.3927F));

		PartDefinition cube_r66 = RightArm.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(28, 84).addBox(-0.5F, -2.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.45F, 10.0F, -0.2F, 0.2443F, 0.0F, -0.3927F));

		PartDefinition cube_r67 = RightArm.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(82, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.582F, 11.9567F, 0.0F, 0.0F, 0.0F, -0.9163F));

		PartDefinition cube_r68 = RightArm.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(48, 88).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, 10.5F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r69 = RightArm.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(12, 88).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-4.0F, 9.2F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(30, 0).addBox(8.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition cube_r70 = LeftArm.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(94, 57).addBox(0.4171F, -1.3638F, -1.7136F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(13.1197F, -4.9998F, 0.6784F, -0.0767F, -0.0516F, 1.0324F));

		PartDefinition cube_r71 = LeftArm.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(56, 94).addBox(-0.4891F, -0.518F, -1.4905F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(13.1197F, -4.9998F, 0.6784F, 0.073F, 0.0382F, 1.0314F));

		PartDefinition cube_r72 = LeftArm.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(96, 29).addBox(-1.6189F, 0.5591F, -1.1757F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.9583F, -4.8024F, -1.2128F, 0.4518F, -0.2075F, 0.9694F));

		PartDefinition cube_r73 = LeftArm.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(96, 26).addBox(-0.5135F, -0.4843F, -1.0102F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.9583F, -4.8024F, -1.2128F, 0.3472F, -0.2754F, 0.9945F));

		PartDefinition cube_r74 = LeftArm.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(70, 94).addBox(0.4817F, -1.678F, -1.2306F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.9583F, -4.8024F, -1.2128F, 0.1905F, -0.3671F, 1.0441F));

		PartDefinition cube_r75 = LeftArm.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(94, 53).addBox(-1.4232F, 0.3706F, -1.6502F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(13.1197F, -4.9998F, 0.6784F, 0.1783F, 0.1006F, 1.0387F));

		PartDefinition cube_r76 = LeftArm.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(70, 41).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(11.576F, -4.0001F, -0.9168F, 0.3491F, 0.0F, 0.2182F));

		PartDefinition cube_r77 = LeftArm.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(70, 35).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.3F, -2.6F, 2.5F, -0.3491F, 0.0F, 0.4451F));

		PartDefinition cube_r78 = LeftArm.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(62, 67).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.0001F)), PartPose.offsetAndRotation(12.4472F, -2.9087F, -0.9168F, 0.3491F, 0.0F, 0.4451F));

		PartDefinition cube_r79 = LeftArm.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(64, 6).addBox(-2.5F, -0.5F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5019F, -3.6662F, 2.5F, -0.3491F, 0.0F, 0.2182F));

		PartDefinition cube_r80 = LeftArm.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(70, 89).addBox(0.5F, -1.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.8F, 7.25F, -1.0568F, -0.7854F, 0.0F, 0.2094F));

		PartDefinition cube_r81 = LeftArm.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(12, 91).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.2709F, 5.245F, 0.0039F, -0.7244F, -0.004F, 0.5115F));

		PartDefinition cube_r82 = LeftArm.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(82, 26).addBox(0.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.8F, 7.25F, 1.0645F, 0.7854F, 0.0F, 0.2094F));

		PartDefinition cube_r83 = LeftArm.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(0, 82).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(13.55F, 3.7F, -1.7376F, 0.0F, 0.3927F, -0.1571F));

		PartDefinition cube_r84 = LeftArm.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(16, 81).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(13.55F, 3.7F, 1.9F, 0.0F, -0.3927F, -0.1571F));

		PartDefinition cube_r85 = LeftArm.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(12, 82).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.1495F, 10.4012F, 1.8616F, 0.2433F, -0.0232F, 0.4859F));

		PartDefinition cube_r86 = LeftArm.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(8, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.8994F, 11.6008F, 2.2572F, 0.2167F, -0.1138F, 0.869F));

		PartDefinition cube_r87 = LeftArm.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(4, 70).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.8994F, 11.6008F, -2.2572F, -0.2167F, 0.1138F, 0.869F));

		PartDefinition cube_r88 = LeftArm.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(28, 81).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.1495F, 10.4012F, -1.8616F, -0.2433F, 0.0232F, 0.4859F));

		PartDefinition cube_r89 = LeftArm.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(48, 96).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(13.45F, 10.0F, 0.2F, -0.2443F, 0.0F, 0.3927F));

		PartDefinition cube_r90 = LeftArm.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(44, 96).addBox(-0.5F, -2.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(13.45F, 10.0F, -0.2F, 0.2443F, 0.0F, 0.3927F));

		PartDefinition cube_r91 = LeftArm.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(84, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.582F, 11.9567F, 0.0F, 0.0F, 0.0F, 0.9163F));

		PartDefinition cube_r92 = LeftArm.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(44, 93).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.6F, 10.5F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r93 = LeftArm.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(8, 92).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(14.0F, 9.2F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(14, 59).addBox(-5.8F, 1.025F, -1.9629F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(46, 45).addBox(-7.0F, 8.025F, -2.9629F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(68, 82).addBox(-1.0F, 7.025F, -2.9629F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(82, 68).addBox(-7.0F, 7.025F, -2.9629F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(12, 40).addBox(-7.0F, 6.025F, -2.9629F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(50, 16).addBox(-7.0F, 6.025F, 3.0371F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 55).addBox(1.8F, 1.025F, -1.9629F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(46, 35).addBox(1.0F, 8.025F, -2.9629F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(82, 54).addBox(1.0F, 7.025F, -2.9629F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(82, 61).addBox(7.0F, 7.275F, -2.9629F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(28, 22).addBox(1.0F, 6.025F, -2.9629F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(1.0F, 6.025F, 3.0371F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

}