package com.gaboj1.tcr.item.custom;

public class SuperResin extends BasicResin{
    public SuperResin(Properties pProperties) {
        super(pProperties);
        repairValue *= 9*9*9;
    }
}
