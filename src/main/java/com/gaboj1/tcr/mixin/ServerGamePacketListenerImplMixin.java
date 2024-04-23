package com.gaboj1.tcr.mixin;

import com.gaboj1.tcr.TCRConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * 通过mixin实现不显示客户端消息，使DisplayClientMessage的第一个参数为空。
 * 启用立即激活时默认关闭输出信息显示。
 * {@link ServerGamePacketListenerImpl#handleSetStructureBlock(ServerboundSetStructureBlockPacket)}
 * @author LZY
 */
@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin  {

//    @Inject(method = "handleSetStructureBlock(Lnet/minecraft/network/protocol/game/ServerboundSetStructureBlockPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"), cancellable = true)
//    private void injected(CallbackInfo ci) {
//        if(ModConfig.DISABLE_CLIENT_MESSAGE_DISPLAY.get() && ModConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.get()){
//            ci.cancel();
//        }
//    }

    /**
     * 如果隐藏的话，内容空还不行，还得在武器栏上面显示。不然左边还是会有带背景的空白消息。
     */
    @ModifyArg(method = "handleSetStructureBlock(Lnet/minecraft/network/protocol/game/ServerboundSetStructureBlockPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"), index = 0)
    private Component injected(Component arg) {
        return TCRConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.get() ? Component.empty() : arg;
    }

    @ModifyArg(method = "handleSetStructureBlock(Lnet/minecraft/network/protocol/game/ServerboundSetStructureBlockPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"), index = 1)
    private boolean injected(boolean arg) {
        return TCRConfig.ENABLE_BETTER_STRUCTURE_BLOCK_LOAD.get() || arg;
    }

}
