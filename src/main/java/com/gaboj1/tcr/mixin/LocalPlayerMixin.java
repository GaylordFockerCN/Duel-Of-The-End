package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.item.custom.Book;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin extends AbstractClientPlayer {

    public LocalPlayerMixin(ClientLevel p_250460_, GameProfile p_249912_) {
        super(p_250460_, p_249912_);
    }

    //不然自定义书打不开。。
    @Inject(method = {"openItemGui"}, at = {@At("HEAD")})
    public void openItemGui(ItemStack itemStack, InteractionHand hand, CallbackInfo info) {
        if (itemStack.getItem() instanceof Book) {
            Minecraft.getInstance().setScreen(new BookEditScreen((LocalPlayer)(Object)this, itemStack, hand));
        }

    }

}
