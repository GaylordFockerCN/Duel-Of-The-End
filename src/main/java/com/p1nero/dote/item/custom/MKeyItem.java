package com.p1nero.dote.item.custom;

import net.minecraft.core.BlockPos;

import java.util.function.Supplier;

public class MKeyItem extends TeleportKeyItem implements DOTEKeepableItem{
    public MKeyItem(Supplier<BlockPos> destination) {
        super(destination);
    }
}
