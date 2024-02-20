package com.gaboj1.tcr.item.custom;

import net.minecraft.world.item.Item;

public class IntermediateResin extends BasicResin {
    public IntermediateResin(Properties pProperties) {
        super(pProperties);
        repairValue *= 9*9;
    }
}
