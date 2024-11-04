package com.gaboj1.tcr.item;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum TCRArmorMaterials implements ArmorMaterial {
    ORICHALCUM("orichalcum", 15, new int[]{ 1, 5, 4, 2 }, 10,
            SoundEvents.ARMOR_EQUIP_GOLD, 3f, 0.3f, () -> Ingredient.of(TCRItems.ORICHALCUM.get())),
    TREE("tree", 33, new int[]{ 3, 5, 7, 3 }, 15,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5f, 0.0f, () -> Ingredient.of(TCRItems.STARLIT_DEWDROP.get())),
    ICE_TIGER("ice_tiger", 33, new int[]{ 3, 5, 7, 3 }, 15,
        SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.5f, () -> Ingredient.of(TCRItems.TIGER_SOUL_ICE.get())),

    TREE_ROBE("tree_robe", 33, new int[]{ 5, 8, 0, 5 }, 15,
    SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.5f, () -> Ingredient.of(TCRItems.TIGER_SOUL_ICE.get()));



    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    TCRArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
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
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return TheCasketOfReveriesMod.MOD_ID + ":" + this.name;
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