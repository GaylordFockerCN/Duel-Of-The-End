package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.ai.behavior.boss.ShootFireBallTask;
import com.gaboj1.tcr.entity.ai.behavior.boss.ShootWaterBallTask;
import com.gaboj1.tcr.entity.ai.behavior.boss.SummonLightningTask;
import com.gaboj1.tcr.entity.ai.behavior.boss.ThrownPoisonPotionTask;
import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome2.*;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.npc.Villager;

/**
 * 根据不同村民给予不同脑子
 */
public class TCRVillagerTasks {

    public static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Villager>>> getTCRVillagerCorePackage(TCRVillager villager) {
        if(villager instanceof ThunderclapZhenYu){
            return ImmutableList.of(
                    Pair.of(0, new SummonLightningTask()),
                    Pair.of(0, new NPCDialogueTask())
            );
        }

        if(villager instanceof FuryTideCangLan){
            return ImmutableList.of(
                    Pair.of(0, new ShootWaterBallTask()),
                    Pair.of(0, new NPCDialogueTask())
            );
        }

        if(villager instanceof BlazingFlameYanXin){
            return ImmutableList.of(
                    Pair.of(0, new ShootFireBallTask()),
                    Pair.of(0, new NPCDialogueTask())
            );
        }

        if(villager instanceof SerpentWhispererCuiHua){
            return ImmutableList.of(
                    Pair.of(0, new ThrownPoisonPotionTask()),
                    Pair.of(0, new NPCDialogueTask())
            );
        }

        //反击和对话
        return ImmutableList.of(
                Pair.of(0, new TCRVillagerRetaliateTask()),
                Pair.of(0, new NPCDialogueTask())
       );
    }
}
