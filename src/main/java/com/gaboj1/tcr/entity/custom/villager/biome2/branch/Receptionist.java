package com.gaboj1.tcr.entity.custom.villager.biome2.branch;

import com.gaboj1.tcr.entity.TCRModEntities;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Receptionist extends YueShiLineNpc {
    public Receptionist(EntityType<? extends Receptionist> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, -1);
    }

    @Override
    public boolean isFemale() {
        return true;
    }

    @Override
    public String getResourceName() {
        return "receptionist";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(TCRModEntities.RECEPTIONIST.get().getDescriptionId());
    }

    @Override
    public void openDialogueScreen(CompoundTag senderData) {

    }

    @Override
    public boolean hurt(DamageSource source, float v) {
        return false;
    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID){

        }
        setConversingPlayer(null);
    }

}
