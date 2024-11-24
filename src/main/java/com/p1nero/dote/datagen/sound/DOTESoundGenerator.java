package com.p1nero.dote.datagen.sound;

import com.p1nero.dote.client.DOTESounds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class    DOTESoundGenerator extends DOTESoundProvider {

    public DOTESoundGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    public void registerSounds() {
        generateNewSoundWithSubtitle(DOTESounds.LOTUSHEAL, "lotusheal", 1);
        generateNewSoundWithSubtitle(DOTESounds.DODGE, "dodge", 1);
        generateNewSoundWithSubtitle(DOTESounds.SENBAI_BGM, "senbaidevil", 1);
        generateNewSoundWithSubtitle(DOTESounds.GOLDEN_FLAME_BGM, "goldenflame", 1);
        generateNewSoundWithSubtitle(DOTESounds.BIOME_BGM, "biome_bgm", 1);
        generateNewSoundWithSubtitle(DOTESounds.BOSS_FIGHT1, "boss_fight1", 1);
        generateNewSoundWithSubtitle(DOTESounds.BOSS_FIGHT2, "boss_fight2", 1);
    }
}
