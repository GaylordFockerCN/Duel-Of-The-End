package com.gaboj1.tcr;

import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.client.TCRSounds;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.TCRVillagers;
import com.gaboj1.tcr.item.TCRItemTabs;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator;
import com.gaboj1.tcr.worldgen.structure.TCRStructurePlacementTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

        TCRSounds.REGISTRY.register(bus);
        TCRItems.REGISTRY.register(bus);
        TCRBlocks.REGISTRY.register(bus);
        TCRBlockEntities.REGISTRY.register(bus);
        TCREntities.REGISTRY.register(bus);
        TCRItemTabs.REGISTRY.register(bus);
        TCREffects.REGISTRY.register(bus);
        TCRStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        TCRVillagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerExtraStuff);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    public static MutableComponent getInfo(String key){
        return Component.translatable("info.the_casket_of_reveries."+key);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        TCRPacketHandler.register();
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER.getId(), TCRBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TCRBlocks.CATNIP.getId(), TCRBlocks.POTTED_CATNIP);
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


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation(REGISTRY_NAMESPACE, name.toLowerCase(Locale.ROOT));
    }

}
