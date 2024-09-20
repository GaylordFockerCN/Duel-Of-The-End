package com.gaboj1.tcr.client.gui.screen.custom;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.gui.screen.component.PortalScreenComponent;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.serverbound.PortalBlockTeleportPacket;
import com.gaboj1.tcr.util.DataManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 传送石窗口，目前只实现了五个传送点（应该够了吧背景图都画了），
 *
 * @author LZY
 */
public class PortalBlockScreen extends Screen {
    public static final ResourceLocation MY_BACKGROUND_LOCATION = new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"textures/gui/portal_background.png");

    PortalScreenComponent bossFinal = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.final"), button -> this.finishChat((byte) 9));
    PortalScreenComponent biome1 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss1"), button -> this.finishChat((byte) 0));
    PortalScreenComponent biome2 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss2"), button -> this.finishChat((byte) 1));
    PortalScreenComponent biome3 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss3"), button -> this.finishChat((byte) 2));
    PortalScreenComponent biome4 = new PortalScreenComponent(Component.translatable(TheCasketOfReveriesMod.MOD_ID+".button.boss4"), button -> this.finishChat((byte) 3));
    private final boolean isVillage;
    private final boolean isFromTeleporter;
    private final boolean isSecondEnter;
    private final BlockPos bedPos;
    private final CompoundTag data;


    public PortalBlockScreen(CompoundTag serverPlayerData) {
        super(Component.empty());
        data = serverPlayerData;
        isVillage = serverPlayerData.getBoolean("isVillage");
        isFromTeleporter = serverPlayerData.getBoolean("isFromPortalBed");
        isSecondEnter = serverPlayerData.getBoolean("isSecondEnter");
        bedPos = new BlockPos(serverPlayerData.getInt("bedPosX"), serverPlayerData.getInt("bedPosY"), serverPlayerData.getInt("bedPosZ"));
    }

    @Override
    protected void init() {
        positionButton();
        this.addRenderableWidget(bossFinal);
        this.addRenderableWidget(biome1);
        this.addRenderableWidget(biome2);
        this.addRenderableWidget(biome3);
        this.addRenderableWidget(biome4);
        bossFinal.setEnable(true);
        if(!isSecondEnter){
            biome1.setEnable(true);
            biome2.setEnable(true);
            //3和4敬请期待！
//            biome3.setEnable(true);
//            biome4.setEnable(true);
        } else {
            if(isVillage){
                biome1.setEnable(DataManager.villager1Unlock.get(data));
                biome2.setEnable(DataManager.villager2Unlock.get(data));
            } else {
                biome1.setEnable(DataManager.boss1Unlock.get(data));
                biome2.setEnable(DataManager.boss2Unlock.get(data));
            }
        }
        super.init();
    }

    /**
     * 服务端处理，不同id对应传送不同位置
    * */
    protected void finishChat(byte interactionID) {
        PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new PortalBlockTeleportPacket(interactionID, isVillage, isFromTeleporter, bedPos));
        super.onClose();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
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
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
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
        biome1.setX(this.width / 2 - biome1.getWidth() / 2);
        biome1.setY(this.height *3 / 4 - biome1.getHeight() / 2);

        //右
        biome2.setX(this.width *3 / 4 - biome2.getWidth() / 2);
        biome2.setY(this.height / 2 - biome2.getHeight() / 2);

        //上
        biome3.setX(this.width / 2 - biome3.getWidth() / 2);
        biome3.setY(this.height / 4 - biome3.getHeight() / 2);

        //左
        biome4.setX(this.width / 4 - biome4.getWidth() / 2);
        biome4.setY(this.height / 2 - biome4.getHeight() / 2);
    }

}
