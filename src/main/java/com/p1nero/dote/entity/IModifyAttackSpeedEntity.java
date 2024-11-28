package com.p1nero.dote.entity;

/**
 * 用于在升级的时候加血量
 */
public interface IModifyAttackSpeedEntity {
    float getAttackSpeed();
    default void setAttackSpeed(float speed){

    };
}