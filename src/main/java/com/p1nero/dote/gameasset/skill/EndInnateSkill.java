package com.p1nero.dote.gameasset.skill;

import com.p1nero.dote.gameasset.DOTEAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class EndInnateSkill extends WeaponInnateSkill {
    public EndInnateSkill(Builder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        int stack = executer.getSkill(SkillSlots.WEAPON_INNATE).getStack();
        switch (stack){
            case 2:
                executer.playAnimationSynchronized(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A1, 0.0F);
                break;
            case 1:
                executer.playAnimationSynchronized(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A2, 0.0F);
                break;
            case 0:
                executer.playAnimationSynchronized(DOTEAnimations.WATERBIRDS_DANCE_WILDLY_A3, 0.0F);
                break;
        }
        super.executeOnServer(executer, args);
    }
}
