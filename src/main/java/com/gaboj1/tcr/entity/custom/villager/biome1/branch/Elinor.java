package com.gaboj1.tcr.entity.custom.villager.biome1.branch;

import com.gaboj1.tcr.client.gui.screen.DialogueComponentBuilder;
import com.gaboj1.tcr.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.client.gui.screen.TreeNode;
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

public class Elinor extends MushroomLineNpc {

    private final EntityType<?> entityType = TCREntities.ELINOR.get();
    private final DialogueComponentBuilder BUILDER = new DialogueComponentBuilder(entityType);
    public Elinor(EntityType<? extends Elinor> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 1);
    }

    @Override
    public String getResourceName() {
        return "elinor";
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCREntities.ELINOR.get().getDescriptionId());
    }

    @Override
    public void die(@NotNull DamageSource source) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);
        builder.setAnswerRoot(new TreeNode(BUILDER.buildDialogueAnswer(0))
                .addLeaf(BUILDER.buildDialogueOption(0), (byte) 1)
                .addLeaf(BUILDER.buildDialogueOption(1), (byte) 1));
        Minecraft.getInstance().setScreen(builder.build());
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        if(interactionID == (byte) 1){
            level().explode(this, this.damageSources().explosion(this, this), null, getOnPos().getCenter(), 3F, false, Level.ExplosionInteraction.NONE);
            this.discard();
            player.displayClientMessage(BUILDER.buildDialogue(this, BUILDER.buildDialogueAnswer(entityType, 1)), false);
        } else {
            setConversingPlayer(null);
        }
    }


}
