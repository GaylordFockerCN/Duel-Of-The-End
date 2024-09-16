package com.gaboj1.tcr.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 因为geckolib没有时间戳事件，所以尝试写了一个，在{@link com.gaboj1.tcr.entity.custom.biome2.SnowSwordmanEntity}首次尝试
 */
public abstract class AggressiveGeoMob extends Monster {
    private final Queue<TimeStamp> queue = new ArrayDeque<>();
    protected AggressiveGeoMob(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        for(TimeStamp event : queue){
            event.tick();
        }
    }

    public void tryHurtTarget(float attackDis, float damage, boolean isDirectDamage){
        if(getTarget() != null && getTarget().distanceTo(this) < attackDis){
            if(isDirectDamage){
                getTarget().setHealth(getTarget().getHealth() - damage);
            } else {
                getTarget().hurt(this.damageSources().mobAttack(this), damage);
            }
        }
    }

    protected void addTask(int timer, TickRunnable runnable){
        queue.add(new TimeStamp(timer, runnable, this));
    }

    /**
     * 普攻，时间到就攻击
     */
    protected void addBasicAttackTask(int delay, float damage, float dis){
        queue.add(new TimeStamp(delay, (tick -> {
            if(tick == 1){
                tryHurtTarget(dis, damage, false);
            }
        }), this));
    }

    /**
     * 多次普攻，每经过指定时间就攻击
     */
    protected void addMultiBasicAttackTask(int delay, float damage, float dis, int... timeStamps){
        queue.add(new TimeStamp(delay, (tick -> {
            for(int i : timeStamps){
                if(tick == i){
                    tryHurtTarget(dis, damage, true);
                }
            }

        }), this));
    }

    public static class TimeStamp {
        private int timer;
        private final TickRunnable runnable;
        private final AggressiveGeoMob mob;
        public TimeStamp(int timer, TickRunnable runnable, AggressiveGeoMob mob){
            this.timer = timer;
            this.runnable = runnable;
            this.mob = mob;
        }

        protected void tick(){
            timer--;
            runnable.run(timer);
            if(timer == 0){
                mob.queue.remove(this);
            }
        }
    }

    public interface TickRunnable{
        void run(int tick);
    }

}
