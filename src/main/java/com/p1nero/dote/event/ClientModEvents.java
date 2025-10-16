package com.p1nero.dote.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.dote.DuelOfTheEndMod;

import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.DOTEBlocks;
import com.p1nero.dote.block.renderer.BetterStructureBlockRenderer;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.client.SimpleBossRenderer;
import com.p1nero.dote.entity.custom.boss.dark_advance.client.DarkAdvanceRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.BlackHoleRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.GoldenFlameRenderer;
import com.p1nero.dote.entity.custom.boss.liu_guang.client.LiuGuangRenderer;
import com.p1nero.dote.entity.custom.boss.ms_abyss.client.MsAbyssRenderer;
import com.p1nero.dote.entity.custom.boss.reaper.client.ReaperRenderer;
import com.p1nero.dote.entity.custom.boss.sand_captain.client.SandCaptainCoffinRenderer;
import com.p1nero.dote.entity.custom.boss.sand_captain.client.SandCaptainRenderer;
import com.p1nero.dote.entity.custom.boss.senbai.client.SenbaiRenderer;
import com.p1nero.dote.entity.custom.boss.slaughter_general.client.SlaughterGeneralRenderer;
import com.p1nero.dote.entity.custom.npc.abyss_dweller.client.AbyssDwellerRenderer;
import com.p1nero.dote.item.model.GoldenDragonArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        //BOSS
        EntityRenderers.register(DOTEEntities.REAPER.get(), ReaperRenderer::new);
        EntityRenderers.register(DOTEEntities.DARK_ADVANCE.get(), DarkAdvanceRenderer::new);
        EntityRenderers.register(DOTEEntities.SAND_CAPTAIN_COFFIN.get(), SandCaptainCoffinRenderer::new);
        EntityRenderers.register(DOTEEntities.SAND_CAPTAIN.get(), SandCaptainRenderer::new);
        EntityRenderers.register(DOTEEntities.SENBAI_DEVIL.get(), SenbaiRenderer::new);
        EntityRenderers.register(DOTEEntities.SLAUGHTER_GENERAL.get(), SlaughterGeneralRenderer::new);

        EntityRenderers.register(DOTEEntities.GOLDEN_FLAME.get(), GoldenFlameRenderer::new);
        EntityRenderers.register(DOTEEntities.BLACK_HOLE.get(), BlackHoleRenderer::new);

        EntityRenderers.register(DOTEEntities.MS_ABYSS.get(), MsAbyssRenderer::new);
        EntityRenderers.register(DOTEEntities.LIU_GUANG.get(), LiuGuangRenderer::new);

        //NPC
        EntityRenderers.register(DOTEEntities.ABYSS_DWELLER.get(), AbyssDwellerRenderer::new);

        EntityRenderers.register(DOTEEntities.FALLEN_JUDGE.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/fallen_judge.png")));
        EntityRenderers.register(DOTEEntities.SAGE.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/sage.png")));
        EntityRenderers.register(DOTEEntities.SHAO_QIN.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/shao_qin.png")));
        EntityRenderers.register(DOTEEntities.SINCER_WARRIOR.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/sincer_warrior.png")));
        EntityRenderers.register(DOTEEntities.THEBANCHENG.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thebancheng.png")));
        EntityRenderers.register(DOTEEntities.THECOWCOWCOW7.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thecowcowcow7.png")));
        EntityRenderers.register(DOTEEntities.THEHOTSUMMER.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thehotsummer.png")));
        EntityRenderers.register(DOTEEntities.THESIXGOOGLE.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thesixgoogle.png")));
        EntityRenderers.register(DOTEEntities.THESUNWUKONG.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thesunwukong.png")));
        EntityRenderers.register(DOTEEntities.THEZHAOZILONG.get(), context -> new SimpleBossRenderer(context, new ResourceLocation(DuelOfTheEndMod.MOD_ID, "textures/entity/thezhaozilong.png")));

    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(DOTEBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GoldenDragonArmorModel.LAYER_LOCATION, GoldenDragonArmorModel::createBodyLayer);
    }
}
