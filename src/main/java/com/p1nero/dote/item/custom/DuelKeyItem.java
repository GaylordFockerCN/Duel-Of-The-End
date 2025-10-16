package com.p1nero.dote.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DuelKeyItem extends Item {
    public DuelKeyItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

}
