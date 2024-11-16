package com.gaboj1.tcr.datagen.lang;

import com.gaboj1.tcr.item.DOTEItems;
import net.minecraft.data.PackOutput;

public class DOTELangGenerator extends DOTELangProvider {
    public DOTELangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {
        add("item_group.duel_of_the_end.all", "终焉决斗");
        add(DOTEItems.P_KEY.get(), "炼狱决斗钥匙");
        add(DOTEItems.M_KEY.get(), "圣堂决斗钥匙");
        add(DOTEItems.U_KEY.get(), "终焉决斗钥匙");
        add(DOTEItems.ADGRAIN.get(), "冒险碎片");
        addItemUsageInfo(DOTEItems.ADGRAIN.get(), "");
        add(DOTEItems.IMMORTALESSENCE.get(), "往生精华");
        addItemUsageInfo(DOTEItems.IMMORTALESSENCE.get(), "似乎蕴含着某种往生的力量...");
        addItemUsageInfo(DOTEItems.P_KEY.get(), "潜行时右键以传送到指定决斗场的位置");
        addItemUsageInfo(DOTEItems.M_KEY.get(), "潜行时右键以传送到指定决斗场的位置");
        addItemUsageInfo(DOTEItems.U_KEY.get(), "潜行时右键以传送到指定决斗场的位置");
        add(DOTEItems.NETHERROT_INGOT.get(), "腐金锭");
        addItemUsageInfo(DOTEItems.NETHERROT_INGOT.get(), "");
        add(DOTEItems.NETHERITESS.get(), "合金残片");
        addItemUsageInfo(DOTEItems.NETHERITESS.get(), "森白影魔掉落");
        add(DOTEItems.TIESTONEH.get(), "绑石头盔");
        addItemUsageInfo(DOTEItems.TIESTONEH.get(), "套装效果：石");
        add(DOTEItems.TIESTONEC.get(), "绑石胸甲");
        addItemUsageInfo(DOTEItems.TIESTONEC.get(), "套装效果：石");
        add(DOTEItems.TIESTONEL.get(), "绑石护腿");
        addItemUsageInfo(DOTEItems.TIESTONEL.get(), "套装效果：石");
        add(DOTEItems.TIESTONES.get(), "绑石靴子");
        addItemUsageInfo(DOTEItems.TIESTONES.get(), "套装效果：石");
        add(DOTEItems.NETHERITEROT_HELMET.get(), "腐金头盔");
        addItemUsageInfo(DOTEItems.NETHERITEROT_HELMET.get(), "套装效果：炎甲");
        add(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "腐金胸甲");
        addItemUsageInfo(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "套装效果：炎甲");
        add(DOTEItems.NETHERITEROT_LEGGINGS.get(), "腐金护腿");
        addItemUsageInfo(DOTEItems.NETHERITEROT_LEGGINGS.get(), "套装效果：炎甲");
        add(DOTEItems.NETHERITEROT_BOOTS.get(), "腐金靴子");
        addItemUsageInfo(DOTEItems.NETHERITEROT_BOOTS.get(), "套装效果：炎甲");
        add("item.duel_of_the_end.golden_dragon0", "");



    }
}
