package com.p1nero.dote.capability.efpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.p1nero.dote.entity.custom.npc.DOTENpc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.world.capabilities.item.WOMWeaponCategories;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Set;

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
        this.idle = () -> Animations.BIPED_IDLE;
        this.talking = null;
        this.idle2talking = null;
        this.endTalking = null;
    }

    @Override
    protected void setWeaponMotions() {
        super.setWeaponMotions();
        this.weaponLivingMotions.put(
                CapabilityItem.WeaponCategories.LONGSWORD, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, Set.of(Pair.of(LivingMotions.IDLE, WOMAnimations.RUINE_IDLE), Pair.of(LivingMotions.WALK, WOMAnimations.RUINE_WALK), Pair.of(LivingMotions.CHASE, WOMAnimations.RUINE_RUN))));
        this.weaponLivingMotions.put(
                WOMWeaponCategories.RUINE, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, Set.of(Pair.of(LivingMotions.IDLE, WOMAnimations.RUINE_IDLE), Pair.of(LivingMotions.WALK, WOMAnimations.RUINE_WALK), Pair.of(LivingMotions.CHASE, WOMAnimations.RUINE_RUN))));
    }

    @Override
    public void initAnimator(Animator animator) {
        super.commonAggresiveMobAnimatorInit(animator);
//        animator.addLivingAnimation(LivingMotions.IDLE, idle.get());
//        if(talking != null){
//            animator.addLivingAnimation(DOTELivingMotions.TALKING, talking.get());
//        }
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
        super.commonAggressiveMobUpdateMotion(b);
//        if(getOriginal().getConversingPlayer() != null && talking != null){
//            this.currentLivingMotion = DOTELivingMotions.TALKING;
//        } else {
//            this.currentLivingMotion = LivingMotions.IDLE;
//        }
    }
}
