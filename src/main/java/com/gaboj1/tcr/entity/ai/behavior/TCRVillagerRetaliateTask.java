package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;

import java.util.Objects;

/**
 * 村民反击任务
 * 由于村民只能用Brain，所以得自己写个Brain。发现跟Goal还挺像，挺简单的。但是不知道为什么{@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal}写那么复杂。。
 *
 * @author LZY
 */
public class TCRVillagerRetaliateTask extends Behavior<Mob> {

    private final int attackInterval = 20;
    private int attackTimer = 0;

    public TCRVillagerRetaliateTask() {
        super(ImmutableMap.of());
    }

    /**
     *
     * @param serverLevel
     * @param mob
     * @return 村民生气或者有攻击目标
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Mob mob) {
        return (mob instanceof TCRVillager tcrVillager && tcrVillager.isAngry() || mob.getTarget()!=null);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Mob mob, long l) {
        return (mob instanceof TCRVillager tcrVillager && tcrVillager.isAngry()|| mob.getTarget()!=null);
    }

    @Override
    protected void tick(ServerLevel level, Mob mob, long l) {
        if(mob instanceof TCRVillager tcrVillager){
            if(attackTimer<0){
                LivingEntity target = tcrVillager.getTarget();
                if(target == null){
                    return;
                }

                //追！
                if(!tcrVillager.isFemale() || tcrVillager.isElder()){
                    tcrVillager.getNavigation().moveTo(target,1.0);
                }
                if(tcrVillager instanceof PastoralPlainVillagerElder){
                    //长老打得远一点很合理
                    if(tcrVillager.distanceTo(target) > 5){
                        return;
                    }
                }else if(!tcrVillager.isWithinMeleeAttackRange(target)){
                    return;
                }
                System.out.println("Chase0");
                //男的和村长才会追（

                tcrVillager.getLookControl().setLookAt(target ,30.0F, 30.0F);
                tcrVillager.doHurtTarget(Objects.requireNonNull(target));
                attackTimer = attackInterval;
            } else {
                attackTimer--;
            }
        }
    }

//    public static OneShot<Mob> create(TCRVillager villager) {
//        return BehaviorBuilder.create((mobInstance) -> mobInstance.group(mobInstance.registered(MemoryModuleType.LOOK_TARGET), mobInstance.present(MemoryModuleType.ATTACK_TARGET), mobInstance.absent(MemoryModuleType.ATTACK_COOLING_DOWN), mobInstance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(mobInstance, (positionTrackerMemoryAccessor, entity, booleanMemoryAccessor, nearestVisibleLivingEntitiesMemoryAccessor) -> (serverLevel, mob, l) -> {
//            LivingEntity livingentity = mobInstance.get(entity);
//            System.out.println("okTask"+(livingentity instanceof TCRVillager tcrVillager && tcrVillager.isAngry()));
////            return livingentity instanceof TCRVillager tcrVillager && tcrVillager.isAngry();
//            return villager.isAngry();
//        }));
//    }

}