package com.p1nero.dote.datagen.lang;

import com.p1nero.dote.block.DOTEBlocks;
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
        this.addInfo("tip1", "使用往生精华右键祭坛以召唤 ");
        this.addInfo("tip2", "似乎有种神秘力量阻止你访问此地...");
        this.addInfo("tip3", "你现在不能召唤，周围有怪物在游荡...");
        this.addInfo("tip4", "其他玩家的力量干扰了祭坛的运作...");
        this.addInfo("tip5", "看准时机闪避或招架，做好准备再来吧…");
        this.addInfo("tip6", "锻造维度专属强力盔甲以提高生存能力，做好准备再来吧…");
        this.addInfo("tip7", "寻找“追星者”学习强力技能以提升战斗能力，做好准备再来吧…");
        this.addInfo("tip9", "你无法逃避来自终焉的决斗...");

        addBiome(DOTEBiomes.AIR, "虚空");
        addBiome(DOTEBiomes.M_BIOME, "圣堂");
        addBiome(DOTEBiomes.P_BIOME, "炼狱");
        
        add("item_group.duel_of_the_end.all", "终焉决斗");
        add(DOTEItems.M_KEY.get(), "圣堂决斗钥匙");
        addItemUsageInfo(DOTEItems.M_KEY.get(), "牧师赠与的钥匙。你...做好准备了吗？");
        add(DOTEItems.P_KEY.get(), "炼狱决斗钥匙");
        addItemUsageInfo(DOTEItems.P_KEY.get(), "所谓的终焉也并非遥不可及...");
        add(DOTEItems.U_KEY.get(), "终焉决斗钥匙");
        addItemUsageInfo(DOTEItems.U_KEY.get(), "§6准备好面对“祂”了吗...");
        add(DOTEItems.ADGRAIN.get(), "冒险碎片");
        addItemUsageInfo(DOTEItems.ADGRAIN.get(), "似乎可以用来与他人交易...");
        add(DOTEItems.ADVENTURESPAR.get(), "冒险晶石");
        addItemUsageInfo(DOTEItems.ADVENTURESPAR.get(), "");
        add(DOTEItems.IMMORTALESSENCE.get(), "往生精华");
        addItemUsageInfo(DOTEItems.IMMORTALESSENCE.get(), "似乎蕴含着某种往生的力量...");
        add(DOTEItems.NETHERITESS.get(), "合金残片");
        addItemUsageInfo(DOTEItems.NETHERITESS.get(), "森白影魔掉落物");
        add(DOTEItems.WITHERC.get(), "凋零残片");
        addItemUsageInfo(DOTEItems.WITHERC.get(), "金焰神王掉落物");
        add(DOTEItems.HOLY_RADIANCE_SEED.get(), "圣辉之种");
        addItemUsageInfo(DOTEItems.HOLY_RADIANCE_SEED.get(), "圣辉裁决者掉落物");
        add(DOTEItems.CORE_OF_HELL.get(), "炼狱核心");
        addItemUsageInfo(DOTEItems.CORE_OF_HELL.get(), "炼狱炎魔掉落物");
        add(DOTEItems.BOOK_OF_ENDING.get(), "终焉之诗");
        addItemUsageInfo(DOTEItems.BOOK_OF_ENDING.get(), "终焉之影掉落物");
        add(DOTEItems.NETHERROT_INGOT.get(), "腐金锭");
        addItemUsageInfo(DOTEItems.NETHERROT_INGOT.get(), "");
        add(DOTEItems.WKNIGHT_INGOT.get(), "白泣锭");
        addItemUsageInfo(DOTEItems.WKNIGHT_INGOT.get(), "");
        add(DOTEItems.DRAGONSTEEL_INGOT.get(), "龙钢锭");
        addItemUsageInfo(DOTEItems.BOOK_OF_ENDING.get(), "");
        add(DOTEItems.TIESTONEH.get(), "绑石头盔");
        addItemUsageInfo(DOTEItems.TIESTONEH.get(), "套装效果：§6石");
        add(DOTEItems.TIESTONEC.get(), "绑石胸甲");
        addItemUsageInfo(DOTEItems.TIESTONEC.get(), "套装效果：§6石");
        add(DOTEItems.TIESTONEL.get(), "绑石护腿");
        addItemUsageInfo(DOTEItems.TIESTONEL.get(), "套装效果：§6石");
        add(DOTEItems.TIESTONES.get(), "绑石靴子");
        addItemUsageInfo(DOTEItems.TIESTONES.get(), "套装效果：§6石");
        add(DOTEItems.WKNIGHT_HELMET.get(), "白泣头盔");
        addItemUsageInfo(DOTEItems.WKNIGHT_HELMET.get(), "套装效果：§f白泣");
        add(DOTEItems.WKNIGHT_CHESTPLATE.get(), "白泣胸甲");
        addItemUsageInfo(DOTEItems.WKNIGHT_CHESTPLATE.get(), "套装效果：§f白泣");
        add(DOTEItems.WKNIGHT_LEGGINGS.get(), "白泣护腿");
        addItemUsageInfo(DOTEItems.WKNIGHT_LEGGINGS.get(), "套装效果：§f白泣");
        add(DOTEItems.WKNIGHT_BOOTS.get(), "白泣靴子");
        addItemUsageInfo(DOTEItems.WKNIGHT_BOOTS.get(), "套装效果：§f白泣");
        add(DOTEItems.NETHERITEROT_HELMET.get(), "腐金头盔");
        addItemUsageInfo(DOTEItems.NETHERITEROT_HELMET.get(), "套装效果：§4炎甲");
        add(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "腐金胸甲");
        addItemUsageInfo(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "套装效果：§4炎甲");
        add(DOTEItems.NETHERITEROT_LEGGINGS.get(), "腐金护腿");
        addItemUsageInfo(DOTEItems.NETHERITEROT_LEGGINGS.get(), "套装效果：§4炎甲");
        add(DOTEItems.NETHERITEROT_BOOTS.get(), "腐金靴子");
        addItemUsageInfo(DOTEItems.NETHERITEROT_BOOTS.get(), "套装效果：§4炎甲");
        add(DOTEItems.BALMUNG.get(), "幻想大剑：天魔失坠");
        addItemUsageInfo(DOTEItems.BALMUNG.get(), "赞助者：§2<xyz>");
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
        add(DOTEBlocks.TAR_SPAWNER.get(), "圣辉裁决者召唤祭坛");
        add(DOTEBlocks.TPP_SPAWNER.get(), "炼狱炎魔召唤祭坛");
        add(DOTEBlocks.TSE_SPAWNER.get(), "终末之影召唤祭坛");

        add(DOTEEntities.DOTE_ZOMBIE.get(), "黑暗先遣");
        add(DOTEEntities.DOTE_ZOMBIE_2.get(), "黑暗先遣");
        add(DOTEEntities.DOTE_PIGLIN.get(), "猪灵");
        add(DOTEEntities.STAR_CHASER.get(), "追星者");
        add(DOTEEntities.SENBAI_DEVIL.get(), "森白影魔");
        add(DOTEEntities.GOLDEN_FLAME.get(), "金焰神王");
        add(DOTEEntities.THE_ARBITER_OF_RADIANCE.get(), "圣辉裁决者：Oblivionis");
        add(DOTEEntities.THE_PYROCLAS_OF_PURGATORY.get(), "炼狱炎魔：Mortis");
        add(DOTEEntities.THE_SHADOW_OF_THE_END.get(), "终焉之影：Timoris");


        this.addDialog(DOTEEntities.STAR_CHASER, 0, "哦？一位幸存者，我对你有印象。你在那场战斗之中的表现还远远不够。和那位耀眼的剑客相比，风沙就足以将你轻易掩埋。");
        this.addDialog(DOTEEntities.STAR_CHASER, 1, "干我们这一行，时刻掌握外界的第一手信息是至关重要的！");
        this.addDialog(DOTEEntities.STAR_CHASER, 2, "我成功了，我看到“祂”了！灾难是有名字的！不、不对！这是陷阱！祂注意到我了！我要结束这个魔法才行！");
        this.addDialog(DOTEEntities.STAR_CHASER, 3, "这帮僵尸什么时候学来的这么灵活的身手！除了使得一手好剑还能在混战里互相协作，他们名字里明明带个“僵”字啊？");
        this.addDialog(DOTEEntities.STAR_CHASER, 4, "“祂”并非不可战胜！人类的未来将会如同今夜天上的群星般闪烁！");
        this.addDialog(DOTEEntities.STAR_CHASER, 5, "这就是虚空的力量……多么强大，多么美丽！所谓的终焉也并非遥不可及！");
        this.addDialog(DOTEEntities.STAR_CHASER, 6, "观星者的职责是保护“终焉”之后所有幸存的人类聚落不被毁灭，这也是我们相聚于此的原因。");
        this.addDialog(DOTEEntities.STAR_CHASER, 7, "最后的观星者们啊！终焉带来了毁灭，却带不走他们——人类“希望”的体现！");
        this.addDialog(DOTEEntities.STAR_CHASER, 8, "我们成功了！通过将钻石与黄金不断的熔炼与敲打，再加入其他稀有的金属以及我在四处探索时得到的一些晶石碎片，一块漆黑的合金锭在高炉里诞生了！经过商议，我们决定将这种熔炼出来的完美的金属命名为“下界合金”。");
        this.addDialog(DOTEEntities.STAR_CHASER, 9, "根据我的初步调查，这个世界和我们之间的距离比我想象的要近得多，也许这里顶层之外的无限虚空之上就是我们世界的最底部。但不论这里是否是我们灵魂的最终归宿地，这里仍然是一片足以刷新我们认知的世界。也许，这里也有着“祂”的一丝线索。");
        this.addDialog(DOTEEntities.STAR_CHASER, 10, "你知道吗？这个世界的地底下竟然也分布着矿脉！");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 0, "交易技能书");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 1, "交易补给");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 2, "闲聊");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 3, "结束对话");
        
    }
}
