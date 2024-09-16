package com.gaboj1.tcr.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class AttackableGeoMob extends Monster {
    private final Queue<TimeStamp> queue = new ArrayDeque<>();
    protected AttackableGeoMob(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public void tick() {
        super.tick();
        for(TimeStamp event : queue){
            event.tick();
        }
    }

    protected void addTask(int timer, TickRunnable runnable){
        queue.add(new TimeStamp(timer, runnable, this));
    }

    public static class TimeStamp {
        private int timer;
        private final TickRunnable runnable;
        private final AttackableGeoMob mob;
        public TimeStamp(int timer, TickRunnable runnable, AttackableGeoMob mob){
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
