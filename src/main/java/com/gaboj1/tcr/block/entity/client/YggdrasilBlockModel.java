package com.gaboj1.tcr.block.entity.client;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.entity.spawner.YggdrasilSpawnerBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class YggdrasilBlockModel extends DefaultedBlockGeoModel<YggdrasilSpawnerBlockEntity> {
    public YggdrasilBlockModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "yggdrasil_block"));
    }

}