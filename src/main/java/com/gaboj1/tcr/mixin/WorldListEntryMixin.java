package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 用于删除地图缓存和获取存档名字。
 */
@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {

    @Shadow @Final private LevelSummary summary;

    /**
     * 通过监听删除世界来实现同步删除缓存文件。
     * 有时候在想要不要把地图缓存一起放进世界文件夹，这样就省的删除了（
     */
    @Inject(method = "doDeleteWorld()V", at = @At("RETURN"))
    private void injectedDoDeleteWorld(CallbackInfo ci){
        TCRBiomeProvider.LOGGER.info("Deleting : " + summary.getLevelId() + ".dat -> "+ (TCRBiomeProvider.deleteCache(summary.getLevelId())?"SUCCESS":"FAILED"));
    }

    @Inject(method = "loadWorld()V", at = @At("HEAD"))
    private void injectedLoadWorld(CallbackInfo ci){
        TCRBiomeProvider.worldName = summary.getLevelId();
        TCRBiomeProvider.LOGGER.info("On loadWorld Sync : " + summary.getLevelId() + " >> TCRBiomeProvider.worldName");
    }

//    @Inject(method = "joinWorld()V", at = @At("HEAD"))
//    private void injectedJoinWorld(CallbackInfo ci){
//        TCRBiomeProvider.worldName = summary.getLevelId();
//        TCRBiomeProvider.LOGGER.info("On joinWorld Sync : " + summary.getLevelId() + " >> TCRBiomeProvider.worldName");
//    }

}
