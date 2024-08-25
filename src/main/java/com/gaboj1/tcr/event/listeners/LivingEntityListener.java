package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainTalkableVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.item.custom.armor.OrichalcumArmorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
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
        }
    }

    /**
     * 1 / 30的概率吸取生命
     */
    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event) {
        if(new Random().nextInt(0,30) == 5 &&event.getSource().getEntity() instanceof Player player && player.getMainHandItem().is(TCRModItems.TREE_SPIRIT_WAND.get())){
            LivingEntity entity = event.getEntity();
            if(entity instanceof PastoralPlainVillager || entity instanceof PastoralPlainTalkableVillager || entity instanceof PastoralPlainVillagerElder){
                AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
                int cnt = player.getMainHandItem().getOrCreateTag().getInt("cnt");
                if(instance != null){
                    instance.addPermanentModifier(new AttributeModifier("killVillagerReward"+cnt++, 2.0f, AttributeModifier.Operation.ADDITION));
                    player.displayClientMessage(Component.translatable("info.the_casket_of_reveries.health_added"), true);
                    player.getMainHandItem().getOrCreateTag().putInt("cnt", cnt);
                }
            }
        }

    }

}
