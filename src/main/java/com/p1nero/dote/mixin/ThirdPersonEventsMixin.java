package com.p1nero.dote.mixin;

import com.github.leawind.thirdperson.ThirdPersonEvents;
import com.github.leawind.thirdperson.api.client.event.CalculateMoveImpulseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(value = ThirdPersonEvents.class, remap = false)
public class ThirdPersonEventsMixin {
    @Inject(method = "onCalculateMoveImpulse", at = @At("HEAD"), cancellable = true)
    private static void inject(CalculateMoveImpulseEvent event, CallbackInfo ci){
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        LocalPlayerPatch localPlayerPatch = EpicFightCapabilities.getEntityPatch(localPlayer, LocalPlayerPatch.class);
        if(localPlayerPatch != null && localPlayerPatch.isTargetLockedOn()){
            ci.cancel();
        }
    }
}
