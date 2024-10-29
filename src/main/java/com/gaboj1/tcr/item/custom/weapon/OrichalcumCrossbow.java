package com.gaboj1.tcr.item.custom.weapon;

import com.gaboj1.tcr.item.TCRItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class OrichalcumCrossbow extends CrossbowItem {
    protected static final UUID SPEED_UUID = UUID.fromString("FA233E1C-1145-1465-B01B-BCCE9785ACA3");
    public OrichalcumCrossbow(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Item modifier", -0.02, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    /**
     * 无需蓄力，但损耗弹药
     */
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_40920_, Player player, @NotNull InteractionHand p_40922_) {
        ItemStack itemstack = player.getItemInHand(p_40922_);
        if (isCharged(itemstack)) {
            performShooting(p_40920_, player, p_40922_, itemstack, 6.0F, 1.0F);
            if(!tryLoadProjectiles(player, itemstack)){
                setCharged(itemstack, false);
            }
            return InteractionResultHolder.consume(itemstack);
        }
        return super.use(p_40920_, player, p_40922_);
    }

    private static boolean tryLoadProjectiles(LivingEntity p_40860_, ItemStack itemStack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
        int j = i == 0 ? 1 : 3;
        //god版自带多重射击
        if(itemStack.is(TCRItems.GOD_ORICHALCUM_CROSSBOW.get())){
            j = 3;
        }
        boolean flag = p_40860_ instanceof Player && ((Player)p_40860_).getAbilities().instabuild;
        ItemStack itemstack = p_40860_.getProjectile(itemStack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(p_40860_, itemStack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(LivingEntity p_40863_, ItemStack p_40864_, ItemStack p_40865_, boolean p_40866_, boolean p_40867_) {
        if (p_40865_.isEmpty()) {
            return false;
        } else {
            boolean flag = p_40867_ && p_40865_.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !p_40867_ && !p_40866_) {
                itemstack = p_40865_.split(1);
                if (p_40865_.isEmpty() && p_40863_ instanceof Player) {
                    ((Player)p_40863_).getInventory().removeItem(p_40865_);
                }
            } else {
                itemstack = p_40865_.copy();
            }

            addChargedProjectile(p_40864_, itemstack);
            return true;
        }
    }

    private static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_) {
        CompoundTag compoundtag = p_40929_.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains("ChargedProjectiles", 9)) {
            listtag = compoundtag.getList("ChargedProjectiles", 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        p_40930_.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put("ChargedProjectiles", listtag);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId()+".usage"));
    }

}
