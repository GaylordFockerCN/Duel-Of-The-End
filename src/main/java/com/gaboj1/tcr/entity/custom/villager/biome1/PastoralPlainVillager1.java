package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PastoralPlainVillager1 extends PastoralPlainVillager{

    public PastoralPlainVillager1(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //替换皮肤名字，不知道有无用
    @Override
    public String getResourceName() {
        return "pastoral_plain_villager1";
    }

}
