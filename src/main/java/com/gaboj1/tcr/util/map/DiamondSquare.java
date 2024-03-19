package com.gaboj1.tcr.util.map;

import java.util.Random;

public class DiamondSquare {
    private static final int SIZE = 1000; // 二维数组边长
    private static final Random random = new Random();

    public static int[][] map = new int[SIZE][SIZE];
    private static final double roughness = 10.0;
    private static final int maxHeight = 300;

    public static void main(String[] args) {
        initializeArray();
        diamondSquare(0, 0, SIZE - 1, SIZE - 1);

        normalizeHeight(); // 将高度限制在合理范围内
        printMap(); // 输出生成的二维数组
    }

    public static void init(){
        initializeArray();
        diamondSquare(0, 0, SIZE - 1, SIZE - 1);

        normalizeHeight(); // 将高度限制在合理范围内
        printMap(); // 输出生成的二维数组
    }

    private static void initializeArray() {
        // 初始化数组为0
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = 0;
            }
        }
        // 设置四个角点的高度
        map[0][0] = random.nextInt(maxHeight);
        map[0][SIZE - 1] = random.nextInt(maxHeight);
        map[SIZE - 1][0] = random.nextInt(maxHeight);
        map[SIZE - 1][SIZE - 1] = random.nextInt(maxHeight);
    }

    private static void diamondSquare(int leftX, int topY, int rightX, int bottomY) {
        if (rightX - leftX < 2) {
            return;
        }

        int middleX = (leftX + rightX) / 2;
        int middleY = (topY + bottomY) / 2;

        // Diamond step
        map[middleX][topY] = (map[leftX][topY] + map[rightX][topY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        map[middleX][bottomY] = (map[leftX][bottomY] + map[rightX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        map[leftX][middleY] = (map[leftX][topY] + map[leftX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        map[rightX][middleY] = (map[rightX][topY] + map[rightX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        map[middleX][middleY] = (map[leftX][topY] + map[rightX][topY] + map[leftX][bottomY] + map[rightX][bottomY]) / 4 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;

        // Square step
        map[middleX][middleY] = (map[leftX][topY] + map[rightX][topY] + map[leftX][bottomY] + map[rightX][bottomY]) / 4 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;

        diamondSquare(leftX, topY, middleX, middleY);
        diamondSquare(middleX, topY, rightX, middleY);
        diamondSquare(leftX, middleY, middleX, bottomY);
        diamondSquare(middleX, middleY, rightX, bottomY);
    }

    private static void normalizeHeight() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] < min) {
                    min = map[i][j];
                }
                if (map[i][j] > max) {
                    max = map[i][j];
                }
            }
        }
        double scale = (double) maxHeight / (max - min);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = (int) (map[i][j] * scale);
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}
