package com.p1nero.dote.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(value = WOMAnimations.class, remap = false)
public class WOMAnimationsMixin {

    /**
     * antic为0会导致出伤异常
     */
    @ModifyArg(method = "build", at = @At(value = "INVOKE", target = "Lreascer/wom/animation/attacks/BasicMultipleAttackAnimation;<init>(FFFFLyesman/epicfight/api/collider/Collider;Lyesman/epicfight/api/animation/Joint;Ljava/lang/String;Lyesman/epicfight/api/model/Armature;)V"), index = 1)
    private static float modify(float antic){
        if(antic == 0){
            return 0.10F;
        }
        return antic;
    }

    /**
     * 补玩家判定防崩溃
     */
    @Inject(method = "lambda$build$171", at = @At("HEAD"), cancellable = true)
    private static void inject(LivingEntityPatch<?> entityPatch, StaticAnimation self, Object[] params, CallbackInfo ci){
        if(!(entityPatch instanceof ServerPlayerPatch)){
            ci.cancel();
        }
    }

}
