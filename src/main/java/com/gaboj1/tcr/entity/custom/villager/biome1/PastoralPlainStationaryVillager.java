package com.gaboj1.tcr.entity.custom.villager.biome1;

import com.gaboj1.tcr.entity.custom.villager.TCRTalkableVillager;
import com.gaboj1.tcr.gui.screen.LinkListStreamDialogueScreenBuilder;
import com.gaboj1.tcr.init.TCRModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 废案。本来打算一种职业的村民一个类，但是这样需要在主类写一大堆东东很烦。不如shift+右键切换职业快，总不能几百个吧哈哈哈
 */
public class PastoralPlainStationaryVillager extends PastoralPlainTalkableVillager {

    public PastoralPlainStationaryVillager(EntityType<? extends PastoralPlainStationaryVillager> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * 不会动的村民不能带脑子！
     */
    @Override
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {

    }

}
