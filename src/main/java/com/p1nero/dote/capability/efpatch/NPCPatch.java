package com.p1nero.dote.capability.efpatch;

import com.p1nero.dote.entity.custom.npc.DOTENpc;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

public class NPCPatch extends HumanoidMobPatch<DOTENpc> {
    public NPCPatch() {
        super(Faction.VILLAGER);
    }

    @Override
    public void initAnimator(Animator animator) {

    }

    @Override
    public void updateMotion(boolean b) {

    }
}
