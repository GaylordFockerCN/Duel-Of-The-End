package com.gaboj1.tcr.item.custom;

import com.gaboj1.tcr.event.PlayerModelEvent;
import net.minecraft.client.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 方便物品调整手的姿势
 */
@OnlyIn(Dist.CLIENT)
public interface PoseItem {
    void adjustInMainHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right);
    void adjustInOffHand(PlayerModelEvent.SetupAngles.Post event, PlayerModel<?> model, boolean right);
}
