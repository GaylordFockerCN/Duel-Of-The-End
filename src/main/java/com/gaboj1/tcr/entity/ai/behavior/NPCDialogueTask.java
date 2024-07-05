package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.NpcDialogue;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;

/**
 * 对话的时候不能动还要看着玩家
 */
public class NPCDialogueTask extends Behavior<Mob> {
    public NPCDialogueTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel p_22538_, Mob mob) {
        return check(mob);
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, Mob mob, long p_22547_) {
        return check(mob);
    }

    private boolean check(Mob mob){
        return mob instanceof NpcDialogue npc && npc.getConversingPlayer() != null;
    }

    @Override
    protected void tick(ServerLevel p_22551_, Mob mob, long p_22553_) {
        if(mob instanceof NpcDialogue npc && npc.getConversingPlayer() != null){
            mob.lookAt(npc.getConversingPlayer(), 30, 30);
        }
        mob.getNavigation().stop();
    }
}
