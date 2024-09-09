package com.gaboj1.tcr.entity.custom.villager.biome2;

import com.gaboj1.tcr.entity.ai.behavior.TCRVillagerTasks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

/**
 * 废案。本来打算一种职业的村民一个类，但是这样需要在主类写一大堆东东很烦。不如shift+右键切换职业快，总不能几百个吧哈哈哈
 */
public class StationaryVillager2 extends TalkableVillager2 {

    public StationaryVillager2(EntityType<? extends StationaryVillager2> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * 不会动的村民不能带脑子！
     */
    @Override
    protected void registerBrainGoals(Brain<Villager> pVillagerBrain) {
        pVillagerBrain.addActivity(Activity.CORE, TCRVillagerTasks.getTCRVillagerCorePackage(this));
    }

    /**
     * 为毛无效。。？？
     */
    @Override
    public void tick() {
        super.tick();
        if(this.conversingPlayer != null){
//            this.lookAt(conversingPlayer, 180, 180);
            this.getLookControl().setLookAt(conversingPlayer);
        }
    }
}
