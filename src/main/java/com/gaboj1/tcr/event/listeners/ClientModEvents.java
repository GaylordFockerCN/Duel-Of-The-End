package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.block.DOTEBlockEntities;
import com.gaboj1.tcr.block.renderer.BetterStructureBlockRenderer;
import com.gaboj1.tcr.block.renderer.PortalBedRenderer;
import com.gaboj1.tcr.entity.DOTEEntities;
import com.gaboj1.tcr.entity.client.SenbaiRenderer;
import com.gaboj1.tcr.entity.client.TCRFakePlayerRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(DOTEEntities.FAKE_PLAYER.get(), TCRFakePlayerRenderer::new);
        EntityRenderers.register(DOTEEntities.SENBAI_DEVIL.get(), SenbaiRenderer::new);
    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(DOTEBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PortalBedRenderer.HEAD, PortalBedRenderer::createHeadLayer);
        event.registerLayerDefinition(PortalBedRenderer.FOOT, PortalBedRenderer::createFootLayer);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderPatched(PatchedRenderersEvent.Add event) {
        EntityRendererProvider.Context context = event.getContext();
        event.addPatchedEntityRenderer(DOTEEntities.SENBAI_DEVIL.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.SKELETON, context, entityType).initLayerLast(context, entityType));
    }

}