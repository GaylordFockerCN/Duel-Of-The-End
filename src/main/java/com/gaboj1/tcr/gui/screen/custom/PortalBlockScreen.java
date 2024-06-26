package com.gaboj1.tcr.gui.screen.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.gui.screen.component.PortalScreenComponent;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.client.PortalBlockTeleportPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * 传送石窗口，目前只实现了五个传送点（应该够了吧背景图都画了），
 *
 * @author LZY
 */
public class PortalBlockScreen extends Screen {
    public static final ResourceLocation MY_BACKGROUND_LOCATION = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/portal_background.png");

    PortalScreenComponent bossFinal = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.final"), button -> this.finishChat((byte) 0));
    PortalScreenComponent boss1 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss1"), button -> this.finishChat((byte) 1));
    PortalScreenComponent boss2 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss2"), button -> this.finishChat((byte) 2));
    PortalScreenComponent boss3 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss3"), button -> this.finishChat((byte) 3));
    PortalScreenComponent boss4 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss4"), button -> this.finishChat((byte) 4));


    public PortalBlockScreen(CompoundTag serverPlayerData) {
        super(Component.empty());
    }

    @Override
    protected void init() {
        positionButton();
        this.addRenderableWidget(bossFinal);
        this.addRenderableWidget(boss1);
        this.addRenderableWidget(boss2);
        this.addRenderableWidget(boss3);
        this.addRenderableWidget(boss4);

        super.init();
    }

    /**
     * 服务端处理，不同id对应传送不同位置
    * */
    protected void finishChat(byte interactionID) {
        PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new PortalBlockTeleportPacket(interactionID));
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 200) / 2;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(posX + 128, posY + 90, 0.0F);
        poseStack.scale(1.2f,1.2f,1.0f);
        guiGraphics.blit(MY_BACKGROUND_LOCATION, -128, -90, 0, 0, 256, 181);
        poseStack.popPose();
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.width = width;
        this.height = height;
        this.positionButton();
    }

    /**
     * 反正就五个按钮手动来啦
     */
    private void positionButton() {
        //正中
        bossFinal.setX(this.width / 2 - bossFinal.getWidth() / 2);
        bossFinal.setY(this.height / 2 - bossFinal.getHeight() / 2);

        //下
        boss1.setX(this.width / 2 - boss1.getWidth() / 2);
        boss1.setY(this.height *3 / 4 - boss1.getHeight() / 2);

        //右
        boss2.setX(this.width *3 / 4 - boss2.getWidth() / 2);
        boss2.setY(this.height / 2 - boss2.getHeight() / 2);

        //上
        boss3.setX(this.width / 2 - boss3.getWidth() / 2);
        boss3.setY(this.height / 4 - boss3.getHeight() / 2);

        //左
        boss4.setX(this.width / 4 - boss4.getWidth() / 2);
        boss4.setY(this.height / 2 - boss4.getHeight() / 2);
    }

}
