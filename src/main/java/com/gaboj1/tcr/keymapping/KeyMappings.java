package com.gaboj1.tcr.keymapping;

import com.gaboj1.tcr.gui.screen.custom.GameProgressScreen;
import com.gaboj1.tcr.item.custom.DesertEagleItem;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.client.DesertEagleReloadPacket;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyMappings {
	public static final KeyMapping RELOAD = new KeyMapping("key.tcr.gun_reload", GLFW.GLFW_KEY_R, "key.categories.tcr") {
		private boolean isDownOld = false;
		private boolean isReloading = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown &&!isReloading) {
				isReloading = true;
				PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new DesertEagleReloadPacket());
				DesertEagleItem.reload(Minecraft.getInstance().player);
				new Thread(()->{
					try {
						Thread.sleep(2000);//NOTE: 当年不懂事暴力写的，现在懒得改了能用就好hhh
						isReloading = false;
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}).start();
			}
			isDownOld = isDown;
		}
	};

	public static final KeyMapping OPEN_PROGRESS = new KeyMapping(buildKey("open_progress"), GLFW.GLFW_KEY_J, "key.categories.tcr");

	public static String buildKey(String name){
		return "key.tcr." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(RELOAD);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if(OPEN_PROGRESS.isDown() && !(Minecraft.getInstance().screen instanceof GameProgressScreen)){
			Minecraft.getInstance().setScreen(new GameProgressScreen());
		}
	}

}
