package com.gaboj1.tcr.entity.client.sprite;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.biome1.SpriteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SpriteModel extends GeoModel<SpriteEntity> {
    @Override
    public ResourceLocation getModelResource(SpriteEntity spriteEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/jingling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpriteEntity spriteEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/entity/jingling.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpriteEntity spriteEntity) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/jingling.animation.json");
    }
}
