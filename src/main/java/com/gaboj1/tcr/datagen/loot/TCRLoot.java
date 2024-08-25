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

    //村民家普通箱子
    public static final ResourceLocation VILLAGE1USUAL = register("chests/village1usual");
    //酒吧箱子
//    public static final ResourceLocation BAR = register("chests/bar");
    //长老家箱子
    public static final ResourceLocation VILLAGE1SPECIAL1 = register("chests/village1special1");
    //铁匠铺箱子
    public static final ResourceLocation VILLAGE1SPECIAL2 = register("chests/village1special2");
    //小树屋箱子
    public static final ResourceLocation TREE_HOUSE = register("chests/treehouse");
    //祭坛箱子（装削弱boss的道具）
    public static final ResourceLocation ALTAR1 = register("chests/altar1");
    //塔顶箱子
    public static final ResourceLocation BOSS1_TOWER_TOP = register("chests/boss1towertop");
    //塔的箱子
    public static final ResourceLocation BOSS1TOWER = register("chests/boss1tower");
    //树顶箱子
    public static final ResourceLocation BOSS1TREETOP = register("chests/boss1treetop");
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
