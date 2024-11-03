package com.gaboj1.tcr.entity.ai.behavior;

import com.gaboj1.tcr.entity.custom.villager.TCRVillager;
import com.gaboj1.tcr.entity.custom.villager.biome1.PastoralPlainVillagerElder;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 村民反击任务
 * 由于村民只能用Brain，所以得自己写个Brain。发现跟Goal还挺像，挺简单的。但是不知道为什么{@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal}写那么复杂。。
 *
 * @author LZY
 */
public class TCRVillagerRetaliateTask extends Behavior<Mob> {

    protected int attackTimer = 0;

    public TCRVillagerRetaliateTask() {
        super(ImmutableMap.of());
    }

    /**
     *判断反击目标
     * @return 村民生气或者有攻击目标
     */
    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel serverLevel, @NotNull Mob mob) {
        return check(mob);
    }

    /**
     * 同 {@link TCRVillagerRetaliateTask#checkExtraStartConditions(ServerLevel, Mob)}
     * @param l 持续时间。因为已经搞了angry所以这个没用了。。
     */
    @Override
    protected boolean canStillUse(@NotNull ServerLevel serverLevel, @NotNull Mob mob, long l) {
        return check(mob);
    }

    private boolean check(Mob mob){
        LivingEntity target = mob.getTarget();
        if(mob instanceof TCRVillager tcrVillager){
            if(target instanceof ServerPlayer && !tcrVillager.isFriendly()){
                return true;
            }else {
                return tcrVillager.isAngry() && target!=null && !(target instanceof TCRVillager);
            }
        }
        return false;
    }

    @Override
    protected void tick(@NotNull ServerLevel level, @NotNull Mob mob, long l) {
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

                if(!checkRange(tcrVillager, target)){
                    return;
                }

                tcrVillager.getLookControl().setLookAt(target ,30.0F, 30.0F);
                doAttack(tcrVillager, target, level);
                tcrVillager.playAttackAnim();
                attackTimer = getAttackInterval();
            } else {
                attackTimer--;
            }
        }
    }

    protected int getAttackInterval(){
        return 20;
    }

    protected boolean checkRange(TCRVillager tcrVillager, LivingEntity target){
        if(tcrVillager.isElder()){
            //长老打得远一点很合理
            return !(tcrVillager.distanceTo(target) > 5);
        }else {
            //太远了不能造成伤害
            return tcrVillager.isWithinMeleeAttackRange(target);
        }
    }

    protected void doAttack(TCRVillager tcrVillager, LivingEntity target, ServerLevel level){
        tcrVillager.doHurtTarget(target);//注意！和平模式无法攻击！
    }

}