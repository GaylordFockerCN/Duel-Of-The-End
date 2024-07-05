package com.gaboj1.tcr.gui.screen.custom;

import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.server.SyncSaveUtilPacket;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

/**
 * 展示所有对话记录
 * TODO: 展示进入游戏以来所有的对话记录，世界等级，已完成的群系，还有当前的任务等等
 */
public class GameProgressScreen extends Screen {

    public GameProgressScreen() {
        super(Component.literal("Progress"));
        PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new SyncSaveUtilPacket(new CompoundTag()));//请求获取服务端的存档记录
    }



}
