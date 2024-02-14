package com.gaboj1.tcr;

import com.gaboj1.tcr.block.renderer.BetterStructureBlockRenderer;
import com.gaboj1.tcr.block.renderer.PortalBedRenderer;
import com.gaboj1.tcr.entity.client.MiddleTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.SmallTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.TreeGuardianRenderer;
import com.gaboj1.tcr.init.*;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.TCRStructurePlacementTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheCasketOfReveriesMod.MOD_ID)
public class TheCasketOfReveriesMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "the_casket_of_reveries";

    public static final String REGISTRY_NAMESPACE = "tcr";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);


    public TheCasketOfReveriesMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TCRModSounds.REGISTRY.register(bus);
        TCRModItems.REGISTRY.register(bus);
        TCRModBlocks.REGISTRY.register(bus);
        TCRModBlockEntities.REGISTRY.register(bus);
        TCRModEntities.REGISTRY.register(bus);
        TCRModItemTabs.REGISTRY.register(bus);
        TCRStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);
        bus.addListener(this::setRegistriesForDatapack);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private static int messageID = 0;
    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.getId(), TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event){

    }

    public void setRegistriesForDatapack(DataPackRegistryEvent.NewRegistry event) {
    }

    public void registerExtraStuff(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
            Registry.register(BuiltInRegistries.BIOME_SOURCE, TheCasketOfReveriesMod.prefix("tcr_biomes"), TCRBiomeProvider.TCR_CODEC);

        }else if (evt.getRegistryKey().equals(Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, TheCasketOfReveriesMod.prefix("structure_locating_wrapper"), TCRChunkGenerator.CODEC);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            EntityRenderers.register(TCRModEntities.MIDDLE_TREE_MONSTER.get(), MiddleTreeMonsterRenderer::new);
            EntityRenderers.register(TCRModEntities.TREE_GUARDIAN.get(), TreeGuardianRenderer::new);
            EntityRenderers.register(TCRModEntities.SMALL_TREE_MONSTER.get(), SmallTreeMonsterRenderer::new);

            BlockEntityRenderers.register(TCRModBlockEntities.PORTAL_BED.get(), PortalBedRenderer::new);
        }

        @SubscribeEvent
        public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(TCRModBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
//            event.registerBlockEntityRenderer(TCRModBlockEntities.PORTAL_BED.get(), PortalBedRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(PortalBedRenderer.HEAD, PortalBedRenderer::createHeadLayer);
            event.registerLayerDefinition(PortalBedRenderer.FOOT, PortalBedRenderer::createFootLayer);
        }

    }


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
    }

}
