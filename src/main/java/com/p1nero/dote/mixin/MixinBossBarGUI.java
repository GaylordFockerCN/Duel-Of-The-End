package com.p1nero.dote.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nameless.indestructible.client.gui.BossBarGUi;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.p1nero.dote.DOTEConfig;
import com.p1nero.dote.entity.custom.DOTEBoss;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import static com.nameless.indestructible.client.gui.BossBarGUi.BossBarEntities;
import static com.nameless.indestructible.client.gui.BossBarGUi.cancelBossBar;

@Mixin(value = BossBarGUi.class, remap = false)
public class MixinBossBarGUI {
    @Inject(method = "renderBossBar", at = @At("HEAD"), cancellable = true)
    private void inject(CustomizeGuiOverlayEvent.BossEventProgress event, CallbackInfo ci){
        if(DOTEConfig.SHOW_BOSS_HEALTH.get()){
            if (!cancelBossBar.isEmpty()) {

                for (String bossEventName : cancelBossBar) {
                    if (event.getBossEvent().getName().getString().equals(bossEventName)) {
                        event.setCanceled(true);
                        event.setIncrement(0);
                    }
                }
            }

            BossBarEntities.values().forEach((k) -> {
                if (event.getBossEvent().getName().getString().contains(k.getOriginal().getDisplayName().getString())) {
                    event.setCanceled(true);
                    event.setIncrement(0);
                }

            });
            if (event.getBossEvent().getName().getString().equals("advanced epic fight boss")) {
                UUID infoID = event.getBossEvent().getId();
                AdvancedCustomHumanoidMobPatch<?> achPatch = BossBarEntities.get(infoID);
                if (achPatch != null) {
                    event.setCanceled(true);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    int l = Minecraft.getInstance().font.width(achPatch.getOriginal().getDisplayName());
                    int i1 = event.getWindow().getGuiScaledWidth() / 2 - l / 2;
                    int x = event.getX() - 36;
                    int y = event.getY();
                    if (event.getY() >= Minecraft.getInstance().getWindow().getGuiScaledHeight() / 3) {
                        return;
                    }

                    GuiGraphics guiGraphics = event.getGuiGraphics();
                    PoseStack barPoseStack = guiGraphics.pose();
                    ResourceLocation rl = achPatch.getBossBar();
                    barPoseStack.pushPose();
                    barPoseStack.scale(1.0F, 1.0F, 1.0F);
                    float healthRatio = Mth.clamp(achPatch.getOriginal().getHealth() / achPatch.getOriginal().getMaxHealth(), 0.0F, 1.0F);
                    int health = (int)(256.0F * healthRatio);
                    float staminaRatio = Mth.clamp(achPatch.getStamina() / achPatch.getMaxStamina(), 0.0F, 1.0F);
                    int stamina = (int)(256.0F * staminaRatio);
                    guiGraphics.blit(rl, x, y, 0.0F, 0.0F, 256, 19, 255, 255);
                    guiGraphics.blit(rl, x, y, 0.0F, 21.0F, health, 19, 255, 255);
                    guiGraphics.blit(rl, x, y + 18, 0.0F, 42.0F, 256, 10, 255, 255);
                    guiGraphics.blit(rl, x, y + 18, 0.0F, 55.0F, stamina, 10, 255, 255);
                    guiGraphics.blit(rl, x, y, 0.0F, 68.0F, 256, 29, 255, 255);
                    guiGraphics.drawString(Minecraft.getInstance().font, achPatch.getCustomName(), i1, y - 9, 16777215);

                    Component healthText = Component.literal(String.format("(%d / %d)", (int) achPatch.getOriginal().getHealth(), (int)(achPatch.getOriginal().getMaxHealth())));
                    int l1 = Minecraft.getInstance().font.width(healthText);
                    int i2 = event.getWindow().getGuiScaledWidth() / 2 - l1 / 2;
                    guiGraphics.drawString(Minecraft.getInstance().font, healthText, i2, y, 16777215);
                    event.setIncrement(44);
                }

            }
            ci.cancel();
        }
    }
}
