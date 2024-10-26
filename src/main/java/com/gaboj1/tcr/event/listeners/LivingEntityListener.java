package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.LevelableEntity;
import com.gaboj1.tcr.entity.MultiPlayerBoostEntity;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import com.gaboj1.tcr.item.custom.armor.TreeArmorItem;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import com.gaboj1.tcr.util.SaveUtil;
import com.gaboj1.tcr.worldgen.dimension.TCRDimension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class LivingEntityListener {

    /**
     * 穿盔甲加效果
     */
    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (!event.isCanceled()) {
            OrichalcumArmorItem.handleArmorAbility(livingEntity);
            TCREffects.onEntityUpdate(event);
        }

        if(TreeArmorItem.isFullSet(livingEntity)){
            TreeArmorItem.onFullSet(livingEntity);
        }
    }

    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        TreeSpiritWand.onKill(event);
        TCREffects.onEntityDie(event);

        //如果在维度里怪杀死了玩家则怪回满血
        if(event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().dimension() == TCRDimension.P_SKY_ISLAND_LEVEL_KEY){
            if(event.getSource().getEntity() instanceof LivingEntity livingEntity){
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
            if(event.getSource().getDirectEntity() instanceof LivingEntity livingEntity){
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        TCREffects.onEntityHurt(event);
        if(event.getSource().is(DamageTypes.FALL) && event.getEntity().getVehicle() != null && event.getEntity().getVehicle().getType() == TCREntities.WIND_FEATHER_FALCON.get()){
            event.setAmount(0);
            event.setCanceled(true);
        }

        TreeArmorItem.onLivingHurt(event);
    }

    /**
     * 实体加入时检查根据玩家和世界等级是否要加血
     */
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if(event.getLevel() instanceof ServerLevel){
            if(event.getEntity() instanceof LevelableEntity levelableEntity){
                levelableEntity.levelUp(SaveUtil.worldLevel);
            }
            if(event.getEntity() instanceof MultiPlayerBoostEntity multiPlayerBoostEntity){
                multiPlayerBoostEntity.whenPlayerCountChange();
            }
        }
    }

}
