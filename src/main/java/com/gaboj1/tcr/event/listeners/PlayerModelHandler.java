package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.item.custom.DesertEagleItem;
import com.gaboj1.tcr.item.custom.boss_loot.HolySword;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.pinero.simpledeserteagle.event.PlayerModelEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerModelHandler {

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new PlayerModelHandler();
    }

    @Mod.EventBusSubscriber
    private static class ForgeBusEvents {

        //TODO: 单手持枪做得帅一点
        @SubscribeEvent
        public static void renderPlayer(PlayerModelEvent.SetupAngles.Post event) {
            Player player = event.getEntity();
            Item mainHand = player.getMainHandItem().getItem();
            Item offHand = player.getOffhandItem().getItem();
            boolean right = player.getMainArm() == HumanoidArm.RIGHT;
            PlayerModel model = event.getModelPlayer();

            //持枪姿势
            if(mainHand instanceof DesertEagleItem){
                if(right){
                    model.rightArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
                    model.rightArm.zRot = model.head.zRot;
                    model.rightArm.yRot = model.head.yRot;
                }else {
                    model.leftArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
                    model.leftArm.zRot = model.head.zRot;
                    model.leftArm.yRot = model.head.yRot;
                }
            }

            if(offHand instanceof DesertEagleItem){
                if(right){
                    model.leftArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
                    model.leftArm.zRot = model.head.zRot;
                    model.leftArm.yRot = model.head.yRot;
                }else {
                    model.rightArm.xRot = model.head.xRot + (float) Math.toRadians(-90);
                    model.rightArm.zRot = model.head.zRot;
                    model.rightArm.yRot = model.head.yRot;
                }

            }

            //御剑飞行的姿势 TODO
            if(mainHand instanceof HolySword mainHandSword && mainHandSword.isFlying()){

            }else if(offHand instanceof HolySword offHandSword && offHandSword.isFlying()){

            }


        }

    }
}
