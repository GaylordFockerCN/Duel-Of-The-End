package com.gaboj1.tcr.event.listeners;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlockEntities;
import com.gaboj1.tcr.block.entity.client.PortalBlockRenderer;
import com.gaboj1.tcr.block.entity.client.TigerSpawnerRenderer;
import com.gaboj1.tcr.block.entity.client.YggdrasilBlockRenderer;
import com.gaboj1.tcr.block.renderer.BetterStructureBlockRenderer;
import com.gaboj1.tcr.block.renderer.PortalBedRenderer;
import com.gaboj1.tcr.entity.TCREntities;
import com.gaboj1.tcr.entity.client.IceThornRenderer;
import com.gaboj1.tcr.entity.client.TCRFakePlayerRenderer;
import com.gaboj1.tcr.entity.client.big_hammer.BigHammerRenderer;
import com.gaboj1.tcr.entity.client.biome1.UnknownRenderer;
import com.gaboj1.tcr.entity.client.biome1.MiddleTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.biome1.SmallTreeMonsterRenderer;
import com.gaboj1.tcr.entity.client.biome1.TreeGuardianRenderer;
import com.gaboj1.tcr.entity.client.biome2.*;
import com.gaboj1.tcr.entity.client.biome3.DesertFigureRenderer;
import com.gaboj1.tcr.entity.client.biome3.SuaLiongRenderer;
import com.gaboj1.tcr.entity.client.boss.second_boss.SecondBossRenderer;
import com.gaboj1.tcr.entity.client.boss.yggrasil.TreeClawRenderer;
import com.gaboj1.tcr.entity.client.boss.yggrasil.YggdrasilRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.CrabRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.JellyCatRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.SquirrelRenderer;
import com.gaboj1.tcr.entity.client.dreamspirit.WindFeatherFalconRenderer;
import com.gaboj1.tcr.entity.client.villager.TCRVillagerRenderer;
import com.gaboj1.tcr.entity.client.SwordEntityRenderer;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CrossbowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(TCREntities.SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.RAIN_SCREEN_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.RAIN_SCREEN_SWORD_FOR_BOSS2.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.RAIN_CUTTER_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.STELLAR_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.CONVERGENCE_SWORD.get(), SwordEntityRenderer::new);
        EntityRenderers.register(TCREntities.MAGIC_PROJECTILE.get(), LlamaSpitRenderer::new);
        EntityRenderers.register(TCREntities.SPRITE_BOW_ARROW.get(), SpectralArrowRenderer::new);

        EntityRenderers.register(TCREntities.MIDDLE_TREE_MONSTER.get(), MiddleTreeMonsterRenderer::new);
        EntityRenderers.register(TCREntities.TREE_GUARDIAN.get(), TreeGuardianRenderer::new);
        EntityRenderers.register(TCREntities.SMALL_TREE_MONSTER.get(), SmallTreeMonsterRenderer::new);
        EntityRenderers.register(TCREntities.UNKNOWN.get(), UnknownRenderer::new);
        EntityRenderers.register(TCREntities.WIND_FEATHER_FALCON.get(), WindFeatherFalconRenderer::new);
        EntityRenderers.register(TCREntities.JELLY_CAT.get(), JellyCatRenderer::new);
        EntityRenderers.register(TCREntities.SQUIRREL.get(), SquirrelRenderer::new);
        EntityRenderers.register(TCREntities.CRAB.get(), CrabRenderer::new);

        EntityRenderers.register(TCREntities.P1NERO.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.FAKE_PLAYER.get(), TCRFakePlayerRenderer::new);

        EntityRenderers.register(TCREntities.PASTORAL_PLAIN_VILLAGER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.PASTORAL_PLAIN_TALKABLE_VILLAGER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.PASTORAL_PLAIN_STATIONARY_VILLAGER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.PASTORAL_PLAIN_VILLAGER_ELDER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.ELINOR.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.SMITH.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.ELIA.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.VILLAGER2.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.VILLAGER2_TALKABLE.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.VILLAGER2_STATIONARY.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.MIAO_YIN.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.SHANG_REN.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.RECEPTIONIST.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.WANDERER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.TRIAL_MASTER.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.CANG_LAN.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.ZHEN_YU.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.DUAN_SHAN.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.CUI_HUA.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.YUN_YI.get(), TCRVillagerRenderer::new);
        EntityRenderers.register(TCREntities.YAN_XIN.get(), TCRVillagerRenderer::new);

        EntityRenderers.register(TCREntities.YGGDRASIL.get(), YggdrasilRenderer::new);
        EntityRenderers.register(TCREntities.TREE_CLAW.get(), TreeClawRenderer::new);

        EntityRenderers.register(TCREntities.SPRITE.get(), SpriteRenderer::new);
        EntityRenderers.register(TCREntities.BOXER.get(), BoxerRenderer::new);
        EntityRenderers.register(TCREntities.BIG_HAMMER.get(), BigHammerRenderer::new);
        EntityRenderers.register(TCREntities.SNOW_SWORDMAN.get(), SnowSwordmanRenderer::new);
        EntityRenderers.register(TCREntities.SWORD_CONTROLLER.get(), SwordControllerRenderer::new);
        EntityRenderers.register(TCREntities.SECOND_BOSS.get(), SecondBossRenderer::new);

        EntityRenderers.register(TCREntities.TIGER.get(), TigerRenderer::new);

        //第三群系
        EntityRenderers.register(TCREntities.SUALIONG.get(), SuaLiongRenderer::new);
        EntityRenderers.register(TCREntities.DESERT_FIGURE.get(), DesertFigureRenderer::new);


        BlockEntityRenderers.register(TCRBlockEntities.PORTAL_BED.get(), PortalBedRenderer::new);

        event.enqueueWork(() ->{

            ItemPropertyFunction PULL = (itemStack, level, entity, i) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    return entity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
                }
            };
            ItemPropertyFunction PULLING = (itemStack, level, entity, i) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F;

            ItemPropertyFunction CHARGED = (itemStack, level, entity, i) -> CrossbowItem.isCharged(itemStack) ? 1 : 0;
            ItemPropertyFunction WU_LIAN_BO = (itemStack, level, entity, i) -> {
                int current = itemStack.getOrCreateTag().getInt("current");
                itemStack.getOrCreateTag().putInt("current", (current + 1) % 101);
                if(current > 50){
                    current = 101 - current;
                }
                return Mth.lerpInt(current / 52.0F, 0, 5);
            };//三连播
            ItemProperties.register(TCRItems.ORICHALCUM_BOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.ORICHALCUM_BOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);
            ItemProperties.register(TCRItems.SPRITE_BOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.SPRITE_BOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);

            ItemProperties.register(TCRItems.ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);
            ItemProperties.register(TCRItems.ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"charged"), CHARGED);
            ItemProperties.register(TCRItems.GOD_ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.GOD_ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);
            ItemProperties.register(TCRItems.GOD_ORICHALCUM_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"charged"), CHARGED);
            ItemProperties.register(TCRItems.SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);
            ItemProperties.register(TCRItems.SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"charged"), CHARGED);
            ItemProperties.register(TCRItems.BASIC_SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pull"), PULL);
            ItemProperties.register(TCRItems.BASIC_SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"pulling"), PULLING);
            ItemProperties.register(TCRItems.BASIC_SPRITE_CROSSBOW.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"charged"), CHARGED);

            ItemProperties.register(TCRItems.GOD_ORICHALCUM.get(), new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"god_orichalcum"), WU_LIAN_BO);

        });

    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(TCRBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.PORTAL_BLOCK_ENTITY.get(), PortalBlockRenderer::new);
        event.registerEntityRenderer(TCREntities.DESERT_EAGLE_BULLET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(TCREntities.ICE_THORN_ENTITY.get(), IceThornRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.YGGDRASIL_SPAWNER_BLOCK_ENTITY.get(), YggdrasilBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.TIGER_TRIAL_SPAWNER_BLOCK_ENTITY.get(), (context -> new TigerSpawnerRenderer()));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PortalBedRenderer.HEAD, PortalBedRenderer::createHeadLayer);
        event.registerLayerDefinition(PortalBedRenderer.FOOT, PortalBedRenderer::createFootLayer);
    }

}