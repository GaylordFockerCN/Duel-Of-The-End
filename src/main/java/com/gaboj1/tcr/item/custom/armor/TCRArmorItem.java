package com.gaboj1.tcr.item.custom.armor;

import com.gaboj1.tcr.item.TCRArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class TCRArmorItem extends ArmorItem {

    public TCRArmorItem(ArmorMaterial armorMaterial, Type p_266831_, Properties p_40388_) {
        super(armorMaterial, p_266831_, p_40388_);
    }

    public boolean hasCorrectArmorOn(Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    public static boolean hasArmorSet(LivingEntity entity, ArmorMaterial material) {
        for (ItemStack armorStack : entity.getArmorSlots()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)entity.getItemBySlot(EquipmentSlot.FEET).getItem());
        ArmorItem leggings = ((ArmorItem)entity.getItemBySlot(EquipmentSlot.LEGS).getItem());
        ArmorItem breastplate = ((ArmorItem)entity.getItemBySlot(EquipmentSlot.CHEST).getItem());
        ArmorItem helmet = ((ArmorItem)entity.getItemBySlot(EquipmentSlot.HEAD).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    public static boolean hasOrichalcumArmorSet(LivingEntity entity) {
        return hasArmorSet(entity, TCRArmorMaterials.ORICHALCUM);
    }

}
