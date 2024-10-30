package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.entity.custom.projectile.SpriteBowArrow;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpriteBow extends BowItem {
    protected static final UUID SPEED_UUID = UUID.fromString("FA233E1C-1145-1465-B01B-BCCE9785ACA3");
    protected static final UUID LUCKY = UUID.fromString("FA233E1C-1145-1419-B19B-BCCE8105ACA3");
    public SpriteBow(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if(enchantment.equals(Enchantments.MULTISHOT)){
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Item modifier", 0.02, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.LUCK, new AttributeModifier(LUCKY, "Item modifier", 0.5, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }
    @Override
    public int getDefaultProjectileRange() {
        return 25;
    }

    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity owner, int p_40670_) {
        if (owner instanceof Player player) {
            int i = this.getUseDuration(itemStack) - p_40670_;

            float f = getPowerForTime(i);
            if (!((double)f < 0.1)) {
                if (!level.isClientSide) {
                    //三重射击
                    int cnt = itemStack.getEnchantmentLevel(Enchantments.MULTISHOT) > 0 ? 3 : 1;
                    for(int ii = 0; ii < cnt ; ii++){
                        AbstractArrow abstractarrow = new SpriteBowArrow(level, player);
                        abstractarrow.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                        abstractarrow = this.customArrow(abstractarrow);
                        if(cnt == 1){
                            abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        } else {
                            abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot() + (ii - 1) * 20, 0.0F, f * 3.0F, 1.0F);
                        }
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5 + 0.5);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        itemStack.hurtAndBreak(1, player, (p_289501_) -> {
                            p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() * 1.5F);//双重射击防霸体
                        level.addFreshEntity(abstractarrow);
                    }
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                player.awardStat(Stats.ITEM_USED.get(this));
//                if(!player.isCreative()){
//                    itemStack.setDamageValue(itemStack.getDamageValue() + 1);
//                    if(itemStack.getDamageValue() > itemStack.getMaxDamage()){
//                        itemStack.shrink(1);
//                    }
//                }
            }
        }

    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }

}
