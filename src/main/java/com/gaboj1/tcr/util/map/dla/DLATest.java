package com.gaboj1.tcr.util.map.dla;

import java.util.Random;

public class DLATest {

    private static final Random random = new Random();
    private static final double[][] AVERAGE_KERNEL = {
            { 1.0/19, 1.0/19, 1.0/19 },
            { 1.0/19, 1.0/9, 1.0/19 },
            { 1.0/19, 1.0/19, 1.0/19 }
    };

    /**
     * 每轮(size + 10) * 2
     * 11 42 104 228 476 972 1964
     * 最后应用模糊
     */
    public static int[][] getMap(int size, long seed){
        random.setSeed(seed);
        int originalSize = 11;
        int[][] originalArray = new int[originalSize][originalSize];
        originalArray[originalSize / 2][originalSize / 2] = size;
        int[][] newArray = originalArray;
        int newSize = originalSize;
        do{
            newArray = DLA(newArray, 5, newSize * 8, size);

            //直接缩放，增加效率
            if(newArray.length < size){
                newArray = interpolate(newArray);
            }

            //防止缩放得过大
            if(newArray.length > size){
                newArray = resizeArray(newArray, size, size);
            }

            newSize = newArray.length;
        }while (newSize < size);
        int numIterations = 5;//卷积次数
        for (int i = 0; i < numIterations; i++) {
            newArray = applyConvolution(newArray, AVERAGE_KERNEL);
        }
        return newArray;
    }

    /**
     * 放大地图，增加效率（一次就放大两倍。。。）
     */
    public static int[][] interpolate(int[][] originalArray){
        int newSize = originalArray.length * 2 - 1;
        int[][] newArray = new int[newSize][newSize];
        for (int i = 0; i < originalArray.length; i++) {
            for (int j = 0; j < originalArray.length; j++) {
                newArray[i * 2][j * 2] = originalArray[i][j];
                if (j < originalArray.length - 1 && originalArray[i][j] != 0 && originalArray[i][j + 1] != 0) {
                    newArray[i * 2][j * 2 + 1] = (originalArray[i][j] + originalArray[i][j + 1]) / 2;
                }
                if (i < originalArray.length - 1 && originalArray[i][j] != 0 && originalArray[i + 1][j] != 0) {
                    newArray[i * 2 + 1][j * 2] = (originalArray[i][j] + originalArray[i + 1][j]) / 2;
                }
            }
        }
        return newArray;
    }

    /**
     * 如果插值后过大则直接缩小
     */
    public static int[][] resizeArray(int[][] array, int newX, int newY) {
        int oldX = array[0].length;
        int oldY = array.length;
        int[][] resizedArray = new int[newY][newX];
        for (int y = 0; y < newY; y++) {
            for (int x = 0; x < newX; x++) {
                int sum = 0;
                int count = 0;

                // 计算在原始数组中的区域
                int startX = x * oldX / newX;
                int endX = (x + 1) * oldX / newX;
                int startY = y * oldY / newY;
                int endY = (y + 1) * oldY / newY;

                // 对区域内的元素求和
                for (int i = startY; i < endY; i++) {
                    for (int j = startX; j < endX; j++) {
                        sum += array[i][j];
                        count++;
                    }
                }

                // 取平均值并存入新数组
                resizedArray[y][x] = sum / count;
            }
        }
        return resizedArray;
    }

    /**
     * 获得添加颗粒后的新地图
     */
    public static int[][] DLA(int[][] map, int addonSize, int numParticles, int initialValue){
        int size = map.length + addonSize * 2;
        int[][] newMap = new int[size][size];

        //把旧的数组复制到新的扩大后的数组
        for(int i = 0; i<map.length; i++){
            System.arraycopy(map[i], 0, newMap[i + addonSize], addonSize, map.length);
        }

        int center = size / 2;
        for (int i = 0; i < numParticles; i++) {
            int startX, startY;

//            // 从边界随机选择一个起始点
//            if (random.nextBoolean()) {
//                startX = random.nextInt(size);
//                startY = random.nextBoolean() ? 0 : size - 1;
//            } else {
//                startX = random.nextBoolean() ? 0 : size - 1;
//                startY = random.nextInt(size);
//            }

            //随机选一个点
            do{
                startX = random.nextInt(size);
                startY = random.nextInt(size);
            }while (newMap[startX][startY] > 0);

            // 扩散过程
            while (true) {
                // 随机选择一个方向移动
                int direction = random.nextInt(4); // 0:上，1:右，2:下，3:左
                int lastX = startX, lastY = startY;
                switch (direction) {
                    case 0:
                        startX = Math.max(0, startX - 1);
                        break;
                    case 1:
                        startY = Math.min(size - 1, startY + 1);
                        break;
                    case 2:
                        startX = Math.min(size - 1, startX + 1);
                        break;
                    case 3:
                        startY = Math.max(0, startY - 1);
                        break;
                }

                // 如果遇到已经聚集的颗粒，停止扩散
                if (newMap[startX][startY] > 0) {
                    // 将当前格子标记为当前距离中心的值
                    int distance = Math.abs(lastX - center) + Math.abs(lastY - center);
                    newMap[lastX][lastY] = Math.max(1, initialValue - distance);
//                    newMap[lastX][lastY] = newMap[startX][startY] - 1;
                    break;
                }
            }
        }
        return newMap;
    }

    /**
     * 定义卷积函数
     */
    private static int[][] applyConvolution(int[][] array, double[][] kernel) {
        int height = array.length;
        int width = array[0].length;
        int kernelSize = kernel.length;
        int kernelRadius = kernelSize / 2;
        int[][] result = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double sum = 0.0;

                for (int ky = -kernelRadius; ky <= kernelRadius; ky++) {
                    for (int kx = -kernelRadius; kx <= kernelRadius; kx++) {
                        int pixelY = clamp(y + ky, 0, height - 1);
                        int pixelX = clamp(x + kx, 0, width - 1);
                        sum += array[pixelY][pixelX] * kernel[ky + kernelRadius][kx + kernelRadius];
                    }
                }

                result[y][x] = (int) Math.round(sum);
            }
        }

        return result;
    }

    /**
     * 辅助函数：确保索引不超出数组边界
     */
    private static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }


    public static void main(String[] args) {
        int size = 1000;
        int[][] grid = getMap(size, 114514);

        for (int[] row : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (row[j] == 0) {
                    System.out.print("--- ");
                } else {
                    System.out.printf("%03d ", row[j]);
                }
            }
            System.out.println();
        }
    }

}
