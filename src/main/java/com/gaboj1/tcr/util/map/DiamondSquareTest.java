package com.gaboj1.tcr.util.map;

import java.util.Random;

public class DiamondSquareTest {
    private int width;
    private int height;
    private static final Random random = new Random();

    private int[][] map;
    private static double roughness = 10.0;
    private static final int maxHeight = 300;

    public DiamondSquareTest(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new int[width][height];
        init();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getMap() {
        return map;
    }

    public void init() {
        initializeArray();
        diamondSquare(0, 0, width - 1, height - 1);

        normalizeHeight(); // 将高度限制在合理范围内
        printMap(); // 输出生成的二维数组
    }

    private void initializeArray() {
        // 初始化数组为0
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                map[i][j] = 0;
//            }
//        }
        // 设置四个角点的高度
        map[0][0] = 0;
        map[0][height - 1] = 0;
        map[width - 1][0] = 0;
        map[width - 1][height - 1] = 0;
    }

    private void diamondSquare(int leftX, int topY, int rightX, int bottomY) {
        int delta = rightX - leftX;
        if (delta < 2) {
            return;
        }
        if (delta < 20) {
            roughness = 0.7;
        }

        int middleX = (leftX + rightX) / 2;
        int middleY = (topY + bottomY) / 2;

        // Diamond step
        if (map[middleX][topY] == 0) {
            map[middleX][topY] = (map[leftX][topY] + map[rightX][topY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        }
        if (map[middleX][bottomY] == 0) {
            map[middleX][bottomY] = (map[leftX][bottomY] + map[rightX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        }
        if (map[leftX][middleY] == 0) {
            map[leftX][middleY] = (map[leftX][topY] + map[leftX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        }
        if (map[rightX][middleY] == 0) {
            map[rightX][middleY] = (map[rightX][topY] + map[rightX][bottomY]) / 2 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        }

        if (middleX == (width - 1) / 2 && middleY == (height - 1) / 2) {
            if (map[middleX][middleY] == 0) {
                map[middleX][middleY] = maxHeight;
            }
        } else {
            if (map[middleX][middleY] == 0) {
                map[middleX][middleY] = (map[leftX][topY] + map[rightX][topY] + map[leftX][bottomY] + map[rightX][bottomY]) / 4 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
            }
        }
        if (delta >= (width - 1) / 2 - 2 && delta <= (width - 1) / 2 + 2) {
            map[middleX][middleY] = maxHeight * 2 / 3 + random.nextInt(maxHeight / 5);
        }

        if (delta >= (width - 1) / 4 - 2 && delta <= (width - 1) / 4 + 2) {
            map[middleX][middleY] = maxHeight / 3 + random.nextInt(maxHeight / 6);
        }

        // Square step
        if (map[middleX][middleY] == 0) {
            map[middleX][middleY] = (map[leftX][topY] + map[rightX][topY] + map[leftX][bottomY] + map[rightX][bottomY]) / 4 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
        }

        diamondSquare(leftX, topY, middleX, middleY);
        diamondSquare(middleX, topY, rightX, middleY);
        diamondSquare(leftX, middleY, middleX, bottomY);
        diamondSquare(middleX, middleY, rightX, bottomY);
    }

    private void normalizeHeight() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] < min) {
                    min = map[i][j];
                }
                if (map[i][j] > max) {
                    max = map[i][j];
                }
            }
        }
        double scale = (double) maxHeight / (max - min);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = (int) (map[i][j] * scale);
            }
        }
    }

    private void printMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}
