package com.p1nero.dote.item.custom;

import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.archive.DataManager;
import com.p1nero.dote.gameasset.skill.DOTESkills;
import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TeleportKeyItem extends SimpleDescriptionFoilItem{
    private final Supplier<BlockPos> destination;
    public TeleportKeyItem(Supplier<BlockPos> destination){
        super(new Item.Properties().setNoRepair().fireResistant().stacksTo(1).rarity(DOTERarities.TE_PIN));
        this.destination = destination;
    }

    /**
     * 事件完成了就失去光泽很合理
     */
    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return !DOTEArchiveManager.isFinished();
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(level instanceof ServerLevel serverLevel && player.isShiftKeyDown()){
            if(DOTEArchiveManager.isFinished() && level.dimension() == Level.OVERWORLD){
                player.displayClientMessage(DuelOfTheEndMod.getInfo("tip10"), true);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
            }
            //清空物品栏
            if(!player.isCreative() && serverLevel.dimension() != DOTEDimension.P_SKY_ISLAND_LEVEL_KEY){
                if(!IDOTEKeepableItem.check(player, true)){
                    player.displayClientMessage(DuelOfTheEndMod.getInfo("tip0"), true);
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
                }
            }
            ServerLevel dim = Objects.requireNonNull(serverLevel.getServer().getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY));
            if(player.changeDimension(dim, new DOTETeleporter(destination.get())) == null){
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);
            ((ServerPlayer)player).setRespawnPosition(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY, destination.get(), 0.0F, true, true);
            //生成向导
            if(!DOTEArchiveManager.BIOME_PROGRESS_DATA.isGuideSummoned()){
//                GuideNpc npc = DOTEEntities.GUIDE_NPC.get().spawn(dim, player.getOnPos(), MobSpawnType.MOB_SUMMONED);
//                if(npc != null){
//                    npc.setPos(player.position());
//                    ForgeEventFactory.onFinalizeSpawn(npc, serverLevel, serverLevel.getCurrentDifficultyAt(player.getOnPos()), MobSpawnType.MOB_SUMMONED, null, null);
//                    serverLevel.addFreshEntity(npc);
//                }
                //生成npc，用上面的办法有bug，不知道为啥进去AI会卡死妈的
                CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                Objects.requireNonNull(serverLevel.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "summon " + DuelOfTheEndMod.MOD_ID + ":guide_npc");
                DOTEArchiveManager.BIOME_PROGRESS_DATA.setGuideSummoned(true);
            }
            //新手福利
            if(DOTEConfig.GIVE_M_KEY.get() && !DataManager.lootGot.get(player)){
                ItemStack guard = new ItemStack(EpicFightItems.SKILLBOOK.get());
                guard.getOrCreateTag().putString("skill", EpicFightSkills.GUARD.toString());
//                ItemStack dodgeDisplay = new ItemStack(EpicFightItems.SKILLBOOK.get());
//                dodgeDisplay.getOrCreateTag().putString("skill", DOTESkills.BETTER_DODGE_DISPLAY.toString());
                ItemUtil.addItem(player, guard);
//                ItemUtil.addItem(player, dodgeDisplay);
                ItemStack sword = Items.IRON_SWORD.getDefaultInstance();
                sword.setDamageValue(sword.getMaxDamage() - 40);
                ItemUtil.addItem(player, sword);
                DataManager.lootGot.put(player, true);
            }
            CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2);
            Objects.requireNonNull(serverLevel.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule keepInventory true");

        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BOW;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
    }
}
