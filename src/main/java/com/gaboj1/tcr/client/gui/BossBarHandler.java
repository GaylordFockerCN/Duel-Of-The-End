package com.gaboj1.tcr.client.gui;

import com.gaboj1.tcr.DuelOfTheEndMod;
import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.entity.custom.TCRBoss;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.*;

public class BossBarHandler {
    public static final Map<UUID, Integer> BOSSES = new HashMap<>();//一个BossBar对应一个实体的id，在实体构建的时候发包来设置

    public static boolean renderBossBar(GuiGraphics guiGraphics, LerpingBossEvent bossEvent, int x, int y){
        ResourceLocation barLocation = new ResourceLocation("");
        Entity boss = null;
        if (BOSSES.isEmpty()) {
            return false;
        }
        if(BOSSES.containsKey(bossEvent.getId()) && Minecraft.getInstance().level != null){
            boss = Minecraft.getInstance().level.getEntity(BOSSES.get(bossEvent.getId()));
        }
//        if(boss instanceof YggdrasilEntity){
//            barLocation = new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/gui/bossbar/first_boss_bar.png");
//        } else if(boss instanceof SecondBossEntity){
//            barLocation = new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/gui/bossbar/second_boss_bar.png");
//        } else {
//            return false;
//        }
        drawBar(guiGraphics, x, y, bossEvent, barLocation);
        //画名字
        Component component = bossEvent.getName();
        Component health = Component.literal(String.format("(%d / %d)", (int)((TCRBoss) boss).getHealth(), (int)((TCRBoss) boss).getMaxHealth()));
        int textWidth = Minecraft.getInstance().font.width(component);
        int textX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth / 2;
        int textY = y - 9;
        guiGraphics.drawString(Minecraft.getInstance().font, component, textX, textY, 16777215);
        if(TCRConfig.SHOW_BOSS_HEALTH.get()){
            int textWidth2 = Minecraft.getInstance().font.width(health);
            int textX2 = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - textWidth2 / 2;
            guiGraphics.drawString(Minecraft.getInstance().font, health, textX2, y, 16777215);
        }
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
