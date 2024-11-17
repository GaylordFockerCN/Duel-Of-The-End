package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.entity.custom.DOTEBoss;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(AttackAnimation.class)
public class AttackAnimationMixin {
    @Inject(method = "getPlaySpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void onGetPlaySpeed(LivingEntityPatch<?> entityPatch, DynamicAnimation animation, CallbackInfoReturnable<Float> cir) {
        if(entityPatch.getOriginal() instanceof DOTEBoss doteBoss){
            cir.setReturnValue(doteBoss.getAttackSpeed());
        }
    }
}