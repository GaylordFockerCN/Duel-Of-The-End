package com.p1nero.dote.client.gui;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.archive.Task;
import com.p1nero.dote.client.keymapping.KeyMappings;
import com.p1nero.dote.archive.DOTEArchiveManager;
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
        int x = (int) (DOTEConfig.TASK_X.get() * window.getGuiScaledWidth());
        int y = (int) (DOTEConfig.TASK_Y.get() * window.getGuiScaledWidth());
        int interval = DOTEConfig.INTERVAL.get();
        if(DOTEArchiveManager.getWorldLevel() > 0){
            Component currentLevel = DuelOfTheEndMod.getInfo("current_level", DOTEArchiveManager.getWorldLevelName());
            guiGraphics.drawString(font, currentLevel, x, y += interval, 0x00ff00, true);
        }
        Component currentTasks = DuelOfTheEndMod.getInfo("current_tasks", KeyMappings.OPEN_PROGRESS.getKeyName());
        guiGraphics.drawString(font, currentTasks, x, y += interval, 0x00ff00, true);
        for(Task task : DOTEArchiveManager.TASK_SET){
//            List<FormattedCharSequence> listName = Minecraft.getInstance().font.split(dialog.name(), Math.min(window.getGuiScaledWidth() - x, TCRConfig.TASK_SIZE.get().intValue()));
            guiGraphics.drawString(font, task.getName(), x, y += interval, 0xff0000, true);
            List<FormattedCharSequence> listDescription = Minecraft.getInstance().font.split(task.getContent(), Math.min(window.getGuiScaledWidth() - x, DOTEConfig.TASK_SIZE.get().intValue()));
//            for(FormattedCharSequence charSequence : listName){
//                guiGraphics.drawString(font, charSequence, x, y += interval, 0xff0000, true);
//            }
            for(FormattedCharSequence charSequence : listDescription){
                guiGraphics.drawString(font, charSequence, x, y += interval, 0xFFFFFF, true);
            }
        }
    }
}
