package com.gaboj1.tcr.client.keymapping;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.client.gui.screen.custom.GameProgressScreen;
import com.gaboj1.tcr.item.custom.weapon.GunCommon;
import com.gaboj1.tcr.network.PacketRelay;
import com.gaboj1.tcr.network.TCRPacketHandler;
import com.gaboj1.tcr.network.packet.serverbound.GunReloadPacket;
import com.gaboj1.tcr.worldgen.biome.TCRBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
	public static final MyKeyMapping RELOAD = new MyKeyMapping("key.tcr.gun_reload", GLFW.GLFW_KEY_R, "key.categories.tcr") {
		private boolean isDownOld = false;
		private boolean isReloading = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown &&!isReloading) {
				isReloading = true;
				PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new GunReloadPacket());
				GunCommon.reload(Minecraft.getInstance().player);
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

	public static final MyKeyMapping OPEN_PROGRESS = new MyKeyMapping(buildKey("open_progress"), GLFW.GLFW_KEY_J, "key.categories.tcr");

	public static String buildKey(String name){
		return "key.tcr." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(RELOAD);
	}

	@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, value = Dist.CLIENT)
	public static class KeyPressHandler {
		/**
		 * 在群系里才能打开窗口看，主世界没法打开
		 */
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
//			if(OPEN_PROGRESS.isDown() && !(Minecraft.getInstance().screen instanceof GameProgressScreen)){
//				if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null){
//					BlockPos pos = Minecraft.getInstance().player.getOnPos();
//					if(Minecraft.getInstance().level.getBiome(pos).is(TCRBiomeTags.IS_TCR)){
//						PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new RequestOpenProgressScreenPacket(new CompoundTag()));
//					}
//				}
//			}
			if(OPEN_PROGRESS.isRelease()){
				TCRConfig.RENDER_CUSTOM_GUI.set(!TCRConfig.RENDER_CUSTOM_GUI.get());
			}
		}
	}



}
