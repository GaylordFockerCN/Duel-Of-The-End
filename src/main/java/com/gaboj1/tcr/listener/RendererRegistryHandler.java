package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.init.TCRModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RendererRegistryHandler {
    @SubscribeEvent
    public static void onTileEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(TCRModBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(),);
    }
}
