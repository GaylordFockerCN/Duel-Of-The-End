package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

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
    }

    /**
     * 1 / 30的概率吸取生命
     */
    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        TreeSpiritWand.onKill(event);
        TCREffects.onEntityDie(event);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        TCREffects.onEntityHurt(event);
    }

}
