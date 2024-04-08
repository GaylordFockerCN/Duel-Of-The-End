package com.gaboj1.tcr;
import com.gaboj1.tcr.block.entity.client.PortalBlockRenderer;
import com.gaboj1.tcr.block.renderer.BetterStructureBlockRenderer;
import com.gaboj1.tcr.block.renderer.PortalBedRenderer;
import com.gaboj1.tcr.entity.client.boss.YggdrasilRenderer;
import com.gaboj1.tcr.entity.client.boss.tree_clawRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.JellyCatRenderer;
import com.gaboj1.tcr.entity.client.tree_monster.MiddleTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.tree_monster.SmallTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.tree_monster.TreeGuardianRenderer;
import com.gaboj1.tcr.entity.client.villager.TCRVillagerRenderer;
import com.gaboj1.tcr.entity.custom.Yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.dreamspirit.JellyCat;
import com.gaboj1.tcr.entity.custom.tree_monsters.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.SmallTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.tree_monsters.TreeGuardianEntity;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager1;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.init.*;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.TCRStructurePlacementTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
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
        TCRModVillagers.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);
        bus.addListener(this::setRegistriesForDatapack);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    private static int messageID = 0;
    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        TCRPacketHandler.register();

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
//            Registry.register(BuiltInRegistries.BIOME_SOURCE, TheCasketOfReveriesMod.prefix("tcr_biomes"), ModBiomeProvider.TCR_CODEC);
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
            EntityRenderers.register(TCRModEntities.JELLY_CAT.get(), JellyCatRenderer::new);

            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER1.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(), TCRVillagerRenderer::new);

            EntityRenderers.register(TCRModEntities.YGGDRASIL.get(), YggdrasilRenderer::new);
            EntityRenderers.register(TCRModEntities.TREE_CLAW.get(), tree_clawRenderer::new);

            BlockEntityRenderers.register(TCRModBlockEntities.PORTAL_BED.get(), PortalBedRenderer::new);
        }

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(TCRModEntities.SMALL_TREE_MONSTER.get(), SmallTreeMonsterEntity.setAttributes());
            event.put(TCRModEntities.TREE_GUARDIAN.get(), TreeGuardianEntity.setAttributes());//设置生物属性功能在此被调用
            event.put(TCRModEntities.MIDDLE_TREE_MONSTER.get(), MiddleTreeMonsterEntity.setAttributes());
            event.put(TCRModEntities.JELLY_CAT.get(), JellyCat.setAttributes());
            event.put(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(),PastoralPlainVillager.setAttributes());
            event.put(TCRModEntities.PASTORAL_PLAIN_VILLAGER1.get(),PastoralPlainVillager.setAttributes());
            event.put(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get(), PastoralPlainTalkableVillager1.setAttributes());
            event.put(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),PastoralPlainVillagerElder.setAttributes());
            event.put(TCRModEntities.YGGDRASIL.get(), YggdrasilEntity.setAttributes());
        }

        //刷新规则
        @SubscribeEvent
        public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
            event.register(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    PastoralPlainTalkableVillager1::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    PastoralPlainVillager::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    PastoralPlainVillagerElder::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

            event.register(TCRModEntities.JELLY_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    JellyCat::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

            event.register(TCRModEntities.SMALL_TREE_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    SmallTreeMonsterEntity::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(TCRModEntities.TREE_GUARDIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    TreeGuardianEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
            event.register(TCRModEntities.MIDDLE_TREE_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    MiddleTreeMonsterEntity::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
        @SubscribeEvent
        public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(TCRModBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
            event.registerBlockEntityRenderer(TCRModBlockEntities.PORTAL_BLOCK_ENTITY.get(), PortalBlockRenderer::new);
            event.registerEntityRenderer(TCRModEntities.DESERT_EAGLE_BULLET.get(), ThrownItemRenderer::new);
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
