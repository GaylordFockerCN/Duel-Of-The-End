package com.gaboj1.tcr.datagen.lang;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.datagen.ModEntityTagGenerator;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.init.TCRModSounds;
import com.gaboj1.tcr.worldgen.biome.TCRBiomes;
import net.minecraft.data.PackOutput;

public class ModLangGenerator extends TCRLangProvider{
    public ModLangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {

        this.add("","");

        this.add("item_group.the_casket_of_reveries.the_casket_of_reveries","远梦之棺");
        this.add(TCRModItems.DESERT_EAGLE.get(),"沙漠之鹰");
        this.add(TCRModItems.DESERT_EAGLE_AMMO.get(),"沙鹰弹药");

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

        this.addAdvancement(TheCasketOfReveriesMod.MOD_ID,"远梦之棺","> <");
        this.addAdvancement("enter_realm_of_the_dream","梦之领域","进入梦之领域");
        this.addAdvancement("wow","嘿！","砍伐密林灵木时小树妖突然出现！");
        this.addAdvancement("try_wake_up","梦中梦？","尝试在梦之领域使用灵梦床");
        this.addAdvancement("shoot_hundred_meters","百步穿杨！","射中一百米外的玻璃或者生物实体");

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
