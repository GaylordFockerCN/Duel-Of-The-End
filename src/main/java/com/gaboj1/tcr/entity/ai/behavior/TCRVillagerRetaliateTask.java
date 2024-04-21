package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.gaboj1.tcr.util.DataManager;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

/**
 * 村民反击任务
 * 由于村民只能用Brain，所以得自己写个Brain。发现跟Goal还挺像，挺简单的。但是不知道为什么{@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal}写那么复杂。。
 *
 * @author LZY
 */
public class TCRVillagerRetaliateTask extends Behavior<Mob> {

    //攻击间隔
    private final int attackInterval = 20;
    private int attackTimer = 0;

    public TCRVillagerRetaliateTask() {
        super(ImmutableMap.of());
    }

    /**
     *判断反击目标
     * @param serverLevel
     * @param mob
     * @return 村民生气或者有攻击目标
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Mob mob) {
        LivingEntity target = mob.getTarget();
        //是坏人也要往死里打！
        if(target instanceof ServerPlayer serverPlayer){
            if(!DataManager.isWhite.getBool(serverPlayer) && DataManager.isWhite.isLocked(serverPlayer)){
                return true;
            }
        }
        //如果村民生气或者存在攻击目标，则进行。（不知道出了什么bug，Target在愤怒结束后不会消失？所以只能判断target非玩家或非村民）
//        return (mob instanceof TCRVillager tcrVillager && tcrVillager.isAngry() || (target!=null && !(target instanceof Player) && !(target instanceof TCRVillager)));
        return (mob instanceof TCRVillager tcrVillager && tcrVillager.isAngry() && target!=null && !(target instanceof TCRVillager));
    }

    /**
     * 同 {@link TCRVillagerRetaliateTask#checkExtraStartConditions(ServerLevel, Mob)}
     * @param serverLevel
     * @param mob
     * @param l
     * @return
     */
    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Mob mob, long l) {
        LivingEntity target = mob.getTarget();
        if(target instanceof ServerPlayer serverPlayer){
            return !DataManager.isWhite.getBool(serverPlayer) && DataManager.isWhite.isLocked(serverPlayer);
        }
        return (mob instanceof TCRVillager tcrVillager && tcrVillager.isAngry() && target!=null && !(target instanceof TCRVillager));
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

                //太远了不能造成伤害
                }else if(!tcrVillager.isWithinMeleeAttackRange(target)){
                    return;
                }

                tcrVillager.getLookControl().setLookAt(target ,30.0F, 30.0F);
                tcrVillager.doHurtTarget(target);//FIXME 有时候无法造成伤害，hurt返回false...
                tcrVillager.playAttackAnim();
                attackTimer = attackInterval;
            } else {
                attackTimer--;
            }
        }
    }

}