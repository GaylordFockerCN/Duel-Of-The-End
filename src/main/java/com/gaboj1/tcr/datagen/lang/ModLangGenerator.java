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

        this.addConfig("is_white","是否属于白方");
        this.addConfig("enable_scaling","是否启用地图缩放。若启用，则无论用何种尺寸的图片生成的地图大小固定");
        this.addConfig("repair_value","基础树脂的修理值");
        this.addConfig("tree_spirit_wand_hungry_consume","树灵法杖回血时的饥饿值消耗");
        this.addConfig("tree_spirit_wand_heal","树灵法杖回血量");
        this.addConfig("spirit_log_consume","树灵法杖召唤小树妖时消耗的原木数");

        this.add("item_group.the_casket_of_reveries.block","远梦之棺-方块");
        this.add("item_group.the_casket_of_reveries.spawn_egg","远梦之棺-生物蛋");
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

        this.add(TCRModItems.COPY_RESIN.get(),"复制树脂");
        this.addItemUsageInfo(TCRModItems.COPY_RESIN.get(),"右键消耗以复制另一只手的物品");

        this.add(TCRModItems.BASIC_RESIN.get(),"初级树脂");
        this.addItemUsageInfo(TCRModItems.BASIC_RESIN.get(),"右键消耗以修复另一只手的物品，每次修复 %d 点耐久度");
        this.add(TCRModItems.INTERMEDIATE_RESIN.get(),"中级树脂");
        this.add(TCRModItems.ADVANCED_RESIN.get(),"高级树脂");
        this.add(TCRModItems.SUPER_RESIN.get(),"超级树脂");

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
        this.add(TCRModBlocks.POTTED_DENSE_FOREST_SPIRIT_FLOWER.get(),"密林灵花盆栽");

        this.addEntityAndEgg(TCRModEntities.SMALL_TREE_MONSTER,"小树妖");
        this.addEntityAndEgg(TCRModEntities.MIDDLE_TREE_MONSTER,"树妖");
        this.addEntityAndEgg(TCRModEntities.TREE_GUARDIAN,"森林守护者");
        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER,"牧歌原野-村民");

        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,"牧歌原野-村民");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,0,"不许伤害小羊小牛小猪！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,1,"上天……上天给我的……这不是祈祷就有了吗？");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,2,"做梦吧你！火铳是我帮你祈祷出来的");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,3,"......");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,4,"我赌你的枪里没有子弹！");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,5,"不要拿去干坏事哦~");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,0,"那你汉堡里面的牛肉是哪来的？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,1,"我也可以吗？我想要把火铳！");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,2,"【获得 火铳*1 弹药*20】");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,3,"我还想要个女朋友！");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,4,"......");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,5,"快给我，不然嘣了你");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1,6,"谢谢你的火铳~");

        this.addEntityAndEgg(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,"牧歌原野-长老");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"勇者啊，我所期盼的勇者啊你终于来了。你可知我这十年来的心在仇恨的尖刀上是如何滴血的么。密林中的魔物危害着这个村子呵。恳请您前去剿除。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"密林，就在 %s 你要睁大眼睛侧起耳朵开动脑筋来揣度密林中提示之物的含义。你要是有心，那么就趁有心之时让它发挥作用。对了，尊敬的勇者……");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"算了，有的东西不过很久，是不可能理解的。 有的东西等到理解了，又为时已晚。 大多时候，我们不得不在尚未清楚认识自己的心的情况下选择行动，因而感到迷惘和困惑，因而悲伤不已。");
        this.addDialog(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"有些事情还无法言喻，有的则不便言喻。但你什么也不必担心。在某种意义上，村子和密林都是公平的。关于你所需要你所应该知道的，村子和密林以后将一一在你面前提示出来。");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,0,"我的命运与密林也不无关系。请你告诉我前往密林的路径。");//到了密林我自然会听从我的心做出我的行动。自然，我的心已经听到了你的声音。
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,1,"嗯？");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,2,"嗯");
        this.addDialogChoice(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER,3,"33");

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


    }
}
