package com.gaboj1.tcr.client.gui;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.keymapping.KeyMappings;
import com.gaboj1.tcr.util.SaveUtil;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class CustomGuiHandler {
    public static int taskTipFadeTicks;//TODO 进出效果
    public static void renderCustomGui(GuiGraphics guiGraphics){
        renderTaskTip(guiGraphics);
    }

    public static void renderTaskTip(GuiGraphics guiGraphics){
        Window window = Minecraft.getInstance().getWindow();
        Font font = Minecraft.getInstance().font;
        int x = (int) (TCRConfig.TASK_X.get() * window.getGuiScaledWidth());
        int y = (int) (TCRConfig.TASK_Y.get() * window.getGuiScaledWidth());
        Component currentTasks = TheCasketOfReveriesMod.getInfo("current_tasks", KeyMappings.OPEN_PROGRESS.getKeyName());
        guiGraphics.drawString(font, currentTasks, x, y += 12, 0x00ffff, true);
        for(SaveUtil.Dialog dialog : SaveUtil.TASK_SET){
            List<FormattedCharSequence> listName = Minecraft.getInstance().font.split(dialog.name(), Math.min(window.getGuiScaledWidth() - x, TCRConfig.TASK_SIZE.get().intValue()));
            List<FormattedCharSequence> listDescription = Minecraft.getInstance().font.split(dialog.content(), Math.min(window.getGuiScaledWidth() - x, TCRConfig.TASK_SIZE.get().intValue()));
            for(FormattedCharSequence charSequence : listName){
                guiGraphics.drawString(font, charSequence, x, y += 12, 0xff0000, true);
            }
            for(FormattedCharSequence charSequence : listDescription){
                guiGraphics.drawString(font, charSequence, x, y += 12, 0xFFFFFF, true);
            }
        }
    }
}
