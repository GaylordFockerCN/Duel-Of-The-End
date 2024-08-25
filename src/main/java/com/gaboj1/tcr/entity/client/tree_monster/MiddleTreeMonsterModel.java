package com.gaboj1.tcr.entity.client.tree_monster;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.MiddleTreeMonsterEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MiddleTreeMonsterModel extends GeoModel<MiddleTreeMonsterEntity> {
    @Override
    public ResourceLocation getModelResource(MiddleTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/middle_tree_monster.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MiddleTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/middle_tree_monster.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MiddleTreeMonsterEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/middle_tree_monster.animation.json");
    }

    @Override
    public void setCustomAnimations(MiddleTreeMonsterEntity animatable, long instanceId, AnimationState<MiddleTreeMonsterEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
