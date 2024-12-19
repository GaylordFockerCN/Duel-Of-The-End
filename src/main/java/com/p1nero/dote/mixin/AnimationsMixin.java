package com.p1nero.dote.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import yesman.epicfight.gameasset.Animations;

@Mixin(value = Animations.class, remap = false)
public class AnimationsMixin {
    /**
     * antic为0会导致出伤异常
     */
    @ModifyArg(method = "build", at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/animation/types/BasicAttackAnimation;<init>(FFFFLyesman/epicfight/api/collider/Collider;Lyesman/epicfight/api/animation/Joint;Ljava/lang/String;Lyesman/epicfight/api/model/Armature;)V"), index = 1)
    private static float modify(float antic){
        if(antic == 0){
            return 0.01F;
        }
        return antic;
    }

    @ModifyArg(method = "build", at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/animation/types/BasicAttackAnimation;<init>(FFFFLnet/minecraft/world/InteractionHand;Lyesman/epicfight/api/collider/Collider;Lyesman/epicfight/api/animation/Joint;Ljava/lang/String;Lyesman/epicfight/api/model/Armature;)V"), index = 1)
    private static float modify2(float antic){
        if(antic == 0){
            return 0.01F;
        }
        return antic;
    }

}
