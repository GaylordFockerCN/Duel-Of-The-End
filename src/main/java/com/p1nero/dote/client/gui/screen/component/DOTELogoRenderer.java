package com.p1nero.dote.client.gui.screen.component;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DOTELogoRenderer {
    public static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/gui/title/main.png");
    public static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/gui/title/sub.png");
    private final boolean keepLogoThroughFade;

    public DOTELogoRenderer(boolean p_265300_) {
        this.keepLogoThroughFade = p_265300_;
    }

    public void renderLogo(GuiGraphics p_282217_, int p_283270_, float p_282051_) {
        this.renderLogo(p_282217_, p_283270_, p_282051_, 30);
    }

    public void renderLogo(GuiGraphics p_281856_, int p_281512_, float p_281290_, int p_282296_) {
        p_281856_.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : p_281290_);
        int $$4 = p_281512_ / 2 - 128;
        p_281856_.blit(MINECRAFT_LOGO, $$4, p_282296_, 0.0F, 0.0F, 256, 44, 256, 64);
        int $$5 = p_281512_ / 2 - 64;
        int $$6 = p_282296_ + 44 - 7;
        p_281856_.blit(MINECRAFT_EDITION, $$5, $$6, 0.0F, 0.0F, 128, 14, 128, 16);
        p_281856_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
