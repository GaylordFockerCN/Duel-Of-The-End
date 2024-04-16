package com.gaboj1.tcr.util.map;

import java.util.Random;

public class MountainGenerator {
    private static final int GRID_SIZE = 100; // 晶格大小
    private static final int SCALE = 50; // 缩放因子
    private static final double AMPLITUDE = 100; // 振幅

    private static final int X = 100; // 山脉的边长

    private static final Random random = new Random();

    public static void main(String[] args) {
        double[][] heights = generateMountain(X);

        // 打印生成的山脉
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < X; j++) {
                System.out.print(heights[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static double[][] generateMountain(int size) {
        double[][] heights = new double[size][size];

        // 随机生成梯度并加上向圆心的向量再乘以缩放因子
        double[][] gradients = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gradients[i][j] = random.nextDouble() * 2 - 1; // 随机梯度范围为[-1, 1]
            }
        }

        // 生成噪声地形
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = (double) i / size;
                double y = (double) j / size;

                double noise = perlinNoise(x * SCALE, y * SCALE, gradients);

                // 使用指数函数调整噪声，使得中间高四周低
                noise = Math.pow(noise, 3);

                // 缩放到合适的高度范围
                heights[i][j] = noise * AMPLITUDE;
            }
        }

        return heights;
    }

    // 计算二维 Perlin 噪声
    private static double perlinNoise(double x, double y, double[][] gradients) {
        int X = (int) Math.floor(x) & 255; // 计算坐标在晶格中的位置
        int Y = (int) Math.floor(y) & 255;

        // 计算在晶格中的偏移量
        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        // 计算梯度
        double gi00 = gradients[X][Y];
        double gi01 = gradients[X][(Y + 1) % GRID_SIZE];
        double gi10 = gradients[(X + 1) % GRID_SIZE][Y];
        double gi11 = gradients[(X + 1) % GRID_SIZE][(Y + 1) % GRID_SIZE];

        // 计算四个顶点到当前点的向量
        double d00 = dotProduct(gradients[(int)gi00][0], gradients[(int)gi00][1], xf, yf);
        double d01 = dotProduct(gradients[(int)gi01][0], gradients[(int)gi01][1], xf, yf - 1);
        double d10 = dotProduct(gradients[(int)gi10][0], gradients[(int)gi10][1], xf - 1, yf);
        double d11 = dotProduct(gradients[(int)gi11][0], gradients[(int)gi11][1], xf - 1, yf - 1);

        // 使用插值函数计算噪声
        double u = fade(xf);
        double v = fade(yf);
        double x1Interpolated = lerp(d00, d10, u);
        double x2Interpolated = lerp(d01, d11, u);
        return lerp(x1Interpolated, x2Interpolated, v);
    }

    // 计算两个向量的点积
    private static double dotProduct(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    // 插值函数
    private static double lerp(double a, double b, double x) {
        return a + x * (b - a);
    }

    // 缓和函数
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
}
