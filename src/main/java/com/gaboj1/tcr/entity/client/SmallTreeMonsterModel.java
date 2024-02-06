package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.SmallTreeMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SmallTreeMonsterModel extends GeoModel<SmallTreeMonsterEntity> {
    @Override
    public ResourceLocation getModelResource(SmallTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/small_tree_monster.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SmallTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/small_tree_monster.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SmallTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/small_tree_monster.animation.json");
    }

    @Override
    public void setCustomAnimations(SmallTreeMonsterEntity animatable, long instanceId, AnimationState<SmallTreeMonsterEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
