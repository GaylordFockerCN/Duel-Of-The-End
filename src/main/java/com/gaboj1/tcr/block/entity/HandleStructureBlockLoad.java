package com.gaboj1.tcr.block.entity;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleStructureBlockLoad {
    public static void load(StructureBlockEntity entity){
        if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null){
            StructureBlockEditScreen screen = new StructureBlockEditScreen(entity);
            Minecraft.getInstance().setScreen(screen);
            screen.loadButton.onPress();
            TheCasketOfReveriesMod.LOGGER.info("post load request : {} ",entity.getStructureName());
        }
    }
}
