package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.entity.TCREntities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder.BUILDER;

public class P1nero extends TCRTalkableVillager {

    private final EntityType<?> entityType = TCREntities.P1NERO.get();
    public P1nero(EntityType<? extends P1nero> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public String getResourceName() {
        return "p1nero";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.P1NERO.get().getDescriptionId());
    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        return false;
    }

    @Override
    public void die(@NotNull DamageSource source) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        builder.start(0)
                .addChoice(0, 1)
                .addChoice(0, 2)
                .addChoice(0, 3)
                .addChoice(0, 4)
                .addChoice(0, 5)
                .addChoice(0, 6)
                .addFinalChoice(1, (byte) 1);
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        if(interactionID == (byte) 1){
            level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
            this.discard();
            player.displayClientMessage(BUILDER.buildDialogue(this, BUILDER.buildDialogueAnswer(entityType, 7)), false);
        } else {
            setConversingPlayer(null);
        }
    }

}
