package com.gaboj1.tcr.util.map.dla;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 填补缝隙
 */
public class FillClosedCircles {
    public static void fill(int[][] grid) {
        if (grid == null || grid.length == 0) return;

        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // 遍历边界，标记所有非封闭的区域
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i == 0 || i == rows - 1 || j == 0 || j == cols - 1) && grid[i][j] != 0 && !visited[i][j]) {
                    markNonClosedArea(grid, visited, i, j);
                }
            }
        }

        // 填补封闭圈
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    int size = fillCircle(grid, i, j);
                    if (size <= 100) {
                        fillAverage(grid, i, j, size);
                    }
                }
            }
        }
    }

    private static void markNonClosedArea(int[][] grid, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j] || grid[i][j] == 0) {
            return;
        }
        visited[i][j] = true;
        markNonClosedArea(grid, visited, i + 1, j);
        markNonClosedArea(grid, visited, i - 1, j);
        markNonClosedArea(grid, visited, i, j + 1);
        markNonClosedArea(grid, visited, i, j - 1);
    }

    private static int fillCircle(int[][] grid, int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        int size = 0;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 0) {
                continue;
            }
            grid[x][y] = -1; // 标记为已访问
            size++;
            if(size > 10){
                return size;
            }
            queue.offer(new int[]{x + 1, y});
            queue.offer(new int[]{x - 1, y});
            queue.offer(new int[]{x, y + 1});
            queue.offer(new int[]{x, y - 1});
        }
        return size;
    }

    private static void fillAverage(int[][] grid, int startX, int startY, int size) {
        List<Integer> numbers = new ArrayList<>();
        List<int[]> positions = new ArrayList<>();
        collectNumbers(grid, startX, startY, numbers, positions);

        if (!numbers.isEmpty()) {
            int avg = (int) Math.round(numbers.stream().mapToInt(num -> num).average().orElse(0));
            for (int[] pos : positions) {
                grid[pos[0]][pos[1]] = avg;
            }
        }
    }

    private static void collectNumbers(int[][] grid, int i, int j, List<Integer> numbers, List<int[]> positions) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != -1) {
                continue;
            }
            positions.add(new int[]{x, y});
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (Math.abs(di) + Math.abs(dj) == 1) {
                        if (x + di >= 0 && x + di < grid.length && y + dj >= 0 && y + dj < grid[0].length) {
                            if (grid[x + di][y + dj] > 0) {
                                numbers.add(grid[x + di][y + dj]);
                            }
                        }
                    }
                }
            }
            grid[x][y] = -2; // 防止重复访问
            queue.offer(new int[]{x + 1, y});
            queue.offer(new int[]{x - 1, y});
            queue.offer(new int[]{x, y + 1});
            queue.offer(new int[]{x, y - 1});
        }
    }
}
