package com.p1nero.dote.item.custom;

public class SimpleKeepableFoilDescriptionItem extends SimpleDescriptionFoilItem{

    public SimpleKeepableFoilDescriptionItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldKeep() {
        return true;
    }
}
