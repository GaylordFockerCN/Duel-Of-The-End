package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.DuelOfTheEndMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class TCRBiomeTags {
    public static final TagKey<Biome> FORBIDDEN_BIOME = TagKey.create(Registries.BIOME, DuelOfTheEndMod.prefix("forbidden_biome"));
    public static final TagKey<Biome> IS_TCR = TagKey.create(Registries.BIOME, DuelOfTheEndMod.prefix("in_tcr"));
    public static final TagKey<Biome> VALID_BIOME1 = TagKey.create(Registries.BIOME, DuelOfTheEndMod.prefix("valid_biome1"));

}
