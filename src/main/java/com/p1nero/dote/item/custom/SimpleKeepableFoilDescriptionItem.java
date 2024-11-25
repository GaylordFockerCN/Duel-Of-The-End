package com.p1nero.dote.item.custom;

public class SimpleKeepableFoilDescriptionItem extends SimpleDescriptionFoilItem{
    public SimpleKeepableFoilDescriptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldKeepWhenExitDim() {
        return true;
    }
    @Override
    public boolean shouldKeepWhenEnterDim() {
        return true;
    }
}
