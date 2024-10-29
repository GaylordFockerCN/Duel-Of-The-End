package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.item.TCRRarities;
import com.gaboj1.tcr.item.custom.DropItem;
import com.gaboj1.tcr.util.EntityUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class TigerKarambit extends DropItem {
    public TigerKarambit() {
        super(new Properties().stacksTo(1).rarity(TCRRarities.SHANG_PIN).durability(1145));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 5, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    /**
     * 在一格远的距离并且视线夹角小于30度则视为背刺
     */
    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if(EntityUtil.getViewDegree(target, attacker) < 90 && target.position().distanceTo(attacker.position()) > 1){
            target.hurt(attacker.damageSources().mobAttack(attacker), 10);
            attacker.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.BLOCKS,1,1);//播放暴击音效
            attacker.level().addParticle(ParticleTypes.CRIT, target.getX(), target.getY(), target.getZ(), 0, 0, 0);
        }
        return super.hurtEnemy(itemStack, target, attacker);
    }
}
