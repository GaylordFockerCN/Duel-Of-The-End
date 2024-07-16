package com.gaboj1.tcr.entity.client.boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class YggdrasilModel extends DefaultedEntityGeoModel<YggdrasilEntity> {
    public YggdrasilModel(){
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "pastoral_plain_villager"), true);
    }

    @Override
    public void setCustomAnimations(YggdrasilEntity animatable, long instanceId, AnimationState<YggdrasilEntity> animationState) {

//        CoreGeoBone head = getAnimationProcessor().getBone("AllHead");
//
//        if (head != null) {
//            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//
//            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
//            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
//        }
    }

    @Override
    public ResourceLocation getModelResource(YggdrasilEntity entity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/entity/boss/yggdrasil"+entity.getEntityData().get(YggdrasilEntity.STATE)+".json");
    }

    @Override
    public ResourceLocation getTextureResource(YggdrasilEntity entity) {
        if(entity.getEntityData().get(YggdrasilEntity.STATE) == 0){
            return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boss/yggdrasil.png");
        }else {
            return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/boss/_yggdrasil.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(YggdrasilEntity entity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/entity/boss/yggdrasil.json");
    }
}
