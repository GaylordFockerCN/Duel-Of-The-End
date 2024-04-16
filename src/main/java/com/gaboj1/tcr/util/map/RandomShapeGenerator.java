package com.gaboj1.tcr.util.map;
import java.util.Random;

public class RandomShapeGenerator {
    private static final int X = 100; // 边长
    private static final int GRID_SIZE = 10; // 晶格大小
    private static final double SCALE = 10.0; // 缩放因子
    private static final double THRESHOLD = 0.5; // 阈值

    private static final Random random = new Random();

    public static void main(String[] args) {
        int[][] shape = generateRandomShape(X);
        printShape(shape);
    }

    public static int[][] generateRandomShape(int size) {
        int[][] shape = new int[size][size];

        // 生成中心点
        int centerX = size / 2;
        int centerY = size / 2;

        // 为每个晶格分配随机梯度向量，并加上指向中心的向量再乘以缩放因子
        double[][][] gradients = new double[size / GRID_SIZE][size / GRID_SIZE][2];
        for (int i = 0; i < size / GRID_SIZE; i++) {
            for (int j = 0; j < size / GRID_SIZE; j++) {
                double angle = random.nextDouble() * 2 * Math.PI; // 随机角度
                double x = Math.cos(angle);
                double y = Math.sin(angle);

                // 将梯度向量加上指向中心的向量
                x += (centerX - i * GRID_SIZE) / SCALE;
                y += (centerY - j * GRID_SIZE) / SCALE;

                // 缩放梯度向量
                x *= SCALE;
                y *= SCALE;

                gradients[i][j][0] = x;
                gradients[i][j][1] = y;
            }
        }

        // 根据噪声值生成图形
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int gridX = i / GRID_SIZE;
                int gridY = j / GRID_SIZE;

                // 计算当前位置在晶格内的相对位置
                double xInGrid = (i % GRID_SIZE) / (double) GRID_SIZE;
                double yInGrid = (j % GRID_SIZE) / (double) GRID_SIZE;

                // 计算梯度向量与当前位置的偏移量
                double dx = xInGrid - 0.5; // 中心化
                double dy = yInGrid - 0.5; // 中心化

                // 获取当前晶格的梯度向量
                double[] gradient = gradients[gridX][gridY];

                // 计算当前位置的噪声值
                double noise = gradient[0] * dx + gradient[1] * dy;

                // 根据阈值判断是否有图形
                shape[i][j] = (noise > THRESHOLD) ? 1 : 0;
            }
        }

        return shape;
    }

    // 打印图形
    public static void printShape(int[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                System.out.print(shape[i][j] + " ");
            }
            System.out.println();
        }
    }
}
