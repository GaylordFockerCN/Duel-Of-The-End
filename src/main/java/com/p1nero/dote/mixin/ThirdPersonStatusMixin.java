package com.p1nero.dote.mixin;

import com.github.leawind.thirdperson.ThirdPersonStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(value = ThirdPersonStatus.class, remap = false)
public class ThirdPersonStatusMixin {
    @Inject(method = "shouldCameraTurnWithEntity", at = @At("HEAD"), cancellable = true)
    private static void inject(CallbackInfoReturnable<Boolean> cir){
//        LocalPlayer localPlayer = Minecraft.getInstance().player;
//        LocalPlayerPatch localPlayerPatch = EpicFightCapabilities.getEntityPatch(localPlayer, LocalPlayerPatch.class);
//        if(localPlayerPatch != null && localPlayerPatch.isTargetLockedOn()){
//            cir.setReturnValue(true);
//        }
    }
}
