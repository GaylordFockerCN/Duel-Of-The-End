package com.gaboj1.tcr.gui.screen;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class PastoralPlainElderLinkListStreamScreen extends LinkListStreamDialogueScreenBuilder {
    public PastoralPlainElderLinkListStreamScreen(Entity entity, EntityType<?> entityType) {
        super(entity, entityType);
    }

    @Override
    public LinkListStreamDialogueScreenBuilder init() {
        return start(screen.buildDialogueDialog(0))
        .addChoice(screen.buildDialogueChoice(0),screen.buildDialogueDialog(1,"(x,z)"))
        .addChoice(screen.buildDialogueChoice(1),screen.buildDialogueDialog(2))
        .addChoice(screen.buildDialogueChoice(1),screen.buildDialogueDialog(3))
        .addFinalChoice(screen.buildDialogueChoice(2),(byte)0);
    }
}
