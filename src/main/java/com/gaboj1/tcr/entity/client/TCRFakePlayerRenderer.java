package com.gaboj1.tcr.entity.client;

import com.gaboj1.tcr.entity.TCRFakePlayer;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TCRFakePlayerRenderer extends EntityRenderer<TCRFakePlayer> {
    public TCRFakePlayerRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TCRFakePlayer fakePlayer) {
        return fakePlayer.getSkinTextureLocation();
    }

    @Override
    public void render(@NotNull TCRFakePlayer fakePlayer, float yaw, float p_114487_, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_114490_) {
        super.render(fakePlayer, yaw, p_114487_, poseStack, bufferSource, p_114490_);
        poseStack.pushPose();
        poseStack.mulPose(fakePlayer.getSleepDirection().getRotation());
        poseStack.translate(0, -1.4, 0);
        AbstractClientPlayer clientPlayer = new RemotePlayer(((ClientLevel) fakePlayer.level()), new GameProfile(fakePlayer.getRealPlayerUuid(), fakePlayer.getDisplayName().getString()));
        getRenderer(clientPlayer).render(clientPlayer, 0, p_114487_, poseStack, bufferSource, p_114490_);
        poseStack.popPose();
    }
    private <T extends Entity> EntityRenderer<? super T> getRenderer(T entity) {
        return Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
    }

}
