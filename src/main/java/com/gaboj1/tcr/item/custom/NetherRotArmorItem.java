package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.item.DOTEArmorMaterials;
import com.gaboj1.tcr.item.DOTEItems;
import com.gaboj1.tcr.item.DOTERarities;
import com.gaboj1.tcr.util.ItemUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class NetherRotArmorItem extends SimpleDescriptionArmorItem {
    public NetherRotArmorItem(Type type) {
        super(DOTEArmorMaterials.NETHERITEROT, type, new Item.Properties().fireResistant().rarity(DOTERarities.TE_PIN));
    }

    public static void onFullSet(LivingEntity livingEntity){
        if(!isFullSet(livingEntity)){
            return;
        }
        if(livingEntity.level() instanceof ServerLevel){
            if(!livingEntity.hasEffect(MobEffects.REGENERATION)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1));
            }
            if(!livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0));
            }
        }
    }

    public static boolean isFullSet(Entity entity){
        return ItemUtil.isFullSets(entity, ObjectArrayList.of(
                DOTEItems.NETHERITEROT_HELMET.get(),
                DOTEItems.NETHERITEROT_CHESTPLATE.get(),
                DOTEItems.NETHERITEROT_LEGGINGS.get(),
                DOTEItems.NETHERITEROT_BOOTS.get()));
    }

}
