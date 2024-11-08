package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.datagen.TCRAdvancementData;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.archive.TCRArchiveManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class Wanderer extends YueShiLineNpc {
    public Wanderer(EntityType<? extends Wanderer> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public String getResourceName() {
        return "wanderer";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.WANDERER.get().getDescriptionId());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        builder.start(0)
                        .addChoice(0, 1)
                                .addFinalChoice(1, (byte) 1);
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        player.displayClientMessage(BUILDER.buildDialogueAnswer(entityType, 2, false), true);
        this.discard();
        ItemEntity item = new ItemEntity(level(), getX(), getY(), getZ(), TCRItems.PI_PA.get().getDefaultInstance());
        TCRAdvancementData.getAdvancement("no_see", ((ServerPlayer) player));
        level().addFreshEntity(item);
        TCRArchiveManager.biome2.isBranchEnd = true;
        setConversingPlayer(null);
    }

}
