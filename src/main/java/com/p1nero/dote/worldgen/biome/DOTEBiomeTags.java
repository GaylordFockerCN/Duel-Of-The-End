package com.p1nero.dote.worldgen.biome;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class DOTEBiomeTags {
    public static final TagKey<Biome> IN_DOTE = TagKey.create(Registries.BIOME, DuelOfTheEndMod.prefix("in_dote"));

}
