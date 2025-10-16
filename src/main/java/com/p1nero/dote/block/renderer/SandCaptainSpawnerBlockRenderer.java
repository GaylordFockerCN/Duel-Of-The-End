package com.p1nero.dote.block.renderer;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.entity.spawner.SandCaptainSpawnerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SandCaptainSpawnerBlockRenderer extends GeoBlockRenderer<SandCaptainSpawnerBlockEntity> {
    public SandCaptainSpawnerBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "sand_captain_spawner")));
    }
}
