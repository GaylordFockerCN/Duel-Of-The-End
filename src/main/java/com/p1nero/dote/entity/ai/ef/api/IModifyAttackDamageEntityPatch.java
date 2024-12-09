package com.p1nero.dote.entity.ai.ef.api;

/**
 * 用于在升级的时候加血量
 */
public interface IModifyAttackDamageEntityPatch {
    void setNewDamage(float damage);
}