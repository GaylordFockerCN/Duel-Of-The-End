package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * 牧歌原野村民。继承主要是为了更好分配id
 * @author LZY
 */
public class PastoralPlainVillager extends TCRVillager {

    public PastoralPlainVillager(EntityType<? extends TCRVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new Random().nextInt(MAX_TYPES+MAX_FEMALE_TYPES)-MAX_FEMALE_TYPES);
        whatCanISay = 5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void talk(Player player){
        talk(player, Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".chat"+(r.nextInt(whatCanISay))));
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void talkFuck(Player player){
        talk(player, Component.translatable(TCRModEntities.PASTORAL_PLAIN_VILLAGER.get().getDescriptionId()+".fuck_chat"+(r.nextInt(whatCanISay))));
    }

}
