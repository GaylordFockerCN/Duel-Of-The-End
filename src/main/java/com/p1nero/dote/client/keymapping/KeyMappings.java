package com.p1nero.dote.client.keymapping;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.serverbound.RequestExitSpectatorPacket;
import com.p1nero.dote.worldgen.dimension.DOTEDimension;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyMappings {

	public static final MyKeyMapping EXIT_SPECTATOR = new MyKeyMapping(buildKey("exit_spectator"), GLFW.GLFW_KEY_ENTER, "key.categories.dote");

	public static String buildKey(String name){
		return "key.dote." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(EXIT_SPECTATOR);
	}

	@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, value = Dist.CLIENT)
	public static class KeyPressHandler {

		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if(event.phase.equals(TickEvent.Phase.END)){
				while (EXIT_SPECTATOR.consumeClick()){
					if (Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused()) {
						if(Minecraft.getInstance().player.isSpectator() && Minecraft.getInstance().player.level().dimension() == DOTEDimension.P_SKY_ISLAND_LEVEL_KEY) {
							PacketRelay.sendToServer(DOTEPacketHandler.INSTANCE, new RequestExitSpectatorPacket());
						}
					}
				}
			}
		}

	}

}
