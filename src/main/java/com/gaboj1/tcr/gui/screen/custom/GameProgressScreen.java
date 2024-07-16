package com.gaboj1.tcr.gui.screen.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.util.SaveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 展示所有对话记录
 * TODO: 展示进入游戏以来所有的对话记录，世界等级，已完成的群系，还有当前的任务等等
 */
@OnlyIn(Dist.CLIENT)
public class GameProgressScreen extends Screen {
    private final ResourceLocation MY_BACKGROUND_LOCATION = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/progress_screen/background.png");
    private final ResourceLocation worldLevel;
    private ObjectSelectionList<?> dialogList;
    private ObjectSelectionList<?> tasksList;
    private ObjectSelectionList<?> currentList;

    public GameProgressScreen() {
        super(Component.literal("Progress"));
        worldLevel = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/world_level_"+SaveUtil.worldLevel+".png");

    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class DialogEntry extends ContainerObjectSelectionList.Entry<DialogEntry> {
        @Nullable
        final List<FormattedCharSequence> tooltip;

        public DialogEntry(@Nullable List<FormattedCharSequence> p_194062_) {
            this.tooltip = p_194062_;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DialogList extends ContainerObjectSelectionList<DialogEntry> {

        public DialogList(Minecraft p_94010_, int p_94011_, int p_94012_, int p_94013_, int p_94014_, int p_94015_) {
            super(p_94010_, p_94011_, p_94012_, p_94013_, p_94014_, p_94015_);
        }
    }

}
