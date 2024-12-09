package com.p1nero.dote.mixin;

import com.p1nero.dote.entity.ai.ef.api.ITimeEventListEntityPatch;
import com.p1nero.dote.entity.ai.ef.api.TimeStampedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.MainFrameAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = MainFrameAnimation.class, remap = false)
public class MainFrameAnimationMixin extends StaticAnimation {

    @Inject(method = "tick(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;)V",at = @At("HEAD"))
    public void onTick(LivingEntityPatch<?> entityPatch, CallbackInfo ci){
        if(!entityPatch.isLogicalClient() && entityPatch instanceof ITimeEventListEntityPatch timeEventListEntity && timeEventListEntity.getTimeEventList() != null){
            AnimationPlayer player = entityPatch.getAnimator().getPlayerFor(this);
            if (player != null) {
                float prevElapsed = player.getPrevElapsedTime();
                float elapsed = player.getElapsedTime();

                List<Integer> toRemove = new ArrayList<>();
                List<TimeStampedEvent> eventList = timeEventListEntity.getTimeEventList();
                for(int i  = 0; i < eventList.size(); i++){
                    TimeStampedEvent event = eventList.get(i);
                    if(!entityPatch.getOriginal().isAlive()){
                        break;
                    }
                    event.testAndExecute(entityPatch, prevElapsed, elapsed);
                    if(event.isExecuted()){
                        toRemove.add(i);
                    }
                }
                for (int i = toRemove.size() - 1; i >= 0; i--) {
                    eventList.remove((int) toRemove.get(i));
                }
                toRemove.clear();
            }
        }
    }

}