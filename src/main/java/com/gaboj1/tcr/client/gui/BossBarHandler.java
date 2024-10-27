package com.gaboj1.tcr.client.gui;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.custom.boss.second_boss.SecondBossEntity;
import com.gaboj1.tcr.entity.custom.boss.yggdrasil.YggdrasilEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class BossBarHandler {
//    public static final Set<LivingEntity> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());
    public static final Map<UUID, Integer> BOSSES = new HashMap<>();

    public static boolean renderBossBar(GuiGraphics guiGraphics, LerpingBossEvent bossEvent, int x, int y){
        ResourceLocation barLocation;
        Entity boss = null;
        if (BOSSES.isEmpty()) {
            return false;
        }
        System.out.println("1");
        Minecraft.getInstance().player.displayClientMessage(Component.literal(bossEvent.getId().toString()),true);
        if(BOSSES.containsKey(bossEvent.getId()) && Minecraft.getInstance().level != null){
            boss = Minecraft.getInstance().level.getEntity(BOSSES.get(bossEvent.getId()));
        }
//        for (LivingEntity mob : BOSSES) {
//            if (mob.getUUID().equals(bossEvent.getId())) {
//                boss = mob;
//                break;
//            }
//        }
        if(boss instanceof YggdrasilEntity){
            barLocation = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/gui/bossbar/first_boss_bar.png");
        } else if(boss instanceof SecondBossEntity){
            barLocation = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/gui/bossbar/second_boss_bar.png");
        } else {
            return false;
        }
        drawBar(guiGraphics, x, y, bossEvent, barLocation);
        //画名字
        Component component = bossEvent.getName();
        int textWidth = Minecraft.getInstance().font.width(component);
        int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
        int textY = y - 9;
        guiGraphics.drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        return true;
    }

    public static void drawBar(GuiGraphics guiGraphics, int x, int y, LerpingBossEvent event, ResourceLocation barLocation) {
        int progress = Mth.lerpInt(event.getProgress(), 0, 184 + 10);
        //画背景
        guiGraphics.blit(barLocation, x - 10, y, 184 + 10, 26, 0, 0, 256, 26, 256, 256);
        //画血量
        if (progress > 0) {
            guiGraphics.blit(barLocation, x - 10, y, progress, 26, 0, 31, Mth.lerpInt(event.getProgress(),0, 256), 26, 256, 256);
        }
    }

}
