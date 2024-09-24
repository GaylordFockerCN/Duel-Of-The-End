package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.event.PlayerModelEvent;
import com.gaboj1.tcr.item.TCRItems;
import com.gaboj1.tcr.item.custom.PoseItem;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class PlayerModelHandler {

    /**
     * 实现手持物品时的角色姿势
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderPlayer(PlayerModelEvent.SetupAngles.Post event) {
        Player player = event.getEntity();
        Item mainHand = player.getMainHandItem().getItem();
        Item offHand = player.getOffhandItem().getItem();
        boolean right = player.getMainArm() == HumanoidArm.RIGHT;
        PlayerModel<?> model = event.getModelPlayer();
        if(mainHand instanceof PoseItem poseItem){
            poseItem.adjustInMainHand(event, model, right);
        }
        if(offHand instanceof PoseItem poseItem){
            poseItem.adjustInOffHand(event, model, right);
        }

        //御剑飞行的姿势
        if(ItemUtil.searchItemStack(player, TCRItems.HOLY_SWORD.get()).getOrCreateTag().getBoolean("isFlying")){

            Consumer<ModelPart> setRot = (modelPart)->{
                modelPart.xRot = 0;
                modelPart.yRot = (float) Math.toRadians(-45);
                modelPart.zRot = 0;
            };
            handleModelPart(setRot, model.body, model.leftArm, model.rightArm, model.leftLeg, model.rightLeg);

            Consumer<ModelPart> setZtoX = (modelPart)->{
                modelPart.z = modelPart.x;
            };
            handleModelPart(setZtoX, model.leftArm, model.rightArm, model.leftLeg, model.rightLeg);
        }

    }

    /**
     * 懒得操作统一写了个consumer批处理
     */

    @OnlyIn(Dist.CLIENT)
    public static void handleModelPart(Consumer<ModelPart> consumer, ModelPart... modelParts){
        for(ModelPart modelPart : modelParts)
            consumer.accept(modelPart);
    }

}
