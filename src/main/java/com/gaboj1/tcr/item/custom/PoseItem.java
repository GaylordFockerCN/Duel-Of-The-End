package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.event.PlayerModelEvent;
import net.minecraft.client.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 方便物品调整手的姿势
 */
public interface PoseItem {
    @OnlyIn(Dist.CLIENT)
    void adjustInMainHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right);
    @OnlyIn(Dist.CLIENT)
    void adjustInOffHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right);
}
