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
    CHURCH(2,0,0),
    SWORD(2,0,12),
    VILLAGE1(2,0,0),
    VILLAGE2_0(2,0,0),
    VILLAGE2_1(2,0,0),
    VILLAGE2_2(2,0,0),
    VILLAGE2_3(2,0,0),
    VILLAGE2_4(2,0,0),
    VILLAGE2_5(2,0,0),
    FLOWER_ALTAR();

    int size = 0;
    int offsetX = 0;
    int offsetZ = 0;

    TCRStructuresEnum() {
    }
    TCRStructuresEnum(int size, int offsetX, int offsetZ) {
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

    public Point getPoint() {
        return switch (this) {
            case FINAL -> BiomeMap.getInstance().getMainCenter();
            case CHURCH -> BiomeMap.getInstance().getCenter1();
            case SWORD -> BiomeMap.getInstance().getCenter2();
            case VILLAGE1 -> BiomeMap.getInstance().getVillage1();
            case VILLAGE2_0 -> BiomeMap.getInstance().getVillage2()[0];
            case VILLAGE2_1 -> BiomeMap.getInstance().getVillage2()[1];
            case VILLAGE2_2 -> BiomeMap.getInstance().getVillage2()[2];
            case VILLAGE2_3 -> BiomeMap.getInstance().getVillage2()[3];
            case VILLAGE2_4 -> BiomeMap.getInstance().getVillage2()[4];
            case VILLAGE2_5 -> BiomeMap.getInstance().getVillage2()[5];
            default -> new Point(0, 0);
        };
    }

}
