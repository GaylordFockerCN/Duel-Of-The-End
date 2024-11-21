package com.p1nero.dote.entity.custom.efpatch;

import com.p1nero.dote.entity.custom.DOTEMonster;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;

public class DOTEZombiePatch extends HumanoidMobPatch<DOTEMonster> {
    public List<StaticAnimation> guardAnim = new ArrayList<>();
    public DOTEZombiePatch() {
        super(Faction.UNDEAD);
        guardAnim.add(Animations.SWORD_GUARD_HIT);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT2);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT2);
    }

    @Override
    public AttackResult tryHarm(Entity target, EpicFightDamageSource source, float amount) {
        AttackResult result = super.tryHarm(target, source, amount);
        //恢复破防值
        if(result.resultType.equals(AttackResult.ResultType.SUCCESS)){
            getOriginal().setNeutralizeCount(getOriginal().getMaxNeutralizeCount());
        }
        return result;
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        getOriginal().setNeutralizeCount(getOriginal().getNeutralizeCount() - 1);
        //破防值满了就破防
        if(damageSource instanceof EpicFightDamageSource source){
            if(getAnimator().getPlayerFor(null).getAnimation() == Animations.BIPED_COMMON_NEUTRALIZED){
                source.setStunType(StunType.NONE);
                return super.tryHurt(damageSource, amount);
            }
            if(getOriginal().getNeutralizeCount() <= 0){
                source.setStunType(StunType.NEUTRALIZE);
                applyStun(StunType.NEUTRALIZE, 5);
                getOriginal().setNeutralizeCount(getOriginal().getMaxNeutralizeCount());
                return super.tryHurt(damageSource, amount);
            }
        }

        AttackResult result = super.tryHurt(damageSource, amount);
        if(result.resultType.equals(AttackResult.ResultType.SUCCESS)) {
            //小概率格挡
            //判断DamageSource是防止卡墙
            if (damageSource.getEntity() != null && !this.getEntityState().attacking() && this.getOriginal().getBlockCount() == 0 && this.getOriginal().getRandom().nextInt(4) == 1) {
                this.getOriginal().setBlockCount(2 + this.getOriginal().getRandom().nextInt(3));
            }
            if (damageSource.getEntity() != null && !this.getEntityState().attacking() && this.getOriginal().getBlockCount() > 0) {
                this.getOriginal().setBlockCount(this.getOriginal().getBlockCount() - 1);
                if(this.getOriginal().getBlockCount() == 0) {
                    this.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                    return AttackResult.missed(0);
                }
                this.playAnimationSynchronized(guardAnim.get(this.getOriginal().getRandom().nextInt(guardAnim.size())), 0.0F);
                this.playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                return AttackResult.blocked(0);
            }
        }
        return result;
    }

    @Override
    public void initAnimator(Animator animator) {
        super.commonAggresiveMobAnimatorInit(animator);
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

}
