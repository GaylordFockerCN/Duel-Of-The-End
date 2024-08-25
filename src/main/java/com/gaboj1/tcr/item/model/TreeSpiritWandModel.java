package com.gaboj1.tcr.item.model;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.boss_loot.TreeSpiritWand;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class TreeSpiritWandModel extends DefaultedItemGeoModel<TreeSpiritWand> {

    public TreeSpiritWandModel() {
        super(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tree_spirit_wand"));
    }

}
