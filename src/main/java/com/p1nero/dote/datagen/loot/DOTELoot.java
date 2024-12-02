package com.p1nero.dote.datagen.loot;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DOTELoot {
    // /give @p chest{BlockEntityTag:{LootTable:"duel_of_the_end:chests/flower_altar"}} 1
    // /give @p chest{BlockEntityTag:{LootTable:"duel_of_the_end:chests/flower_altar"},CustomName:'{"text":"Test Crate"}'} 1
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
    public static final ResourceLocation WATCHTOWER = register("chests/watchtower");
    public static final ResourceLocation CHURCH1 = register("chests/church1");
    public static final ResourceLocation CHURCH2 = register("chests/church2");
    public static final ResourceLocation CHURCH_RARE = register("chests/church_rare");

    private static ResourceLocation register(String id) {
        return register(new ResourceLocation(DuelOfTheEndMod.MOD_ID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
