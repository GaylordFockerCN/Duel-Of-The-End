package com.gaboj1.tcr.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

public class HolderBiomeMaker {
    //TODO: 换成自己的群系
    public static List<Holder<Biome>> getHolderBiomeList(HolderGetter<Biome> biomeRegistry){
        return List.of(
                biomeRegistry.getOrThrow(TCRBiomes.AIR),//0
                biomeRegistry.getOrThrow(TCRBiomes.PASTORAL_PLAINS),//1
                biomeRegistry.getOrThrow(TCRBiomes.DENSE_FOREST),//2
                biomeRegistry.getOrThrow(Biomes.PLAINS),//3
                biomeRegistry.getOrThrow(Biomes.DARK_FOREST),//4
                biomeRegistry.getOrThrow(Biomes.PLAINS),//5
                biomeRegistry.getOrThrow(Biomes.DARK_FOREST),//6
                biomeRegistry.getOrThrow(Biomes.PLAINS),//7
                biomeRegistry.getOrThrow(Biomes.DARK_FOREST)//8
                );
    }
}
