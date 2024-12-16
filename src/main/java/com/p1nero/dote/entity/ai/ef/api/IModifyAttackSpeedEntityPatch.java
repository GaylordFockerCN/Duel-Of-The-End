package com.p1nero.dote.entity.ai.ef.api;

/**
 * 用于修改攻速
 */
public interface IModifyAttackSpeedEntityPatch {
    float getAttackSpeed();
    default void setAttackSpeed(float speed){};
}