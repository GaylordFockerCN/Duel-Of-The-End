package com.gaboj1.tcr.entity.custom.sword;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;

/**
 * 裁雨留虹的剑改成AbstractArrow了，所以得写个接口
 */
public interface AbstractSwordEntity {
    void setPose(PoseStack poseStack);
    ItemStack getItemStack();

}
