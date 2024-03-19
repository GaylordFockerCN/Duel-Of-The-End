package com.gaboj1.tcr.util.map;
public class RandomMountainGenerator {

    public static void main(String[] args) {
        int width = 1000;
        int length = 1000;
        int numPeaks = 32;
        int[][] terrain = getMountains(width, length, numPeaks);
        // 输出地形数据
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                System.out.print(terrain[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] getMountains(int width, int length, int numPeaks){
//        int[][] baseMap = WaveRiver.generateIntersectingRivers(width, length, 8);
//        baseMap = addDecay(baseMap);
//        return baseMap;
//        int[][] sphere = createSphere(20);
//        return addSphereValues(baseMap,sphere);
        DiamondSquareTest.init();
        return DiamondSquareTest.map;
    }

    public static int[][] createSphere(int size) {
        int[][] sphere = new int[size][size];
        int centerX = size / 2;
        int centerY = size / 2;
        int radius = size / 2 - 1;  // 球体的半径

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int distance = (i - centerX) * (i - centerX) + (j - centerY) * (j - centerY);
                if (distance <= radius * radius) {
                    sphere[i][j] = (int) Math.sqrt(radius * radius - distance) * 2;  // 使用勾股定理计算高度
                } else {
                    sphere[i][j] = 0;  // 非球体内部的位置高度为0
                }
            }
        }
        return sphere;
    }

    private static int[][] addDecay(int[][] baseMap) {
        int[][] result = baseMap.clone();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if(baseMap[i][j]==0){
                    continue;
                }

                // 在直径为 width/8 范围内进行衰减处理
                int radius = result.length / 16;
                for (int x = Math.max(0, i - radius); x <= Math.min(result.length - 1, i + radius); x++) {
                    for (int y = Math.max(0, j - radius); y <= Math.min(result[0].length - 1, j + radius); y++) {
                        // 计算距离
                        double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));

                        // 计算衰减值
//                        double attenuation = Math.exp(-1 * Math.pow(distance, 0.5));
                        double attenuation = 1 / (1 + 0.2 * distance);

                        // 更新相邻格子的值
                        int updateValue = (int) (attenuation*result[i][j]);
                        if(updateValue>result[x][y])
                            result[x][y] = updateValue;
                    }
                }
            }
        }
        return result;
    }

    private static int[][] addSphereValues(int[][] baseMap, int[][] sphere) {
        int height = baseMap.length;
        int WIDTH = baseMap[0].length;
        int[][] result = new int[height][WIDTH];
        int sphereSize = sphere.length;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < height; y++) {
                int baseValue = baseMap[y][x];

                for (int i = 0; i < sphereSize; i++) {
                    int sphereX = x - sphereSize / 2 + i;

                    if (sphereX >= 0 && sphereX < WIDTH) {
                        for (int j = 0; j < sphereSize; j++) {
                            int sphereY = y - sphereSize / 2 + j;

                            if (sphereY >= 0 && sphereY < height && result[sphereY][sphereX] < baseValue * sphere[j][i] ) {
                                result[sphereY][sphereX] += baseValue * sphere[j][i];
                            }
                        }
                    }
                }
            }
        }

        // 缩放结果数组中的数值
        int maxResult = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (result[i][j] > maxResult) {
                    maxResult = result[i][j];
                }
            }
        }

        double scaleFactor = 1.0;
        if (maxResult > 300) {
            scaleFactor = 300.0 / maxResult;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < WIDTH; j++) {
                result[i][j] = (int) (result[i][j] * scaleFactor);
            }
        }

        return result;
    }

    // 生成柏林噪声并确保中间高四周低
//    public double noise(int x, int y, double frequency, int octaves) {
//        double value = 0.0;
//        double maxAmplitude = 0.0;
//        double totalAmplitude = 0.0;
//
//        for (int i = 0; i < octaves; i++) {
//            double amplitude = Math.pow(0.5, i);
//            totalAmplitude += amplitude;
//            value += interpolatedNoise(x * frequency, y * frequency) * amplitude;
//            frequency *= 2.0;
//        }
//
//        return value / totalAmplitude;
//    }
//
//    // 插值噪声函数，这里可以使用线性插值、立方插值等方法
//    public double interpolatedNoise(double x, double y) {
//        int intX = (int)x;
//        int intY = (int)y;
//        double fracX = x - intX;
//        double fracY = y - intY;
//
//        double v1 = smoothNoise(intX, intY);
//        double v2 = smoothNoise(intX + 1, intY);
//        double v3 = smoothNoise(intX, intY + 1);
//        double v4 = smoothNoise(intX + 1, intY + 1);
//
//        double i1 = interpolate(v1, v2, fracX);
//        double i2 = interpolate(v3, v4, fracX);
//
//        return interpolate(i1, i2, fracY);
//    }
//
//    // 平滑噪声函数
//    public double smoothNoise(int x, int y) {
//        double corners = (randomNoise(x-1, y-1) + randomNoise(x+1, y-1) + randomNoise(x-1, y+1) + randomNoise(x+1, y+1)) / 16.0;
//        double sides = (randomNoise(x-1, y) + randomNoise(x+1, y) + randomNoise(x, y-1) + randomNoise(x, y+1)) / 8.0;
//        double center = randomNoise(x, y) / 4.0;
//
//        return corners + sides + center;
//    }
//
//    // 随机噪声函数，这里可以使用任意随机数生成方法
//    public double randomNoise(int x, int y) {
//        Random random = new Random(x * 142857 + y); // 使用 x 和 y 来种子随机数生成器
//        return random.nextDouble(); // 返回一个 [0, 1) 范围内的随机数
//    }
//    // 线性插值函数
//    public double interpolate(double a, double b, double x) {
//        double ft = x * Math.PI;
//        double f = (1 - Math.cos(ft)) * 0.5;
//
//        return a * (1 - f) + b * f;
//    }
}
