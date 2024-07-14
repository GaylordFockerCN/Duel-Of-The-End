package com.gaboj1.tcr.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.StructureBlockEntity;

public class HandleStructureBlockLoad {
    public static void load(StructureBlockEntity entity){
        if(Minecraft.getInstance().level != null){
            StructureBlockEditScreen screen = new StructureBlockEditScreen(entity);
            Minecraft.getInstance().setScreen(screen);
            screen.loadButton.onPress();
        }
    }
}
