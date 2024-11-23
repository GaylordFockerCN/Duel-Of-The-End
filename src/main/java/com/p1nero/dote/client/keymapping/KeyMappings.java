package com.p1nero.dote.client.keymapping;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.DOTEConfig;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyMappings {

	public static final MyKeyMapping OPEN_PROGRESS = new MyKeyMapping(buildKey("open_progress"), GLFW.GLFW_KEY_J, "key.categories.tcr");

	public static String buildKey(String name){
		return "key.tcr." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
	}

	@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, value = Dist.CLIENT)
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
//			if(OPEN_PROGRESS.isRelease()){
//				DOTEConfig.RENDER_CUSTOM_GUI.set(!DOTEConfig.RENDER_CUSTOM_GUI.get());
//			}
		}
	}



}
