package com.p1nero.dote.datagen.lang;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlocks;
import com.p1nero.dote.client.DOTESounds;
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
        this.add("pack.dote_animation.title", "内置奇妙动画包");

        this.addInfo("tip0", "§4警告！身上存在非法物品！无法进出维度！");
        this.addInfo("tip1", "使用往生精华右键祭坛以召唤 ");
        this.addInfo("tip2", "似乎有种神秘力量阻止你访问此地...");
        this.addInfo("tip3", "你现在不能召唤，周围有怪物在游荡...");
        this.addInfo("tip4", "其他玩家的力量干扰了祭坛的运作...");
        this.addInfo("tip5", "不要贪刀，看准时机闪避或招架，做好准备再来吧…");
        this.addInfo("tip6", "锻造维度专属强力盔甲以提高生存能力，做好准备再来吧…");
        this.addInfo("tip7", "寻找“追星者”学习强力技能以提升战斗能力，做好准备再来吧…");
        this.addInfo("tip9", "你无法逃避来自终焉的决斗...");
        this.addInfo("tip10", "没有任何回应...");
        this.addInfo("tip11", "是时候回去找巴伦了...");
        this.addInfo("tip12", "终焉维度数据已清空");
        this.addInfo("tip13", "我应该先找到巴伦...");

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
        this.addSubtitle(DOTESounds.BIOME_BGM, "圣堂群系bgm");
        this.addSubtitle(DOTESounds.BOSS_FIGHT1, "最终boss战一阶段bgm");
        this.addSubtitle(DOTESounds.BOSS_FIGHT2, "最终boss战二阶段bgm");

        addBiome(DOTEBiomes.AIR, "虚空");
        addBiome(DOTEBiomes.M_BIOME, "圣堂");
        addBiome(DOTEBiomes.P_BIOME, "炼狱");
        
        add("item_group.duel_of_the_end.all", "终焉决斗");
        add(DOTEItems.M_KEY.get(), "圣堂决斗钥匙");
        addItemUsageInfo(DOTEItems.M_KEY.get(), "你...做好准备了吗？");
        add(DOTEItems.P_KEY.get(), "炼狱决斗钥匙");
        addItemUsageInfo(DOTEItems.P_KEY.get(), "所谓的终焉也并非遥不可及...§6（炼狱群系怪物掉率较高）§r");
        add(DOTEItems.U_KEY.get(), "终焉决斗钥匙");
        addItemUsageInfo(DOTEItems.U_KEY.get(), "§6准备好面对“§k祂§r§6”了吗...");
        add(DOTEItems.ADGRAIN.get(), "冒险碎片");
        addItemUsageInfo(DOTEItems.ADGRAIN.get(), "似乎可以用来与他人交易...");
        add(DOTEItems.ADVENTURESPAR.get(), "冒险晶石");
        addItemUsageInfo(DOTEItems.ADVENTURESPAR.get(), "可由冒险碎片合成，也可分解成冒险碎片");
        add(DOTEItems.IMMORTALESSENCE.get(), "往生精华");
        addItemUsageInfo(DOTEItems.IMMORTALESSENCE.get(), "似乎蕴含着某种往生的力量...");
        add(DOTEItems.STAR_CORE.get(), "星辰核心");
        addItemUsageInfo(DOTEItems.STAR_CORE.get(), "似乎蕴含着重启世界的力量...§6你确定要重置数据吗？");
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
        add(DOTEItems.WKNIGHT_INGOT.get(), "白泣锭");
        addItemUsageInfo(DOTEItems.WKNIGHT_INGOT.get(), "可用于锻造台锻造白泣盔甲");
        add(DOTEItems.NETHERROT_INGOT.get(), "腐金锭");
        addItemUsageInfo(DOTEItems.NETHERROT_INGOT.get(), "可用于锻造台锻造腐金盔甲");
        add(DOTEItems.DRAGONSTEEL_INGOT.get(), "龙钢锭");
        addItemUsageInfo(DOTEItems.DRAGONSTEEL_INGOT.get(), "");
        add(DOTEItems.TIESTONEH.get(), "绑石头盔");
        addItemUsageInfo(DOTEItems.TIESTONEH.get(), "套装效果：§6§l石");
        add(DOTEItems.TIESTONEC.get(), "绑石胸甲");
        addItemUsageInfo(DOTEItems.TIESTONEC.get(), "套装效果：§6§l石");
        add(DOTEItems.TIESTONEL.get(), "绑石护腿");
        addItemUsageInfo(DOTEItems.TIESTONEL.get(), "套装效果：§6§l石");
        add(DOTEItems.TIESTONES.get(), "绑石靴子");
        addItemUsageInfo(DOTEItems.TIESTONES.get(), "套装效果：§6§l石");
        add(DOTEItems.WKNIGHT_HELMET.get(), "白泣头盔");
        addItemUsageInfo(DOTEItems.WKNIGHT_HELMET.get(), "套装效果：§l白泣");
        add(DOTEItems.WKNIGHT_CHESTPLATE.get(), "白泣胸甲");
        addItemUsageInfo(DOTEItems.WKNIGHT_CHESTPLATE.get(), "套装效果：§l白泣");
        add(DOTEItems.WKNIGHT_LEGGINGS.get(), "白泣护腿");
        addItemUsageInfo(DOTEItems.WKNIGHT_LEGGINGS.get(), "套装效果：§l白泣");
        add(DOTEItems.WKNIGHT_BOOTS.get(), "白泣靴子");
        addItemUsageInfo(DOTEItems.WKNIGHT_BOOTS.get(), "套装效果：§l白泣");
        add(DOTEItems.NETHERITEROT_HELMET.get(), "腐金头盔");
        addItemUsageInfo(DOTEItems.NETHERITEROT_HELMET.get(), "套装效果：§4§l炎甲");
        add(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "腐金胸甲");
        addItemUsageInfo(DOTEItems.NETHERITEROT_CHESTPLATE.get(), "套装效果：§4§l炎甲");
        add(DOTEItems.NETHERITEROT_LEGGINGS.get(), "腐金护腿");
        addItemUsageInfo(DOTEItems.NETHERITEROT_LEGGINGS.get(), "套装效果：§4§l炎甲");
        add(DOTEItems.NETHERITEROT_BOOTS.get(), "腐金靴子");
        addItemUsageInfo(DOTEItems.NETHERITEROT_BOOTS.get(), "套装效果：§4§l炎甲");
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
        add(DOTEEntities.SENBAI_DEVIL.get(), "森白影魔（？）");
        add(DOTEEntities.GOLDEN_FLAME.get(), "金焰神王（？）");
        add(DOTEEntities.THE_ARBITER_OF_RADIANCE.get(), "圣辉裁决者：Oblivionis");
        add(DOTEEntities.THE_PYROCLAS_OF_PURGATORY.get(), "炼狱炎魔：Mortis");
        add(DOTEEntities.THE_SHADOW_OF_THE_END.get(), "终焉之影：Timoris");
        add(DOTEEntities.GUIDE_NPC.get(), "神秘的向导");
        add(DOTEEntities.KNIGHT_COMMANDER.get(), "圣殿骑士长-巴伦");
        add(DOTEEntities.SCARLET_HIGH_PRIEST.get(), "猩红大祭司-肖恩");

        this.addDialog(DOTEEntities.STAR_CHASER, 0, "哦？一位幸存者，我对你有印象。你在那场战斗之中的表现还远远不够。和那位耀眼的剑客相比，风沙就足以将你轻易掩埋。");
        this.addDialog(DOTEEntities.STAR_CHASER, 1, "干我们这一行，时刻掌握外界的第一手信息是至关重要的！");
        this.addDialog(DOTEEntities.STAR_CHASER, 2, "我成功了，我看到“祂”了！灾难是有名字的！不、不对！这是陷阱！祂注意到我了！我要结束这个魔法才行！");
        this.addDialog(DOTEEntities.STAR_CHASER, 3, "这帮僵尸什么时候学来的这么灵活的身手！除了使得一手好剑还能在混战里互相协作，他们名字里明明带个“僵”字啊？");
        this.addDialog(DOTEEntities.STAR_CHASER, 4, "“祂”并非不可战胜！人类的未来将会如同今夜天上的群星般闪烁！");
        this.addDialog(DOTEEntities.STAR_CHASER, 5, "这就是虚空的力量……多么强大，多么美丽！所谓的终焉也并非遥不可及！");
        this.addDialog(DOTEEntities.STAR_CHASER, 6, "观星者的职责是保护“终焉”之后所有幸存的人类聚落不被毁灭，这也是我们相聚于此的原因。");
        this.addDialog(DOTEEntities.STAR_CHASER, 7, "圣堂里的骑士长巴伦，在§6得到认可后§r可以与他交易更珍贵的道具。");
        this.addDialog(DOTEEntities.STAR_CHASER, 8, "我们成功了！通过将钻石与黄金不断的熔炼与敲打，再加入其他稀有的金属以及我在四处探索时得到的一些晶石碎片，一块漆黑的合金锭在高炉里诞生了！经过商议，我们决定将这种熔炼出来的完美的金属命名为“下界合金”。");
        this.addDialog(DOTEEntities.STAR_CHASER, 9, "根据我的初步调查，这个世界和我们之间的距离比我想象的要近得多，也许这里顶层之外的无限虚空之上就是我们世界的最底部。但不论这里是否是我们灵魂的最终归宿地，这里仍然是一片足以刷新我们认知的世界。也许，这里也有着“祂”的一丝线索。");
        this.addDialog(DOTEEntities.STAR_CHASER, 10, "你知道吗？这个世界的地底下竟然也分布着矿脉！");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 0, "交易技能书");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 1, "交易补给");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 2, "闲聊");
        this.addDialogChoice(DOTEEntities.STAR_CHASER, 3, "结束对话");

        this.addDialog(DOTEEntities.GUIDE_NPC, 0, "噢，又来了一位骁勇善战的战士！想必您舟车劳顿至此一定怀揣着崇高的信念与理想。圣殿就在前方，我们伟大的§6骑士长巴伦§r早已在那里恭候多时了！");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 0, "这是哪，我为什么会出现在这里？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 1, "这里就是终焉前线啊骑士大人，是我们对抗猩红入侵的坚实防线，您一定是受到了圣辉之光的感召最终抵达了这里！");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 1, "圣殿是什么，骑士长又是谁？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 2, "圣殿是专门为奔赴前线的勇士提供试炼的最终关卡。骑士长大人是所有战士的管理者和引路人，负责所有跃跃欲试参与圣辉试炼的战士的统筹工作，他就在§6圣殿的讲台§r上。");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 2, "我现在要做什么？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 3, "您是被圣殿选中对抗猩红入侵的伟大战士！鄙人行动受缚无法为您接风洗尘，请您继续沿道路向前，§6往北§r穿过树林就能看到那座雄伟的神圣的大殿。");

        this.addDialog(DOTEEntities.GUIDE_NPC, 4, "噢，§kBV1v14y1z7MV§r来了一位骁勇善战的战士！想必您舟车劳顿至此一定怀揣着崇高的信念与理想。§k???§r就在前方，我们伟大的§k???§r早已在那里恭候多时了！");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 3, "我们之前不是见过面吗？你不记得了？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 5, "啊？怎么可能呢骑士大人？我可是第一次§k???§r...快快前往圣殿吧，骑士长在那里候你多时了！");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 4, "你的名字到底是什么？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 6, "我就是您这种被圣光感召的战士前往圣殿的向...名字？名字...我为什么§k???§r");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 5, "你为什么被束缚在这里？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 7, "被束缚？我只是...§k???§r我不想...§k???§r");

        this.addDialog(DOTEEntities.GUIDE_NPC, 8, "又来一个....我是到底是谁？我在这里...多久了...");
        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 6, "前往...圣殿？");
        this.addDialog(DOTEEntities.GUIDE_NPC, 9, "圣殿...需要牺牲....");

        this.addDialogChoice(DOTEEntities.GUIDE_NPC, 9, "结束对话");

        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, -1, "结束对话");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, -2, "锻造请求");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 0, "你好，英勇的战士！看来我们的圣殿骑士团又要多出一名沐浴神圣之力的骑士了。愿圣辉之光永远照耀在我们祖辈挥洒鲜血的神圣土壤上！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 0, "圣辉之光的沐浴？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 1, "圣辉之光是圣光之主对每一个完成试炼的虔诚信徒赐予恩惠。祂将强化我们的肉体，庇佑我们的灵魂，让我们成为对抗邪恶猩红入侵的不败战士！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 1, "圣殿最后的试炼？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 2, "每一个跃跃欲试前往前线的战士都必须通过神圣之主的代言人：圣辉裁决者的试炼。这是对你的考验，也是对你的保护！我们需要的是强壮的战士，不是一碰就倒的懦夫！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 2, "我该如何武装自己？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 3, "想必你在到达圣殿之前的路上已经碰到了那些麻烦的不死者。收集他们掉落的冒险碎片，我将为你打造远超凡人铁匠能制造的强大武备！");

        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 4, "你好，英勇的战士！看来我们的圣殿骑士团又要多出一名沐浴神圣之力的§k???§r了。愿§k???§r永远照耀在我们祖辈挥洒鲜血的神圣土壤上！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 3, "巴伦，我到底是第几个到达这里的“战士”？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 5, "当然是第§k???§r个...等等，你怎么知道我叫巴伦？");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 4, "圣辉之种到底是什么？你为何要引导我将其制成那些装备？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 6, "圣辉之种是神圣之主降下伟力的缩影，就是为了制成强大的白泣装甲保护我们忠诚的战士...不对，你为何知道圣辉之种的存在？");

        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 7, "你好，§k???§r！看来我们的§k???§r又要多出一名沐浴神圣之力的§k???§r了。愿§k???§r永远照耀在§k???§r上！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 5, "圣殿只是需要棋子。我只是殉道者，对吗？");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 8, "§k???§r §k???§r §k???§r！！！");

        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 6, "我已经击败了猩红之地的统领，拿到了下界之核！");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 9, "我就知道被圣光之主亲自感召之人并非有名无实！这片土地将再次归于安定祥和。如果你准备好了，我便把你送回初始之地！");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 10, "你看，只要听从圣光指引，这片土地将再次归于安定祥和。如果你准备好了，我便把你送回§k???§r！");
        this.addDialog(DOTEEntities.KNIGHT_COMMANDER, 11, "你看，只要乖乖听从§k???§r，这片§k???§r将再次归于§k???§r。再见，§k???§r！！");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 7, "我准备好了");
        this.addDialogChoice(DOTEEntities.KNIGHT_COMMANDER, 8, "再等等吧");

        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, -1, "结束对话");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, -2, "遗骸交易");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, -1, "见过炎魔大人后再来找我吧。");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 0, "巴伦又派来一个不知好歹的蠢货...我懒得与你多说，炎魔大人下令要亲自会会你这头无药可救的蠢猪。");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 0, "这里是猩红污秽蔓延的中心？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 1, "这里是我们伟大炼狱之主亲赐我们坚守地狱入口的门户，孕育着强大无畏的下界战士！注意你的言辞，道貌岸然的神圣走狗！");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 1, "你说的炎魔大人是什么存在？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 2, "炎魔大人是统领所有下界战士的最高指挥官，他拥有着十分之一伟大炼狱之主的力量恩赐！记住，能死在炎魔大人剑下是你的光荣，你们这些自诩正义的伪君子！");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 2, "那些路上的猪人士兵是什么？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 3, "奋战在最前线的勇士本不该落得这个下场...把他们的遗骸带给我，我会给予你一些不同寻常的材料。");

        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 4, "又来一个不知好歹的蠢货...我懒得与你多说，炎魔大人下令要亲自会会你这头无药可救的蠢猪。");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 3, "你的意思是，你们只是为了抵抗圣辉骑士团对你们的赶尽杀绝？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 5, "这里是我们伟大炼狱之主亲赐我们坚守地狱入口的门户，孕育着强大无畏的下界战士！你们这些道貌岸然的神圣走狗，只是借着看似正义的接口抢夺炎魔大人掌握的§k???§r！");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 4, "终焉之地中心悬浮的紫色岛屿到底是什么存在？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 6, "岛屿...岛屿在哪？终焉战场从来只有你们圣辉大陆和我们炼狱大陆...哪来的什么其他岛屿？终焉战场的中心除了毁灭什么都没有，什么都§k???§r！");

        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 7, "过去吧，炎魔大人在等着你。");
        this.addDialogChoice(DOTEEntities.SCARLET_HIGH_PRIEST, 5, "你记得我？");
        this.addDialog(DOTEEntities.SCARLET_HIGH_PRIEST, 8, "我不记得你。但炎魔大人已经告诉了我一切...我想，我需要安静一会。");


        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 0, "来吧朝圣者，让我试试你的意志与决心！");
        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 1, "来吧§k???§r的灵魂，让我试试你的意志与决心！");
        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 2, "...光与暗...终将合二为一....");

        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 3, "看起来我们又多了一位虔诚英勇的骑士！拿着这颗种子继续前进吧，它是圣光之主力量的化身，将为你扫清前路的黑暗并在关键之时爆发出璀璨夺目的力量！");
        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 4, "看起来我们又多了一位§k???§r的骑士！拿着这颗种子继续前进吧，它是圣光之主力量的化身，将为你扫清前路的黑暗并在关键之时§k???§r！");
        this.addDialog(DOTEEntities.THE_ARBITER_OF_RADIANCE, 5, "看起来我们又多了一位§k???§r的§k???§r！拿着这把§k???§r继续前进吧，它是§k???§r的化身，将为你§k???§r关键之时§k???§r！");

        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 0, "有直面我的勇气，看来你与那些失去神圣加护的懦夫便绝望无神的行尸走肉有着天壤之别。让我试试团灭我下界军团的你到底是愚忠至极还是确有其实！");
        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 1, "熟悉的味道。我明明没有见过你，但为何会觉得你有别于那些失去神圣加护的懦夫便绝望无神的行尸走肉？有趣，甚是有趣！");
        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 2, "...岛上的那位跟你说，我们还要再循环几次？这个世界还要支离破碎几次？！");

        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 3, "那么多圣甲堆成的尸山血河，不就是为了这颗承载炼狱力量的下界之核吗？哈哈哈哈哈哈，被那神圣光辉蛊惑的小辈，我们有缘再见...");
        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 4, "看来你和那些被圣光蛊惑的小辈确有不同...希望你还留着那所谓的圣光之种...这两种§k???§r§k???§r§k???§r§k???§r");
        this.addDialog(DOTEEntities.THE_PYROCLAS_OF_PURGATORY, 5, "终焉已经发现了这里...快去吧，它在等着你...");

        this.addDialog(DOTEEntities.SENBAI_DEVIL, 0, "巴伦...谎言...不要轻信...");
        this.addDialog(DOTEEntities.SENBAI_DEVIL, 1, "灵魂...远离...圣殿...");
        this.addDialog(DOTEEntities.SENBAI_DEVIL, 2, "最后...一次...");

        this.addDialog(DOTEEntities.GOLDEN_FLAME, 0, "种子...核心...终末钥匙...");
        this.addDialog(DOTEEntities.GOLDEN_FLAME, 1, "再次...通过...还有...");
        this.addDialog(DOTEEntities.GOLDEN_FLAME, 2, "终焉...隐秘...");

        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 0, "...不是神圣的走狗或是那蠢货的代言人？有意思...");
        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 1, "...光与暗的融合将再次启迪未竟者的心智...");
        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 2, "..来吧...");
        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 3, "...虚伪的肉体承载着另一个世界的灵魂...我们之后自会有再见之时...");
        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 4, "...终焉的真相...观星者的隐秘...破局之匙...再次...");
        this.addDialog(DOTEEntities.THE_SHADOW_OF_THE_END, 5, "...参见...碎星者...");

    }
}
