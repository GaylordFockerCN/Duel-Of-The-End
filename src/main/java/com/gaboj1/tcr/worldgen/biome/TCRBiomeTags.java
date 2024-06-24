package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class TCRBiomeTags {
    public static final TagKey<Biome> FORBIDDEN_BIOME = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("forbidden_biome"));
    public static final TagKey<Biome> IS_TCR = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("in_tcr"));
    public static final TagKey<Biome> VALID_BIOME1 = TagKey.create(Registries.BIOME, TheCasketOfReveriesMod.prefix("valid_biome1"));

    public static boolean isInDim(Player player){
        Level level = player.level();
        Holder<Biome> currentBiome = level.getBiome(player.getOnPos());
        return currentBiome.is(IS_TCR);
    }

}
