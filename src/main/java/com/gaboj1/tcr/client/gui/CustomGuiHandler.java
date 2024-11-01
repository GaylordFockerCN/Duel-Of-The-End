package com.gaboj1.tcr.client.gui;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.keymapping.KeyMappings;
import com.gaboj1.tcr.client.keymapping.MyKeyMapping;
import com.gaboj1.tcr.util.SaveUtil;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
        guiGraphics.drawString(font, TheCasketOfReveriesMod.getInfo("current_tasks", KeyMappings.OPEN_PROGRESS.getKeyName()), x, y += 12, 0xFFFFFF, true);
        for(SaveUtil.Dialog dialog : SaveUtil.TASK_SET){
            List<FormattedCharSequence> listName = Minecraft.getInstance().font.split(dialog.name(), window.getGuiScaledWidth() - x);
            List<FormattedCharSequence> listDescription = Minecraft.getInstance().font.split(dialog.content(), window.getGuiScaledWidth() - x);
            for(FormattedCharSequence charSequence : listName){
                guiGraphics.drawString(font, charSequence, x, y += 12, 0xFFFFFF, true);
            }
            for(FormattedCharSequence charSequence : listDescription){
                guiGraphics.drawString(font, charSequence, x, y += 12, 0xFFFFFF, true);
            }
        }
    }
}
