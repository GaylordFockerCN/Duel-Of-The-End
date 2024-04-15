package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.gaboj1.tcr.block.entity.spawner.YggdrasilSpawnerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YggdrasilBlockModel extends GeoModel<YggdrasilSpawnerBlockEntity> {
    @Override
    public ResourceLocation getModelResource(YggdrasilSpawnerBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "geo/yggdrasil_spawn_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YggdrasilSpawnerBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/yggdrasil_spawn_block.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YggdrasilSpawnerBlockEntity animatable) {
        return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "animations/yggdrasil_spawn_block.animation.json");
    }
}