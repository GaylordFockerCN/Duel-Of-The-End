package com.gaboj1.tcr.entity.ai.goal;

import com.gaboj1.tcr.entity.custom.Yggdrasil.YggdrasilEntity;
import com.gaboj1.tcr.entity.custom.Yggdrasil.tree_clawEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;

public class LaunchTreeClawGoal extends Goal {
    private final YggdrasilEntity yggdrasil;

    public LaunchTreeClawGoal(YggdrasilEntity yggdrasil){
        this.yggdrasil = yggdrasil;
    }


    @Override
    public void tick() {
        LivingEntity targetedEntity =this.yggdrasil.getTarget();
        if (targetedEntity == null)
            return;
//        float dist =this.yggdrasil.distanceTo(targetedEntity);
//        if(dist < 20F && this.yggdrasil.getSensing().hasLineOfSight(targetedEntity))
        this.yggdrasil.launchProjectileAt(new tree_clawEntity(this.yggdrasil.level(), this.yggdrasil));
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
