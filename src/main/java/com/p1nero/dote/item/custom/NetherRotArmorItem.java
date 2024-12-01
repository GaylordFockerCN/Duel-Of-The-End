package com.p1nero.dote.item.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.item.DOTEArmorMaterials;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.util.ItemUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class NetherRotArmorItem extends SimpleDescriptionArmorItem implements IDOTEKeepableItem {
    public NetherRotArmorItem(Type type) {
        super(DOTEArmorMaterials.NETHERITEROT, type, new Item.Properties().fireResistant().rarity(DOTERarities.TE_PIN));
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        if ((equipmentSlot == EquipmentSlot.HEAD && this.type.equals(Type.HELMET))
                || (equipmentSlot == EquipmentSlot.CHEST && this.type.equals(Type.CHESTPLATE))
                    || (equipmentSlot == EquipmentSlot.LEGS&& this.type.equals(Type.LEGGINGS))
                        || (equipmentSlot == EquipmentSlot.FEET&& this.type.equals(Type.BOOTS))) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(EpicFightAttributes.MAX_STAMINA.get(), new AttributeModifier(UUID.fromString("CC111E1C-4180-4820-B01B-BCCE1234ACA" + equipmentSlot.getIndex()), "Item modifier", 6, AttributeModifier.Operation.ADDITION));
            builder.put(EpicFightAttributes.STAMINA_REGEN.get(), new AttributeModifier(UUID.fromString("CC222E1C-3333-4820-B01B-BCCE1234ACA" + equipmentSlot.getIndex()), "Item modifier", 0.18, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public static void onFullSet(LivingEntity livingEntity){
        if(!isFullSet(livingEntity)){
            return;
        }
        if(livingEntity.level() instanceof ServerLevel){
            if(!livingEntity.hasEffect(MobEffects.REGENERATION)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            }
            if(!livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0));
            }
            if(!livingEntity.hasEffect(MobEffects.DAMAGE_BOOST)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 4));
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

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return DOTEArchiveManager.getWorldLevel() >= 1;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return DOTEArchiveManager.getWorldLevel() >= 1;
    }

}
