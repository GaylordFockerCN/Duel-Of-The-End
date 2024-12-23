package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.util.ItemUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class SenbaiDevil extends DOTEBoss{

    public SenbaiDevil(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, WOMItems.SATSUJIN.get().getDefaultInstance());
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 573.03f)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .add(EpicFightAttributes.IMPACT.get(), 1.1f)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 3)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 80)
                .add(EpicFightAttributes.WEIGHT.get(), 3)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public float getHomeRadius() {
        return 37;
    }

    @Override
    public int getMaxNeutralizeCount() {
        return 16 + DOTEArchiveManager.getWorldLevel() * 3;
    }

    /**
     * 播放音效，解锁玩家进入维度的权限
     */
    @Override
    public void die(@NotNull DamageSource source) {
        level().playSound(null , getX(), getY(), getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS,1,1);
        if(source.getEntity() instanceof ServerPlayer player){
            player.getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(dotePlayer -> dotePlayer.setCanEnterPBiome(true));
            DialogueComponentBuilder builder = new DialogueComponentBuilder(this);
            switch (DOTEArchiveManager.getWorldLevel()){
                case 0:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(0)), false);
                    break;
                case 1:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(1)), false);
                    break;
                default:
                    player.displayClientMessage(builder.buildDialogue(this, builder.buildDialogueAnswer(2)), false);
            }
            DOTEArchiveManager.BIOME_PROGRESS_DATA.setSenbaiFought(true);
            if(DOTEArchiveManager.BIOME_PROGRESS_DATA.isGoldenFlameFought()){
                DOTEAdvancementData.getAdvancement("golden_flame", player);
            }
            if(!DOTEArchiveManager.BIOME_PROGRESS_DATA.isSenbaiFought()){
                ItemUtil.addItem(player, DOTEItems.ADVENTURESPAR.get(), 20 * (DOTEArchiveManager.getWorldLevel() + 1));
            }
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.SENBAI_BGM.get();
    }

}
