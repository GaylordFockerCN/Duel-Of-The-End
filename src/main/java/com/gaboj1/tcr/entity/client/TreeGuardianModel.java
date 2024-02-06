package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.TreeGuardianEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TreeGuardianModel extends GeoModel<TreeGuardianEntity> {
    //关联模型文件
    @Override
    public ResourceLocation getModelResource(TreeGuardianEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/tree_guardian.geo.json");
    }

    //关联贴图文件
    @Override
    public ResourceLocation getTextureResource(TreeGuardianEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/tree_guardian.png");
    }

    //关联动画文件
    @Override
    public ResourceLocation getAnimationResource(TreeGuardianEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/tree_guardian.animation.json");
    }

    @Override
    public void setCustomAnimations(TreeGuardianEntity animatable, long instanceId, AnimationState<TreeGuardianEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
