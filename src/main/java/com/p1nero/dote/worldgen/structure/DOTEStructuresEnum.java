package com.p1nero.dote.worldgen.structure;

import com.p1nero.dote.worldgen.biome.BiomeMap;
import com.p1nero.dote.worldgen.dimension.DOTEChunkGenerator;

import java.awt.Point;

/**
 * 有新的建筑加进来只要在这里添加枚举类型即可，BiomeForceLandmark类和ChunkGenerator类会遍历这个枚举类型（顶级优化哈哈）
 * 以后看看能不能优化成json里面直接写类型而不是写数字（EnumCodec还不会）
 * @author LZY
 * @see PositionPlacement
 * @see DOTEChunkGenerator
 */
public enum DOTEStructuresEnum {

    //offset应为偏移的方块数量除以四。举例：FINAL偏移61格，应填15 。
    FINAL(2, 15, 15),
    CHURCH1(2,50,10),
    CHURCH2(2,50,10),
    ALTAR1(2,2,2),
    ALTAR2(2,2,2);

    final int size;
    final int offsetX;
    final int offsetZ;
    DOTEStructuresEnum(int size, int offsetX, int offsetZ) {
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
            case ALTAR1 -> BiomeMap.getInstance().getAltar1();
            case ALTAR2 -> BiomeMap.getInstance().getAltar2();
        };
    }

}
