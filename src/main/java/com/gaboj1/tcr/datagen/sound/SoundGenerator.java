package com.gaboj1.tcr.datagen.sound;

import com.gaboj1.tcr.client.TCRSounds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SoundGenerator extends TCRSoundProvider {

    public SoundGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    public void registerSounds() {
        this.generateNewSoundWithSubtitle(TCRSounds.PIPA, "item/pi_pa/", 5);
        this.generateNewSoundWithSubtitle(TCRSounds.DESERT_EAGLE_FIRE, "item/desert_eagle/fire", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.DESERT_EAGLE_RELOAD, "item/desert_eagle/reload", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.TREE_MONSTERS_DEATH, "entity/tree_monsters/tree_monsters_death", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.TREE_MONSTERS_HURT, "entity/tree_monsters/tree_monsters_hurt", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.YGGDRASIL_AMBIENT_SOUND, "entity/yggdrasil/yggdrasil_sound", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.YGGDRASIL_CRY, "entity/yggdrasil/yggdrasil_cry", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.YGGDRASIL_ATTACK_ONE, "entity/yggdrasil/yggdrasil_attack1", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.YGGDRASIL_ATTACK_TWO, "entity/yggdrasil/yggdrasil_attack2", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.YGGDRASIL_RECOVER_LAUGHTER, "entity/yggdrasil/yggdrasil_recover_laughter", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_HELLO, "entity/female_villager/hello", 4);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_EI, "entity/female_villager/ei", 3);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_HI, "entity/female_villager/hi", 2);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_HI_THERE, "entity/female_villager/hi_there", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_HENG, "entity/female_villager/heng", 2);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_DEATH, "entity/female_villager/female_die", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_HURT, "entity/female_villager/female_hurt", 3);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_VILLAGER_OHAYO, "entity/female_villager/female_ohayo", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_HENG, "entity/female_villager/female_heng", 2);
        this.generateNewSoundWithSubtitle(TCRSounds.FEMALE_SIGH, "entity/female_villager/female_sigh", 3);
        this.generateNewSoundWithSubtitle(TCRSounds.JELLY_CAT_AMBIENT, "entity/jelly_cat/jelly_cat_ambient", 4);
        this.generateNewSoundWithSubtitle(TCRSounds.JELLY_CAT_HURT, "entity/jelly_cat/jelly_cat_hurt", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.JELLY_CAT_DEATH, "entity/jelly_cat/jelly_cat_die", 1);
        this.generateNewSoundWithSubtitle(TCRSounds.UNKNOWN_AMBIENT, "entity/unkown/", 5);
        generateNewSoundWithSubtitle(TCRSounds.MALE_DEATH, "entity/male_villager/male_death", 2);
        generateNewSoundWithSubtitle(TCRSounds.MALE_EYO, "entity/male_villager/male_eyo", 1);
        generateNewSoundWithSubtitle(TCRSounds.MALE_GET_HURT, "entity/male_villager/male_get_hurt", 3);
        generateNewSoundWithSubtitle(TCRSounds.MALE_HELLO, "entity/male_villager/male_hello", 2);
        generateNewSoundWithSubtitle(TCRSounds.MALE_HENG, "entity/male_villager/male_heng", 1);
        generateNewSoundWithSubtitle(TCRSounds.MALE_HI, "entity/male_villager/male_hi", 2);
        generateNewSoundWithSubtitle(TCRSounds.MALE_SIGH, "entity/male_villager/male_sigh", 1);
        generateNewSoundWithSubtitle(TCRSounds.BIOME1VILLAGE, "bgm/biome1/village", 2);
        generateNewSoundWithSubtitle(TCRSounds.BIOME1FOREST, "bgm/biome1/forest", 2);
        generateNewSoundWithSubtitle(TCRSounds.BIOME1BOSS_FIGHT, "bgm/biome1/fight", 1);
    }
}
