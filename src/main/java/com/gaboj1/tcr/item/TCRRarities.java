package com.gaboj1.tcr.item;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

/**
 * 品级控制；
 * 神金套，小怪掉落物做的装备：上品
 * 打完第一个boss的装备：特品
 * 真·神金套，打完两个boss可以融合的装备：仙品
 * 打完三个才可以合成的装备：神珍
 */
public class TCRRarities {
    public static final Rarity SHEN_ZHEN = Rarity.create("tcr.shen_zhen", ChatFormatting.RED);
    public static final Rarity XIAN_PIN = Rarity.create("tcr.xian_pin", ChatFormatting.GOLD);
    public static final Rarity TE_PIN = Rarity.create("tcr.te_pin", ChatFormatting.DARK_PURPLE);
    public static final Rarity SHANG_PIN = Rarity.create("tcr.shang_pin", ChatFormatting.AQUA);
    public static final Rarity LIANG_PIN = Rarity.create("tcr.liang_pin", ChatFormatting.GREEN);
    public static final Rarity FAN_PIN = Rarity.create("tcr.fan_pin", ChatFormatting.GRAY);

}
