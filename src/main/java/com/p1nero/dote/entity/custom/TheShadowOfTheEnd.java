package com.p1nero.dote.entity.custom;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.client.gui.DialogueComponentBuilder;
import com.p1nero.dote.datagen.DOTEAdvancementData;
import com.p1nero.dote.item.DOTEItems;
import com.p1nero.dote.util.ItemUtil;
import com.p1nero.dote.worldgen.biome.BiomeMap;
import com.p1nero.dote.worldgen.portal.DOTETeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reascer.wom.world.item.WOMItems;

import java.util.Objects;

/**
 * 终末之影
 */
public class TheShadowOfTheEnd extends DOTEBoss {
    private int deathTick;
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
//                .add(Attributes.MAX_HEALTH, 10.0f)//测试用
                .add(Attributes.MAX_HEALTH, 442.79f)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .build();
    }

    public int getDeathTick() {
        return deathTick;
    }

    @Override
    public void tick() {
        super.tick();
        if(deathTick > 0){
            setTarget(null);
            deathTick++;
            this.move(MoverType.SELF, new Vec3(0.0, 0.10000000149011612, 0.0));
            if (this.deathTick >= 70 && this.deathTick <= 100) {
                float f = (this.random.nextFloat() - 0.5F) * 8.0F;
                float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
                float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
                if(level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)f, this.getY() + 2.0 + (double)f1, this.getZ() + (double)f2, 1, 0.0, 0.0, 0.0, 0.01);
                    serverLevel.playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1, 1);
                }
            }
            if(this.deathTick == 100 && getLastAttacker() instanceof ServerPlayer player){
                DOTEAdvancementData.getAdvancement("book", player);
                DOTEArchiveManager.worldLevelUp(player.serverLevel(), false);
                setHealth(0);
                this.remove(RemovalReason.KILLED);
                this.gameEvent(GameEvent.ENTITY_DIE);
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_21017_) {
        if(deathTick > 0){
            return false;
        }
        return super.hurt(source, p_21017_);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if(deathTick >= 200){
            super.die(source);
            return;
        }
        if(deathTick != 0){
            return;
        }
        setHealth(1);
        setNoGravity(true);
        deathTick = 1;
        //保险
        if(source.getEntity() instanceof ServerPlayer player){
            setLastHurtByMob(player);
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
        }
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
        return getHealth() >= getMaxHealth() / 2 ? DOTESounds.BOSS_FIGHT1.get() : DOTESounds.BOSS_FIGHT2.get();
    }

}
