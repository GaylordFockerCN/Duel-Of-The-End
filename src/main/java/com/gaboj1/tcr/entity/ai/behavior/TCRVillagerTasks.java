package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerRetaliateTask;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.npc.Villager;

public class TCRVillagerTasks {
    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getGuardCorePackage() {
        return ImmutableList.of(
                Pair.of(0, new TCRVillagerRetaliateTask())
//                Pair.of(0, MeleeAttack.create(100))
       );
    }
}
