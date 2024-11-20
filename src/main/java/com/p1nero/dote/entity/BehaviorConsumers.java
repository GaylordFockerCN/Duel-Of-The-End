package com.p1nero.dote.entity;

import com.nameless.indestructible.api.animation.types.CommandEvent;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.Mob;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * copy from {@link com.nameless.indestructible.data.AdvancedMobpatchReloader#customAttackAnimation(AdvancedCustomHumanoidMobPatch.CustomAnimationMotion, AdvancedCustomHumanoidMobPatch.DamageSourceModifier, List, List, int, int)}
 */
public class BehaviorConsumers {
    public static <T extends MobPatch<?>> Consumer<T> customAttackAnimation(AdvancedCustomHumanoidMobPatch.CustomAnimationMotion motion, @Nullable AdvancedCustomHumanoidMobPatch.DamageSourceModifier damageSourceModifier, @Nullable List<CommandEvent.TimeStampedEvent> timeEvents, @Nullable List<CommandEvent.HitEvent> hitEvents, int phase, int hurtResist) {
        return (mobpatch) -> {
            if (mobpatch instanceof AdvancedCustomHumanoidMobPatch<?> advancedCustomHumanoidMobPatch) {
                advancedCustomHumanoidMobPatch.setHurtResistLevel(hurtResist);
                advancedCustomHumanoidMobPatch.setAttackSpeed(motion.speed());
                advancedCustomHumanoidMobPatch.setBlocking(false);
                if (motion.stamina() != 0.0F) {
                    advancedCustomHumanoidMobPatch.setStamina(advancedCustomHumanoidMobPatch.getStamina() - motion.stamina());
                }

                Iterator var8;
                if (timeEvents != null) {
                    var8 = timeEvents.iterator();

                    while(var8.hasNext()) {
                        CommandEvent.TimeStampedEvent event = (CommandEvent.TimeStampedEvent)var8.next();
                        advancedCustomHumanoidMobPatch.addEvent(event);
                    }
                }

                if (hitEvents != null) {
                    var8 = hitEvents.iterator();

                    while(var8.hasNext()) {
                        CommandEvent.HitEvent eventx = (CommandEvent.HitEvent)var8.next();
                        advancedCustomHumanoidMobPatch.addEvent(eventx);
                    }
                }

                advancedCustomHumanoidMobPatch.setDamageSourceModifier(damageSourceModifier);
                if (phase >= 0) {
                    advancedCustomHumanoidMobPatch.setPhase(phase);
                }
            }

            if (!mobpatch.getEntityState().turningLocked()) {
                ((Mob)mobpatch.getOriginal()).lookAt(mobpatch.getTarget(), 30.0F, 30.0F);
            }

            mobpatch.playAnimationSynchronized(motion.animation(), motion.convertTime(), SPPlayAnimation::new);
        };
    }
}
