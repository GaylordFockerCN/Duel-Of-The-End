package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.event.PlayerModelEvent;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
public class PlayerModelHandler {

    /**
     * 实现手持物品时的角色姿势
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderPlayer(PlayerModelEvent.SetupAngles.Post event) {

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
