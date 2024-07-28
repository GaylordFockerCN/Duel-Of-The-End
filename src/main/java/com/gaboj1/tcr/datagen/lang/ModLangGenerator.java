package com.gaboj1.tcr.datagen.lang;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.effect.TCREffects;
import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.client.TCRModSounds;
import com.gaboj1.tcr.client.keymapping.KeyMappings;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.data.PackOutput;

public class ModLangGenerator extends ModLangProvider {
    public ModLangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {

        this.addEffect(TCREffects.ORICHALCUM, "神金");
        this.addEffect(TCREffects.FLY_SPEED, "御剑飞行");

        this.addTask("kill_boss1", "清道夺邪魔", "杀死密林中危害牧歌原野安全的邪恶树魔");

        this.add("key.categories.tcr", "远梦之棺按键");
        this.addKeyMapping(KeyMappings.RELOAD, "沙鹰换弹");
        this.addKeyMapping(KeyMappings.OPEN_PROGRESS, "打开进度");

        this.add("info.the_casket_of_reveries.enter_forbidden_biome","前面的区域，以后再来探索吧~");
        this.add("info.the_casket_of_reveries.teleport_lock","该区域尚未解锁");
        this.add("info.the_casket_of_reveries.teleport_unlock","成功激活此锚点！");
        this.add("info.the_casket_of_reveries.boss1invincible","摧毁树爪或小怪以解除伊格德拉希尔无敌状态");
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
        this.addConfig("typewriter_effect_speed","打字机效果逐次出现的字数。越大语速越快。");
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
        this.addItemUsageInfo(TCRModItems.HOLY_SWORD.get(),"灵气值 ： %d",3);

        this.add(TCRModItems.COPY_RESIN.get(),"复制树脂");
        this.addItemUsageInfo(TCRModItems.COPY_RESIN.get(),"右键消耗以复制另一只手的物品");

        this.add(TCRModItems.BASIC_RESIN.get(),"初级树脂");
        this.addItemUsageInfo(TCRModItems.BASIC_RESIN.get(),"右键消耗以修复另一只手的物品，每次修复 %d 点耐久度");
        this.add(TCRModItems.INTERMEDIATE_RESIN.get(),"中级树脂");
        this.add(TCRModItems.ADVANCED_RESIN.get(),"高级树脂");
        this.add(TCRModItems.SUPER_RESIN.get(),"超级树脂");

        this.add(TCRModItems.RAW_ORICHALCUM.get(),"粗神金");
        this.addItemUsageInfo(TCRModItems.RAW_ORICHALCUM.get(),"神金，怎么在梦里还要挖矿？");
        this.add(TCRModItems.ORICHALCUM.get(),"神金");
        this.addItemUsageInfo(TCRModItems.ORICHALCUM.get(),"神金，能让人突然想笑一下。");

        this.add(TCRModItems.ORICHALCUM_HELMET.get(),"神金头盔");
        this.add(TCRModItems.ORICHALCUM_CHESTPLATE.get(),"神金胸甲");
        this.add(TCRModItems.ORICHALCUM_LEGGINGS.get(),"神金护腿");
        this.add(TCRModItems.ORICHALCUM_BOOTS.get(),"神金靴子");
        this.addItemUsageInfo(TCRModItems.ORICHALCUM_HELMET.get(),"神金套装，拥有非常高的韧性和击退抗性，但是护甲值较低。");
        this.addItemUsageInfo(TCRModItems.ORICHALCUM_CHESTPLATE.get(),"神金套装，拥有非常高的韧性和击退抗性，但是护甲值较低。");
        this.addItemUsageInfo(TCRModItems.ORICHALCUM_LEGGINGS.get(),"神金套装，拥有非常高的韧性和击退抗性，但是护甲值较低。");
        this.addItemUsageInfo(TCRModItems.ORICHALCUM_BOOTS.get(),"神金套装，拥有非常高的韧性和击退抗性，但是护甲值较低。");


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
        this.add(TCRModItems.ELDER_CAKE.get(),"长老亲手做的蛋糕");
        this.addItemUsageInfo(TCRModItems.ELDER_CAKE.get(),"长老刚做的蛋糕，看起来可以吃得很饱的样子。");
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
        this.add(TCRModBlocks.ORICHALCUM_ORE.get(),"神金矿");
        this.add(TCRModBlocks.YGGDRASIL_BLOCK.get(),"Yggdrasil召唤石");

        this.addEntityAndEgg(TCRModEntities.JELLY_CAT,"猫猫果冻");
        this.addEntityAndEgg(TCRModEntities.SMALL_TREE_MONSTER,"小树妖");
        this.addEntityAndEgg(TCRModEntities.MIDDLE_TREE_MONSTER,"树妖");
        this.addEntityAndEgg(TCRModEntities.SPRITE,"精灵");
        this.addEntityAndEgg(TCRModEntities.TIGER,"老虎");
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
        this.addVillagerChat(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER, 4, true, "你的攻击对我毫无意义！");
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

        //长老
        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,"牧歌原野-海拉长老");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-4,"此外，我推测他们似乎用居民们许愿的力量在做什么，许愿给我们生活保障和愉悦的礼物，但许愿有时却不能灵验，这是以前没有的事");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-3,"密林的魔物曾杀尽了穿过密林将抵达终点的人，只有我逃回了这里，他们在那徘徊太久了，再无人敢靠近，而这里的居民也忘记了为什么要穿达尽头。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-2,"只有梦中才有许愿成真。只有精神才能入梦。我们只是地面人们的投影，但你却是精神与肉体结合的完整之人。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-1,"若你曾冲破世界的蛋壳拥抱地面的身躯，那你一定能帮助我们解决危害着这个村子的密林魔物。恳请您助我们一臂之力!");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"你，与我好像……不对，你不是投影，是完全的人！咳，你不是梦的居民。你一定能帮助我们解决危害着这个村子的密林魔物！");//
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"只有精神才能入梦，我们只是投影，但你却是精神与肉体结合的完整之人。密林的魔物曾杀尽了穿过密林之人，只有我逃回了这里。魔物无时不危害着村民，恳请您助我们一臂之力！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"密林，就在 %s。勇者，千万不要受了那魔物的蛊惑！请收下我的诚意，愿为勇士尽绵薄之力。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"（凝视着你，眼中充满了期待）");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,4,"（目光坚定）你带来的是希望的曙光，旅者。村庄的每个角落都感激你的勇气。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,5,"你的名字将被铭记。请接受我们的祝福，愿你的道路永远光明！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,6,"我将为你指引其他群系的位置。山水画廊（群系名）发生了天灾，位于%s，樱之原野（群系名）似乎在进行大选，位于 %s。亚特兰蒂斯（群系名）嘛，这我就不清楚了，埋藏深海之国，消息比较不灵通。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,111,"你已经收过了哦~");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"请你告诉我前往密林的路径。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"完全的人？魔物？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"继续");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-2,"收下");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-1,"这里的一切都如此熟悉，你可以告诉我更多关于这里的真相吗？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"长老，我已将树魔击败，它不再威胁我们的村庄了。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,4,"我只是做了应该做的事。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,5,"我将去往何方？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,6,"感谢你，长老。");
        //Yggdrasil
        this.addEntityAndEgg(TCRModEntities.YGGDRASIL,"伊格德拉希尔");
        this.addEntityShaderName(TCRModEntities.YGGDRASIL, "伊格德拉希尔-残影");
        this.addDialog(TCRModEntities.YGGDRASIL,0,"哦，可怜的年轻人，还是来了……，不对，你是完整的人");
        this.addDialog(TCRModEntities.YGGDRASIL,1,"将你同化已经是不可能的了。看来只有消灭你了啊孩子……");
        this.addDialog(TCRModEntities.YGGDRASIL,2,"我必须承认，你的能力超出了我的预期。但很遗憾，你的到来打破了我们的平衡。");
        this.addDialog(TCRModEntities.YGGDRASIL,3,"你不明白……你的胜利将会带来灾难。我是孩子们愿望的实现者，他们的欢笑是我力量的源泉。");
        this.addDialog(TCRModEntities.YGGDRASIL,4,"贪婪？不，不，这只是确保我们的世界持续繁荣的必要手段。来的路上，你击败了不少小树魔吧，他们曾经也是可怜的居民啊。");
        this.addDialog(TCRModEntities.YGGDRASIL,5,"我既然无法给他们创造长久的幸福，只能定格他们的年纪。至少让他们短暂的一生是幸福的。我保证，受生命之力同化的他们不曾受到一点痛苦，不过是带着美梦完成重构罢了……");
        this.addDialog(TCRModEntities.YGGDRASIL,6,"而海拉，那个背叛者，他一直在暗中削弱我的力量。但是我无法踏出密林，只有你，强大的冒险者，才能帮我结束这一切！\n");
        this.addDialog(TCRModEntities.YGGDRASIL,7,"是的，海拉一直在欺骗我们。他利用孩子们的愿望，却从未真正实现过。他是个寄生虫，吸取着这片土地的生命力。杀了他，夺取他的力量，我将赋予你更多你无法想象的力量。而杀了我，就是杀了世界尽头的投影，许愿成真的力量将被世界主收回。");
        this.addDialog(TCRModEntities.YGGDRASIL,8,"时间不多了，你必须做出选择。加入我，或者看着这个世界崩溃。");
        this.addDialog(TCRModEntities.YGGDRASIL,9,"如何？我已经迫不及待吸收他的鲜血了！");
        this.addDialog(TCRModEntities.YGGDRASIL,10,"很好，我将赐予你生命之力！");
        this.addDialog(TCRModEntities.YGGDRASIL,11,"享受这强大的力量吧！");
        this.addDialog(TCRModEntities.YGGDRASIL,12,"我可以为你介绍其他地区的现状，我能感应到每个地区都存在着动荡。");
        this.addDialog(TCRModEntities.YGGDRASIL,13,"位于%s的青云之巅（群系名）皇位不保，位于%s的樱之原野（群系名）也有一位智者现世，他将以他的才智狠狠压榨那群傻子。位于%s的亚特兰蒂斯（群系名）嘛，这我就不清楚了，埋藏深海之国，消息比较不灵通");

        this.addDialogChoice(TCRModEntities.YGGDRASIL,-1,"继续");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,0,"什么是同化？？等等……");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,1,"你是谁？这里的平衡又是什么？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,2,"如果真是如此，为何你的眼中只有贪婪和欲望？为什么要吞噬那些孩子们？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,3,"海拉长老？背叛者？");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,4,"你的话听起来更像是在为自己谋利。");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,5,"你以为我会信了你的鬼话？（处决树魔）");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,6,"守护森林我义不容辞！（刺杀海拉）");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,7,"（呈上人头）");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,8,"万分感谢！");
        this.addDialogChoice(TCRModEntities.YGGDRASIL,9,"我将去往何方？");

        this.addBookAndContents("book1","Test","Test1","Test1.1");
        this.addBookAndContents("book2","Test2","Test2","Test2.2");
        this.addBookAndAuthorAndContents("biome1_elder_diary3","海拉","海拉长老的日记3","我收取的生命之力居然让孩子们愿望不再百分百实现。而且没有了使者，这个地方的生命之力就会消失？可怜的孩子啊……不，最可怜的不是我吗？","为什么这样的重任只能我独自承担啊？可我为何再也不敢挺身而出而是一直等待他人的助力？这样的我该怎么回应我长眠的伙伴们");

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
        this.addAdvancement("spend_money_like_water","挥金如土","合成一套神金套装。神金，不拿去换钱却换一个普通的盔甲。");

        this.addBiome(TCRBiomes.PASTORAL_PLAINS,"牧歌原野");
        this.addBiome(TCRBiomes.DENSE_FOREST,"回溯密林");
        this.addBiome(TCRBiomes.SAKURA,"樱之原野");
        this.addBiome(TCRBiomes.DARK_SAKURA,"深暗之樱");
        this.addBiome(TCRBiomes.GALLERY_OF_SERENE,"山水画廊");
        this.addBiome(TCRBiomes.AZURE_SKIES,"青云之巅");
        this.addBiome(TCRBiomes.AQUATIC_MAJESTY,"蓝礁海");
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
