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

public class TieStoneArmorItem extends SimpleDescriptionArmorItem {
    public TieStoneArmorItem(Type type) {
        super(DOTEArmorMaterials.TIESTONE, type,  new Item.Properties().rarity(DOTERarities.LIANG_PIN));
    }

    public static void onFullSet(LivingEntity livingEntity){
        if(!isFullSet(livingEntity)){
            return;
        }
        if(livingEntity.level() instanceof ServerLevel){
            if(!livingEntity.hasEffect(MobEffects.DAMAGE_RESISTANCE)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0));
            }
        }
    }

    public static boolean isFullSet(Entity entity){
        return ItemUtil.isFullSets(entity, ObjectArrayList.of(
                DOTEItems.TIESTONEH.get(),
                DOTEItems.TIESTONEC.get(),
                DOTEItems.TIESTONEL.get(),
                DOTEItems.TIESTONES.get()));
    }

}
