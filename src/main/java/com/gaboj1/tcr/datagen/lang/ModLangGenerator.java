package com.gaboj1.tcr.datagen.lang;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.init.TCRModSounds;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.data.PackOutput;

public class ModLangGenerator extends ModLangProvider {
    public ModLangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {

        this.add("info.the_casket_of_reveries.enter_forbidden_biome","前面的区域，以后再来探索吧~");
        this.add("info.the_casket_of_reveries.teleport_lock","该区域尚未解锁");
        this.add("info.the_casket_of_reveries.teleport_unlock","成功激活此锚点！");
        this.add("info.the_casket_of_reveries.boss1invincible","摧毁树爪以解除伊格德拉希尔无敌状态");
        this.add("info.the_casket_of_reveries.alreadyAddWhite","你已经是村民阵营了，不能伤害村民哦");

        this.add("the_casket_of_reveries.button.boss1","泰兰妮尔之心");
        this.add("the_casket_of_reveries.button.boss2","九霄");
        this.add("the_casket_of_reveries.button.boss3","敬请期待");
        this.add("the_casket_of_reveries.button.boss4","敬请期待");
        this.add("the_casket_of_reveries.button.final","终焉之地");

        this.addConfig("enable_better_structure_block_load","更好的结构方块是否立即刷新（默认开启，开发时关闭）");
        this.addConfig("more_hole","是否启用更多空洞的世界（地图更像空岛，类似天境，但是陆地较少）");
        this.addConfig("enable_scaling","是否启用地图缩放。若启用，则无论用何种尺寸的图片生成的地图大小固定");
        this.addConfig("enable_typewriter_effect","是否启用打字机效果（即对话文本是否逐字出现");
        this.addConfig("typewriter_effect_interval","打字机效果间隔。越大语速越慢。");
        this.addConfig("repair_value","基础树脂的修理值");
        this.addConfig("tree_spirit_wand_hungry_consume","树灵法杖回血时的饥饿值消耗");
        this.addConfig("tree_spirit_wand_heal","树灵法杖回血量");
        this.addConfig("spirit_log_consume","树灵法杖召唤小树妖时消耗的原木数");

        this.add("item_group.the_casket_of_reveries.block","远梦之棺-方块");
        this.add("item_group.the_casket_of_reveries.spawn_egg","远梦之棺-刷怪蛋");
        this.add("item_group.the_casket_of_reveries.loot","远梦之棺-战利品");

        this.add(TCRModItems.DESERT_EAGLE.get(),"沙漠之鹰");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"headshot","爆头！");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"ammo_count","剩余弹药数：");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"ammo_cooldown","冷却时间: ");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"reloadbutton","请按 %s 键换弹");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"no_ammo","无后备弹药!");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"main_hand_ammo","主手剩余弹药:");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"off_hand_ammo","副手剩余弹药:");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"main_ammo_full","主手弹药充足！");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"off_ammo_full","副手弹药充足！");
        this.addItemInfo(TCRModItems.DESERT_EAGLE.get(),"reloading","您正在换弹！");
        this.add(TCRModItems.DESERT_EAGLE_AMMO.get(),"沙鹰弹药");

        this.add(TCRModItems.ELDER_STAFF.get(),"长老的拐杖");
        this.addItemUsageInfo(TCRModItems.ELDER_STAFF.get(),"长老生前用过的拐杖，上面还带着点血迹。");
        this.add(TCRModItems.TREE_DEMON_HORN.get(),"树魔之角");
        this.addItemUsageInfo(TCRModItems.TREE_DEMON_HORN.get(),"树魔头上的角，上面还带着点血迹。");
        this.add(TCRModItems.TREE_SPIRIT_WAND.get(),"树灵法杖");
        this.addItemInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"no_spirit_tree","密林灵树原木不足，无法召唤小树妖");
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"右键地面消耗密林原木和生命值召唤小树妖",1);
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"右键空气消耗饥饿值回血",2);
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"只能使用超级树脂修复",3);


        this.add(TCRModItems.HOLY_SWORD.get(),"额滴圣剑");
        this.addItemUsageInfo(TCRModItems.HOLY_SWORD.get(),"某位高人留下的圣剑",1);

        this.add(TCRModItems.COPY_RESIN.get(),"复制树脂");
        this.addItemUsageInfo(TCRModItems.COPY_RESIN.get(),"右键消耗以复制另一只手的物品");

        this.add(TCRModItems.BASIC_RESIN.get(),"初级树脂");
        this.addItemUsageInfo(TCRModItems.BASIC_RESIN.get(),"右键消耗以修复另一只手的物品，每次修复 %d 点耐久度");
        this.add(TCRModItems.INTERMEDIATE_RESIN.get(),"中级树脂");
        this.add(TCRModItems.ADVANCED_RESIN.get(),"高级树脂");
        this.add(TCRModItems.SUPER_RESIN.get(),"超级树脂");


        this.add(TCRModItems.DREAMSCAPE_COIN.get(),"奇梦币");
        this.addItemUsageInfo(TCRModItems.DREAMSCAPE_COIN.get(),"在梦之领域通用的货币，闪闪发光十分珍贵。九个奇梦币可以合成一个大奇梦币");
        this.add(TCRModItems.DREAMSCAPE_COIN_PLUS.get(),"大奇梦币");
        this.addItemUsageInfo(TCRModItems.DREAMSCAPE_COIN_PLUS.get(),"在梦之领域通用的货币，闪闪发光十分珍贵。一个大奇梦币可以分解成九个奇梦币");

        this.add(TCRModItems.BLUE_BANANA.get(),"蓝蕉");
        this.addItemUsageInfo(TCRModItems.BLUE_BANANA.get(),"蓝色的香蕉品种，不知道有什么特殊含义。");
        this.add(TCRModItems.DREAM_TA.get(),"梦达");//芬达美年达打钱
        this.addItemUsageInfo(TCRModItems.DREAM_TA.get(),"一种老少皆宜的气泡饮料，好像在哪里听过这个名字？『喝上这瓶梦达~欢乐美梦速速达~』");
        this.add(TCRModItems.BEER.get(),"啤酒");
        this.addItemUsageInfo(TCRModItems.BEER.get(),"小孩子不能喝！");
        this.add(TCRModItems.COOKIE.get(),"曲奇");
        this.addItemUsageInfo(TCRModItems.COOKIE.get(),"很普通但是很美味");
        //说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！免责声明（划掉））
        this.add(TCRModItems.EDEN_APPLE.get(), "伊甸苹果");
        this.addItemUsageInfo(TCRModItems.EDEN_APPLE.get(), "一种神秘的苹果，传说中带有无限的力量。");
        this.add(TCRModItems.DRINK1.get(), "清凉神仙水");
        this.addItemUsageInfo(TCRModItems.DRINK1.get(), "一种清凉解渴的饮料，适合夏日消暑。");
        this.add(TCRModItems.DRINK2.get(), "果果味汽泡妖精水");
        this.addItemUsageInfo(TCRModItems.DRINK2.get(), "一种充满果香的饮料，口感甘甜可口。");
        this.add(TCRModItems.GOLDEN_WIND_AND_DEW.get(), "金风玉露液");
        this.addItemUsageInfo(TCRModItems.GOLDEN_WIND_AND_DEW.get(), "一种传说中的仙饮，据说能赋予长生不老之力。");
        this.add(TCRModItems.GREEN_BANANA.get(), "蕉绿");
        this.addItemUsageInfo(TCRModItems.GREEN_BANANA.get(), "一种独特的绿色香蕉，富含维生素和矿物质。但是看起来吃了会不开心。");
        this.add(TCRModItems.HOT_CHOCOLATE.get(), "热巧克力");
        this.addItemUsageInfo(TCRModItems.HOT_CHOCOLATE.get(), "一种暖身的饮品，深受寒冷天气中人们的喜爱。");
        this.add(TCRModItems.JUICE_TEA.get(), "果果味茶香魔法药水");
        this.addItemUsageInfo(TCRModItems.JUICE_TEA.get(), "其实就是一种结合了果汁和茶叶的饮品，清新美味。");
        this.add(TCRModItems.MAO_DAI.get(), "茅台仙酒");
        this.addItemUsageInfo(TCRModItems.MAO_DAI.get(), "一种名贵的白酒，香气浓郁，口感醇厚。『一口喝下，仿佛坐上了云端，与仙女共饮琼浆，世间烦恼尽皆飘散。』");
        this.add(TCRModItems.PINE_CONE.get(), "松果");
        this.addItemUsageInfo(TCRModItems.PINE_CONE.get(), "一种装饰和美食兼备的植物果实，常用于节日装饰。");
        this.add(TCRModItems.RED_WINE.get(), "红酒");
        this.addItemUsageInfo(TCRModItems.RED_WINE.get(), "一种经过发酵的葡萄酒，风味独特，适合与美食搭配。");

        this.add(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get(),"更好的结构方块");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get(),"密林灵叶");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get(),"灵木木板");
        this.add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get(),"去皮密林木头");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(),"密林木头");
        this.add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get(),"去皮密林原木");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get(),"密林原木");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),"密林灵花");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get(),"密林灵树树苗");
        this.add(TCRModBlocks.PORTAL_BED.get(),"灵梦床");//等等，灵梦？
        this.add(TCRModBlocks.PORTAL_BLOCK.get(),"天域凭证");//传送石碑
        this.add(TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(),"密林灵花盆栽");
        this.add(TCRModBlocks.YGGDRASIL_BLOCK.get(),"Yggdrasil召唤石");

        this.addEntityAndEgg(TCRModEntities.JELLY_CAT,"猫猫果冻");
        this.addEntityAndEgg(TCRModEntities.SMALL_TREE_MONSTER,"小树妖");
        this.addEntityAndEgg(TCRModEntities.MIDDLE_TREE_MONSTER,"树妖");
        this.addEntityAndEgg(TCRModEntities.SQUIRREL,"松鼠");
        this.addEntityAndEgg(TCRModEntities.TREE_GUARDIAN,"森林守护者");
        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER,"牧歌原野-村民");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 0, false, "今天天气真好，让我们一起飞快地跑！");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 1, false, "小猫……小斗（狗）……今天是要养只小猫还是小斗？");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 2, false, "今天要和小美去放烟花，你要一起吗！");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 3, false, "异乡人，这么着急地去干嘛呀？");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 4, false, "嘘……你知道前面那片森林有些古怪的东西吗？千万不要进去！从来没看到有人从那片森林走出来过……");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 5, false, "阿飘，阿飘！呜呜呜我再也不敢靠近森林一步了……");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 0, true, "你脑子被约翰叔叔家的驴踢了");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 1, true, "你的思维似乎被彼得大叔的鹦鹉弄乱了");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 2, true, "你的判断力就像艾米丽小姐家的猫一样混乱");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 3, true, "你的决策能力仿佛受到了汤姆先生的鹅的影响");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 4, true, "你的逻辑就像威廉先生的羊一样跑偏");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER, 5, true, "你的观点让人想起了安妮小姐家的鸭子闹剧");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 0, true, "暴力解决不了问题！");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 1, true, "你为何选择这样的道路？");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 2, true, "即使受到攻击，我也不会动摇我的信念！");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 3, true, "暴力是愚蠢的选择，它只会导致更多的痛苦！");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 4, true, "你为何选择这样的道路？");
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 5, true, "收手吧！让我们冷静下来，找到解决问题的方法，而不是互相伤害！");

        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,"牧歌原野-村民");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-1,"牧歌原野-舞女");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-2,"牧歌原野-服务生");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-3,"牧歌原野-商人");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-4,"牧歌原野-商人");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,0,"牧歌原野-商人");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,1,"牧歌原野-工匠");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,"牧歌原野-学者");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3,"牧歌原野-牧羊人");
        this.addEntityType(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,4,"牧歌原野-猎人");

        //商人
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-3,"这里的人们都是能幸福地许愿日常用品。至于我为什么要当商人？只是享受这种以物易物的感觉");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-2,"最近来找我交易的人变多了。许愿的能力为什么……哦有什么事吗");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-1,"很欢迎你来与我交易");
        //工匠
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,0,"人们都以我的手艺自豪呢。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,1,"我可是能像许愿一样满足大家的愿望呢，你要不要试试啊？");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,"刚刚那把火铳是我加工许愿物得到的家伙。即便如此，也别对许愿成真太贪心了");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3,"......");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,4,"我赌你的枪里没有子弹！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,5,"不要拿去干坏事哦~");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,1,1,"我可是能创造所有人希冀的奇迹，交给我吧！");
        //学者
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,7,"抱歉没注意到你，我一思考就容易陷进去了呢，哈哈");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,8,"知识从不对人吝啬");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,9,"愿我的智慧为你扫开前路");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2, 1,"哦？新面孔。你也是来听长老讲述大地的故事吗？");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2, 2,"长老说我们的双脚要从轻飘飘的空中落到大地上。大地上有着半人高的蜘蛛，跑得飞快黑瘦人，但是PVP大佬会通通横扫干净。在那里有地底的宝藏，异世界召唤的巨龙，都是火的下界。怎么样，是不是很新奇");
        //牧羊人
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,10,"云朵和羊…都是软绵绵的，要跟我一起躲在云里晒太阳吗");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,11,"一只羊，两只羊…….抱歉又睡着了，没给你添麻烦吧");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,12,"真是有干劲啊，那我也要全力以赴了，朋友");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3, 1,"异乡人，你来自何方？可以和我说说你的过往吗？");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3, 2,"异乡人，你怎么了？");
        this.addVillagerDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3, 3,"啊，欸，很厉害的样子");
        //猎人
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,13,"今天出去解决了几只小“猎物”呢");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,14,"要来和我比赛打猎吗，您看上去可是相当强大啊，真是按耐不住想比试的心");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,15,"我这边可是有很多帮助您打猎的好东西呢，呵呵");
        //舞者
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,16,"嘿，你是来看我跳舞的吗？在这个小镇上我可是最出名的舞者哦。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,17,"当然可以！这支舞是我从外地学来的，希望你喜欢！顺便问一下，你是来这里做什么的呢？");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,18,"哦，那你一定要小心，那些传说听起来让人直做噩梦。 ");
        //侍者
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,19,"哦，你是新来的旅人吗？欢迎来到我们的小镇！需要我为你倒杯酒吗？");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,20,"当然，这是我们特制的饮料，品尝一下吧！顺便问一句，你是来冒险的还是仅仅路过这里？");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,21,"那座教堂？嘘……你知道前面那片森林有些古怪的东西吗？千万不要进去！从来没看到有人从那片森林走出来过…… ");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,22,"（你已经白嫖过饮料了~）");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,23,"想喝点什么？");

        //商人
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,-3,"我想买点东西");
        //工匠
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,0,"哦？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,1,"我也可以想要什么有什么吗？我想要一只火枪！");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,"【获得 火铳*1 弹药*20】");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3,"我还想要个女朋友！");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,4,"......");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,5,"快给我，不然嘣了你");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,6,"谢谢你的火铳~");
        //学者
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,7,"询问");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,1,"大地？");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,2,"长老？");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,3,"妈妈我上电视了！");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,2,4,"天啊，这简直就是我");

        //牧羊人
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,10,"我要去忙了");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3,1,"我的过往……我的过往……为何这里让我……");
        this.addVillagerDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,3,2,"站在你面前的是——村庄英雄，下界统帅，伟大的遗迹探险家，英勇的巨龙讨伐者，穿越无数世界的旅者是也！");
        //猎人
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,13,"交易");
        //舞者
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,16,"你跳的舞一定很精彩");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,17,"我是来探险的，听说这个小镇有很多古老的传说。");
        //侍者
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,19,"谢谢，我想来一杯你们这里最畅销的饮料。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,20,"我是来探险的，听说这附近有个古老的教堂，我想去看看。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER,21,"购买饮料");

        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,"牧歌原野-海拉长老");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-3,"密林的魔物曾杀尽了穿过密林将抵达终点的人，只有我逃回了这里，他们在那徘徊太久了，再无人敢靠近，而这里的居民也忘记了为什么要穿达尽头。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-2,"只有梦中才有许愿成真。只有精神才能入梦。我们只是地面人们的投影，但你却是精神与肉体结合的完整之人。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-1,"若你曾冲破世界的蛋壳拥抱地面的身躯，那你一定能帮助我们解决危害着这个村子的密林魔物。恳请您助我们一臂之力!");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"你，与我好像……不对，你不是投影，是完全的人！咳，你不是梦的居民。如果可以，请你倾听我这十年来的心在仇恨的尖刀上是如何滴血！");//
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"密林，就在 %s 你要睁大眼睛侧起耳朵开动脑筋来揣度密林中提示之物的含义。你要是有心，那么就趁有心之时让它发挥作用。对了，尊敬的勇者……");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"算了，有的东西不过很久，是不可能理解的。 有的东西等到理解了，又为时已晚。 大多时候，我们不得不在尚未清楚认识自己的心的情况下选择行动，因而感到迷惘和困惑，因而悲伤不已。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"有些事情还无法言喻，有的则不便言喻。但你什么也不必担心。在某种意义上，村子和密林都是公平的。关于你所需要你所应该知道的，村子和密林以后将一一在你面前提示出来。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,4,"你已然知晓了一切。村子所祈祷的力量来自于世界主和四座使者……这点我没告诉你，而是寄托于命运的安排……");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,5,"这是我的懦弱，是我在逃避对于村子的责任。我收取生命之力却不敢踏足密林，我发誓，村里的孩子将由我来见证他们的终点，由我来引领他们的成长。到那时，我也能回到我应有的身躯上。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,6,"我将担负起我的使命，用我的生命发誓！");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"请你告诉我前往密林的路径。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"完全的人？魔物？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"继续");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-1,"结束对话");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"这一切树魔已经都告诉我了……");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,4,"所谓进化就是这么回事，进化总是苦涩而寂寞的。不可能有令人心旷神怡的进化。进化是严峻的。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,5,"嗯？");

        //Yggdrasil
        this.addEntityAndEgg(TCRModEntities.YGGDRASIL,"伊格德拉希尔");
        this.addDialog(TCRModEntities.YGGDRASIL,0,"可怜的年轻人，你还是来了……");
        this.addDialog(TCRModEntities.YGGDRASIL,1,"不对，你的气息和普通的孩子都不一样！你竟然已经成长到如此地步。");
        this.addDialog(TCRModEntities.YGGDRASIL,2,"将你同化已经是不可能的了。看来只有消灭你了啊孩子……");
        this.addDialog(TCRModEntities.YGGDRASIL,3,"你是村子以前的孩子吧……你能突破我的限制回到世界尽头也着实厉害");
        this.addDialog(TCRModEntities.YGGDRASIL,4,"哈哈，我用了极大代价恳请世界主做的抹除记忆还是成功的。都到这个地步了，我就将一切告诉你吧……");
        this.addDialog(TCRModEntities.YGGDRASIL,5,"在来的路上，你击杀了不少小树魔吧，他们曾经也是可怜的孩童啊。我既然无法给他们创造长久的幸福，只能将他们定格在二十来岁的年纪了，这样，至少他们短暂的一生是幸福的。");
        this.addDialog(TCRModEntities.YGGDRASIL,6,"我保证，我将他们同化的时候他们不曾受到一点痛苦，他们不过是带着美梦完成重构罢了……");
        this.addDialog(TCRModEntities.YGGDRASIL,7,"你要知道，我利用我独特的生命之力与世界尽头已融为一体，孩子们所祈祷的东西都经由我的力量创造。我无所谓他们对生命之源密林的恐惧，我也无需他们的爱戴，我只希望孩子们快乐啊……");
        this.addDialog(TCRModEntities.YGGDRASIL,8,"是的。杀了我，就是杀了世界尽头。是要毁灭孩子们们的美梦，还是去杀了那个村子中格格不入的美梦破坏者，你知道我说的是谁……世界尽头的未来，就系在你的剑上了。");
        this.addDialog(TCRModEntities.YGGDRASIL,9,"欢迎你，与我们一同创造一个美好的世界……");
        this.addDialog(TCRModEntities.YGGDRASIL,10,"想知道答案的话，用村长的鲜血来换取吧……");
        this.addDialog(TCRModEntities.YGGDRASIL,11,"早在成为生命使者的时候，我已经用我的血生成誓言，不得踏入世界尽头一步。这也是每一任守护者都必须遵守的规则……");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,-3,"结束对话");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,-2,"查看真相");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,-1,"下一句");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,0,"什么是同化？？等等……");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,1,"村子以前的孩子？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,2,"所以，杀了你，孩子们依赖的祈祷将再也发挥不了作用？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,3,"你们？你们是谁？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,4,"(冷笑)对于你而言，杀死村长不是轻而易举的事情吗？");


        this.addBookAndContents("book1","Test","Test1","Test1.1");
        this.addBookAndContents("book2","Test2","Test2","Test2.2");
        this.addBookAndAuthorAndContents("biome1_elder_diary3","海拉","海拉长老的日记3","那个怪物说的话是真的吗？怪物竟然是守护着我们的守护神？没有了它，这个地方的生命之力就会消失？那孩子们要怎么活下去啊？真该死啊！","是愿望？还是生存？这是我能决定的吗？可怜的孩子啊……不，最可怜的不是我吗？为什么这样的重任只能我独自承担啊？我长眠的伙伴们……");

        this.addAdvancement(TheCasketOfReveriesMod.MOD_ID,"远梦之棺","> <");
        this.addAdvancement("enter_realm_of_the_dream","梦之领域","进入梦之领域");
        this.addAdvancement("wow","嘿！","砍伐密林灵木时小树妖突然出现！");
        this.addAdvancement("try_wake_up","梦中梦？","尝试在梦之领域使用灵梦床");
        this.addAdvancement("shoot_hundred_meters","百步穿杨！","射中一百米外的玻璃或者生物实体");
        this.addAdvancement("melee_mage","近战法师！","使用树灵法杖近战攻击");
        this.addAdvancement("mass_production","量产！","使用复制树脂成功复制一件物品");
        this.addAdvancement("so_rich","有钱真好","使用复制树脂成功复制一份空气");
        this.addAdvancement("die_for_summon","一命换一命","用生命值召唤小树妖而死");
        this.addAdvancement("day_dreamer","白日梦想家","通过村民帮助获得火铳");
        this.addAdvancement("can_double_hold","原来这也行？","双持火铳");
        this.addAdvancement("cats_friend","猫猫之友","驯服所有种类的猫猫果冻");

        this.addBiome(TCRBiomes.PASTORAL_PLAINS,"牧歌原野");
        this.addBiome(TCRBiomes.DENSE_FOREST,"回溯密林");
        this.addBiome(TCRBiomes.SAKURA,"樱之原野");
        this.addBiome(TCRBiomes.DARK_SAKURA,"深暗之樱");
        this.addBiome(TCRBiomes.GALLERY_OF_SERENE,"山水画廊");
        this.addBiome(TCRBiomes.AZURE_SKIES,"青云之巅");
        this.addBiome(TCRBiomes.AQUATIC_MAJESTY,"威严之水");
        this.addBiome(TCRBiomes.ATLANTIS,"亚特兰蒂斯");
        this.addBiome(TCRBiomes.AIR,"空");
        this.addBiome(TCRBiomes.FINAL,"终焉之地");

        this.addSubtitle(TCRModSounds.DESERT_EAGLE_FIRE,"沙鹰开火");
        this.addSubtitle(TCRModSounds.DESERT_EAGLE_RELOAD,"沙鹰换弹");
        this.addSubtitle(TCRModSounds.TREE_MONSTERS_DEATH,"树怪死亡");
        this.addSubtitle(TCRModSounds.TREE_MONSTERS_HURT,"树怪受伤");
        this.addSubtitle(TCRModSounds.YGGDRASIL_AMBIENT_SOUND,"伊格德拉希尔叫声");
        this.addSubtitle(TCRModSounds.YGGDRASIL_CRY,"伊格德拉希尔:嚎啕大哭");
        this.addSubtitle(TCRModSounds.YGGDRASIL_ATTACK_ONE,"伊格德拉希尔:发起攻击");
        this.addSubtitle(TCRModSounds.YGGDRASIL_ATTACK_TWO,"伊格德拉希尔:发起攻击");
        this.addSubtitle(TCRModSounds.YGGDRASIL_RECOVER_LAUGHTER,"伊格德拉希尔:治愈一笑");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_HELLO, "女村民：你好");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_EI, "女村民：嗯");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_HI, "女村民：嗨");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_HI_THERE, "女村民：嘿那里");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_HENG, "女村民：哼");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_DEATH, "女性村民死亡");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_HURT, "女性村民受伤");
        this.addSubtitle(TCRModSounds.FEMALE_VILLAGER_OHAYO, "女性村民：早上好");
        this.addSubtitle(TCRModSounds.FEMALE_HENG, "女性NPC吼叫");
        this.addSubtitle(TCRModSounds.FEMALE_SIGH, "女性NPC叹息");
        this.addSubtitle(TCRModSounds.MALE_DEATH, "男村民死亡");
        this.addSubtitle(TCRModSounds.MALE_HI, "男村民：嗨");
        this.addSubtitle(TCRModSounds.MALE_EYO, "男村民：哟！");
        this.addSubtitle(TCRModSounds.MALE_GET_HURT, "男村民受伤");
        this.addSubtitle(TCRModSounds.MALE_HENG, "男村民：哼");
        this.addSubtitle(TCRModSounds.MALE_SIGH, "男村民：叹气");
        this.addSubtitle(TCRModSounds.MALE_HELLO, "男村民：你好");
        this.addSubtitle(TCRModSounds.JELLY_CAT_AMBIENT, "猫猫果冻叫声");
        this.addSubtitle(TCRModSounds.JELLY_CAT_HURT, "猫猫果冻受伤");
        this.addSubtitle(TCRModSounds.JELLY_CAT_DEATH, "猫猫果冻死亡");
    }
}
