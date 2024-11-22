package com.p1nero.dote.item.custom;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.npc.GuideNpc;
import com.p1nero.dote.item.DOTERarities;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TeleportKeyItem extends SimpleDescriptionFoilItem implements DOTEKeepableItem{
    private final Supplier<BlockPos> destination;
    public TeleportKeyItem(Supplier<BlockPos> destination){
        super(new Item.Properties().setNoRepair().fireResistant().stacksTo(1).rarity(DOTERarities.TE_PIN));
        this.destination = destination;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(level instanceof ServerLevel serverLevel && player.isShiftKeyDown()){
            if(DOTEArchiveManager.getWorldLevel() == 2){
                player.displayClientMessage(DuelOfTheEndMod.getInfo("tip10"), true);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide);
            }
            //清空物品栏
            if(!player.isCreative()){
                for(ItemStack stack : player.getInventory().items){
                    if(!(stack.getItem() instanceof DOTEKeepableItem)){
                        ItemUtil.addItemEntity(serverLevel, player.getX(), player.getY(), player.getZ(), stack);
                        stack.setCount(0);
                    }
                }
                for(ItemStack stack : player.getInventory().armor){
                    if(!(stack.getItem() instanceof DOTEKeepableItem)){
                        ItemUtil.addItemEntity(serverLevel, player.getX(), player.getY(), player.getZ(), stack);
                        stack.setCount(0);
                    }
                }
            }
            boolean inDim = serverLevel.dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY;
            if(inDim){
                player.changeDimension(Objects.requireNonNull(serverLevel.getServer().overworld()),
                        new DOTETeleporter(destination.get()));
            } else {
                player.changeDimension(Objects.requireNonNull(serverLevel.getServer().getLevel(DOTEDimension.P_SKY_ISLAND_LEVEL_KEY)),
                        new DOTETeleporter(destination.get()));
                //生成向导
                if(!DOTEArchiveManager.BIOME_PROGRESS_DATA.isGuideSummoned()){
                    GuideNpc npc = DOTEEntities.GUIDE_NPC.get().create(level);
                    if(npc != null){
                        npc.setPos(player.position());
                        npc.setHomePos(player.getOnPos());
                        level.addFreshEntity(npc);
                        DOTEArchiveManager.BIOME_PROGRESS_DATA.setGuideSummoned(true);
                    }
                }
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,1,1);

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
        list.add(DuelOfTheEndMod.getInfo("tip0").withStyle(ChatFormatting.BOLD));
    }
}
