package com.gaboj1.tcr.worldgen.structure;

import com.gaboj1.tcr.worldgen.biome.BiomeMap;

import java.awt.Point;

/**
 * 有新的建筑加进来只要在这里添加枚举类型即可，BiomeForceLandmark类和ChunkGenerator类会遍历这个枚举类型（顶级优化哈哈）
 * 以后看看能不能优化成json里面直接写类型而不是写数字（EnumCodec还不会）
 * @author LZY
 * @see PositionPlacement
 * @see com.gaboj1.tcr.worldgen.dimension.TCRChunkGenerator
 */
public enum TCRStructuresEnum {

    //offset应为偏移的方块数量除以四。举例：FINAL偏移104格，应填26。
    FINAL(2, 26, 26),
    CHURCH1(2,0,0),
    CHURCH2(2,0,12);

    final int size;
    final int offsetX;
    final int offsetZ;
    TCRStructuresEnum(int size, int offsetX, int offsetZ) {
        this.size = size;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
    }

    public int getSize() {
        return size;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public Point getPoint() {
        return switch (this) {
            case FINAL -> BiomeMap.getInstance().getCenter();
            case CHURCH1 -> BiomeMap.getInstance().getCenter1();
            case CHURCH2 -> BiomeMap.getInstance().getCenter2();
        };
    }

}
