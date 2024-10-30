package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.entity.custom.villager.biome2.branch.MiaoYin;
import com.gaboj1.tcr.item.TCRItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

public class TCRVillagerRenderer extends DynamicGeoEntityRenderer<TCRVillager> {
    private static final String LEFT_HAND = "LeftArm";
    private static final String RIGHT_HAND = "RightArm";
    protected ItemStack elderMainHandItem = TCRItems.ELDER_STAFF.get().getDefaultInstance();
    protected ItemStack miaoYinMainHandItem = TCRItems.PI_PA.get().getDefaultInstance();
    protected ItemStack offhandItem;
    public TCRVillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TCRVillagerModel());
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, TCRVillager animatable) {
                if(animatable instanceof MiaoYin){
                    return switch (bone.getName()) {
                        case LEFT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.miaoYinMainHandItem : TCRVillagerRenderer.this.offhandItem;
                        case RIGHT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.offhandItem : TCRVillagerRenderer.this.miaoYinMainHandItem;
                        default -> null;
                    };
                }
                // Retrieve the items in the entity's hands for the relevant bone
                if(animatable instanceof PastoralPlainVillagerElder){
                    return switch (bone.getName()) {
                        case LEFT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.elderMainHandItem : TCRVillagerRenderer.this.offhandItem;
                        case RIGHT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.offhandItem : TCRVillagerRenderer.this.elderMainHandItem;
                        default -> null;
                    };
                }
                return null;
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, TCRVillager animatable) {
                // Apply the camera transform for the given hand
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    default -> ItemDisplayContext.NONE;
                };
            }

            // Do some quick render modifications depending on what the item is
            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, TCRVillager villager, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == TCRVillagerRenderer.this.elderMainHandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                    poseStack.translate(0, 0.3, -0.45);
                } else if (stack == TCRVillagerRenderer.this.offhandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                    poseStack.translate(0, 0.3, 0.45);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                }

                if(stack == TCRVillagerRenderer.this.miaoYinMainHandItem){
                    poseStack.mulPose(Axis.XP.rotationDegrees(-50f));
                    poseStack.translate(0, 0, -1.1);
                    if(villager instanceof MiaoYin miaoYin && miaoYin.isSitting()){
                        poseStack.mulPose(Axis.XP.rotationDegrees(30));
                        poseStack.mulPose(Axis.YP.rotationDegrees(50));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, villager, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

}