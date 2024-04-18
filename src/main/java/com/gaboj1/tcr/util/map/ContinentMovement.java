package com.gaboj1.tcr.util.map;

import java.awt.*;
import java.util.*;

/**
 * 使用广度优先搜索，用于将村庄向大陆中心靠拢。数组中0代表虚空，非0代表大陆。
 */
public class ContinentMovement {

    public static boolean isLand(double[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != 0;
    }

    /**
     * 判断是否整个村庄都在大陆上
     */
    public static boolean isAllInRange(double[][] grid, int x, int y, int villageSize) {
        return isLand(grid, x - villageSize, y) && isLand(grid, x + villageSize, y) && isLand(grid, x, y + villageSize) && isLand(grid, x, y - villageSize);
    }

    /**
     * 判断是否是孤岛
     */
    public static boolean isIsolate(double[][] grid, int x, int y, int villageSize) {
        for (int i = x - villageSize; i <= x + villageSize; i++) {
            for (int j = y - villageSize; j <= y + villageSize; j++) {
                if (isLand(grid, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 广搜返回最近大陆点
     * @param grid
     * @param x
     * @param y
     * @param step 广搜的步长
     * @return
     */
    public static Point moveTowardsLand(double[][] grid, int x, int y ,int step) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        queue.offer(new Point(x, y));

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            visited.add(current);

            if (isLand(grid, current.x, current.y)) {
                return current;
            }

            int[][] directions = {{step, 0}, {-step, 0}, {0, step}, {0, -step}};
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                Point next = new Point(newX, newY);
                if (!visited.contains(next) && newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
                    queue.offer(next);
                }
            }
        }

        // No land found, return the original point
        return new Point(x, y);
    }

}
