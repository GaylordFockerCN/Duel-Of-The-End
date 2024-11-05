package com.gaboj1.tcr.item.renderer.armor;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.armor.TreeRobeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class TreeRobeRenderer extends GeoArmorRenderer<TreeRobeItem> {
    public TreeRobeRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "armor/tree_robe")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
