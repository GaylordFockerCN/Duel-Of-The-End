package com.gaboj1.tcr.util.map;

import java.util.Random;

public class DiamondSquareTest {
    private static final int SIZE = 1000; // 二维数组边长
    private static final Random random = new Random();

    public static int[][] map = new int[SIZE][SIZE];
    private static double roughness = 10.0;
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
//        map[0][0] = random.nextInt(maxHeight);
//        map[0][SIZE - 1] = random.nextInt(maxHeight);
//        map[SIZE - 1][0] = random.nextInt(maxHeight);
//        map[SIZE - 1][SIZE - 1] = random.nextInt(maxHeight);
        map[0][0] = 0;
        map[0][SIZE - 1] = 0;
        map[SIZE - 1][0] = 0;
        map[SIZE - 1][SIZE - 1] = 0;
//        map[(SIZE - 1)/3][(SIZE - 1)/3] = maxHeight/4+random.nextInt(maxHeight/3);
//        map[2*(SIZE - 1)/3][(SIZE - 1)/3] = maxHeight/4+random.nextInt(maxHeight/3);
//        map[(SIZE - 1)/3][2*(SIZE - 1)/3] = maxHeight/4+random.nextInt(maxHeight/3);
//        map[2*(SIZE - 1)/3][2*(SIZE - 1)/3] = maxHeight/4+random.nextInt(maxHeight/3);

    }

    private static void diamondSquare(int leftX, int topY, int rightX, int bottomY) {
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

        if (middleX == (SIZE - 1) / 2 && middleY == (SIZE - 1) / 2) {
            if (map[middleX][middleY] == 0) {
                map[middleX][middleY] = maxHeight;
            }
        } else {
            if (map[middleX][middleY] == 0) {
                map[middleX][middleY] = (map[leftX][topY] + map[rightX][topY] + map[leftX][bottomY] + map[rightX][bottomY]) / 4 + (int) (random.nextDouble() * roughness * 2) - (int) roughness;
            }
        }
        if(delta >= (SIZE-1)/2 - 2 && delta <= (SIZE-1)/2 + 2){
            map[middleX][middleY] = maxHeight*2 / 3 + random.nextInt(maxHeight/5);
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
