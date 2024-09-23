package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.PortalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class PortalBlockRenderer extends GeoBlockRenderer<PortalBlockEntity> {
    public PortalBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "portal_block")){
//            @Override
//            public ResourceLocation getTextureResource(PortalBlockEntity portalBlockEntity) {
//                return new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/block/portal_block" + portalBlockEntity.getId());
//            }
        });
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
