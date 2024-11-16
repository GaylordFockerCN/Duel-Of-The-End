package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.worldgen.biome.DOTEBiomeProvider;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.Optional;

/**
 * 用于在创建地图的时候获取存档名。因为创建地图和进入世界是两个地方，保存位置不同。
 */
@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Shadow @Final
    WorldCreationUiState uiState;

    @Inject(method = "createNewWorldDirectory()Ljava/util/Optional;", at = @At("HEAD"))
    private void injected(CallbackInfoReturnable<Optional<LevelStorageSource.LevelStorageAccess>> cir){
        DOTEBiomeProvider.worldName = new File(uiState.getTargetFolder()).getName();
        DOTEBiomeProvider.LOGGER.info("On Create New World Sync : " + DOTEBiomeProvider.worldName + " >> TCRBiomeProvider.worldName");
    }
}
