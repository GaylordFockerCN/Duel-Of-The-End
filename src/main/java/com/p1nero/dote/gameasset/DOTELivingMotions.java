package com.p1nero.dote.gameasset;

import yesman.epicfight.api.animation.LivingMotion;

public enum DOTELivingMotions implements LivingMotion {
    TALKING;

    final int id;

    private DOTELivingMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }
}
