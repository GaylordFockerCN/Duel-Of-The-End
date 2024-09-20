package com.gaboj1.tcr.item.renderer;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.item.custom.armor.IceTigerArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public final class IceTigerArmorRenderer extends GeoArmorRenderer<IceTigerArmorItem> {
    public IceTigerArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "armor/ice_tiger")));
    }
}