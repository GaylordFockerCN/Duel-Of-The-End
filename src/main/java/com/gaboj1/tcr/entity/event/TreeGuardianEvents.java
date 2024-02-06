package com.gaboj1.tcr.entity.event;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.TreeGuardianEntity;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TreeGuardianEvents {

    public static EntityAttributeCreationEvent event;

//    @SubscribeEvent
//    public static void TickAttribute(TickEvent.PlayerTickEvent event1){
//        entityAttributeEvent(event);
//    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(TCRModEntities.TREE_GUARDIAN.get(), TreeGuardianEntity.setAttributes());//设置生物属性功能在此被调用
    }

    //刷新规则
    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(TCRModEntities.TREE_GUARDIAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TreeGuardianEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
