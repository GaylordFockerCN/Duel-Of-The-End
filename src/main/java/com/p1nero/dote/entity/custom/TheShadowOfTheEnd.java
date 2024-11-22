package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.screen.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.util.BookManager;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.biome.BiomeMap;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.Objects;

/**
 * 终末之影
 */
public class TheShadowOfTheEnd extends DOTEBoss {

    public TheShadowOfTheEnd(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SATSUJIN.get().getDefaultInstance());
    }

    @Override
    public boolean shouldRenderBossBar() {
        return false;
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1422.0f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .build();
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(source.getEntity() instanceof ServerPlayer player){
            DialogueComponentBuilder builder = new DialogueComponentBuilder(this);
            switch (DOTEArchiveManager.getWorldLevel()){
                case 0:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(3)), false);
                    break;
                case 1:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(4)), false);
                    break;
                default:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(5)), false);
            }
            player.changeDimension(Objects.requireNonNull(player.serverLevel().getServer().overworld()),
                    new DOTETeleporter(BiomeMap.getInstance().getBlockPos(BiomeMap.getInstance().getCenter1(), 100).offset(0, 0, 400)));
            DOTEArchiveManager.worldLevelUp(player.serverLevel(), false);
            //结局判断
            if(DOTEArchiveManager.getWorldLevel() == 2){
                if(DOTEArchiveManager.BIOME_PROGRESS_DATA.isEnd3()){
                    DOTEAdvancementData.getAdvancement("star", player);
                } else {
                    DOTEAdvancementData.getAdvancement("unfinished", player);
                }
            }
            DOTEAdvancementData.getAdvancement("book", player);
        }
        super.die(source);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onAddedToWorld() {
        super.onAddedToWorld();
        DialogueComponentBuilder builder = new DialogueComponentBuilder(this);
        if(!level().isClientSide || Minecraft.getInstance().player == null){
            return;
        }
        switch (DOTEArchiveManager.getWorldLevel()){
            case 0:
                Minecraft.getInstance().player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(0)), false);
                break;
            case 1:
                Minecraft.getInstance().player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(1)), false);
                break;
            default:
                Minecraft.getInstance().player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(2)), false);
        }
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.SENBAI_BGM.get();
    }

}
