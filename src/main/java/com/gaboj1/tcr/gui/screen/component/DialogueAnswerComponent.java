package com.gaboj1.tcr.gui.screen.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * LZY:添加了实现打字机效果
 * A widget to handle an NPC's name and their response inside the dialogue screen.
 * @author LZY
 * @author The Aether
 */
public class DialogueAnswerComponent {
    private final List<NpcDialogueElement> splitLines;
    private Component message,name;
    public int height;
    //打字机效果的下标
    private int index;
    //打字机效果的最大值
    private int max;

    public DialogueAnswerComponent(Component message) {
        this.splitLines = new ArrayList<>();
        name = message;
        this.updateDialogue(Component.empty());
    }

    public Component getMessage() {
        return message;
    }

    public void render(GuiGraphics guiGraphics) {
        this.splitLines.forEach(element -> element.render(guiGraphics));
    }

    /**
     * Repositions the dialogue to the center of the screen.
     * @param width The {@link Integer} for the parent screen width.
     * @param height The {@link Integer} for the parent screen height.
     */
    public void reposition(int width, int height) {
        int i = 0;
        for (NpcDialogueElement dialogue : this.splitLines) {
            dialogue.width = Minecraft.getInstance().font.width(dialogue.text) + 2;
            dialogue.x = width / 2 - dialogue.width / 2;
            dialogue.y = height / 2 + i * 12;
            i++;
        }
        this.height = this.splitLines.size() * 12;
    }

    public void updateDialogue(Component message) {
        this.splitLines.clear();
        List<FormattedCharSequence> list = Minecraft.getInstance().font.split(name.copy().append(message), 300);
        this.height = list.size() * 12;
        list.forEach(text -> this.splitLines.add(new NpcDialogueElement(0, 0, 0, text)));
    }

    /**
     * 更新打字机效果的完整文本内容，并且执行一次打印机效果。
     */
    public void updateTypewriterDialogue(Component message) {
        this.message = message;
//        index = name.getString().length();//名字就不用打字机了
        index = 0;
        max = message.getString().length();
        updateTypewriterDialogue();
    }

    /**
     * 添加打字机效果，一次更新一个字
     * 不知道为什么Component提供根据下标截取String的方法，太感人了
     */
    public void updateTypewriterDialogue() {
        Style style = message.getStyle();
        updateDialogue(Component.literal(message.getString(index++)).withStyle(style));
        if(index > max){
            index--;
        }
    }

    /**
     * This inner class is used to store data for each line of text.
     */
    public static class NpcDialogueElement {
        private final FormattedCharSequence text;
        private int x;
        private int y;
        private int width;

        public NpcDialogueElement(int x, int y, int width, FormattedCharSequence text) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.width = width;
        }

        public void render(GuiGraphics guiGraphics) {
            guiGraphics.fillGradient(this.x, this.y, this.x + width, this.y + 12, 0x66000000, 0x66000000);
            guiGraphics.drawString(Minecraft.getInstance().font, this.text, this.x + 1, this.y + 1, 0xFFFFFF);
        }
    }
}
