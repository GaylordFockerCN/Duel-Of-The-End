package com.gaboj1.tcr.util.map.dla;

import net.minecraft.util.Mth;

import java.util.Random;

public class DLA {

    private final Random random = new Random();
    private static final double[][] AVERAGE_KERNEL_5_5 = {
            { 1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25 },
            { 1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25 },
            { 1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25 },
            { 1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25 },
            { 1.0/25, 1.0/25, 1.0/25, 1.0/25, 1.0/25 }
    };

    private static final double[][] AVERAGE_KERNEL_3_3 = {
            { 1.0/9, 1.0/9, 1.0/9},
            { 1.0/9, 1.0/9, 1.0/9},
            { 1.0/9, 1.0/9, 1.0/9}
    };

    private boolean[][] original;
    private int[][] blur;

    public DLA(long seed){
        this(seed, 11);
    }

    public DLA(long seed, int originalSize){
        random.setSeed(seed);
        original = new boolean[originalSize][originalSize];
        original[originalSize / 2][originalSize / 2] = true;
        blur = new int[originalSize][originalSize];
    }

    public int[][] getBlur() {
        return blur;
    }

    /**
     * 整体放大并作插值处理
     */
    public void interpolate(){
        interpolateOriginal();
        interpolateBlur();
        fillHole();
    }

    /**
     * 放大模糊处理后的图
     */
    public void interpolateBlur(){
        int newSize = blur.length * 2 - 1;
        int[][] newArray = new int[newSize][newSize];
        for (int i = 0; i < blur.length-1; i++) {
            for (int j = 0; j < blur.length-1; j++) {
                newArray[i * 2][j * 2] = blur[i][j];
                newArray[i * 2+1][j * 2] = blur[i][j];
                newArray[i * 2][j * 2+1] = blur[i][j];
                newArray[i * 2+1][j * 2+1] = blur[i][j];
            }
        }
        blur = newArray;
    }

    /**
     * 放大分子图
     */
    public void interpolateOriginal(){
        int newSize = original.length * 2 - 1;
        boolean[][] newArray = new boolean[newSize][newSize];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original.length; j++) {
                newArray[i * 2][j * 2] = original[i][j];
                if (j < original.length - 1 && original[i][j] && original[i][j + 1]) {
                    newArray[i * 2][j * 2 + 1] = true;
                }
                if (i < original.length - 1 && original[i][j] && original[i + 1][j]) {
                    newArray[i * 2 + 1][j * 2] = true;
                }
            }
        }
        original = newArray;
    }

    public void fillHole(){
        for (int i = 1; i < original.length-1; i++) {
            for (int j = 1; j < original.length-1; j++) {
                if(!original[i][j] && original[i+1][j] && original[i][j+1] && original[i-1][j] && original[i][j-1]){
                    original[i][j] = true;
                    blur[i][j] = (blur[i+1][j] + blur[i-1][j] + blur[i][j+1] + blur[i][j-1]) / 4;
                }
            }
        }
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
     * 把original上为true的点在blur上都加上某个值
     */
    public void applyDLAResult(int value, int size){
        if(original.length > blur.length){
            int[][] newBlur = new int[original.length][original[0].length];
            int addonSize = (original.length-blur.length) / 2;
            for(int i = 0; i<blur.length; i++){
                System.arraycopy(blur[i], 0, newBlur[i + addonSize], addonSize, blur.length);
            }
            blur = newBlur;
        }
        int center = original.length / 2;
        for(int i = 0; i < original.length; i++){
            for(int j = 0; j < original[0].length; j++){
                if(original[i][j]){
                    int distance = Math.abs(i - center) + Math.abs(j - center);
                    double sigma = size / 20.0;//3 sigma hhhh
//                    blur[i][j] += value * (1 + (1 - (1 / (1 + size-distance))));
                    double attenuation = Math.exp(-Math.pow(distance, 2) / (2 * Math.pow(sigma, 2)));
//                    blur[i][j] += (int) (size / 5 * (1 + attenuation));
                    blur[i][j] += (int) (value * (0.5 + attenuation));
//                    blur[i][j] += value;
                }
            }
        }
    }

    /**
     * 整体乘以某数
     */
    public static int[][] mul(int[][] arr, double scale){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                arr[i][j] = (int) (arr[i][j] * scale);
            }
        }
        return arr;
    }

    /**
     * 模拟聚集过程，只负责判断有无，不负责数据大小
     */
    public void doDLA(int addonSize, int numParticles){
        int newSize = original.length + addonSize * 2;
        boolean[][] newMap = new boolean[newSize][newSize];

        //把旧的数组复制到新的扩大后的数组
        for(int i = 0; i<original.length; i++){
            System.arraycopy(original[i], 0, newMap[i + addonSize], addonSize, original.length);
        }

        for (int i = 0; i < numParticles; i++) {
            int startX, startY;

            //随机选一个点
            do{
                startX = random.nextInt(newSize);
                startY = random.nextInt(newSize);
            }while (newMap[startX][startY]);

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
                        startY = Math.min(newSize - 1, startY + 1);
                        break;
                    case 2:
                        startX = Math.min(newSize - 1, startX + 1);
                        break;
                    case 3:
                        startY = Math.max(0, startY - 1);
                        break;
                }

                // 如果遇到已经聚集的颗粒，停止扩散
                if (newMap[startX][startY]) {
                    newMap[lastX][lastY] = true;
                    break;
                }
            }
        }
        original = newMap;
    }

    /**
     * 应用卷积
     * @param kernel 所用卷积核
     * @param numIterations 卷积次数
     */
    private void applyConvolution(double[][] kernel, int numIterations){
        blur = applyConvolution(blur, kernel, numIterations);
    }

    /**
     * 应用卷积
     */
    public static int[][] applyConvolution(int[][] array, double[][] kernel, int numIterations) {
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
                        int pixelY = Mth.clamp(y + ky, 0, height - 1);
                        int pixelX = Mth.clamp(x + kx, 0, width - 1);
                        sum += array[pixelY][pixelX] * kernel[ky + kernelRadius][kx + kernelRadius];
                    }
                }

                result[y][x] = (int) Math.round(sum);
            }
        }
        if(--numIterations > 0){
            return applyConvolution(result, kernel, numIterations);
        }
        return result;
    }

    public int[][] getDefault(int size){
        int cnt = 0;
        while (blur.length < size){
            cnt++;
            doDLA(5, blur.length * (20 + cnt));
            applyDLAResult(30, size);
            applyConvolution(DLA.AVERAGE_KERNEL_5_5, 1);
            interpolate();
            applyConvolution(DLA.AVERAGE_KERNEL_3_3, 2);
        }
        if(blur.length > size){
            blur = resizeArray(blur, size, size);
        }
        applyConvolution(DLA.AVERAGE_KERNEL_3_3, 50);
        return blur;
    }

    public static void main(String[] args) {
        int size = 1600;
        DLA dla = new DLA(114514);
        int[][] grid = dla.getDefault(size);

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
