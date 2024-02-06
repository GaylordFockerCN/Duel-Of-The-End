package com.gaboj1.tcr.entity.event;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.MiddleTreeMonsterEntity;
import com.gaboj1.tcr.entity.custom.SmallTreeMonsterEntity;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SmallTreeMonsterEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(TCRModEntities.SMALL_TREE_MONSTER.get(), SmallTreeMonsterEntity.setAttributes());
    }

    //刷新规则
    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(TCRModEntities.SMALL_TREE_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SmallTreeMonsterEntity::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
