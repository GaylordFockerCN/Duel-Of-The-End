package com.gaboj1.tcr.network.packet.server;

import com.gaboj1.tcr.entity.ManySkinEntity;
import com.gaboj1.tcr.entity.NpcDialogue;
import com.gaboj1.tcr.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
/**
 * 算是魔改了，为了让对话框能获取游戏进度数据（boss是否被击败），不得不把nbt标签传过去。因为客户端是无法使用ServerPlayer的，调用ServerPlayer对象会出错（试过了）
 *
 */
public record NPCDialoguePacketWithSkinID(int id, CompoundTag tag, int skinID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id());
        buf.writeNbt(this.tag());
        buf.writeInt(this.skinID());
    }

    public static NPCDialoguePacketWithSkinID decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        CompoundTag tag = buf.readNbt();
        int skinID = buf.readInt();
        return new NPCDialoguePacketWithSkinID(id, tag, skinID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            //防止意外这里也得同步一下。有时候固定位置npc不会切皮肤，很奇怪。
            if(Minecraft.getInstance().level.getEntity(this.id()) instanceof ManySkinEntity manySkinEntity){
                manySkinEntity.setSkinID(skinID);
            }
            if (Minecraft.getInstance().level.getEntity(this.id()) instanceof NpcDialogue npc) {
                npc.openDialogueScreen(this.tag());
            }
        }
    }
}