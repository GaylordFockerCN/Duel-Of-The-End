package com.gaboj1.tcr;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING = BUILDER
            .translation("config.tcr.enable_scaling")
            .define("enableScaling", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj){
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){

    }
}
