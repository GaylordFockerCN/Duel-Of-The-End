package com.p1nero.dote.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class DOFCollider {
    public static final Collider GREATSWORD = new MultiOBBCollider(3, 0.2, 0.8, 1.0, 0.0, 0.0, -1.2);
    public static final Collider GREATSWORD_DOUBLESWING = new MultiOBBCollider(3, 0.6, 0.8, 1.0, -0.4, 0.0, -1.2);
    public static final Collider GREATSWORD_DUAL = new OBBCollider(2.4000000953674316, 0.699999988079071, 2.0999999046325684, 0.0, 1.0, -0.75);
    public static final Collider AIRSLAM = new OBBCollider(1.2000000476837158, 0.800000011920929, 1.2000000476837158, 0.0, 0.800000011920929, -1.600000023841858);
    public static final Collider SHOULDER_BUMP = new OBBCollider(0.8, 0.8, 0.8, 0.0, 1.2, -1.2);
    public static final Collider LETHAL_SLICING = new OBBCollider(2.0D, 0.25D, 1.5D, 0D, 1.0D, -1.0D);
    public static final Collider LETHAL_SLICING1 = new OBBCollider(2.0D, 0.25D, 1.5D, 0D, 0.5D, -1.0D);
    public static final Collider FATAL_DRAW_DASH = new OBBCollider(0.7, 0.7, 4.0, 0.0, 1.0, -4.0);
    public static final Collider BLADE_RUSH_FINISHER = new OBBCollider(1.2D, 0.8D, 2.0D, 0D, 1.0D, -1.2D);
    public static final Collider YAMATO = new MultiOBBCollider(3, 0.4D, 0.4D, 1.0D, 0.0D, 0.0D, -0.5D);
    public static final Collider YAMATO_SHEATH = new MultiOBBCollider(3, 0.5D, 0.5D, 1.0D, 0.0D, 0.0D, 0.5D);
    public static final Collider YAMATO_P = new MultiOBBCollider(3, 0.4D, 0.4D, 1.5D, 0.0D, 0.0D, -0.5D);
    public static final Collider YAMATO_DASH = new OBBCollider(1.7D, 1.0D, 2.0D, 0.0D, 1.0D, -1.0D);
    public static final Collider YAMATO_P0 = new OBBCollider(1.7D, 1.0D, -3.5D, 0.0D, 1.0D, -2.5D);
    public static final Collider YAMATO_DASH_FINISH = new OBBCollider(1.7D, 1.0D, 3.5D, 0.0D, 1.0D, 1.0D);
    public static final Collider EXECUTE = new MultiOBBCollider(2, 0.8, 0.8, 1.383, 0.0, 0.0, -0.66);

    public DOFCollider() {
    }
}