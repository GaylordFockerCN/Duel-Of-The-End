package com.gaboj1.tcr.worldgen.noise;

import org.spongepowered.noise.module.source.Perlin;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoiseMapGenerator {

    private int length = 50;
    private int width = 50;

    private int maxHeight = 50;
    private double scale = 0.1;//default 0.1
    private int octaves = 6;//default 6
    private double persistence = 0.5;//default 0.5
    private double lacunarity = 2;//default 2.0
    private int seed = 2;

    private List<Point> aPoints = new ArrayList<>();
    private List<Point> bPoints = new ArrayList<>();
    private List<Point> cPoints = new ArrayList<>();
    private List<Point> dPoints = new ArrayList<>();

    public static final double CURVE_INTENSITY = 0.1;
    public static final double SCALE_OF_CENTER_R = 0.05;//相对宽度width的比例，中心空岛半径即为width*scaleOfCenterR
    public final double scaleOfaCenterR = 0.5;//相对各个中心到整体中心的距离的比例， 各群系的中心群系的噪声半径 即 center.distance(aCenter)*scaleOfaCenterR
    private Random random;
    public final Point centerPoint = new Point(length/2,width/2);
    public void setLength(int length) {
        this.length = length;
        centerPoint.x = length/2;
    }

    public void setWidth(int width) {
        this.width = width;
        centerPoint.y = width/2;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setOctaves(int octaves) {
        this.octaves = octaves;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        random.setSeed(seed);
    }

    public NoiseMapGenerator(){
        random = new Random(seed);
    }

    public double[][] generateNoiseMap() {
        maxHeight /= scale;
        setSeed(seed);
        double[][] skyIsland = new double[length][width];
        int centerX = width / 2;
        int centerY = length / 2;
        double maxDistance = Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2));

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width * scale;
                double ny = y / (double) length * scale;
                double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                double normalizedDistance = distanceToCenter / maxDistance;
                if (normalizedDistance >= 1) {
                    skyIsland[y][x] = 0;
                    continue;
                }
                double value = noise(nx, ny, octaves, persistence, lacunarity) * (1 - normalizedDistance) * maxHeight;

                skyIsland[y][x] = value;
            }
        }

        // 找出最高值
        double maxEdgeValue = -Double.MAX_VALUE;
        for (int x = 0; x < width; x++) {
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[0][x]);
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[length - 1][x]);
        }
        for (int y = 0; y < length; y++) {
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[y][0]);
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[y][width - 1]);
        }

        // 减去最高值并设为0
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                skyIsland[y][x] = Math.max(skyIsland[y][x] - maxEdgeValue, 0);
            }
        }

        return skyIsland;
    }

    public double [][] divideTest(double [][]map1) {

        double[][] map = map1.clone();
        int width = map.length; // 假设生成的数组大小为100x100
        int length = map[0].length;
// 生成噪声地图数组 map

// 求中心点坐标（重心）
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) {
                    points.add(new Point(j,i));
                }
            }
        }
        Point center = computeCenter(points);
        int centerX = center.x;
        int centerY = center.y;

        int centerR = (int) (width * SCALE_OF_CENTER_R);//TODO: 调整合适大小
//        int centerR = 16;

        //TODO: 调整合适大小
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                double value = map[y][x];

                // 判断当前位置是否为零
                if (value != 0) {
                    // 计算当前位置相对于中心点的角度
                    double angle = Math.atan2(y - centerY, x - centerX);
                    double distance = Math.sqrt((y - centerY) * (y - centerY) + (x - centerX) * (x - centerX));

                    // 根据角度划分区域并旋转45度
//                    angle += Math.PI / 4;  // 添加旋转角度的偏移量

                    // 将角度限制在 -PI 到 PI 之间
                    while (angle < -Math.PI) {
                        angle += 2 * Math.PI;
                    }
                    while (angle >= Math.PI) {
                        angle -= 2 * Math.PI;
                    }

                    // 使用曲线函数调整角度
//                    double curveValue = Math.exp(-CURVE_INTENSITY * distance); // 根据距离计算曲线值
//                    angle += curveValue; // 添加曲线值到角度

                    // 使用 S 型曲线函数调整角度
                    angle = getSigmoidAngle(distance, angle);

                    // 根据角度划分区域
                    if(distance <= centerR){
                        //中间挖个空
                        System.out.print("- ");
                        map[y][x] = 0;
                    } else if (angle >= 0 && angle < Math.PI / 2) {
                        // 区域A
                        System.out.print("A ");
                        map[y][x] = 1;
                    } else if (angle >= Math.PI / 2 && angle < Math.PI) {
                        // 区域B
                        System.out.print("B ");
                        map[y][x] = 2;
                    } else if (angle >= -Math.PI / 2 && angle < 0) {
                        // 区域C
                        System.out.print("C ");
                        map[y][x] = 3;
                    } else {
                        // 区域D
                        System.out.print("D ");
                        map[y][x] = 4;
                    }
                } else {
                    // 当前位置为零，那不用管
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        return map;
    }

    //不输出信息版
    public double [][] divide(double [][]map1) {

        double[][] map = map1.clone();
        int width = map.length;
        int length = map[0].length;

        // 求中心点坐标（重心）
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) {
                    points.add(new Point(j,i));
                }
            }
        }
        Point center = computeCenter(points);
        int centerX = center.x;
        int centerY = center.y;

        int centerR = (int) (width * SCALE_OF_CENTER_R);//TODO:调整合适大小

//        // 定义曲线参数
//        double sigmoidIntensityA = 0.1; // 曲线强度A
//        double sigmoidIntensityB = 0.2; // 曲线强度B
//        Random random = new Random();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                double value = map[y][x];
                if (value != 0) {
                    double angle = Math.atan2(y - centerY, x - centerX);
                    double distance = Math.sqrt((y - centerY) * (y - centerY) + (x - centerX) * (x - centerX));

                    // 根据角度划分区域并旋转45度
//                    angle += Math.PI / 4;  // 添加旋转角度的偏移量

                    // 将角度限制在 -PI 到 PI 之间
                    while (angle < -Math.PI) {
                        angle += 2 * Math.PI;
                    }
                    while (angle >= Math.PI) {
                        angle -= 2 * Math.PI;
                    }

//                    // 使用曲线函数调整角度
//                    double curveValue = Math.exp(-CURVE_INTENSITY * distance); // 根据距离计算曲线值
//                    angle += curveValue; // 添加曲线值到角度

                    // 使用 S 型曲线函数调整角度
                    angle = getSigmoidAngle(distance, angle);

                    // 根据角度划分区域
                    if(distance <= centerR){
                        map[y][x] = 0;
                    } else if (angle >= 0 && angle < Math.PI / 2) {
                        // 区域A
                        map[y][x] = 1;
                        aPoints.add(new Point(y, x));//保存各个群系所含有的点来计算重心
                    } else if (angle >= Math.PI / 2 && angle < Math.PI) {
                        // 区域B
                        map[y][x] = 2;
                        bPoints.add(new Point(y, x));
                    } else if (angle >= -Math.PI / 2 && angle < 0) {
                        // 区域C
                        map[y][x] = 3;
                        cPoints.add(new Point(y, x));
                    } else {
                        // 区域D
                        map[y][x] = 4;
                        dPoints.add(new Point(y, x));
                    }
                }
            }
        }
        return map;
    }

    //TODO 调整合适数值
    private double getSigmoidAngle(double distance, double angle) {
        // 定义曲线参数
        double sigmoidIntensityA = 0.1; // 曲线强度A
        double sigmoidIntensityB = 0.2; // 曲线强度B
        double sigmoidValue = 1 / (1 + Math.exp(-sigmoidIntensityA * (distance *0.75))); // Sigmoid 曲线值 A
        if (angle >= 0 && angle < Math.PI / 2) {
            sigmoidValue = 1 / (1 + Math.exp(-sigmoidIntensityB * (distance *0.75))); // Sigmoid 曲线值 B
        }
        double randomOffset = random.nextDouble() * 0.1 - 0.05; // 随机偏移值
        angle += sigmoidValue + randomOffset; // 添加 S 型曲线值和随机偏移值到角度
        return angle;
    }

    //给四个扇形分别添加中心群系
    public double[][] addCenter(double[][] map){
        double[][] map1 = map.clone();

        //生成各个群系的中心群系位置并应用到原地图
        copyMap(aPoints,map1,5);
        copyMap(bPoints,map1,6);
        copyMap(cPoints,map1,7);
        copyMap(dPoints,map1,8);
        return map1;
    }

    //生成中心并且把中心复制到各个区域
    public void copyMap(List<Point> aPoints,double map[][],double tag){
        //再以各个点为中心生成噪声图，比较自然一点~
        Point aCenter = computeCenter(aPoints);
        NoiseMapGenerator generator = new NoiseMapGenerator();

        //生成中心群系
        int centerR = (int)( aCenter.distance(centerPoint) * scaleOfaCenterR);
        generator.setWidth(centerR);
        generator.setLength(centerR);
        generator.setLacunarity(12);
        generator.setOctaves(8);
        double[][] aCenterBiomeMap = generator.generateNoiseMap();

        //centerR即偏移量
        for(int i = aCenter.x - centerR/2 ,a = 0; i < aCenter.x + centerR/2 && a < centerR ; i++,a++){
            for(int j = aCenter.y - centerR/2 ,b = 0; j < aCenter.y + centerR/2 && b < centerR; j++,b++){
                if(i > 0 && j > 0 && i<map.length && j<map[0].length && aCenterBiomeMap[a][b] != 0)
                    map[i][j] = tag;
            }
        }
    }

    //计算重心（所有x之和除以2即为重心的x，所有y之和除以2即为重心的y）
    public static Point computeCenter(List<Point> points) {
        if(points.size() == 0)return new Point(0,0);
        int sumX = 0;
        int sumY = 0;
        for (Point point : points) {
            sumX += point.x;
            sumY += point.y;
        }
        int centerX = sumX / points.size();
        int centerY = sumY / points.size();
        return new Point(centerX, centerY);
    }


    private double noise(double x, double y, int octaves, double persistence, double lacunarity) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxNoiseValue = 0;

        for (int i = 0; i < octaves; i++) {
            total += interpolatedNoise(x * frequency, y * frequency) * amplitude;
            maxNoiseValue += amplitude;
            frequency *= lacunarity;
            amplitude *= persistence;
        }

        return total / maxNoiseValue;
    }

    private double interpolatedNoise(double x, double y) {
        int ix = (int) x;
        int iy = (int) y;
        double fx = x - ix;
        double fy = y - iy;

        double v1 = smoothNoise(ix, iy);
        double v2 = smoothNoise(ix + 1, iy);
        double v3 = smoothNoise(ix, iy + 1);
        double v4 = smoothNoise(ix + 1, iy + 1);

        double i1 = interpolate(v1, v2, fx);
        double i2 = interpolate(v3, v4, fx);

        return interpolate(i1, i2, fy);
    }

    private double smoothNoise(int x, int y) {
        double corners = (noise2D(x - 1, y - 1) + noise2D(x + 1, y - 1)
                + noise2D(x - 1, y + 1) + noise2D(x + 1, y + 1)) / 16.0;
        double sides = (noise2D(x - 1, y) + noise2D(x + 1, y)
                + noise2D(x, y - 1) + noise2D(x, y + 1)) / 8.0;
        double center = noise2D(x, y) / 4.0;

        return corners + sides + center;
    }

    public double interpolate(double a, double b, double blend) {
        double theta = blend * Math.PI;
        double f = (1 - Math.cos(theta)) * 0.5;

        return a * (1 - f) + b * f;
    }

    private double noise2D(int x, int y) {
        random.setSeed(x * 49632 + y * 325176 + seed);
        return random.nextDouble() * 2 - 1;
    }

    public static void main(String args[]){
//        int size = 180;
//        double[][] map = getDoubles(size);
//
//        for(int i = 0 ; i < size ; i++){
//            for(int j = 0 ; j < size ; j++){
////                System.out.print(String.format("%.0f ",map[i][j]));
//                if(map[i][j] == 1){
//                    System.out.print("@ ");
//                }else if(map[i][j] == 2){
//                    System.out.print("# ");
//                }else if(map[i][j] == 3){
//                    System.out.print("^ ");
//                }else if(map[i][j] == 4){
//                    System.out.print("* ");
//                }else {
//                    System.out.print("- ");
//                }
//            }
//            System.out.println();
//        }

        NoiseMapGenerator generator = new NoiseMapGenerator();
        Perlin perlin = new Perlin();
        perlin.setLacunarity(2);
        perlin.setPersistence(0.5);
        perlin.setOctaveCount(6);
        for(int i = 0;i<100;i++){
            for (int j = 0;j<100;j++){
                double x = perlin.get(i*0.1,0,j*0.1);
                System.out.print(String.format("%.1f ",x));
            }
            System.out.println();
        }


    }

    private static double[][] getDoubles(int size) {
        NoiseMapGenerator noiseMapGenerator = new NoiseMapGenerator();
        noiseMapGenerator.setSeed(new Random().nextInt(100));
//        noiseMapGenerator.setSeed(99);
        noiseMapGenerator.setLength(size);
        noiseMapGenerator.setWidth(size);
        noiseMapGenerator.setLacunarity(20);
        noiseMapGenerator.setPersistence(0.5);
        noiseMapGenerator.setOctaves(8);
        double map[][] = noiseMapGenerator.generateNoiseMap();
        map = noiseMapGenerator.divide(map);
        map = noiseMapGenerator.addCenter(map);
        return map;
    }

}
