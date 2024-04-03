package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.custom.villager.TCRStationaryVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.gaboj1.tcr.gui.screen.DialogueComponentBuilder.BUILDER;

/**
 * 废案。本来打算一种职业的村民一个类，但是这样需要在主类写一大堆东东很烦。不如shift+右键切换职业快，总不能几百个吧哈哈哈
 */
public class PastoralPlainTalkableVillager2 extends TCRStationaryVillager {

    EntityType<?> entityType = TCRModEntities.PASTORAL_PLAIN_TALKABLE_VILLAGER_1.get();

    public PastoralPlainTalkableVillager2(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level,2);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag serverPlayerData) {

        LinkListStreamDialogueScreenBuilder builder =  new LinkListStreamDialogueScreenBuilder(this, entityType);



        Minecraft.getInstance().setScreen(builder.build());

    }

    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {

        switch (interactionID) {

        }

        this.setConversingPlayer(null);
    }
//
//    @Override
//    public String getResourceName() {
//        return "pastoral_plain_villager";
//    }
}
