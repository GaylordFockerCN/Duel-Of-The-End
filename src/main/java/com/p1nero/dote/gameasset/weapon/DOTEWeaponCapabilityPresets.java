package com.p1nero.dote.gameasset.weapon;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.gameasset.DOTEAnimations;
import com.p1nero.dote.gameasset.skill.DOTESkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEWeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> END = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.LONGSWORD)
                    .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.RUINE)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .hitParticle(EpicFightParticles.HIT_BLADE.get())
                    .canBePlacedOffhand(false)
                    .comboCancel((style) -> false)
                    .newStyleCombo(
                            CapabilityItem.Styles.TWO_HAND,
                            DOTEAnimations.YULLIAN_COMBOA1,
                            DOTEAnimations.YULLIAN_COMBOA2,
                            DOTEAnimations.YULLIAN_COMBOC1,
                            DOTEAnimations.YULLIAN_COMBOC2,
                            DOTEAnimations.YULLIAN_SPECIALATTACK2,
                            DOTEAnimations.SAKURA_DANCE)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> DOTESkills.END_INNATE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, DOTEAnimations.YULLIAN_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, DOTEAnimations.YULLIAN_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, DOTEAnimations.YULLIAN_RUN)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, DOTEAnimations.YULLIAN_RUN)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.RUINE_BLOCK);

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "end"), END);
    }

}
