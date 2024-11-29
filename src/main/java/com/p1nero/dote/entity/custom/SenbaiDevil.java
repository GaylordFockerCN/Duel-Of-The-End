package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.capability.DOTECapabilityProvider;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.item.EpicFightItems;

public class SenbaiDevil extends DOTEBoss{

    protected static final EntityDataAccessor<Boolean> IS_PHASE2 = SynchedEntityData.defineId(SenbaiDevil.class, EntityDataSerializers.BOOLEAN);
    public SenbaiDevil(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setItemInHand(InteractionHand.MAIN_HAND, EpicFightItems.UCHIGATANA.get().getDefaultInstance());
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, 10.0f)//测试用
                .add(Attributes.MAX_HEALTH, 1125.03f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
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
        getEntityData().define(IS_PHASE2, false);
    }

    public void setPhase2(boolean isPhase2){
        getEntityData().set(IS_PHASE2, isPhase2);
    }

    public boolean isPhase2(){
        return getEntityData().get(IS_PHASE2);
    }

    @Override
    public float getHomeRadius() {
        return 39;
    }


    @Override
    public int getMaxNeutralizeCount() {
        return 16 + DOTEArchiveManager.getWorldLevel() * 3;
    }

    @Override
    public void tick() {
        super.tick();
        if(getHealth() > getMaxHealth() / 2){
            setPhase2(false);
        }
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
        }
        super.die(source);
    }

    @Override
    public @Nullable SoundEvent getFightMusic() {
        return DOTESounds.SENBAI_BGM.get();
    }

}
