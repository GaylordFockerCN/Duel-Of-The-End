package com.p1nero.dote.item;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum DOTEArmorMaterials implements ArmorMaterial {
    TIESTONE("tiestone", 15, new int[]{ 2, 4, 4, 3}, 10,
            SoundEvents.ARMOR_EQUIP_CHAIN, 1f, 0.3f, () -> Ingredient.of(Blocks.COBBLESTONE)),
    WHITEKNIGHT("whiteknight", 37, new int[]{ 3, 6, 8, 3}, 15,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0f, 0.3f, () -> Ingredient.of(DOTEItems.WKNIGHT_INGOT.get())),
    NETHERITEROT("netheriterot", 68, new int[]{ 2, 4, 5, 3}, 22,
        SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0f, 0.3f, () -> Ingredient.of(DOTEItems.NETHERROT_INGOT.get())),
//    GOLDEN_DRAGON("golden_dragon", 168, new int[]{ 4, 7, 9, 6}, 22,
//            SoundEvents.ARMOR_EQUIP_NETHERITE, 4f, 0.3f, () -> Ingredient.EMPTY)
    GOLDEN_DRAGON("golden_dragon", 168, new int[]{ 0, 0, 0, 0}, 22,
    SoundEvents.ARMOR_EQUIP_NETHERITE, 4f, 0.3f, () -> Ingredient.EMPTY);

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    DOTEArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                       float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return DuelOfTheEndMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}