package com.gaboj1.tcr.entity.custom.villager;

import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PastoralPlainVillager extends TCRVillager {

    public PastoralPlainVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        whatCanISay = 3;
    }

    @Override
    public void talk(Player player){
        player.sendSystemMessage(Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".chat"+(1+r.nextInt(whatCanISay))));
    }
    @Override
    public void talkFuck(Player player){
        player.sendSystemMessage(Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".fuck_chat"+(1+r.nextInt(whatCanISay))));
    }

    @Override
    public String getResourceName() {
        return "pastoral_plain_villager";
    }

}
