package com.p1nero.dote.entity.custom.npc;

import com.p1nero.dote.archive.DOTEArchiveManager;
import com.p1nero.dote.client.gui.screen.LinkListStreamDialogueScreenBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

/**
 * 进维度的第一个向导
 */
public class GuideNpc extends DOTENpc{
    public GuideNpc(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.hasEffect(MobEffects.GLOWING)){
            this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
        }
        if(!level().isClientSide && level().dimension().equals(Objects.requireNonNull(level().getServer()).overworld().dimension())){
            this.discard();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen(CompoundTag senderData) {
        LinkListStreamDialogueScreenBuilder builder = new LinkListStreamDialogueScreenBuilder(this);

        switch (DOTEArchiveManager.getWorldLevel()){
            case 0:
                builder.start(0)
                    .addChoice(0, 1)
                    .addChoice(1, 2)
                    .addChoice(2, 3)
                    .addFinalChoice(9, (byte) 1);
                break;
            case 1:
                builder.start(4)
                        .addChoice(3, 5)
                        .addChoice(4, 6)
                        .addChoice(5, 7)
                        .addFinalChoice(9, (byte) 1);
                break;
            default:
                builder.start(8)
                        .addChoice(6, 9)
                        .addFinalChoice(9, (byte) 1);
                break;
        }
        if(!builder.isEmpty()){
            Minecraft.getInstance().setScreen(builder.build());
        }
    }

}
