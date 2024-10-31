package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.entity.custom.projectile.SpriteBowArrow;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class SpriteCrossbow extends CrossbowItem {
    protected static final UUID SPEED_UUID = UUID.fromString("FA233E1C-1145-1465-B01B-BCCE9785ACA3");
    protected static final UUID LUCKY = UUID.fromString("FA233E1C-1145-1419-B19B-BCCE8105ACA3");
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    public SpriteCrossbow(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Item modifier", 0.02, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.LUCK, new AttributeModifier(LUCKY, "Item modifier", 0.79, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ARROW_OR_FIREWORK;
    }

    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    /**
     * shift+右键开大
     */
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        //防止耐久耗尽的时候损坏
        if(itemstack.getDamageValue() + 3 >= itemstack.getMaxDamage()){
            return InteractionResultHolder.fail(itemstack);
        }
        if (isCharged(itemstack)) {
            if(player.isShiftKeyDown() && itemstack.getDamageValue() < itemstack.getMaxDamage() - 52){
                if(!player.isCreative()){
                    itemstack.setDamageValue(itemstack.getDamageValue() + 48);
                    player.getCooldowns().addCooldown(itemstack.getItem(), 200);
                }
                Vec3 attacker = player.getEyePosition();
                Vec3 target = player.getViewVector(1.0F).normalize();
                for(int i = 1; i < Mth.floor(target.length()) + 17; ++i) {
                    Vec3 pos = attacker.add(target.scale(i));
                    if(level instanceof ServerLevel serverLevel){
                        if(i > 3){
                            level.explode(player, player.damageSources().explosion(player, player), null, pos, Mth.lerp(i / 17.0F, 0, 5), false, Level.ExplosionInteraction.NONE);
                        }
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, pos.x, pos.y + 1, pos.z, 1, 0, 0, 0, 0);
                        serverLevel.sendParticles(ParticleTypes.FIREWORK, pos.x, pos.y - 1, pos.z, 1, 0, 0, 0, 0);
                    }
                }
            } else {
                performShooting(level, player, hand, itemstack, getShootingPower(itemstack), 1.0F);
            }
            setCharged(itemstack, false);
        } else {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }

        }
        return InteractionResultHolder.consume(itemstack);
    }

    private static float getShootingPower(ItemStack crossbowStack) {
        return containsChargedProjectile(crossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = getPowerForTime(i, stack);
        if (f >= 1.0F && !isCharged(stack)) {
            setCharged(stack, true);
            SoundSource soundsource = entityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    public static boolean isCharged(ItemStack crossbowStack) {
        CompoundTag compoundtag = crossbowStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack crossbowStack, boolean isCharged) {
        CompoundTag compoundtag = crossbowStack.getOrCreateTag();
        compoundtag.putBoolean("Charged", isCharged);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack crossbowStack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundtag = crossbowStack.getTag();
        if (compoundtag != null && compoundtag.contains("ChargedProjectiles", 9)) {
            ListTag listtag = compoundtag.getList("ChargedProjectiles", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag1 = listtag.getCompound(i);
                list.add(ItemStack.of(compoundtag1));
            }
        }

        return list;
    }

    private static void clearChargedProjectiles(ItemStack crossbowStack) {
        CompoundTag compoundtag = crossbowStack.getTag();
        if (compoundtag != null) {
            ListTag listtag = compoundtag.getList("ChargedProjectiles", 9);
            listtag.clear();
            compoundtag.put("ChargedProjectiles", listtag);
        }

    }

    public static boolean containsChargedProjectile(ItemStack crossbowStack, Item ammoItem) {
        return getChargedProjectiles(crossbowStack).stream().anyMatch((stackToCheck) -> stackToCheck.is(ammoItem));
    }

    /**
     * 主要改了这个，改为射自己的箭
     */
    private static void shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, float soundPitch, float velocity, float inaccuracy, float projectileAngle) {
        if (!level.isClientSide) {
            SpriteBowArrow projectile = new SpriteBowArrow(level, shooter);
            projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            if (shooter instanceof CrossbowAttackMob crossbowattackmob) {
                crossbowattackmob.shootCrossbowProjectile(Objects.requireNonNull(crossbowattackmob.getTarget()), crossbowStack, projectile, projectileAngle);
            } else {
                Vec3 vec31 = shooter.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(projectileAngle * 0.017453292F, vec31.x, vec31.y, vec31.z);
                Vec3 vec3 = shooter.getViewVector(1.0F);
                Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 3.15F, inaccuracy);
            }

            crossbowStack.hurtAndBreak(1, shooter, (contextEntity) -> contextEntity.broadcastBreakEvent(hand));
            projectile.setBaseDamage(projectile.getBaseDamage() * 2.0F);
            level.addFreshEntity(projectile);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
        }
    }

    public static void performShooting(@NotNull Level level, LivingEntity shooter, @NotNull InteractionHand usedHand, @NotNull ItemStack crossbowStack, float velocity, float inaccuracy) {
        if (shooter instanceof Player player) {
            if (ForgeEventFactory.onArrowLoose(crossbowStack, shooter.level(), player, 1, true) < 0) {
                return;
            }
        }

        int cnt = crossbowStack.getEnchantmentLevel(Enchantments.MULTISHOT) > 0 ? 3 : 1;
        float[] afloat = getShotPitches(shooter.getRandom());

        for(int i = 0; i < cnt; ++i) {
            if (i == 0) {
                shootProjectile(level, shooter, usedHand, crossbowStack, afloat[i], velocity, inaccuracy, 0.0F);
            } else if (i == 1) {
                shootProjectile(level, shooter, usedHand, crossbowStack, afloat[i], velocity, inaccuracy, -10.0F);
            } else {
                shootProjectile(level, shooter, usedHand, crossbowStack, afloat[i], velocity, inaccuracy, 10.0F);
            }
        }

        onCrossbowShot(level, shooter, crossbowStack);
    }

    private static float[] getShotPitches(RandomSource random) {
        boolean flag = random.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, random), getRandomShotPitch(!flag, random)};
    }

    private static float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
        float f = isHighPitched ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(Level level, LivingEntity shooter, ItemStack crossbowStack) {
        if (shooter instanceof ServerPlayer serverplayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, crossbowStack);
            }

            serverplayer.awardStat(Stats.ITEM_USED.get(crossbowStack.getItem()));
        }

        clearChargedProjectiles(crossbowStack);
    }

    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(stack.getUseDuration() - count) / (float)getChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    public int getUseDuration(ItemStack stack) {
        return getChargeDuration(stack) + 3;
    }

    public static int getChargeDuration(ItemStack crossbowStack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, crossbowStack);
        return i == 0 ? 25 : 25 - 5 * i;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }

    private SoundEvent getStartSound(int enchantmentLevel) {
        switch (enchantmentLevel) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    private static float getPowerForTime(int useTime, ItemStack crossbowStack) {
        float f = (float)useTime / (float)getChargeDuration(crossbowStack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        List<ItemStack> list = getChargedProjectiles(stack);
        if (isCharged(stack) && !list.isEmpty()) {
            ItemStack itemstack = (ItemStack)list.get(0);
            tooltip.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(itemstack.getDisplayName()));
            if (flag.isAdvanced() && itemstack.is(Items.FIREWORK_ROCKET)) {
                List<Component> list1 = Lists.newArrayList();
                Items.FIREWORK_ROCKET.appendHoverText(itemstack, level, list1, flag);
                if (!list1.isEmpty()) {
                    for(int i = 0; i < list1.size(); ++i) {
                        list1.set(i, Component.literal("  ").append((Component)list1.get(i)).withStyle(ChatFormatting.GRAY));
                    }

                    tooltip.addAll(list1);
                }
            }
        }

    }

    public boolean useOnRelease(ItemStack stack) {
        return stack.is(this);
    }

    public int getDefaultProjectileRange() {
        return 8;
    }
}
