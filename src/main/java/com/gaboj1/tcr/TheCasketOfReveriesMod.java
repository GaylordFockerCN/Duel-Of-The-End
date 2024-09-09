package com.gaboj1.tcr;

import com.gaboj1.tcr.block.TCRModBlockEntities;
import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.block.entity.client.PortalBlockRenderer;
import com.gaboj1.tcr.block.entity.client.YggdrasilBlockRenderer;
import com.gaboj1.tcr.block.renderer.BetterStructureBlockRenderer;
import com.gaboj1.tcr.block.renderer.PortalBedRenderer;
import com.gaboj1.tcr.client.TCRModSounds;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.entity.TCRModVillagers;
import com.gaboj1.tcr.entity.client.big_hammer.BigHammerRenderer;
import com.gaboj1.tcr.entity.client.boss.yggrasil.TreeClawRenderer;
import com.gaboj1.tcr.entity.client.boss.yggrasil.YggdrasilRenderer;
import com.gaboj1.tcr.entity.client.biome2.BoxerRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.CrabRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.JellyCatRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.SquirrelRenderer;
import com.gaboj1.tcr.entity.client.boss.second_boss.SecondBossRenderer;
import com.gaboj1.tcr.entity.client.biome2.SnowSwordmanRenderer;
import com.gaboj1.tcr.entity.client.biome2.SpriteRenderer;
import com.gaboj1.tcr.entity.client.biome2.SwordControllerRenderer;
import com.gaboj1.tcr.entity.client.biome2.TigerRenderer;
import com.gaboj1.tcr.entity.client.biome1.MiddleTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.biome1.SmallTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.biome1.TreeGuardianRenderer;
import com.gaboj1.tcr.entity.client.villager.TCRVillagerRenderer;
import com.gaboj1.tcr.entity.custom.sword.SwordEntityRenderer;
import com.gaboj1.tcr.item.TCRModItemTabs;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.TCRStructurePlacementTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

import static com.gaboj1.tcr.worldgen.biome.BiomeMap.README;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheCasketOfReveriesMod.MOD_ID)
public class TheCasketOfReveriesMod {
    // Define mod x in a common place for everything to reference
    public static final String MOD_ID = "the_casket_of_reveries";

    public static final String REGISTRY_NAMESPACE = "tcr";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public TheCasketOfReveriesMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TCRModSounds.REGISTRY.register(bus);
        TCRModItems.REGISTRY.register(bus);
        TCRModBlocks.REGISTRY.register(bus);
        TCRModBlockEntities.REGISTRY.register(bus);
        TCRModEntities.REGISTRY.register(bus);
        TCRModItemTabs.REGISTRY.register(bus);
        TCREffects.REGISTRY.register(bus);
        TCRStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        TCRModVillagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        TCRPacketHandler.register();
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.getId(), TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TCRModBlocks.CATNIP.getId(), TCRModBlocks.POTTED_CATNIP);
        });
        try{
            File dir = FMLPaths.CONFIGDIR.get().resolve(TheCasketOfReveriesMod.MOD_ID).toFile();
            if(!dir.exists()){
                TheCasketOfReveriesMod.LOGGER.info("creating dir : "+dir.mkdirs());
            }
            File readme = new File(README);
            if(!readme.exists()){
                TheCasketOfReveriesMod.LOGGER.info("creating README : "+readme.createNewFile());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(readme))) {
                    writer.write("""
                            注意，新建存档最好单独命名，并且确保游戏重启后新建，以免进度没有刷新

                            图片请命名为“map.png”，透明为空域其余为大陆。建议图片大小1600x1600（但是加载比较缓慢）。可以在config/the_casket_of_reveries-common.toml中设置是否使用缩放，如果开启则地图大小固定，图片越大锯齿越少。图片不存在则按默认预设生成地图。

                            Please name the image "map. png", with transparent indicating airspace and the rest indicating mainland. Suggest an image size of 1600x1600(However, loading is relatively slow). You can set whether to use scaling in config/the_casket_of_reveries-common.toml. If enabled, the map size is fixed, and the larger the image, the less jagged it will be. If the image does not exist, generate a map according to the default preset.""");
                }
                TheCasketOfReveriesMod.LOGGER.info("created.");
            }
        }catch (Exception e){
            TheCasketOfReveriesMod.LOGGER.error("Failed to read map！",e);
        }


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


            EntityRenderers.register(TCRModEntities.SWORD.get(), SwordEntityRenderer::new);
            EntityRenderers.register(TCRModEntities.RAIN_SCREEN_SWORD.get(), SwordEntityRenderer::new);
            EntityRenderers.register(TCRModEntities.RAIN_CUTTER_SWORD.get(), SwordEntityRenderer::new);
            EntityRenderers.register(TCRModEntities.MAGIC_PROJECTILE.get(), LlamaSpitRenderer::new);

            EntityRenderers.register(TCRModEntities.MIDDLE_TREE_MONSTER.get(), MiddleTreeMonsterRenderer::new);
            EntityRenderers.register(TCRModEntities.TREE_GUARDIAN.get(), TreeGuardianRenderer::new);
            EntityRenderers.register(TCRModEntities.SMALL_TREE_MONSTER.get(), SmallTreeMonsterRenderer::new);
            EntityRenderers.register(TCRModEntities.JELLY_CAT.get(), JellyCatRenderer::new);
            EntityRenderers.register(TCRModEntities.SQUIRREL.get(), SquirrelRenderer::new);
            EntityRenderers.register(TCRModEntities.CRAB.get(), CrabRenderer::new);

            EntityRenderers.register(TCRModEntities.P1NERO.get(), TCRVillagerRenderer::new);

            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.VILLAGER2.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.VILLAGER2_TALKABLE.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.VILLAGER2_STATIONARY.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.CANG_LAN.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.ZHEN_YU.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.DUAN_SHAN.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.CUI_HUA.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.YUN_YI.get(), TCRVillagerRenderer::new);
            EntityRenderers.register(TCRModEntities.YAN_XIN.get(), TCRVillagerRenderer::new);

            EntityRenderers.register(TCRModEntities.YGGDRASIL.get(), YggdrasilRenderer::new);
            EntityRenderers.register(TCRModEntities.TREE_CLAW.get(), TreeClawRenderer::new);

            EntityRenderers.register(TCRModEntities.SPRITE.get(), SpriteRenderer::new);
            EntityRenderers.register(TCRModEntities.BOXER.get(), BoxerRenderer::new);
            EntityRenderers.register(TCRModEntities.BIG_HAMMER.get(), BigHammerRenderer::new);
            EntityRenderers.register(TCRModEntities.SNOW_SWORDMAN.get(), SnowSwordmanRenderer::new);
            EntityRenderers.register(TCRModEntities.SWORD_CONTROLLER.get(), SwordControllerRenderer::new);
            EntityRenderers.register(TCRModEntities.SECOND_BOSS.get(), SecondBossRenderer::new);

            EntityRenderers.register(TCRModEntities.TIGER.get(), TigerRenderer::new);


            BlockEntityRenderers.register(TCRModBlockEntities.PORTAL_BED.get(), PortalBedRenderer::new);
        }

        @SubscribeEvent
        public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(TCRModBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
            event.registerBlockEntityRenderer(TCRModBlockEntities.PORTAL_BLOCK_ENTITY.get(), PortalBlockRenderer::new);
            event.registerEntityRenderer(TCRModEntities.DESERT_EAGLE_BULLET.get(), ThrownItemRenderer::new);
            event.registerBlockEntityRenderer(TCRModBlockEntities.YGGDRASIL_SPAWNER_BLOCK_ENTITY.get(), YggdrasilBlockRenderer::new);
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
