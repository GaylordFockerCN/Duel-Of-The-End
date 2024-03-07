package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TCRBiomeTags {
    public static final TagKey<Biome> FORBIDDEN_BIOME = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("forbidden_biome"));
    public static final TagKey<Biome> IS_TCR = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("in_tcr"));
    public static final TagKey<Biome> VALID_BIOME1 = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("valid_biome1"));
}
