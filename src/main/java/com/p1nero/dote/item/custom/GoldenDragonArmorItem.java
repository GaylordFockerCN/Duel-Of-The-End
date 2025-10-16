package com.p1nero.dote.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.p1nero.dote.item.DOTEArmorMaterials;
import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.item.model.GoldenDragonArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;


public class GoldenDragonArmorItem extends ArmorItem {

    public GoldenDragonArmorItem(Type type) {
        super(DOTEArmorMaterials.GOLDEN_DRAGON, type, new Properties().fireResistant().rarity(DOTERarities.SHEN_ZHEN));
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        if ((equipmentSlot == EquipmentSlot.HEAD && this.type.equals(Type.HELMET))
                || (equipmentSlot == EquipmentSlot.CHEST && this.type.equals(Type.CHESTPLATE))
                || (equipmentSlot == EquipmentSlot.LEGS&& this.type.equals(Type.LEGGINGS))
                || (equipmentSlot == EquipmentSlot.FEET&& this.type.equals(Type.BOOTS))) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
                ModelPart modelPart = modelSet.bakeLayer(GoldenDragonArmorModel.LAYER_LOCATION);
                return equipmentSlot == EquipmentSlot.LEGS ? original : new GoldenDragonArmorModel(modelPart);
            }
        });
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
//        list.add(Component.translatable("item.duel_of_the_end.golden_dragon0"));
    }
}
