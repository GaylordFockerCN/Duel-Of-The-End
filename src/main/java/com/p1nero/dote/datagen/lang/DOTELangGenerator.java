package com.p1nero.dote.datagen.lang;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlocks;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.effect.DOTEEffects;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.worldgen.biome.DOTEBiomes;
import net.minecraft.data.PackOutput;

public class DOTELangGenerator extends DOTELangProvider {
    public DOTELangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {

        this.addEffect(DOTEEffects.BURNT, "灼伤");

        this.add("pack.dote_animation.title", "内置奇妙动画包");
        this.addSkill("better_dodge_display", "完美闪避显示", "成功闪避将留下残影和播放音效，并额外恢复一点耐力");

        this.addAdvancement(DuelOfTheEndMod.MOD_ID, "光与暗与终末", "进入光与暗与终末的维度");
        this.addAdvancement("seed", "圣辉之种", "完成圣辉试炼");
        this.addAdvancement("core", "炼狱之核", "完成炎魔试炼");
        this.addAdvancement("golden_flame", "冥冥之中", "击败森白影魔与金焰神王");
        this.addAdvancement("book", "终末之诗", "踏足终末岛屿，并击败终末之影");
        this.addAdvancement("knight", "朝圣者", "将圣辉之种和炼狱之核交给骑士长巴伦");
        this.addAdvancement("loyal", "忠诚的群星", "三次轮回都将圣辉之种和炼狱之核交给骑士长巴伦");
        this.addAdvancement("unfinished", "未竟之志", "三次轮回中至少有一次将圣辉之种和炼狱之核交给骑士长巴伦");
        this.addAdvancement("star", "碎星者", "三次轮回中皆选择终末之影");

        this.addSubtitle(DOTESounds.LOTUSHEAL, "祭坛召唤音效");
        this.addSubtitle(DOTESounds.DODGE, "闪避音效");
        this.addSubtitle(DOTESounds.SENBAI_BGM, "森白影魔战斗bgm");
        this.addSubtitle(DOTESounds.GOLDEN_FLAME_BGM, "金焰神王战斗bgm");

        addBiome(DOTEBiomes.AIR, "虚空");
        
        add("item_group.duel_of_the_end.all", "终焉决斗");

        add(DOTEItems.IMMORTALESSENCE.get(), "往生精华");
        addItemUsageInfo(DOTEItems.IMMORTALESSENCE.get(), "似乎蕴含着某种往生的力量...");

        add(DOTEItems.GOLDEN_DRAGON_HELMET.get(), "赤焰金龙战盔");
        addItemUsageInfo(DOTEItems.GOLDEN_DRAGON_HELMET.get(), "赞助者：§2<雨落风情>");
        add(DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get(), "赤焰金龙战甲");
        addItemUsageInfo(DOTEItems.GOLDEN_DRAGON_CHESTPLATE.get(), "赞助者：§2<雨落风情>");
        add(DOTEItems.GOLDEN_DRAGON_LEGGINGS.get(), "赤焰金龙护腿");
        addItemUsageInfo(DOTEItems.GOLDEN_DRAGON_LEGGINGS.get(), "赞助者：§2<雨落风情>");
        add(DOTEItems.GOLDEN_DRAGON_BOOTS.get(), "赤焰金龙战靴");
        addItemUsageInfo(DOTEItems.GOLDEN_DRAGON_BOOTS.get(), "赞助者：§2<雨落风情>");

        add(DOTEBlocks.SENBAI_SPAWNER.get(), "森白影魔召唤祭坛");
        add(DOTEBlocks.GOLDEN_FLAME_SPAWNER.get(), "金焰神王召唤祭坛");

        add(DOTEEntities.REAPER.get(), "赤焰魔使");
        add(DOTEEntities.DARK_ADVANCE.get(), "黑暗先遣");
        add(DOTEEntities.SAND_CAPTAIN.get(), "狂沙之枪");
        add(DOTEEntities.SENBAI_DEVIL.get(), "森白影魔");
        add(DOTEEntities.SLAUGHTER_GENERAL.get(), "杀戮将军");
        add(DOTEEntities.GOLDEN_FLAME.get(), "金焰神王");
        add(DOTEEntities.MS_ABYSS.get(), "晨星之渊");
        add(DOTEEntities.LIU_GUANG.get(), "流光");

        add(DOTEEntities.ABYSS_DWELLER.get(), "凝渊人");

    }
}
