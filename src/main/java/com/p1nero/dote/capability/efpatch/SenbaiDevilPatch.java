package com.p1nero.dote.capability.efpatch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.p1nero.dote.entity.ai.ef.SenbaiDevilCombatBehaviors;
import com.p1nero.dote.entity.custom.SenbaiDevil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SenbaiDevilPatch extends DOTEBossPatch<SenbaiDevil> {
    public List<StaticAnimation> guardAnim = new ArrayList<>();
    public List<StaticAnimation> antiAttack = new ArrayList<>();

    public SenbaiDevilPatch() {
        super(Faction.UNDEAD);
        guardAnim.add(Animations.SWORD_GUARD_HIT);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.SWORD_GUARD_ACTIVE_HIT2);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT1);
        guardAnim.add(Animations.LONGSWORD_GUARD_ACTIVE_HIT2);
        antiAttack.add(Animations.LONGSWORD_AUTO1);
        antiAttack.add(Animations.LONGSWORD_AUTO2);
        antiAttack.add(Animations.LONGSWORD_AUTO3);
        antiAttack.add(Animations.LONGSWORD_LIECHTENAUER_AUTO1);
        antiAttack.add(Animations.LONGSWORD_LIECHTENAUER_AUTO2);
        antiAttack.add(Animations.LONGSWORD_LIECHTENAUER_AUTO3);
    }

    @Override
    public AttackResult attack(EpicFightDamageSource damageSource, Entity target, InteractionHand hand) {
        AttackResult result = super.attack(damageSource, target, hand);
        if (result.resultType.dealtDamage()) {
            //击中补刀
            if (damageSource.getAnimation() == Animations.LONGSWORD_DASH) {
                this.reserveAnimation(WOMAnimations.KATANA_AUTO_3);
            }
            //击倒
            if (damageSource.getAnimation() == Animations.TACHI_DASH) {
                damageSource.setStunType(StunType.KNOCKDOWN);
                this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 3.0F, 0.0F, 0.1F);
            }
            //加格挡次数
            this.getOriginal().setBlockCount(this.getOriginal().getBlockCount() + 1);
        }
        return result;
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = 1.2F;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        //免疫远程
        if (damageSource.isIndirect()) {
            this.playAnimationSynchronized(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F);
            return AttackResult.missed(0);
        }
        AttackResult result = super.tryHurt(damageSource, amount);
        if (result.resultType.equals(AttackResult.ResultType.SUCCESS)) {
            //小概率格挡
            if (!this.getEntityState().inaction() && this.getOriginal().getBlockCount() == 0 && this.getOriginal().getRandom().nextInt(5) == 1) {
                this.getOriginal().setBlockCount(2 + this.getOriginal().getRandom().nextInt(2));
            }
            if (!this.getEntityState().inaction() && this.getOriginal().getBlockCount() > 0) {
                this.getOriginal().setBlockCount(this.getOriginal().getBlockCount() - 1);
                //概率反击
                if (this.getOriginal().getBlockCount() == 0) {
                    if(this.getOriginal().getRandom().nextBoolean()){
                        this.playAnimationSynchronized(antiAttack.get(this.getOriginal().getRandom().nextInt(antiAttack.size())), 0.15F);
                    }
                    return super.tryHurt(damageSource, amount);
                }
                this.playAnimationSynchronized(guardAnim.get(this.getOriginal().getRandom().nextInt(guardAnim.size())), 0.0F);
                this.playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                return AttackResult.blocked(0);
            }
        }
        return result;
    }

    @Override
    public boolean applyStun(StunType stunType, float stunTime) {
        return false;
    }

    @Override
    public void initAnimator(Animator animator) {
        super.commonAggresiveMobAnimatorInit(animator);
    }

    @Override
    protected void setWeaponMotions() {
        this.weaponLivingMotions = Maps.newHashMap();
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.UCHIGATANA,
                ImmutableMap.of(CapabilityItem.Styles.TWO_HAND,
                        Set.of(Pair.of(LivingMotions.IDLE,  WOMAnimations.KATANA_IDLE),
                                Pair.of(LivingMotions.WALK, WOMAnimations.KATANA_WALK),
                                Pair.of(LivingMotions.RUN, WOMAnimations.KATANA_WALK),
                                Pair.of(LivingMotions.CHASE, WOMAnimations.KATANA_WALK))));
        this.weaponAttackMotions = Maps.newHashMap();
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.UCHIGATANA, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, SenbaiDevilCombatBehaviors.SENBAI));
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

}
