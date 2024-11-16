package com.gaboj1.tcr.datagen.sound;

import com.gaboj1.tcr.client.TCRSounds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TCRSoundGenerator extends TCRSoundProvider {

    public TCRSoundGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, helper);
    }

    @Override
    public void registerSounds() {
    }
}
