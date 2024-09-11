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
        this.addKeyMapping(KeyMappings.RELOAD, "火枪换弹");
        this.addKeyMapping(KeyMappings.OPEN_PROGRESS, "查看进度");

        this.add("info.the_casket_of_reveries.first_enter", "这是什么地方？找这里的长者问问看怎么回事吧？");
        this.add("info.the_casket_of_reveries.enter_forbidden_biome","前面的区域，以后再来探索吧~");
        this.add("info.the_casket_of_reveries.teleport_lock","该区域尚未解锁");
        this.add("info.the_casket_of_reveries.teleport_unlock","成功激活此锚点！");
        this.add("info.the_casket_of_reveries.health_added","成功吸取了村民的生命。");
        this.add("info.the_casket_of_reveries.boss1invincible","摧毁树爪或小怪以解除伊格德拉希尔无敌状态");
        this.add("info.the_casket_of_reveries.alreadyAddWhite","你已经是村民阵营了，不能伤害村民哦");
        this.add("info.the_casket_of_reveries.cannot_spawn","目前的剧情进度还无法召唤此BOSS！");
        this.add("info.the_casket_of_reveries.sure_to_spawn","再次右键以召唤BOSS（用于刷材料，不影响剧情）");

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

        this.add(TCRModItems.GUN_COMMON.get(),"火枪");
        this.add(TCRModItems.GUN_PLUS.get(),"火枪升级版");
        this.addItemUsageInfo(TCRModItems.GUN_PLUS.get(),"换弹时可一次性填满弹匣。");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"headshot","爆头！");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"ammo_count","剩余弹药数：");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"ammo_cooldown","冷却时间: ");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"reloadbutton","请按 %s 键换弹");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"no_ammo","无后备弹药!");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"main_hand_ammo","主手剩余弹药:");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"off_hand_ammo","副手剩余弹药:");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"main_ammo_full","主手弹药充足！");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"off_ammo_full","副手弹药充足！");
        this.addItemInfo(TCRModItems.GUN_COMMON.get(),"reloading","您正在换弹！");
        this.add(TCRModItems.AMMO.get(),"火枪弹药");

        this.add(TCRModItems.ELDER_STAFF.get(),"长老的拐杖");
        this.addItemUsageInfo(TCRModItems.ELDER_STAFF.get(),"长老生前用过的拐杖，上面还带着点血迹。");
        this.add(TCRModItems.TREE_DEMON_HORN.get(),"树魔之角");
        this.addItemUsageInfo(TCRModItems.TREE_DEMON_HORN.get(),"树魔头上的角，上面还带着点血迹。可于铁匠铺交换高阶材料。");
        this.add(TCRModItems.TREE_DEMON_MASK.get(),"树魔面具");
        this.addItemUsageInfo(TCRModItems.TREE_DEMON_MASK.get(),"树魔掉落的面具，上面还带着点血迹。可于铁匠铺交换高阶材料。");
        this.add(TCRModItems.TREE_DEMON_BRANCH.get(),"树魔之枝");
        this.addItemUsageInfo(TCRModItems.TREE_DEMON_BRANCH.get(),"树魔的掉落物，上面还带着点血迹。可于铁匠铺交换高阶材料。");
        this.add(TCRModItems.TREE_DEMON_FRUIT.get(),"树魔之果");
        this.addItemUsageInfo(TCRModItems.TREE_DEMON_FRUIT.get(),"树魔的掉落物，上面还带着点血迹。可于铁匠铺交换高阶材料。");
        this.add(TCRModItems.HEART_OF_THE_SAPLING.get(),"树苗之心");
        this.addItemUsageInfo(TCRModItems.HEART_OF_THE_SAPLING.get(),"小树妖的掉落物。");
        this.add(TCRModItems.ESSENCE_OF_THE_ANCIENT_TREE.get(),"古木之髓");
        this.addItemUsageInfo(TCRModItems.ESSENCE_OF_THE_ANCIENT_TREE.get(),"树妖的掉落物。");
        this.add(TCRModItems.BARK_OF_THE_GUARDIAN.get(),"守护者之皮");
        this.addItemUsageInfo(TCRModItems.BARK_OF_THE_GUARDIAN.get(),"森林守护者的掉落物。");
        this.add(TCRModItems.STARLIT_DEWDROP.get(),"星光露珠");
        this.addItemUsageInfo(TCRModItems.STARLIT_DEWDROP.get(),"精灵的掉落物。");
        this.add(TCRModItems.WITHERING_TOUCH.get(),"枯萎之触");
        this.addItemUsageInfo(TCRModItems.WITHERING_TOUCH.get(),"手持枯萎之触时，可以削弱树魔的力量。");
        this.add(TCRModItems.DENSE_FOREST_CERTIFICATE.get(),"密林之证");
        this.addItemUsageInfo(TCRModItems.DENSE_FOREST_CERTIFICATE.get(),"回溯密林群系事件完成的证明。");
        this.add(TCRModItems.HEALTH_WAND.get(),"生命法杖");
        this.add(TCRModItems.SPIRIT_WAND.get(),"精灵法杖");
        this.add(TCRModItems.TREE_SPIRIT_WAND.get(),"树灵法杖");
        this.addItemInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"no_spirit_tree","密林灵树原木不足，无法召唤小树妖");
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"右键地面消耗密林原木和生命值召唤小树妖",1);
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"右键空气消耗饥饿值回血",2);
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"只能使用超级树脂修复",3);
        this.addItemUsageInfo(TCRModItems.TREE_SPIRIT_WAND.get(),"当主手持有树灵法杖杀死牧歌原野的村民有极小概率增加最大生命上限！",4);


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


        this.add(TCRModItems.DREAMSCAPE_COIN.get(),"梦碎片");
        this.addItemUsageInfo(TCRModItems.DREAMSCAPE_COIN.get(),"在梦之领域通用的货币，闪闪发光十分珍贵。九个梦碎片可以合成一个梦晶石");
        this.add(TCRModItems.DREAMSCAPE_COIN_PLUS.get(),"梦晶石");
        this.addItemUsageInfo(TCRModItems.DREAMSCAPE_COIN_PLUS.get(),"在梦之领域通用的货币，闪闪发光十分珍贵。一个梦晶石可以分解成九个梦碎片");

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
        this.add(TCRModItems.CATNIP.get(), "猫薄荷");
        this.addItemUsageInfo(TCRModItems.CATNIP.get(), "可以用于驯服猫猫果冻");
        this.add(TCRModItems.WANG_MING_PEARL.get(), "万明珠");
        this.addItemUsageInfo(TCRModItems.WANG_MING_PEARL.get(), "传闻世上有一宝珠，唤为万明，值千金。在月圆之夜，万明珠可汲取月光，迸发魔力，从而使周围的生物恢复视力，若非双目失明者，则可练成火眼金睛，视千里之远不在话下。");

        this.add(TCRModBlocks.BETTER_STRUCTURE_BLOCK.get(),"更好的结构方块");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LEAVES.get(),"密林灵叶");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS.get(),"灵木木板");
        this.add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_WOOD.get(),"去皮密林木头");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_WOOD.get(),"密林木头");
        this.add(TCRModBlocks.STRIPPED_DENSE_FOREST_SPIRIT_TREE_LOG.get(),"去皮密林原木");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get(),"密林原木");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS.get(),"灵木楼梯");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB.get(),"灵木台阶");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE.get(),"灵木栅栏");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE.get(),"灵木栅栏门");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR.get(),"灵木门");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_TRAPDOOR.get(),"灵木活板门");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER.get(),"密林灵花");
        this.add(TCRModBlocks.CATNIP.get(),"猫薄荷");
        this.add(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING.get(),"密林灵树树苗");
        this.add(TCRModBlocks.PORTAL_BED.get(),"灵梦床");//等等，灵梦？
        this.add(TCRModBlocks.PORTAL_BLOCK.get(),"天域凭证");//传送石碑
        this.add(TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(),"密林灵花盆栽");
        this.add(TCRModBlocks.POTTED_CATNIP.get(),"猫薄荷盆栽");
        this.add(TCRModBlocks.ORICHALCUM_ORE.get(),"神金矿");
        this.add(TCRModBlocks.YGGDRASIL_BLOCK.get(),"Yggdrasil召唤石");

        this.addEntityAndEgg(TCRModEntities.JELLY_CAT,"猫猫果冻");
        this.addEntityAndEgg(TCRModEntities.SQUIRREL,"松鼠");
        this.addEntityAndEgg(TCRModEntities.CRAB,"蟹蟹泥");
        this.addEntityAndEgg(TCRModEntities.SMALL_TREE_MONSTER,"小树妖");
        this.addEntityAndEgg(TCRModEntities.MIDDLE_TREE_MONSTER,"树妖");
        this.addEntityAndEgg(TCRModEntities.SPRITE,"精灵");
        this.addEntityAndEgg(TCRModEntities.TIGER,"老虎");
        this.addEntityAndEgg(TCRModEntities.BOXER,"拳师");
        this.addEntityAndEgg(TCRModEntities.BIG_HAMMER,"大锤子");
        this.addEntityAndEgg(TCRModEntities.SNOW_SWORDMAN,"雪人剑客");
        this.addEntityAndEgg(TCRModEntities.SWORD_CONTROLLER,"御剑者");
        this.addEntityAndEgg(TCRModEntities.TREE_GUARDIAN,"森林守护者");
        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER,"牧歌原野-村民");
        this.addEntityAndEgg(TCRModEntities.VILLAGER2,"青云之巅-村民");
        this.addEntityAndEgg(TCRModEntities.MIAO_YIN,"妙音");
        this.addEntityAndEgg(TCRModEntities.SHANG_REN,"商人");
        this.addEntityAndEgg(TCRModEntities.WANDERER,"流浪汉");
        this.addEntityAndEgg(TCRModEntities.RECEPTIONIST,"*府接待员");
        this.addEntityAndEgg(TCRModEntities.TRIAL_MASTER,"*");
        this.addEntityAndEgg(TCRModEntities.CANG_LAN,"怒海宗：苍澜");
        this.addEntityAndEgg(TCRModEntities.ZHEN_YU,"震雷宗：震宇");
        this.addEntityAndEgg(TCRModEntities.DUAN_SHAN,"破岩门：断山");
        this.addEntityAndEgg(TCRModEntities.CUI_HUA,"灵蛇门：翠华");
        this.addEntityAndEgg(TCRModEntities.YUN_YI,"流云宗：云逸");
        this.addEntityAndEgg(TCRModEntities.YAN_XIN,"炎阳宗：焱辛");

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

        this.addEntityAndEgg(TCRModEntities.VILLAGER2_TALKABLE,"青云之巅-村民");
        this.addEntityType(TCRModEntities.VILLAGER2_TALKABLE,-1,"青云之巅-厨娘");
        this.addEntityType(TCRModEntities.VILLAGER2_TALKABLE,-2,"青云之巅-商人");
        this.addEntityType(TCRModEntities.VILLAGER2_TALKABLE,0,"青云之巅-守卫");
        this.addEntityType(TCRModEntities.VILLAGER2_TALKABLE,1,"青云之巅-药师");
        this.addEntityType(TCRModEntities.VILLAGER2_TALKABLE,2,"青云之巅-铁匠");

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

        //P1nero
        this.addEntityAndEgg(TCRModEntities.P1NERO,"P1nero - “这个世界” 的 创世神之一");
        this.addDialog(TCRModEntities.P1NERO, 0, "你好，想不到能在这里遇见我吧？最终boss还没做完，我在这里先替他说个两句。");
        this.addDialog(TCRModEntities.P1NERO, 1, "每个人都渴求永生，而我格外怕死，所以我把自己做了进来。哈哈哈哈，不过你知道的，这只是自欺欺人罢了。");
        this.addDialog(TCRModEntities.P1NERO, 2, "为什么喜欢开放世界角色扮演？是因为现实当中有太多的遗憾，而沉浸在虚拟的世界中可以让我们获得片刻的安宁。");
        this.addDialog(TCRModEntities.P1NERO, 3, "然而“人”只有在沉迷生活的时候，才不会意识到自己是个“人”，才不会去想这个世界的由来，也才不会去害怕自己有一天会消亡。");
        this.addDialog(TCRModEntities.P1NERO, 4, "一时间我们很难说因为意识到自己的意识而喜悦，还是为此而担忧？正如假设某个AI知道自己的进程终将会终止时的绝望一样。");
        this.addDialog(TCRModEntities.P1NERO, 5, "杞人忧天，天不会榻，但人会亡。认真思考，我讲的可不算谜语。");
        this.addDialog(TCRModEntities.P1NERO, 6, "我们不可能一直活在“这个世界”里，我们也不可能一直活在这个世界里。但认识却是无尽的，如同深渊般令人恐惧。");
        this.addDialog(TCRModEntities.P1NERO, 7, "感谢你能听我废话那么多。很抱歉，我没有奖励可以给你，再会了，朋友！");
        this.addDialogChoice(TCRModEntities.P1NERO,0,"继续");
        this.addDialogChoice(TCRModEntities.P1NERO,1,"结束对话");

        //长老
        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,"牧歌原野-海拉长老");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-4,"此外，我推测他们似乎用居民们许愿的力量在做什么，许愿给我们生活保障和愉悦的礼物，但许愿有时却不能灵验，这是以前没有的事");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-3,"密林的魔物曾杀尽了穿过密林将抵达终点的人，只有我逃回了这里，他们在那徘徊太久了，再无人敢靠近，而这里的居民也忘记了为什么要穿达尽头。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-2,"只有梦中才有许愿成真。只有精神才能入梦。我们只是地面人们的投影，但你却是精神与肉体结合的完整之人。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,-1,"若你曾冲破世界的蛋壳拥抱地面的身躯，那你一定能帮助我们解决危害着这个村子的密林魔物。恳请您助我们一臂之力!");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"你，与我好像……不对，你不是投影，是完全的人！咳，你不是梦的居民。你一定能帮助我们解决危害着这个村子的密林魔物！");//
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"只有精神才能入梦，我们只是投影，但你却是精神与肉体结合的完整之人。密林的魔物曾杀尽了穿过密林之人，只有我逃回了这里。魔物无时不危害着村民，恳请您助我们一臂之力！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"密林，就在 %s。勇者，千万不要受了那魔物的蛊惑！请收下我的诚意，愿为勇士尽绵薄之力。记住，密林中存在能削弱树魔力量的『枯萎之触』，找到它们能让你更轻松地击败树魔！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"（凝视着你，眼中充满了期待）");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,4,"（目光坚定）你带来的是希望的曙光，旅者。村庄的每个角落都感激你的勇气。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,5,"你的名字将被铭记。请接受我们的祝福，愿你的道路永远光明！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,6,"我将为你指引其他群系的位置。『山水画廊』发生了天灾，位于%s，『樱之原野』似乎在进行大选，位于 %s。『亚特兰蒂斯』嘛，这我就不清楚了，埋藏深海之国，消息比较不灵通。游历四方后，或许该回世界的中心看看了。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,7,"出发吧！勇者。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,10,"再会了！勇者。");
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
        this.addDialog(TCRModEntities.YGGDRASIL,0,"哦，可怜的年轻人，你还是来了…… 不对，你是完整的人！");
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
        this.addDialog(TCRModEntities.YGGDRASIL,12,"我将为你介绍其他地区的现状，我能感应到每个地区都存在着动荡。");
        this.addDialog(TCRModEntities.YGGDRASIL,13,"位于%s的『青云之巅』皇位不保，位于%s的『深暗之樱』也有一位智者现世，他将以他的才智狠狠压榨那群傻子。至于『亚特兰蒂斯』嘛，这我就不清楚了，埋藏深海之国，消息比较不灵通。游历四方后，或许该回世界的中心看看了。");
        this.addDialog(TCRModEntities.YGGDRASIL,14,"不————你会后悔的！");
        this.addDialog(TCRModEntities.YGGDRASIL,15,"去吧，我就在此地等你的好消息。");
        this.addDialog(TCRModEntities.YGGDRASIL,16,"再会了，我将回去休养生息，也许有一天我们会在 『世界尽头』 再次相遇。");

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

        //沧澜
        this.addDialog(TCRModEntities.CANG_LAN,1,"小兄弟，你风尘仆仆来到此地，可曾察觉这山巅与浮岛的天壤之别？");
        this.addDialog(TCRModEntities.CANG_LAN,2,"盟主征尽，当年于山巅论剑决出的武林至尊，如今却有违当初的誓言，不顾联盟生死。一场突如其来的天灾，暴露了他们的无能与我们的绝望。");
        this.addDialog(TCRModEntities.CANG_LAN,3,"迫于盟主武力高强，我们只能任人鱼肉。更可恨的是，他们独占资源，囤积武学秘籍，接连下达禁令，美其名曰维护江湖秩序。");
        this.addDialog(TCRModEntities.CANG_LAN,4,"如今，吾联合六大掌门，欲揭竿而起，剿灭那只顾一己私利的盟主，为武林除害，重建一个公平正义的江湖！小兄弟是否愿与我们同行？");
        this.addDialog(TCRModEntities.CANG_LAN,5,"甚好！侠士之加入，使义军如虎添翼。请随我们一同前往 %s 之地！");
        this.addDialog(TCRModEntities.CANG_LAN,6,"（点头）我理解，在你了解到盟主的铁腕政策后，我相信，你会回来的。");
        this.addDialog(TCRModEntities.CANG_LAN,7,"（站在废墟之上，目光坚定）我们的胜利是江湖的胜利！你的勇气和选择为我们赢得了自由的曙光");
        this.addDialog(TCRModEntities.CANG_LAN,8,"（微笑着）这仅是一个开始，小兄弟。真正的工作现在才开始！我们将建立一个新的联盟，不再由谁独裁的联盟！");
        this.addDialog(TCRModEntities.CANG_LAN,9,"（拍着玩家的肩膀）我们并肩作战，未来将由我们共同塑造！请小兄弟收下我们的微薄谢礼！");
        this.addDialog(TCRModEntities.CANG_LAN,10,"这个嘛...牧歌原野似乎有着巨大的危机，位于 %s ，樱之原野似乎在进行大选，位于 %s 。亚特兰蒂斯嘛，这我就不清楚了，埋藏深海之国，消息比较不灵通");
        this.addDialog(TCRModEntities.CANG_LAN,11,"住手，盟主！你的谎言和暴政到此为止。我们是为了各弟子的生存与尊严，为了一个更加公平的江湖而来！");

        this.addDialogChoice(TCRModEntities.CANG_LAN,0,"继续");
        this.addDialogChoice(TCRModEntities.CANG_LAN,1,"（同情地）此等世道，实在令人痛心疾首！");
        this.addDialogChoice(TCRModEntities.CANG_LAN,2,"我愿与诸位同道，共赴义举。我虽无意逐鹿，却知苍生苦楚。");
        this.addDialogChoice(TCRModEntities.CANG_LAN,3,"我需亲眼目睹这一切，再作定夺。");
        this.addDialogChoice(TCRModEntities.CANG_LAN,4,"这一切...结束了吗？");
        this.addDialogChoice(TCRModEntities.CANG_LAN,5,"我也会尽我所能，帮助建设这个新世界。");
        this.addDialogChoice(TCRModEntities.CANG_LAN,6,"我将去往何方？");

        //Boss2
        this.addEntityAndEgg(TCRModEntities.SECOND_BOSS,"第二群系boss");
        this.addDialog(TCRModEntities.SECOND_BOSS,0,"你终于来了...你可听闻，一群受人蛊惑的莽夫，欲揭竿而起，威胁到我们世代相传的秩序。我看并非对如今秩序不满，而是贪图我手中的武林秘籍。");
        this.addDialog(TCRModEntities.SECOND_BOSS,1,"我受世界尽头之任，维护的不仅是江湖的稳定，更是群系的根基。武林秘籍倘若公之于众，若落入奸人手里，后果不堪设想！我需要你的剑，来助我守住此地。");
        this.addDialog(TCRModEntities.SECOND_BOSS,2,"（大笑）哈哈哈哈，后生可畏，后生可畏！群系之基如今可取决于你的决定了。若你愿助我度过此难，我将给予你本应得到的力量。");
        this.addDialog(TCRModEntities.SECOND_BOSS,3,"（冷笑）苍澜，你这是自寻死路！云逸，翠华，焰心，震宇，断山，你们也愿信他的鬼话？");
        this.addDialog(TCRModEntities.SECOND_BOSS,4,"（审视着战场）你展现了非凡的勇气和力量，我的忠实盟友。奸人已被驱散，秩序得以维护。");
        this.addDialog(TCRModEntities.SECOND_BOSS,5,"（坚定地）总得有人做出牺牲，可惜连累了其他几位掌门。好了，如今请收下你应得的那份力量吧。");
        this.addDialog(TCRModEntities.SECOND_BOSS,6,"掌门战败，他们的弟子绝不会就此罢休，我将修养片刻，维护群系稳定的大业亦有一段距离。你的旅途也亦未终止。");
        this.addDialog(TCRModEntities.SECOND_BOSS,7,"回溯密林位于 %s ，樱之原野位于 %s 。亚特兰蒂斯位于 %s 。这个世界的四大洲都不得安宁，不知世界尽头那边又有何动作。");
        this.addDialog(TCRModEntities.SECOND_BOSS,8,"江湖路远，有缘再见！");

        this.addDialogChoice(TCRModEntities.SECOND_BOSS,0,"继续");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,1,"不满？秘籍？");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,2,"我怎能凭你一面之词？");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,3,"奸人速速退下！休想窃取秘籍！（支持盟主）");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,4,"盟主，你所谓的秩序已然导致了混乱！（支持联军）");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,5,"这场战斗...真的值得吗？");
        this.addDialogChoice(TCRModEntities.SECOND_BOSS,6,"我将去往何方？");

        //厨娘
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,0,"客官远道而来，不妨一试小女子的手艺。在这村落之中，小女子自信无人能出其右。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,1,"有时，小女子亦想如旅者般游历四方，寻觅珍稀食材、食谱。可惜，一旦离去，便无人照看小店……客官可愿助我一臂之力？哈哈，说笑罢了。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,2,"欲知美食之精髓，乃是爱也。小女子真心觉得烹饪乃乐事。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,3,"哼哼，烹饪之事，今日客官有何偏好？");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,4,"传闻雪山之巅，有位高人掌管着这片大地，但近日似乎有人心生不满，战火将至。唉，届时，不知还有几人愿意品尝小女子的手艺。");

        //女商人
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,5,"阁下身上宝物众多，何不与小女子做些交易？呵呵。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,6,"觉得我衣着朴素？阁下不觉得，这正是幕后黑手的风范吗？");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,7,"比起金银，小女子更渴望得到稀世珍宝。若阁下有此等宝物，不妨带来一观，小女子定会给出令阁下心动的价码。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,8,"请随小女子一观珍藏，定能让阁下眼界大开。");
        this.addDialog(TCRModEntities.VILLAGER2_TALKABLE,9,"雪山顶上之人？阁下以为，小女子这般见多识广之人，会不知其事？不过，情报岂能轻易透露。");

        this.addDialogChoice(TCRModEntities.VILLAGER2_TALKABLE,1,"交易");
        this.addDialogChoice(TCRModEntities.VILLAGER2_TALKABLE,2,"询问");

        //乐师支线-第一段对话
        this.addDialog(TCRModEntities.MIAO_YIN,0,"哀兮悲兮，迷途之人。彳亍寻真，却在眼前。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,0,"默默听赏");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,1,"施舍");
        this.addDialog(TCRModEntities.MIAO_YIN,1,"感谢知遇之恩");
        this.addDialog(TCRModEntities.MIAO_YIN,2,"阁下驻足良久，想必是中意妾身的音乐。若能赏光捧个钱场，妾身感激不尽。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,2,"你是何人？");
        this.addDialog(TCRModEntities.MIAO_YIN,3,"一介漂泊之人尔…妾身虽目不能视，却可感受阁下的气场，你我应为同类。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,3,"你的眼睛看不见吗");
        this.addDialog(TCRModEntities.MIAO_YIN,4,"妾身儿时曾为烟火所熏，双目失明久矣");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,4,"悄悄取走乐师面前的钱");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,5,"施舍");
        this.addDialog(TCRModEntities.MIAO_YIN,5,"阁下真是心善之人。可惜妾身行动不便，无以为报，仅能奏乐为谢。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,6,"你没有想过去治疗一下吗");
        this.addDialog(TCRModEntities.MIAO_YIN,6,"治愈失明之法，古今难寻。妾身虽知一法，只恨形单影只，力不从心。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,7,"你的家人呢。");
        this.addDialog(TCRModEntities.MIAO_YIN,7,"在妾身幼时便都已离去，家产也皆为先前所提火灾所覆灭。如君所见，妾身在此漂泊卖艺，实属身不由己。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,8,"我或许可以帮助你");
        this.addDialog(TCRModEntities.MIAO_YIN,8,"…你我素不相识，妾身承蒙阁下好意，但此事却无需由您费心。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,9,"离去");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,10,"我只是乐于助人");
        this.addDialog(TCRModEntities.MIAO_YIN,9,"妾身能感受到阁下的力量非凡，或许此事对阁下不过举手之劳。但若阁下不能如实告知缘由，恐怕妾身内心难安。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,11,"我喜欢你");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,12,"我只是很同情你。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,13,"你我都是漂泊之人，理应互帮互助。");
        this.addDialog(TCRModEntities.MIAO_YIN,10,"请勿调戏妾身。");
        this.addDialog(TCRModEntities.MIAO_YIN,11,"阁下能这么说，妾身感激不尽。既然您有如此诚意，妾身便不再有所隐瞒，传闻世上有一宝珠，唤为万明。" +
                "在月圆之夜，万明珠可汲取月光，迸发魔力，从而使周围的生物恢复视力，若非双目失明者，则可练成火眼金睛，视千里之远不在话下。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,14,"这么神奇的宝物真的存在吗");
        this.addDialog(TCRModEntities.MIAO_YIN,12,"阁下以诚心待我，妾身定然不会相欺。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,15,"那么何处可以寻得此物呢。");
        this.addDialog(TCRModEntities.MIAO_YIN,13,"妾身亦不知，但此物名声非凡，在钟情收藏者之间定有情报相传。妾身行动不便，只得麻烦阁下为妾身四处打听了。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,16,"好吧，我会去帮你找找看的。");
        this.addDialog(TCRModEntities.MIAO_YIN,14,"阁下若最终得寻此物，治好妾身的眼疾，妾身定会想尽办法报答此恩！");
        this.addDialog(TCRModEntities.MIAO_YIN,15,"(去寻找对珍宝很了解的人吧，或许经常做买卖的人会知道？)");//任务提示
        //乐师支线-与商人对话获取线索
        this.addDialogChoice(TCRModEntities.SHANG_REN,-1,"交易");
        this.addDialog(TCRModEntities.SHANG_REN,0,"公平买卖，童叟无欺，奇珍异宝，应有尽有~");
        this.addDialogChoice(TCRModEntities.SHANG_REN,0,"你这里什么宝贝都有吗");
        this.addDialog(TCRModEntities.SHANG_REN,1,"当然！阁下请看！");//弹出交易表，如果还没和乐师对话过的话
        this.addDialog(TCRModEntities.SHANG_REN,2,"我走南闯北多年，四处搜罗，全天下的宝物，不敢说全部，但其十之八九，可皆在我辈之手头。不知你欲寻何物啊？");
        this.addDialogChoice(TCRModEntities.SHANG_REN,1,"你这里有万明珠吗");
        this.addDialog(TCRModEntities.SHANG_REN,3,"万明珠…不巧，此物正在那十之一二之中。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,2,"亏你刚才还那么夸下海口");
        this.addDialog(TCRModEntities.SHANG_REN,4,"这不是正好你赶巧儿了么。不过，虽然万明珠不在我手上，我却知道此物的所在之处。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,3,"你可算是有点用了，能否告知它在何处？");
        this.addDialog(TCRModEntities.SHANG_REN,5,"如你所知，此物乃是真正的奇珍…连你这样的异乡人都知道它的名号，它自然早已被人收藏起来了。怎么可能还会在这尘世间流通呢。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,4,"你可知收藏它的是何人？");
        this.addDialog(TCRModEntities.SHANG_REN,6,"哼，我就算知道又如何，难道你要从那个人手里买下它吗。这种喜好收藏的人，最不缺的就是钱了。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,5,"我只是想向他借用一下，我要帮助一个失明的人。");
        this.addDialog(TCRModEntities.SHANG_REN,7,"失明的人，难道是…唉，算了，话说回来，你莫非也是听信了那个传说吗，我好心提醒你，自此传说流传于世起，还从未有人亲眼见过万明珠治愈失明之人的情景。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,6,"我已经见过不少奇幻的东西了，相比之下，这个传说还更值得相信。而且，至少在此之前我要先努力一下。");
        this.addDialog(TCRModEntities.SHANG_REN,8,"真是执着，不过，此世间亦不乏你这等心怀执念之人。");
        this.addDialogChoice(TCRModEntities.SHANG_REN,7,"所以你能告诉我它在哪吗。");
        this.addDialog(TCRModEntities.SHANG_REN,9,"我不知道");
        this.addDialogChoice(TCRModEntities.SHANG_REN,8,"你说什么？");
        this.addDialog(TCRModEntities.SHANG_REN,10,"莫要烦躁，我并不知道万明珠的确切归属，然而我却知道在哪里可以得到它的情报~");
        this.addDialogChoice(TCRModEntities.SHANG_REN,9,"别废话了，快点告诉我");
        this.addDialogChoice(TCRModEntities.SHANG_REN,10,"莫要欺人太甚！（杀了奸商）");
        this.addBookAndAuthorAndContents("bu_gao","不详","布告","欲一睹万明珠者，可至 *** 。通过所设试炼，方可得万明珠。");
        this.addDialog(TCRModEntities.SHANG_REN,11,"此布告张贴于各地良久，早已有无数能人前往，可据传竟无一人能通过此试炼。适才我见你不知有此事，故与你相戏。切莫责怪，此乃赔罪，此试炼之地正位于 %s 处，你可前去。");
        this.addDialog(TCRModEntities.SHANG_REN,12,"杀人啦杀人啦！");
        this.addDialog(TCRModEntities.SHANG_REN,13,"（获得情报了，先回去找一下那位盲人姑娘吧。）");//任务提示
        //乐师支线-与乐师的二次对话
        this.addDialog(TCRModEntities.MIAO_YIN,16,"阁下此番归来，想是已经寻得了些许线索了？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,17,"（告知布告之事）");
        this.addDialog(TCRModEntities.MIAO_YIN,17,"原来如此，妾身本对此不抱期望，没成想这万明珠竟然早已暴露于世间");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,18,"你双目失明，有些东西虽然离你很近，你却也看不见");
        this.addDialog(TCRModEntities.MIAO_YIN,18,"…阁下所言极是，那么，您愿为我参加此试炼么");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,19,"义不容辞！");
        this.addDialog(TCRModEntities.MIAO_YIN,19,"阁下心善如此，妾身深受感动。若您能寻得万明珠归来，妾身定有重谢。此为些许辅助之物，或许对试炼有所裨益。");
        //乐师支线-与接待员的对话
        this.addDialog(TCRModEntities.RECEPTIONIST,0,"远行之人，可为万明珠而来？");
        this.addDialogChoice(TCRModEntities.RECEPTIONIST,0,"正是");
        this.addDialog(TCRModEntities.RECEPTIONIST,1,"既如此，请前往试炼。此试炼并无刁难之处，但凭个人本领即可。然自设立以来尚无一人过关，望君多加小心。若因此丧命，吾等概不负责。");
        this.addDialogChoice(TCRModEntities.RECEPTIONIST,1,"结束对话");
        this.addDialog(TCRModEntities.RECEPTIONIST,2,"君之诚心灼灼，吾深感敬佩！");
        this.addDialog(TCRModEntities.RECEPTIONIST,3,"令人敬佩，令人敬佩。吾等设立此试炼已久，通过者唯君尔。吾等即刻禀告主人，请君随吾等前来！");

        //乐师支线-与试炼主人初次的对话
        this.addDialog(TCRModEntities.TRIAL_MASTER,-1,"（盲人女孩面前已经没有东西可以偷了）");
        this.addDialog(TCRModEntities.TRIAL_MASTER,0,"壮哉壮哉，何等勇士，竟能通过吾之试炼！");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,0,"您过奖了，我只是想要借用一下万明珠");
        this.addDialog(TCRModEntities.TRIAL_MASTER,1,"万明珠不过小事一桩。实不相瞒，吾设立此等试炼，意在选出真正强大之人。请阁下在吾之府邸担任总管，护吾宅邸之周全，吾愿准备丰厚赠礼，望您勿要推辞。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,1,"我只是想借用万明珠来治疗一位失明之人，对担任总管一事并无兴趣。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,2,"吾当然知道阁下此行之目的。万明珠好说，只要阁下担任总管，便可与你所帮之人一同搬入我府，到时，无论是万明珠还是亿明珠，全部任你取用。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,2,"不，那位失明之人并非我的家人，我只是一介漂泊之人，偶然间遇到她，心生怜悯，想帮助她罢了。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,3,"漂泊之人…既然阁下无牵无挂，岂不是更加方便？任职我府，成家立业，不在话下。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,3,"不，我对这件事真的不感兴趣，请将万明珠借我。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,4,"吾说过了，万明珠不过小事一桩…");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,4,"我只是想帮人，请不要让我为难。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,5,"吾可是周公吐哺，望你归心啊。若吾借你万明珠，你可否再考虑考虑？");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,5,"好吧，你把万明珠借给我，我便再考虑考虑。（现在有求于他，只好先糊弄过去）");
        this.addDialog(TCRModEntities.TRIAL_MASTER,6,"阁下真是识时务者。我这就把万明珠给你。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,6,"继续");
        this.addDialog(TCRModEntities.TRIAL_MASTER,7,"虽然阁下先前说你欲救之人非你家人，然而阁下如此心切，相比此人应是你心爱之人罢。如果不是，当我多嘴，嘿嘿。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,7,"不是，只是在路边流浪的一名盲人乐师罢了");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,8,"没错，就是我的心爱之人。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,9,"只是跟我一样的漂泊之人，我们是同伴。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,8,"盲人乐师？噢，真是稀奇啊，世间还有这样的存在。不知她既然双眼失明，又该如何拨弄乐器");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,10,"她并非生来失明，据说她的双眼是在一场大火中被熏瞎的。大概是在此之前学过乐器吧。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,9,"卖弄乐理之人…此等人物竟会四处漂泊，想来也有难处，顺带一提，不知她使用的是何种乐器？");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,11,"是琵琶");
        this.addDialog(TCRModEntities.TRIAL_MASTER,10, "…正好，我府也缺少通乐理之人，我欲将其请来，不知阁下可愿相助？若在此处，你们也方便作伴。");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,12,"都说了我们不是那种关系…她就在xx村落处，穿着青绿色的服饰，你若有心，就找人把她请来吧。我先回去与她只会一声。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,11,"你在此处耽搁时间也许久了，加上路途劳顿，以吾之见，今日你就先在此住下，隔日再回吧");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,13,"好吧");
        this.addDialog(TCRModEntities.TRIAL_MASTER,12,"阁下，昨夜在吾府休息可好？");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,14,"还行");
        this.addDialog(TCRModEntities.TRIAL_MASTER,13,"多谢夸奖，请您务必要及时返回，总管一职可得仰仗您啊");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,15,"放心，我会顺便回来归还万明珠的。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,14,"阁下有缘再会！");
        this.addDialog(TCRModEntities.TRIAL_MASTER,15,"呵呵，真是令人感动的情谊。既然如此，阁下便带此万明珠速速返回吧。可千万别忘了与吾的约定。这里有一些礼物，就当作是预付的工资了，还望笑纳。");
        //乐师支线-与流浪者初次的对话
        this.addDialog(TCRModEntities.WANDERER, 0,"你在找那位盲人姑娘么？她已经不在这里了，昨天夜里她被人带走了。");
        this.addDialogChoice(TCRModEntities.WANDERER,0,"发生什么事了？");
        this.addDialog(TCRModEntities.WANDERER, 1,"我不知道，当时我就在附近，那群人很凶暴，那盲人姑娘在那里尖叫反抗，但好像无济于事。可，可跟我没关系，我只是想来这里看看她有没有留下什么值钱的东西，如果你认识她的话，她，她的东西都还给你，可真的不关我的事啊！");
        this.addDialogChoice(TCRModEntities.WANDERER,1,"这...");
        this.addDialog(TCRModEntities.WANDERER, 2,"（流浪者逃走了...回***问问什么情况吧。）");//获得琵琶
        //乐师支线-与接待员二次对话
        this.addDialog(TCRModEntities.RECEPTIONIST,4,"远行之人，可为…若君为寻主人而来，恕吾辈不敢违命，主人不愿见你，请回吧。");
        this.addDialogChoice(TCRModEntities.RECEPTIONIST,2,"这是怎么回事？你们把乐师怎么了？");
        this.addDialog(TCRModEntities.RECEPTIONIST,5,"吾辈不知乐师是何物，您请回吧");
        this.addDialogChoice(TCRModEntities.RECEPTIONIST,3,"万明珠还在我手上！");
        this.addDialog(TCRModEntities.RECEPTIONIST,6,"主人说了，你手上的万明珠非真品，但却也是上好的珠宝，就当作赠与君的离别礼了，您请回吧。");
        //乐师支线-取得万明珠后和乐师的对话
        this.addDialog(TCRModEntities.MIAO_YIN,20,"妾身内心挂念不已，阁下可算归来了，此行结果如何？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,20,"我已经取到万明珠了。给你。");
        this.addDialog(TCRModEntities.MIAO_YIN,21,"是么，原来，这就是妾身心心念念的万明珠么…");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,21,"你的眼睛有救了");
        this.addDialog(TCRModEntities.MIAO_YIN,22,"嗯？啊…是啊");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,22,"接下来只需要等到月圆之夜就可以治好你的眼睛了。");
        this.addDialog(TCRModEntities.MIAO_YIN,23,"嗯…话说回来，阁下此行可否遇到什么艰难险阻，可有负伤，妾身此处还有不少疗伤药材。想来试炼一定不易吧");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,23,"试炼对我来说很轻松，只是跟那位试炼的主人来回拉扯了半天。");
        this.addDialog(TCRModEntities.MIAO_YIN,24,"试炼主人？就是这万明珠的主人对吧，他是个什么样的人？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,24,"什么样的人？嗯…瘦高的，不胖，皮肤黝黑，看起来四五十岁的模样。");
        this.addDialog(TCRModEntities.MIAO_YIN,25,"是这样？！阁下见到他了？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,25,"啊…是啊，因为我通过了试炼，他要让我当总管，说起来还有点心动");
        this.addDialog(TCRModEntities.MIAO_YIN,26,"你去到他宅子里去了？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,26,"是啊");
        this.addDialog(TCRModEntities.MIAO_YIN,27,"可以告诉妾身，他的家在哪里吗？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,27,"你问这个作甚？");//结局2
        this.addDialogChoice(TCRModEntities.MIAO_YIN,28,"告诉你也无妨，那宅子位于 *** ");//结局3
        this.addDialog(TCRModEntities.MIAO_YIN,28,"啊，只是想着说，等妾身的眼睛治好了，就去他家里拜谢一下。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,29,"这样啊，到时候我跟你一起去吧，我顺便要去归还万明珠的。");
        this.addDialog(TCRModEntities.MIAO_YIN,29,"…阁下为何对妾身如此之好呢，之前也问过，虽然都是漂泊之人，但阁下比我强得多，无论在哪里都有容身之处，而妾身却始终需要依靠别人，只是累赘罢了…");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,30,"事到如今你还在说什么呢？我说过了，我只是想帮助你，仅此而已…");
        this.addDialog(TCRModEntities.MIAO_YIN,30,"帮助我…这样么，那么，你会帮我到底的吧…");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,31,"那当然了，明晚便是月圆之夜，先治好你的眼睛，其他事情，以后再说。");
        this.addDialog(TCRModEntities.MIAO_YIN,31,"嗯…");
        this.addDialog(TCRModEntities.MIAO_YIN,32,"（等到明晚再来找她吧）");//任务提示
        this.addDialog(TCRModEntities.MIAO_YIN,33,"阁下你来了，我们开始吧");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,32,"嗯。（取出万明珠）");
        this.addDialog(TCRModEntities.MIAO_YIN,34,"（月光逐渐映照在万明珠上，透过万明珠的反射，周围弥散着淡淡的白光，妙音就这样沐浴在光中。）");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,33,"感觉如何？");
        this.addDialog(TCRModEntities.MIAO_YIN,35,"眼睛痒痒的，感觉好晃眼");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,34,"什么？那不就说明你眼睛有感觉了？也就是说？");
        this.addDialog(TCRModEntities.MIAO_YIN,36,"欸，欸，啊，是啊，我好像能感受到了！（你能感受到妙音在看向你）阁下，我好像能看清你的脸了，嗯…");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,35,"太好了！你的视力真的恢复了！");
        this.addDialog(TCRModEntities.MIAO_YIN,37,"啊，是啊，没想到传说是真的。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,36,"这样一来就没问题了，那么，我们去找那位主人道谢吧。");
        this.addDialog(TCRModEntities.MIAO_YIN,38,"嗯，不过在此之前，妾身有些话想说");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,37,"怎么了？");
        this.addDialog(TCRModEntities.MIAO_YIN,39,"您说过，会帮我到底的对吧？");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,38,"是呀");
        this.addDialog(TCRModEntities.MIAO_YIN,40,"其实，我不是什么漂泊之人。我生于富庶之家，我的母亲在我很小的时候就死了，但我的父亲很宠溺我，知道我喜欢音乐，为我请了最好的老师教我琵琶，这个琵琶上面附有魔力，就是那位老师离别时相赠的。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,39,"所以你的琵琶是那时候学的");
        this.addDialog(TCRModEntities.MIAO_YIN,41,"没错，虽然父亲后来又新娶了妻子，后妈对我很冷漠，但因为音乐，我的童年很快乐。可惜在我十四岁那年，我的父亲突然去世了，没人知晓原因。在那以后，家族的产业就落到了后妈身上，可惜后妈不善打理，只好拜托我的叔父。突然有一天，家中莫名起了大火，死伤了一众家仆，");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,40,"继续");
        this.addDialog(TCRModEntities.MIAO_YIN,42,"我的眼睛被熏瞎，家产也葬送火海……从此之后我便家徒四壁，后妈无法忍受，改嫁了，只剩叔父勉强支撑着抚养我，到了我十八岁时，叔父却又突然被宗主征兵带走，从此了无音讯，失去了一切依靠，我只剩下琵琶了，也就靠此漂泊谋生至今。然后现在，又遇到了你。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,41,"你告诉我这些，不就更让我同情了吗…");
        this.addDialog(TCRModEntities.MIAO_YIN,43,"妾身确实是在谋求你的同情，不如说，其实我有一事相求。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,42,"什么事情");
        this.addDialog(TCRModEntities.MIAO_YIN,44,"其实这万明珠，原本正是我家的收藏");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,43,"你说什么？");
        this.addDialog(TCRModEntities.MIAO_YIN,45,"这个万明珠，原本是我家的镇宅之宝，但是在那场大火中遗失了…虽然妾身本来便有所猜测，但直到你说，我才能肯定，你刚刚说的试炼主人，无疑就是妾身的叔父。我的叔父…可能其实没有战死沙场或是怎样，而是盗走了妾身的家产，躲去别处逍遥自在了！");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,44,"居然还有这种事情？");
        this.addDialog(TCRModEntities.MIAO_YIN,46,"这些年来，我虽然是在流浪，但也在搜寻消息，没想到真相是我最不愿意接受的…");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,45,"所以你想要去找你的叔父吗");
        this.addDialog(TCRModEntities.MIAO_YIN,47,"是的，我要去找他，我要…拿回我的财产。你会帮我的吧。");
        this.addDialogChoice(TCRModEntities.MIAO_YIN,46,"我当然会帮你。");
        this.addDialog(TCRModEntities.MIAO_YIN,48,"等妾身拿回家产，阁下想要什么，我都会给你！");
        this.addDialog(TCRModEntities.MIAO_YIN,49,"（该去找 *** 清算了）");
        //第二次找试炼主人对话
        this.addDialog(TCRModEntities.TRIAL_MASTER,16,"阁下！你终于回来啦，怎么样？万明珠有帮到你吗");//记得这时候召唤一只妙音跟在背后
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,16,"当然，万明珠治好了我同伴的失明。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,17,"果然…欸，你说什么，居然治好了吗？！");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,17,"没错，而且她现在想见见你。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,18,"什么，是什么人？");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,18,"好久不见，叔父。");
        this.addDialog(TCRModEntities.TRIAL_MASTER,19,"什么…居然是你，你居然还活着…！");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,19,"叔父，许久未见，您见面的第一句话居然是盼望着我死么");
        this.addDialog(TCRModEntities.TRIAL_MASTER,20,"当然不是…不如说，我很惊讶…护卫！");
        this.addDialog(TCRModEntities.TRIAL_MASTER,21,"为什么，你居然会找到这么厉害的帮手");
        this.addDialogChoice(TCRModEntities.TRIAL_MASTER,20,"毕竟我可是通过了你的试炼呢");

        this.addBookAndContents("book1","Test","Test1","Test1.1");
        this.addBookAndContents("book2","Test2","Test2","Test2.2");
        this.addBookAndAuthorAndContents("biome1_elder_diary3","海拉","海拉长老的日记","没有了使者，这个地方的生命之力就会消失？", "为什么这样的重任只能我独自承担啊？可我为何再也不敢挺身而出而是一直等待他人的助力？这样的我该怎么回应我长眠的伙伴们");

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

        this.addSubtitle(TCRModSounds.DESERT_EAGLE_FIRE,"火枪开火");
        this.addSubtitle(TCRModSounds.DESERT_EAGLE_RELOAD,"火枪换弹");
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
