package com.p1nero.dote.event.listeners;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.renderer.BetterStructureBlockRenderer;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.client.*;
import com.p1nero.dote.item.model.GoldenDragonArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(DOTEEntities.SENBAI_DEVIL.get(), SenbaiRenderer::new);
        EntityRenderers.register(DOTEEntities.GOLDEN_FLAME.get(), GoldenFlameRenderer::new);
        EntityRenderers.register(DOTEEntities.BLACK_HOLE.get(), BlackHoleRenderer::new);
        EntityRenderers.register(DOTEEntities.FLAME_CIRCLE.get(), FlameCircleRenderer::new);
        EntityRenderers.register(DOTEEntities.DOTE_PIGLIN.get(), DOTEPiglinRenderer::new);
        EntityRenderers.register(DOTEEntities.DOTE_ZOMBIE.get(), DOTEZombieRenderer::new);
        EntityRenderers.register(DOTEEntities.DOTE_ZOMBIE_2.get(), DOTEZombieRenderer::new);
        EntityRenderers.register(DOTEEntities.STAR_CHASER.get(), StarChaserRenderer::new);
        EntityRenderers.register(DOTEEntities.THE_ARBITER_OF_RADIANCE.get(), TARRenderer::new);
        EntityRenderers.register(DOTEEntities.THE_PYROCLAS_OF_PURGATORY.get(), TPPRenderer::new);
        EntityRenderers.register(DOTEEntities.THE_SHADOW_OF_THE_END.get(), TSERenderer::new);
        EntityRenderers.register(DOTEEntities.GUIDE_NPC.get(), GuideNpcRenderer::new);
        EntityRenderers.register(DOTEEntities.KNIGHT_COMMANDER.get(), KnightCommanderRenderer::new);
        EntityRenderers.register(DOTEEntities.SCARLET_HIGH_PRIEST.get(), ScarletHighPriestRenderer::new);

    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(DOTEBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GoldenDragonArmorModel.LAYER_LOCATION, GoldenDragonArmorModel::createBodyLayer);
    }

    /**
     * 需要先绑定Patch和 Armature
     * {@link DOTEEntities#setArmature(ModelBuildEvent.ArmatureBuild)}
     * {@link DOTEEntities#setPatch(EntityPatchRegistryEvent)}
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderPatched(PatchedRenderersEvent.Add event) {
        EntityRendererProvider.Context context = event.getContext();
        event.addPatchedEntityRenderer(DOTEEntities.SENBAI_DEVIL.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.SKELETON, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.GOLDEN_FLAME.get(), (entityType) -> new GoldenFlamePatchedRenderer(() -> Meshes.SKELETON, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.DOTE_ZOMBIE.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.BIPED_OLD_TEX, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.DOTE_PIGLIN.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.PIGLIN, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.STAR_CHASER.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.ALEX, context, entityType).initLayerLast(context, entityType));//懒得区分粗细手臂了
        event.addPatchedEntityRenderer(DOTEEntities.GUIDE_NPC.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.ALEX, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.SCARLET_HIGH_PRIEST.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.ALEX, context, entityType).initLayerLast(context, entityType));
        event.addPatchedEntityRenderer(DOTEEntities.KNIGHT_COMMANDER.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.ALEX, context, entityType).initLayerLast(context, entityType));
    }

}