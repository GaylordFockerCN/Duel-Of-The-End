package com.gaboj1.tcr.entity.custom.villager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
