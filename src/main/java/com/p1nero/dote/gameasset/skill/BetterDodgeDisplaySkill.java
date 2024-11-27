package com.p1nero.dote.gameasset.skill;

import com.p1nero.dote.network.DOTEPacketHandler;
import com.p1nero.dote.network.PacketRelay;
import com.p1nero.dote.network.packet.clientbound.AddEntityAfterImageParticle;
import net.minecraft.client.gui.GuiGraphics;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class BetterDodgeDisplaySkill extends PassiveSkill {

    private static final UUID EVENT_UUID = UUID.fromString("d2d000cc-f30f-00ed-a66b-1919ac114514");
    public BetterDodgeDisplaySkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event -> {
            event.getPlayerPatch().playSound(EpicFightSounds.ENTITY_MOVE.get(), 1, 1);
            PacketRelay.sendToAll(DOTEPacketHandler.INSTANCE, new AddEntityAfterImageParticle(event.getPlayerPatch().getOriginal().getId()));
        }));
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID);
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
    }
}
