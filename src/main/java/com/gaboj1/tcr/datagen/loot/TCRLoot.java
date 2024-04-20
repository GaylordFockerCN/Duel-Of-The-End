package com.gaboj1.tcr.datagen.loot;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TCRLoot {
    // /give @p chest{BlockEntityTag:{LootTable:"the_casket_of_reveries:chests/flower_altar"}} 1
    // /give @p chest{BlockEntityTag:{LootTable:"the_casket_of_reveries:chests/flower_altar"},CustomName:'{"text":"Test Crate"}'} 1
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
    public static final ResourceLocation FLOWER_ALTAR = register("chests/flower_altar");
    public static final ResourceLocation ENTER_REALM_OF_THE_DREAM = register("advancements/enter_realm_of_the_dream");

    private static ResourceLocation register(String id) {
        return register(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
