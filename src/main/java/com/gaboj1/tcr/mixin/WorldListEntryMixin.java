package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 用于删除地图缓存
 */
@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {

    @Shadow @Final private LevelSummary summary;

    @Inject(method = "doDeleteWorld()V", at = @At("RETURN"))
    private void injected(CallbackInfo ci){
        TCRBiomeProvider.deleteCache(this.summary.getLevelName());
    }
}
