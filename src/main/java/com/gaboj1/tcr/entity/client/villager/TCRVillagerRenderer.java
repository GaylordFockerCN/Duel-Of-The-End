package com.gaboj1.tcr.entity.client.villager;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

public class TCRVillagerRenderer extends DynamicGeoEntityRenderer<TCRVillager> {
    private static final String LEFT_HAND = "LeftArm";
    private static final String RIGHT_HAND = "RightArm";
    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;
    public TCRVillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TCRVillagerModel());
//        if(this.animatable instanceof PastoralPlainVillagerElder){
            addRenderLayer(new BlockAndItemGeoLayer<>(this) {
                @Nullable
                @Override
                protected ItemStack getStackForBone(GeoBone bone, TCRVillager animatable) {
                    // Retrieve the items in the entity's hands for the relevant bone
                    return switch (bone.getName()) {
                        case LEFT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.mainHandItem : TCRVillagerRenderer.this.offhandItem;
                        case RIGHT_HAND -> animatable.isLeftHanded() ?
                                TCRVillagerRenderer.this.offhandItem : TCRVillagerRenderer.this.mainHandItem;
                        default -> null;
                    };
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
                protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, TCRVillager animatable,
                                                  MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                    if (stack == TCRVillagerRenderer.this.mainHandItem) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

//                        if (stack.getItem() instanceof ShieldItem)
                            poseStack.translate(0, 0.3, -0.45);
                    }
                    else if (stack == TCRVillagerRenderer.this.offhandItem) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

//                        if (stack.getItem() instanceof ShieldItem) {
                            poseStack.translate(0, 0.3, 0.45);
                            poseStack.mulPose(Axis.YP.rotationDegrees(180));
//                        }
                    }

                    super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
                }
            });
//        }
    }
    @Override
    public void preRender(PoseStack poseStack, TCRVillager animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.mainHandItem = animatable.getMainHandItem();
        this.offhandItem = animatable.getOffhandItem();
    }

}