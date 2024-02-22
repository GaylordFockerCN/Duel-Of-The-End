package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.init.TCRModBlocks;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TreeSpiritWand extends MagicWeapon {

    public TreeSpiritWand() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).defaultDurability(128));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //TODO 消耗饱食度回血
        FoodData foodData = pPlayer.getFoodData();
        if(foodData.getFoodLevel()==0){
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }
        foodData.setFoodLevel(foodData.getFoodLevel()-TCRConfig.tree_spirit_wand_hungry_consume.get());
        pPlayer.heal(5);
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if(level.isClientSide){
            return InteractionResult.SUCCESS;
        }

        if(ItemUtil.searchAndConsumeItem(player, TCRModBlocks.DENSE_FOREST_SPIRIT_TREE_LOG.get().asItem(), TCRConfig.spirit_log_consume.get()) == 0){
            player.displayClientMessage(Component.translatable(this.getDescriptionId()+".no_spirit_tree"), true);
            return InteractionResult.FAIL;
        }
        player.hurt(level.damageSources().magic(), 0.1f);
        TCRModEntities.SMALL_TREE_MONSTER.get().spawn(((ServerLevel) level), pos, MobSpawnType.SPAWN_EGG)
                .setInLove(player);//TODO 测试是否驯服过
        return InteractionResult.SUCCESS;
    }
}
