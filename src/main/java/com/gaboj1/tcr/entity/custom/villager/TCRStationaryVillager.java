package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.datagen.ModAdvancementData;
import com.gaboj1.tcr.entity.custom.villager.TCRTalkableVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.gui.screen.TreeNode;
import com.gaboj1.tcr.init.TCRModEntities;
import com.gaboj1.tcr.init.TCRModItems;
import com.gaboj1.tcr.util.DataManager;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

/**
 * 不可移动的村民
 * 不注册大脑
 * @author LZY
 */
public abstract class TCRStationaryVillager extends TCRTalkableVillager {


    public TCRStationaryVillager(EntityType<? extends TCRStationaryVillager> entityType, Level level) {
        super(entityType, level,1);
    }


    /**
     * 不会动的村民不能带脑子！
     */
    @Override
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
    }

}
