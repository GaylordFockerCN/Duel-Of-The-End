package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.noise.NoiseMapGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

/**
 * 统一在这里调参数，共有默认地图和噪声生成地图两种形式
 */

public class BiomeMap {

    /**
     *世界的seed，为了使得不同seed生成的分布一致。
     * 在{@link com.gaboj1.tcr.mixin.WorldOptionsMixin}中实现
     */
    public static long seed;

    //方便全局调用
    private static BiomeMap INSTANCE;

    private NoiseMapGenerator generator;
    private double[][] map;
    public Point getCenter1() {
        return generator.getCenter1();
    }

    public Point getCenter2() {
        return generator.getCenter2();
    }

    public Point getCenter3() {
        return generator.getCenter3();
    }

    public Point getCenter4() {
        return generator.getCenter4();
    }

    public Point getMainCenter() {
        return generator.getCenter();
    }
    public Point getVillage1() {
        return generator.getVillage1();
    }

    public Point getVillage2() {
        return generator.getVillage2();
    }

    public Point getVillage3() {
        return generator.getVillage3();
    }

    public Point getVillage4() {
        return generator.getVillage4();
    }

    public int getR() {
        return generator.getLength()/2;
    }
    public Point getBlockPos(Point biomePos0) {
        Point biomePos = (Point) biomePos0.clone();
        biomePos.x = (biomePos0.x-getR())*4;
        biomePos.y = (biomePos0.y-getR())*4;
        return biomePos;
    }

    public BlockPos getBlockPos(Point biomePos0,int y){
        Point pos = getBlockPos(biomePos0);
        return new BlockPos(pos.x, y, pos.y);
    }

    public static final int SIZE = 320;

    public static final String DIR = "config/"+TheCasketOfReveriesMod.MOD_ID+"/";
    public static final String README = DIR+"MAP_README以图生图注意事项.txt";
    public static final String FILE = DIR+"map.png";

    public static BiomeMap getInstance(){
        return INSTANCE;
    }

    public double[][] createNoiseMap(NoiseMapGenerator generator){
//        generator.setSeed(new Random().nextInt(3));
        generator.setSeed(seed);
        generator.setLength(SIZE);
        generator.setWidth(SIZE);
        generator.setLacunarity(12);//TODO 调整合适大小
        generator.setPersistence(0.5);
        generator.setOctaves(8);
        double[][] map = generator.generateNoiseMap();
        map = generator.divide(map);
        map = generator.addCenter(map);
        return map;
    }

    public double[][] createImageMap(NoiseMapGenerator generator){

        try {

            File file = new File(FILE);
            BufferedImage image;
            if(!file.exists()){//如果自定义地图图片不存在则用资源包自带的默认地图生成
                ResourceManager manager = Minecraft.getInstance().getResourceManager();
                InputStream inputStream = manager.getResource(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"default_map.png")).get().open();
                image = ImageIO.read(inputStream);
                TheCasketOfReveriesMod.LOGGER.info("reading default_map");
                TCRBiomeProvider.mapName = "default_map";
            }else {
                image = ImageIO.read(file);
                TCRBiomeProvider.mapName = file.getName();
            }
            int height = image.getHeight();
            int width = image.getWidth();
            double[][] map = new double[height][width];
            for(int i = 0;i < height; i++){
                for(int j = 0;j < width; j++){
                    map[i][j] = (image.getRGB(i,j) == 0xffffff || ((image.getRGB(i,j)>>24)&0xff) < 50 ? 0 : 1);//白色或近透明则为空域
                }
            }
            generator.setLength(height);
            generator.setWidth(width);
            map = generator.divide(map);
            map = generator.addCenter(map);
            generator.setImage(true);
            return map;

        } catch (Exception e) {
            TheCasketOfReveriesMod.LOGGER.error("维度地图图片文件异常！将以默认噪声生成地图",e);
            generator.setImage(false);
            //如果还是不行就输出默认噪声地图
            return createNoiseMap(generator);
        }
    }


    public static double[][] createNoiseMapStatic(NoiseMapGenerator generator){
        INSTANCE = new BiomeMap();
        INSTANCE.generator = generator;
        TCRBiomeProvider.mapName = "noise";
        return INSTANCE.createNoiseMap(generator);
    }

    public static double[][] createImageMapStatic(NoiseMapGenerator generator){
        INSTANCE = new BiomeMap();
        INSTANCE.generator = generator;
        INSTANCE.map = INSTANCE.createImageMap(generator);
        return INSTANCE.map;
    }

    public static void init(NoiseMapGenerator generator){
        INSTANCE = new BiomeMap();
        INSTANCE.generator = generator;
        INSTANCE.map = generator.getMap();
    }

    public static void main(String[] args) {
        for(double[] a : BiomeMap.createImageMapStatic(new NoiseMapGenerator())){
            for (double b:a){
                System.out.print(b+" ");
            }
            System.out.println();
        }
    }

}
