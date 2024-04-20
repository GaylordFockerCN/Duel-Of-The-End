package com.gaboj1.tcr.gui.screen.villager;

import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

public class PastoralPlainElderLinkListStreamScreen extends LinkListStreamDialogueScreenBuilder {
    public PastoralPlainElderLinkListStreamScreen(Entity entity, EntityType<?> entityType) {
        super(entity, entityType);
    }

    @Override
    public LinkListStreamDialogueScreenBuilder init() {
        return start(screen.buildDialogueDialog(0))
        .addChoice(screen.buildDialogueChoice(0), BUILDER.buildDialogueAnswer(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),1,"(x,y)"))
        .addChoice(screen.buildDialogueChoice(1),BUILDER.buildDialogueAnswer(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),2))
        .addChoice(screen.buildDialogueChoice(1),BUILDER.buildDialogueAnswer(TCRModEntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(),3))
        .addFinalChoice(screen.buildDialogueChoice(2),(byte)0);
    }
}
