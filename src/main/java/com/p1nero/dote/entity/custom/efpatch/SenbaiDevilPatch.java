package com.p1nero.dote.entity.custom.efpatch;

import com.p1nero.dote.entity.ai.DOTECombatBehaviors;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;

public class SenbaiDevilPatch extends HumanoidMobPatch<SenbaiDevil> {
    public List<StaticAnimation> guardAnim = new ArrayList<>();
    public SenbaiDevilPatch() {
        super(Faction.UNDEAD);
        guardAnim.add(Animations.SWORD_GUARD_HIT);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT2);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT2);
    }

    @Override
    public AttackResult tryHarm(Entity target, EpicFightDamageSource epicFightDamageSource, float amount) {
        //击中补刀
        if(epicFightDamageSource.getAnimation() == Animations.LONGSWORD_DASH){
            this.reserveAnimation(WOMAnimations.KATANA_AUTO_3);
        }
        //击倒
        if(epicFightDamageSource.getAnimation() == Animations.TACHI_DASH){
            epicFightDamageSource.setStunType(StunType.KNOCKDOWN);
            this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 3.0F, 0.0F, 0.1F);
        }
        AttackResult result = super.tryHarm(target, epicFightDamageSource, amount);
        //恢复破防值
        if(result.resultType.equals(AttackResult.ResultType.SUCCESS)){
            getOriginal().setNeutralizeCount(getOriginal().getMaxNeutralizeCount());
        }
        return result;
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        //免疫远程
        if(damageSource.isIndirect()){
            this.playAnimationSynchronized(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F);
            return AttackResult.missed(0);
        }
        getOriginal().setNeutralizeCount(getOriginal().getNeutralizeCount() - 1);
        //免疫小硬直，破防值满了就破防
        if(damageSource instanceof EpicFightDamageSource source){
            if(getAnimator().getPlayerFor(null).getAnimation() == Animations.BIPED_COMMON_NEUTRALIZED){
                source.setStunType(StunType.NONE);
            }
            if(source.getStunType() == StunType.SHORT){
                source.setStunType(StunType.NONE);
            }
            if(getOriginal().getNeutralizeCount() == 0){
                source.setStunType(StunType.NEUTRALIZE);
                applyStun(StunType.NEUTRALIZE, 5);
                getOriginal().setNeutralizeCount(getOriginal().getMaxNeutralizeCount());
            }
        }
        AttackResult result = super.tryHurt(damageSource, amount);
        if(result.resultType.equals(AttackResult.ResultType.SUCCESS)){
            //小概率格挡
            if(!this.getEntityState().attacking() && this.getOriginal().getBlockCount() == 0 && this.getOriginal().getRandom().nextInt(4) == 1){
                this.getOriginal().setBlockCount(this.getOriginal().getRandom().nextInt(3));
            }
            if(!this.getEntityState().attacking() && this.getOriginal().getBlockCount() > 0){
                this.playAnimationSynchronized(guardAnim.get(this.getOriginal().getRandom().nextInt(guardAnim.size())), 0.0F);
                this.playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                this.getOriginal().setBlockCount(this.getOriginal().getBlockCount() - 1);
                if(this.getOriginal().getBlockCount() == 0){
                    this.playAnimationSynchronized(Animations.LONGSWORD_AUTO1, 0.25F);
                }
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
    protected void setWeaponMotions() {
        this.weaponLivingMotions = Maps.newHashMap();
        this.weaponAttackMotions = Maps.newHashMap();
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.UCHIGATANA, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, DOTECombatBehaviors.SENBAI));
    }
    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

}
