package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, value = Dist.CLIENT)
public class GUIListener {
    private static int worldLevel;
    private static int timer = 100;

    public static void drawLevel(int worldLevel){
        GUIListener.worldLevel = worldLevel;
    }

    /**
     * 画自己的boss战血条
     */
    @SubscribeEvent
    public static void onRenderGui(CustomizeGuiOverlayEvent.BossEventProgress event){

    }

    /**
     * 绘制使用的图片名
     */
    @SubscribeEvent
    public static void onGuiDraw(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if ((screen instanceof GenericDirtMessageScreen || screen instanceof LevelLoadingScreen) && !TCRBiomeProvider.mapName.isEmpty()) {
            Component mapName = Component.literal("[The Casket of Reveries] ").withStyle(ChatFormatting.BOLD,ChatFormatting.BLUE).append(Component.literal("Current Map : " + TCRBiomeProvider.mapName).withStyle(ChatFormatting.GOLD));
            Font font = Minecraft.getInstance().font;
//            int y = (screen.height - 7) - font.wordWrapHeight(mapName, screen.width);
            int y = 7 + font.wordWrapHeight(mapName, screen.width);
            for (FormattedCharSequence sequence : font.split(mapName, screen.width)) {
                guiGraphics.drawCenteredString(font, sequence, screen.width / 2, y, 16777113);
                y += 9;
            }

        }
    }

    /**
     * 5s内渲染世界等级提升图标
     * TODO 淡化效果，加上alpha
     */
    @SubscribeEvent
    public static void renderWorldLevel(RenderGuiEvent.Pre event) {
        if(worldLevel == 0){
            return;
        }
        timer--;
        if(timer <= 0){
            worldLevel = 0;
            timer = 100;
        }
        Window window = Minecraft.getInstance().getWindow();
        ResourceLocation bg = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "textures/gui/level"+worldLevel);
        event.getGuiGraphics().blit(bg, window.getGuiScaledWidth()/2 - 20, 0, 0, 0, 256, 256);
    }
}
