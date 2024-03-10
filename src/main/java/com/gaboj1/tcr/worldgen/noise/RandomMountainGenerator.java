package com.gaboj1.tcr.worldgen.noise;

import java.util.Random;

public class RandomMountainGenerator {
    public static void main(String[] args) {
//        int size = 100;
//        double[][] mountain = new double[size][size];
//
//        // 指定山顶位置
//        int peakX = 50;
//        int peakY = 50;
//
//        // 生成山峰地形
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                mountain[x][y] = multipleGaussianFunctions(x, y, peakX, peakY, 10.0) + randomDisplacement(); // 使用多个高斯函数的组合并添加随机扰动
//            }
//        }
//
//        // 打印结果
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                System.out.print(String.format("%.0f",mountain[x][y]*100) + " ");
//            }
//            System.out.println();
//        }

        int width = 100;
        int height = 100;
        int[][] mountain = new int[width][height];

        // 柏林噪声参数
        double frequency = 0.1;
        int octaves = 6;
        double persistence = 0.5;

        // 生成山峰
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double noiseValue = perlinNoise(x, y, frequency, octaves, persistence);
                double distanceToCenter = distance(x, y, width / 2, height / 2);
                double mountainHeight = Math.pow(1 - distanceToCenter, 2); // 距离中心越近，高度增加得更快

                // 根据噪声值和距离调整山峰高度
                mountain[x][y] = (int) (noiseValue * mountainHeight * 100);
            }
        }


        // 输出数组
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.printf("%3d ", mountain[x][y]);
            }
            System.out.println();
        }

    }

    private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    // 生成柏林噪声
    private static double perlinNoise(double x, double y, double frequency, int octaves, double persistence) {
        double total = 0;
        double maxValue = 0;
        for (int i = 0; i < octaves; i++) {
            double freq = Math.pow(2, i) * frequency;
            double amp = Math.pow(persistence, i);
            total += interpolatedNoise(x * freq, y * freq) * amp;
            maxValue += amp;
        }
        return total / maxValue;
    }

    // 插值噪声
    // 线性插值
    private static double interpolate(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        return a * (1 - f) + b * f;
    }

    // 插值噪声
    private static double interpolatedNoise(double x, double y) {
        int integerX = (int)x;
        int integerY = (int)y;
        double fractionalX = x - integerX;
        double fractionalY = y - integerY;

        double v1 = smoothNoise(integerX, integerY);
        double v2 = smoothNoise(integerX + 1, integerY);
        double v3 = smoothNoise(integerX, integerY + 1);
        double v4 = smoothNoise(integerX + 1, integerY + 1);

        double i1 = interpolate(v1, v2, fractionalX);
        double i2 = interpolate(v3, v4, fractionalX);

        return interpolate(i1, i2, fractionalY);
    }

    // 平滑噪声
    private static double smoothNoise(int x, int y) {
        double corners = (noise(x - 1, y - 1) + noise(x + 1, y - 1) + noise(x - 1, y + 1) + noise(x + 1, y + 1)) / 16;
        double sides = (noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(x, y + 1)) / 8;
        double center = noise(x, y) / 4;
        return corners + sides + center;
    }

    // 基础噪声
    private static double noise(int x, int y) {
        // 这里需要一个随机数生成器，例如使用Java的Random类
        Random random = new Random((x * 49157) ^ (y * 59417));
        return random.nextDouble() * 2 - 1;
    }



    // 多个高斯函数的组合
    public static double multipleGaussianFunctions(int x, int y, int peakX, int peakY, double variance) {
        double result = 0.0;
        result += Math.exp(-((x - peakX) * (x - peakX) + (y - peakY) * (y - peakY)) / (2 * variance * variance));
        result += Math.exp(-((x - peakX + 10) * (x - peakX + 10) + (y - peakY - 5) * (y - peakY - 5)) / (2 * variance * variance));
        // 可根据需要添加更多高斯函数的组合
        return result;
    }

    // 随机扰动
    public static double randomDisplacement() {
        Random rand = new Random();
        return rand.nextDouble() * 0.1; // 在0到0.1之间生成随机扰动
    }
}
