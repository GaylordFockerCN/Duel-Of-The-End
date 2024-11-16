package com.gaboj1.tcr.item.custom;
import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.item.DOTEArmorMaterials;
import com.gaboj1.tcr.item.DOTERarities;
import com.gaboj1.tcr.item.model.Modelgdb;
import com.gaboj1.tcr.item.model.Modelgdc;
import com.gaboj1.tcr.item.model.Modelgdh;
import com.gaboj1.tcr.item.model.Modelgdl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GoldenDragonItem extends ArmorItem {

    public GoldenDragonItem(Type type) {
        super(DOTEArmorMaterials.GOLDEN_DRAGON, type, new Item.Properties().fireResistant().rarity(DOTERarities.SHEN_ZHEN));
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> head = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                        Map.of("head", new Modelgdh<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdh.LAYER_LOCATION)).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                head.crouching = livingEntity.isShiftKeyDown();
                head.riding = original.riding;
                head.young = livingEntity.isBaby();

                HumanoidModel<?> chest = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                        Map.of("body", new Modelgdc<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdc.LAYER_LOCATION)).Body, "left_arm",
                                new Modelgdc<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdc.LAYER_LOCATION)).LeftArm, "right_arm", new Modelgdc<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdc.LAYER_LOCATION)).RightArm,
                                "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                chest.crouching = livingEntity.isShiftKeyDown();
                chest.riding = original.riding;
                chest.young = livingEntity.isBaby();

                HumanoidModel<?> legs = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                        Map.of("left_leg", new Modelgdl<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdl.LAYER_LOCATION)).LeftLeg, "right_leg",
                                new Modelgdl<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdl.LAYER_LOCATION)).RightLeg, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                legs.crouching = livingEntity.isShiftKeyDown();
                legs.riding = original.riding;
                legs.young = livingEntity.isBaby();

                HumanoidModel<?> feet = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                        Map.of("left_leg", new Modelgdb<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdb.LAYER_LOCATION)).LeftLeg, "right_leg",
                                new Modelgdb<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgdb.LAYER_LOCATION)).RightLeg, "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "hat",
                                new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                feet.crouching = livingEntity.isShiftKeyDown();
                feet.riding = original.riding;
                feet.young = livingEntity.isBaby();

                return switch (equipmentSlot){
                    case MAINHAND, OFFHAND -> IClientItemExtensions.super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
                    case FEET -> feet;
                    case LEGS -> legs;
                    case CHEST -> chest;
                    case HEAD -> head;
                };
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return switch (slot) {
            case MAINHAND, OFFHAND -> super.getArmorTexture(stack, entity, slot, type);
            default -> DuelOfTheEndMod.MOD_ID + ":textures/item/armor/golden_dragon_armor.png";
        };
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable("item.duel_of_the_end.golden_dragon0"));
    }
}
