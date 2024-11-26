package com.p1nero.dote.capability.efpatch;

import com.p1nero.dote.entity.custom.npc.DOTENpc;
import com.p1nero.dote.gameasset.DOTELivingMotions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class NPCPatch extends HumanoidMobPatch<DOTENpc> {
    @NotNull
    private final AnimationProvider<?> idle;
    @Nullable
    private final AnimationProvider<?> talking;
    @Nullable
    private final AnimationProvider<?> idle2talking;
    @Nullable
    private final AnimationProvider<?> endTalking;

    public NPCPatch(@NotNull AnimationProvider<?> idle, @Nullable AnimationProvider<?> talking, @Nullable AnimationProvider<?> idle2talking, @Nullable AnimationProvider<?> endTalking) {
        super(Faction.VILLAGER);
        this.idle = idle;
        this.talking = talking;
        this.idle2talking = idle2talking;
        this.endTalking = endTalking;
    }

    public NPCPatch() {
        super(Faction.VILLAGER);
        this.idle = ()-> Animations.BIPED_IDLE;
        this.talking = null;
        this.idle2talking = null;
        this.endTalking = null;
    }

    @Override
    public void initAnimator(Animator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, idle.get());
        if(talking != null){
            animator.addLivingAnimation(DOTELivingMotions.TALKING, talking.get());
        }
    }

    public void playIdleToTalking(){
        if(idle2talking != null){
            playAnimationSynchronized(idle2talking.get(), 0.3F);
        }
    }

    public void playEndTalking(){
        if(endTalking != null){
            playAnimationSynchronized(endTalking.get(), 0.3F);
        }
    }

    @Override
    public void updateMotion(boolean b) {
        if(getOriginal().getConversingPlayer() != null && talking != null){
            this.currentLivingMotion = DOTELivingMotions.TALKING;
        } else {
            this.currentLivingMotion = LivingMotions.IDLE;
        }
    }
}
