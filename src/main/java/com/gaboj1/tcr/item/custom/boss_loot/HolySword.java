package com.gaboj1.tcr.item.custom.boss_loot;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HolySword extends MagicWeapon {

    private final float damage = 5.0f;

    public HolySword() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(1428));
    }

    //平A伤害调整
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", damage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    //天下武功，唯快不破！
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker instanceof ServerPlayer serverPlayer) {

//            float speed = serverPlayer.getSpeed();
            float speed = (float) serverPlayer.getDeltaMovement().distanceTo(Vec3.ZERO) * 2;
            System.out.println("speed:"+speed+" damage:"+speed*speed*damage);//Ek = mv²/2哈哈
            pTarget.setHealth(pTarget.getHealth()-speed*speed*damage);
//            pTarget.hurt(pAttacker.damageSources().playerAttack(serverPlayer),speed==0?damage:damage*speed*speed);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage1"));
//        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage2"));
//        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3"));
    }

}
