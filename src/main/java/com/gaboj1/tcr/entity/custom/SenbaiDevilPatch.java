package com.gaboj1.tcr.entity.custom;

import com.gaboj1.tcr.entity.ai.DOTECombatBehaviors;
import com.google.common.collect.ImmutableMap;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class SenbaiDevilPatch extends HumanoidMobPatch<SenbaiDevil> {
    public SenbaiDevilPatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(Animator animator) {
        super.commonAggresiveMobAnimatorInit(animator);
    }
    @Override
    protected void setWeaponMotions() {
        super.setWeaponMotions();
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.UCHIGATANA, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, DOTECombatBehaviors.SENBAI));
    }
    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }
}
