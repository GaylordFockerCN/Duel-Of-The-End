package com.gaboj1.tcr.client.gui;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.archive.Task;
import com.gaboj1.tcr.client.keymapping.KeyMappings;
import com.gaboj1.tcr.archive.TCRArchiveManager;
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
        int interval = TCRConfig.INTERVAL.get();
        if(TCRArchiveManager.getWorldLevel() > 0){
            Component currentLevel = TheCasketOfReveriesMod.getInfo("current_level", TCRArchiveManager.getWorldLevelName());
            guiGraphics.drawString(font, currentLevel, x, y += interval, 0x00ff00, true);
        }
        Component currentTasks = TheCasketOfReveriesMod.getInfo("current_tasks", KeyMappings.OPEN_PROGRESS.getKeyName());
        guiGraphics.drawString(font, currentTasks, x, y += interval, 0x00ff00, true);
        for(Task task : TCRArchiveManager.TASK_SET){
//            List<FormattedCharSequence> listName = Minecraft.getInstance().font.split(dialog.name(), Math.min(window.getGuiScaledWidth() - x, TCRConfig.TASK_SIZE.get().intValue()));
            guiGraphics.drawString(font, task.getName(), x, y += interval, 0xff0000, true);
            List<FormattedCharSequence> listDescription = Minecraft.getInstance().font.split(task.getContent(), Math.min(window.getGuiScaledWidth() - x, TCRConfig.TASK_SIZE.get().intValue()));
//            for(FormattedCharSequence charSequence : listName){
//                guiGraphics.drawString(font, charSequence, x, y += interval, 0xff0000, true);
//            }
            for(FormattedCharSequence charSequence : listDescription){
                guiGraphics.drawString(font, charSequence, x, y += interval, 0xFFFFFF, true);
            }
        }
    }
}
