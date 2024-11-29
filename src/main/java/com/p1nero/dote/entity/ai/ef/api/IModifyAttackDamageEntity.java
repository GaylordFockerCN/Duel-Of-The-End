package com.p1nero.dote.entity.ai.ef.api;

import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.damagesource.StunType;

/**
 * 用于在升级的时候加血量
 */
public interface IModifyAttackDamageEntity {
    void setNewDamage(float damage);
}