package com.gaboj1.tcr.util.map;
import java.util.Random;

public class River {

    public static void main(String[] args) {
        int width = 100;
        int height = 100;
        double[][] river = generateIntersectingRivers(width, height, 8); // 生成5条交错的河流

        // 打印河流数组
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print((river[i][j] == 1 ? "1" : "0") + " ");
            }
            System.out.println();
        }
    }

    public static double[][] generateIntersectingRivers(int width, int height, int numRivers) {
        double[][] river = new double[height][width];

        Random rand = new Random();

        int innerWidth = width - width / 5; // 内部区域的宽度
        int innerHeight = height - height / 5; // 内部区域的高度
        int startXOffset = width / 10; // X 起始偏移量
        int startYOffset = height / 10; // Y 起始偏移量

        for (int r = 0; r < numRivers; r++) {
            int startX, startY;
            if (r % 2 == 0) {
                startX = startXOffset; // 偶数河流从左侧开始
                startY = startYOffset + r * innerHeight / numRivers + rand.nextInt(innerWidth / 10); // 河流起始点y坐标
            } else {
                startX = width - startXOffset - 1; // 奇数河流从右侧开始
                startY = startYOffset + r * innerHeight / numRivers + rand.nextInt(innerHeight / 10); // 河流起始点y坐标
            }

            int currentX = startX;
            int currentY = startY;

            int directionX = r % 2 == 0 ? 1 : -1; // 河流方向，偶数河流向右，奇数河流向左
            int directionY = 1; // 河流在 y 轴上的方向，默认向下

            // 模拟河流走向
            while (currentX >= startXOffset && currentX < width - startXOffset && currentY >= startYOffset && currentY < height - startYOffset) {
                river[currentY][currentX] = 1;

                double changeY = rand.nextDouble() * 3 - 1; // y坐标的变化
                currentX += directionX;
                currentY += directionY * (int) changeY;

                if (currentY >= height - startYOffset || currentY < startYOffset) {
                    directionY *= -1; // 河流撞到边界，翻转方向
                    currentY += directionY; // 调整当前位置，避免超出边界
                }
            }
        }

        return river;
    }
}
