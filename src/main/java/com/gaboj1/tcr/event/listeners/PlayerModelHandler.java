package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.event.PlayerModelEvent;
import com.gaboj1.tcr.item.TCRModItems;
import com.gaboj1.tcr.item.custom.DesertEagleItem;
import com.gaboj1.tcr.item.custom.boss_loot.HolySword;
import com.gaboj1.tcr.util.ItemUtil;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
public class PlayerModelHandler {

    /**
     * 实现手持物品时的角色姿势
     */
    @SubscribeEvent
    public static void renderPlayer(PlayerModelEvent.SetupAngles.Post event) {
        Player player = event.getEntity();
        Item mainHand = player.getMainHandItem().getItem();
        Item offHand = player.getOffhandItem().getItem();
        boolean right = player.getMainArm() == HumanoidArm.RIGHT;
        PlayerModel model = event.getModelPlayer();
        //持枪姿势
        //TODO: 单手持枪做得帅一点
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

        //御剑飞行的姿势
        if(ItemUtil.searchItem(player, TCRModItems.HOLY_SWORD.get()).getOrCreateTag().getBoolean("isFlying")){

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

//            Consumer<ModelPart> printXYZ = (modelPart)->{
//                System.out.println(modelPart.x+" "+modelPart.y+" "+modelPart.z);
//            };
//            handleModelPart(printXYZ, model.rightArm);
        }

    }

    /**
     * 懒得操作统一写了个consumer批处理
     */
    public static void handleModelPart(Consumer<ModelPart> consumer, ModelPart... modelParts){
        for(ModelPart modelPart : modelParts)
            consumer.accept(modelPart);
    }

}
