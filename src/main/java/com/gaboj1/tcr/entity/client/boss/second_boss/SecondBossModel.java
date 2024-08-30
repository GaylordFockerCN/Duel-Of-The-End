package com.gaboj1.tcr.entity.client.boss.second_boss;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class SecondBossModel extends DefaultedEntityGeoModel<SecondBossEntity> {
    public SecondBossModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "second_boss"));
    }
}
