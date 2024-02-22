package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.util.ItemUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreeSpiritWand extends MagicWeapon {

    public TreeSpiritWand() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(128));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        FoodData foodData = pPlayer.getFoodData();
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(foodData.getFoodLevel()==0){
            return InteractionResultHolder.fail(itemStack);
        }
        foodData.setFoodLevel(foodData.getFoodLevel()-TCRConfig.tree_spirit_wand_hungry_consume.get());
        pPlayer.heal(TCRConfig.tree_spirit_wand_heal.get());
        if(!pPlayer.isCreative())
            itemStack.setDamageValue(itemStack.getDamageValue()+1);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if(!level.isClientSide){
            if(ItemUtil.searchAndConsumeItem(player, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem(), TCRConfig.spirit_log_consume.get()) == 0){
                player.displayClientMessage(Component.translatable(this.getDescriptionId()+".no_spirit_tree"), true);
                return InteractionResult.FAIL;
            }
            player.hurt(level.damageSources().magic(), 2f);
            if(player.isDeadOrDying()){
                Advancement _adv = player.getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"die_for_summon"));
                AdvancementProgress _ap = ((ServerPlayer) player).getAdvancements().getOrStartProgress(_adv);

                if (!_ap.isDone()) {
                    for (String criteria : _ap.getRemainingCriteria())
                        ((ServerPlayer) player).getAdvancements().award(_adv, criteria);
                }
            }

            TCRModEntities.SMALL_TREE_MONSTER.get().spawn(((ServerLevel) level), pos.above(), MobSpawnType.SPAWN_EGG).tame(player);

            ItemStack itemStack = player.getItemInHand(pContext.getHand());
            if(!player.isCreative())
                itemStack.setDamageValue(itemStack.getDamageValue()+1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    //平A伤害调整
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 7d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker instanceof ServerPlayer serverPlayer) {
            Advancement _adv = serverPlayer.getServer().getAdvancements().getAdvancement(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "melee_mage"));
            AdvancementProgress _ap = serverPlayer.getAdvancements().getOrStartProgress(_adv);
            if (!_ap.isDone()) {
                for (String criteria : _ap.getRemainingCriteria())
                    serverPlayer.getAdvancements().award(_adv, criteria);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage1"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage2"));
        pTooltipComponents.add(Component.translatable(this.getDescriptionId()+".usage3"));
    }
}
