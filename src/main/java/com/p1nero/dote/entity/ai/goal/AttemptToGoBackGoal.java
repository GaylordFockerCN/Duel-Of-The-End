package com.p1nero.dote.entity.ai.goal;

import com.p1nero.dote.entity.api.HomePointEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class AttemptToGoBackGoal<T extends PathfinderMob & HomePointEntity> extends Goal {
	private final T mob;
	private double wantedX;
	private double wantedY;
	private double wantedZ;
	private final double speedModifier;

	public AttemptToGoBackGoal(T mob, double speedModifier) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if(!this.mob.getTags().contains("marathon_b")){
			return false;
		}
		if (this.mob.getTarget() == null) {
			return false;
		} else {
			LivingEntity target = this.mob.getTarget();
			Vec3 vec3 = this.mob.position().add(this.mob.position().subtract(target.position()));
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone();
	}

	@Override
	public void start() {
		this.mob.getNavigation().stop();
		this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
	}
}