package com.p1nero.dote.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.npc.Villager;

/**
 * 根据不同村民给予不同脑子
 */
public class VillagerTasks {

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getTCRVillagerCorePackage() {
        return null;
    }
}
