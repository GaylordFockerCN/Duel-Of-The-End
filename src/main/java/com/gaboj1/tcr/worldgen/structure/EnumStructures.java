package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.worldgen.biome.TCRBiomeProvider;

import java.awt.Point;

public enum EnumStructures {
    FINAL(2, 26, 26),
    CHURCH(2,0,0),
    FLOWER_ALTAR();

    int size = 0;
    int offsetX = 0;
    int offsetZ = 0;

    EnumStructures() {
    }
    EnumStructures(int size, int offsetX, int offsetZ) {
        this.size = size;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
    }

    // 可选的getter方法
    public int getSize() {
        return size;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public Point getPoint(TCRBiomeProvider provider) {
        Point point;
        switch (this){
            case FINAL: point = provider.getMainCenter();break;
            case CHURCH: point = provider.getCenter1();break;
            default:point = new Point(0,0);
        }

        return point;
    }

}
