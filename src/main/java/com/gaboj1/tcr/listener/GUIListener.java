package com.gaboj1.tcr.listener;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, value = Dist.CLIENT)
public class GUIListener {
    /**
     * 绘制使用的图片名
     */
    @SubscribeEvent
    public static void onGuiDraw(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (screen instanceof LevelLoadingScreen && !TCRBiomeProvider.mapName.isEmpty()) {
            Component mapName = Component.literal("[The Casket of Reveries] ").withStyle(ChatFormatting.BOLD,ChatFormatting.BLUE).append(Component.literal("Current Map : " + TCRBiomeProvider.mapName).withStyle(ChatFormatting.ITALIC,ChatFormatting.GOLD));
            Font font = Minecraft.getInstance().font;
//            int y = (screen.height - 7) - font.wordWrapHeight(mapName, screen.width);
            int y = 7 + font.wordWrapHeight(mapName, screen.width);
            for (FormattedCharSequence sequence : font.split(mapName, screen.width)) {
                guiGraphics.drawCenteredString(font, sequence, screen.width / 2, y, 16777113);
                y += 9;
            }

        }
    }
}
