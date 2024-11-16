package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.archive.DOTEArchiveManager;
import com.gaboj1.tcr.item.custom.NetherRotArmorItem;
import com.gaboj1.tcr.item.custom.TieStoneArmorItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class LivingEntityListener {

    /**
     * 穿盔甲加效果
     */
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        NetherRotArmorItem.onFullSet(livingEntity);
        TieStoneArmorItem.onFullSet(livingEntity);
    }

    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {

    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
    }

    /**
     * 实体加入时检查根据玩家和世界等级是否要加血
     */
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(event.getLevel() instanceof ServerLevel){
            if(event.getEntity() instanceof LevelableEntity levelableEntity){
                levelableEntity.levelUp(DOTEArchiveManager.getWorldLevel());
            }
            if(event.getEntity() instanceof MultiPlayerBoostEntity multiPlayerBoostEntity){
                multiPlayerBoostEntity.whenPlayerCountChange();
            }
        }
    }

}
