package com.gaboj1.tcr.util.map;
import java.util.Random;

public class CustomDiamondSquareAlgorithm {
    private static final int SIZE = 101; // 二维数组边长，为奇数，方便处理边界
    private static final int MAX_HEIGHT = 200; // 最大高度
    private static final int MIN_HEIGHT = 50; // 最小高度

    private static final double ROUGHNESS = 0.5; // 控制地形粗糙度的参数
    private static final Random random = new Random();

    private static double[][] map = new double[SIZE][SIZE];

    public static void main(String[] args) {
        initializeArray();
        runDiamondSquareAlgorithm();

        printMap(); // 输出生成的高度图
    }

    private static void initializeArray() {
        // 初始化数组边界和中心点的值
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = -1; // 初始化为负数，表示未计算过
            }
        }
        map[0][0] = MAX_HEIGHT;
        map[0][SIZE - 1] = MAX_HEIGHT;
        map[SIZE - 1][0] = MAX_HEIGHT;
        map[SIZE - 1][SIZE - 1] = MAX_HEIGHT;
    }

    private static void runDiamondSquareAlgorithm() {
        int stepSize = SIZE - 1;
        double scale = MAX_HEIGHT - MIN_HEIGHT;
        while (stepSize > 1) {
            diamondSquareStep(stepSize, scale);
            stepSize /= 2;
            scale *= Math.pow(2, -ROUGHNESS);
        }
    }

    private static void diamondSquareStep(int stepSize, double scale) {
        int halfStep = stepSize / 2;
        for (int i = halfStep; i < SIZE - 1; i += stepSize) {
            for (int j = halfStep; j < SIZE - 1; j += stepSize) {
                diamondStep(i, j, halfStep, scale);
            }
        }
    }

    private static void diamondStep(int x, int y, int stepSize, double scale) {
        if (map[x][y] == -1) {
            double sum = 0;
            int count = 0;
            if (x - stepSize >= 0 && y - stepSize >= 0) {
                sum += map[x - stepSize][y - stepSize];
                count++;
            }
            if (x + stepSize < SIZE && y - stepSize >= 0) {
                sum += map[x + stepSize][y - stepSize];
                count++;
            }
            if (x - stepSize >= 0 && y + stepSize < SIZE) {
                sum += map[x - stepSize][y + stepSize];
                count++;
            }
            if (x + stepSize < SIZE && y + stepSize < SIZE) {
                sum += map[x + stepSize][y + stepSize];
                count++;
            }

            double avg = sum / count + random.nextDouble() * scale;
            map[x][y] = avg;
        }
    }
    private static void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%.2f ", map[i][j]);
            }
            System.out.println();
        }
    }
}