package com.gaboj1.tcr.datagen.sound;

import com.gaboj1.tcr.client.DOTESounds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DOTESoundGenerator extends DOTESoundProvider {

    public DOTESoundGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    public void registerSounds() {
        generateNewSoundWithSubtitle(DOTESounds.LOTUSHEAL, "lotusheal", 1);
        generateNewSoundWithSubtitle(DOTESounds.SENBAI_BGM, "senbaidevil", 1);
        generateNewSoundWithSubtitle(DOTESounds.GOLDEN_FLAME_BGM, "goldenflame", 1);
    }
}
