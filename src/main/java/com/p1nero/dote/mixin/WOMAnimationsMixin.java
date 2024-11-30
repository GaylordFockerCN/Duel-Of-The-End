package com.p1nero.dote.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import reascer.wom.animation.attacks.BasicAttackNoRotAnimation;
import reascer.wom.animation.attacks.BasicAttackNoRotNoASAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.gameasset.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.skill.WOMSkillDataKeys;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

@Mixin(value = WOMAnimations.class, remap = false)
public class WOMAnimationsMixin {

    /**
     * 补玩家判定防崩溃
     */
    @Shadow public static StaticAnimation SOLAR_BRASERO;

    /**
     * 调antic
     */
    @Shadow public static StaticAnimation TORMENT_AUTO_2;

    /**
     * 调ConvertTime
     */
    @Shadow public static StaticAnimation TORMENT_CHARGED_ATTACK_2;

    @Shadow public static StaticAnimation TORMENT_AUTO_3;

    @Shadow public static StaticAnimation KATANA_AUTO_1;

    @Inject(method = "build", at = @At("TAIL"))
    private static void inject(CallbackInfo ci){
        HumanoidArmature biped = Armatures.BIPED;
        SOLAR_BRASERO = (new BasicAttackNoRotNoASAnimation(0.3F, "biped/skill/solar_brasero", biped, new AttackAnimation.Phase(0.0F, 0.45F, 0.65F, 1.0F, Float.MAX_VALUE, biped.rootJoint, WOMWeaponColliders.SOLAR_INFIERNO))).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_OBSCURIDAD_POLVORA_HIT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addEvents(new AnimationEvent.TimePeriodEvent[]{AnimationEvent.TimePeriodEvent.create(0.0F, Float.MAX_VALUE, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch spp) {
                if (spp.getSkill(SkillSlots.WEAPON_INNATE).getSkill() == WOMSkills.SOLAR_ARCANO) {
                    spp.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync(WOMSkillDataKeys.CHARGING.get(), false, (ServerPlayer)entitypatch.getOriginal());
                }
            }

        }, AnimationEvent.Side.SERVER)}).addEvents(AnimationEvent.TimeStampedEvent.create(0.45F, (entityPatch, self, params) -> {
            if (entityPatch instanceof ServerPlayerPatch spp) {
                if (spp.getSkill(SkillSlots.WEAPON_PASSIVE).getSkill() == WOMSkills.SOLAR_PASSIVE) {
                    spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.HEATING_SPEED.get(), -5, (ServerPlayer)entityPatch.getOriginal());
                    spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.CYCLE.get(), 0, (ServerPlayer)entityPatch.getOriginal());
                    spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.TIMER.get(), 0, (ServerPlayer)entityPatch.getOriginal());
                    spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.STORED_HEAT_LEVEL.get(), 0.0F, (ServerPlayer)entityPatch.getOriginal());
                    spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.PARTICLE.get(), true, (ServerPlayer)entityPatch.getOriginal());
                    boolean zero_sauce = ((PlayerPatch<?>)entityPatch).getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(WOMSkillDataKeys.HEAT_LEVEL.get()) == 0.0F;
                    entityPatch.getOriginal().level().playSound(null, entityPatch.getOriginal(), zero_sauce ? SoundEvents.FIRE_EXTINGUISH : WOMSounds.SOLAR_HIT.get(), SoundSource.MASTER, 0.5F, 0.7F);
                }
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.5F, (entityPatch, self, params) -> {
            if (entityPatch instanceof ServerPlayerPatch spp) {
                if (spp.getSkill(SkillSlots.WEAPON_PASSIVE).getSkill() == WOMSkills.SOLAR_PASSIVE) {
                    boolean zero_sauce = spp.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(WOMSkillDataKeys.HEAT_LEVEL.get()) == 0.0F;
                    OpenMatrix4f transformMatrix = entityPatch.getArmature().getBindedTransformFor(entityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.toolL);
                    transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                    OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(entityPatch.getOriginal().yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    int n = zero_sauce ? 30 : 70;
                    double r = 0.1;

                    for (int i = 0; i < n; ++i) {
                        double theta = 6.283185307179586 * (new Random()).nextDouble();
                        double phi = Math.acos(2.0 * (new Random()).nextDouble() - 1.0);
                        double x = r * Math.sin(phi) * Math.cos(theta);
                        double y = r * Math.sin(phi) * Math.sin(theta);
                        double z = r * Math.cos(phi);
                        entityPatch.getOriginal().level().addParticle(zero_sauce ? ParticleTypes.SMOKE : ParticleTypes.SOUL_FIRE_FLAME, (double) transformMatrix.m30 + entityPatch.getOriginal().getX(), (double) transformMatrix.m31 + entityPatch.getOriginal().getY(), (double) transformMatrix.m32 + entityPatch.getOriginal().getZ(), (float) x, (float) y, (float) z);
                        if (i % 2 == 0) {
                            entityPatch.getOriginal().level().addParticle(zero_sauce ? ParticleTypes.LARGE_SMOKE : ParticleTypes.LAVA, (double) transformMatrix.m30 + entityPatch.getOriginal().getX(), (double) transformMatrix.m31 + entityPatch.getOriginal().getY(), (double) transformMatrix.m32 + entityPatch.getOriginal().getZ(), (float) x, (float) y, (float) z);
                        }
                    }
                }
            }
        }, AnimationEvent.Side.CLIENT));

        KATANA_AUTO_1 = (new BasicMultipleAttackAnimation(0.15F, 0.1F, 0.2F, 0.2F, null, biped.toolR, "biped/combat/katana_auto_1", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F);

        TORMENT_AUTO_3 = (new BasicMultipleAttackAnimation(0.25F, 0.15F, 0.3F, 0.3F, null, biped.toolR, "biped/combat/torment_auto_3", biped)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

    }
}
