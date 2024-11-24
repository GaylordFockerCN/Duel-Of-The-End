package com.p1nero.dote.item.custom;

/**
 * 进出维度不会被删除的物品
 */
public interface DOTEKeepableItem {
    default boolean shouldKeep(){
        return true;
    }
}
