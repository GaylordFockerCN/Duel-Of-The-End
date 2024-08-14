package com.gaboj1.tcr.block.entity;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class HandleStructureBlockLoad {
    public static void load(StructureBlockEntity entity){
        if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null){
//            StructureBlockEditScreen screen = new StructureBlockEditScreen(entity);
//            Minecraft.getInstance().setScreen(screen);
//            screen.loadButton.onPress();
            Objects.requireNonNull(Minecraft.getInstance().getConnection()).send(new ServerboundSetStructureBlockPacket(entity.getBlockPos(), StructureBlockEntity.UpdateType.LOAD_AREA, entity.getMode(), entity.getStructureName(), entity.getStructurePos(), entity.getStructureSize(), entity.getMirror(), entity.getRotation(), entity.getMetaData(), entity.isIgnoreEntities(), entity.getShowAir(), entity.getShowBoundingBox(), entity.getIntegrity(), entity.getSeed()));
            TheCasketOfReveriesMod.LOGGER.info("post load request : {} ",entity.getStructureName());
        }
    }
}
