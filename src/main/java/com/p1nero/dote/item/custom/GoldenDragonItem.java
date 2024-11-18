package com.p1nero.dote.item.custom;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.item.DOTEArmorMaterials;
import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.item.model.GoldenDragonArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class GoldenDragonItem extends ArmorItem {

    public GoldenDragonItem(Type type) {
        super(DOTEArmorMaterials.GOLDEN_DRAGON, type, new Properties().fireResistant().rarity(DOTERarities.SHEN_ZHEN));
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
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return DuelOfTheEndMod.MOD_ID + ":textures/item/armor/golden_dragon_armor.png";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
//        list.add(Component.translatable("item.duel_of_the_end.golden_dragon0"));
    }
}
