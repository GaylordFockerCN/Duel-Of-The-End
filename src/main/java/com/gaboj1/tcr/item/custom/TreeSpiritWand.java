package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TreeSpiritWand extends MagicWeapon {

    public TreeSpiritWand() {
        super(new Item.Properties().defaultDurability(128).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //TODO 消耗饱食度回血
        pPlayer.displayClientMessage(Component.translatable("info.tcr.hungry_not_enough"), true);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        //TODO: 消耗代价
        TCRModEntities.SMALL_TREE_MONSTER.get().spawn(((ServerLevel) level),pos, MobSpawnType.SPAWN_EGG)
                .setInLove(player);//TODO 测试是否驯服过
        return super.useOn(pContext);
    }
}
