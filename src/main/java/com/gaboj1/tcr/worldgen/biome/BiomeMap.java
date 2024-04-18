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

    boolean isImage;

    public static final String DIR = "config/"+TheCasketOfReveriesMod.MOD_ID+"/";
    public static final String README = DIR+"MAP_README以图生图注意事项.txt";
    public static final String FILE = DIR+"map.png";

    public static BiomeMap getInstance(){
        return INSTANCE;
    }

    public double[][] createNoiseMap(NoiseMapGenerator generator){
        generator.setSeed(new Random().nextInt(3));//TODO: 改成世界种子
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
//            File dir = new File(DIR);


            File file = new File(FILE);
            BufferedImage image;
            if(!file.exists()){//如果自定义地图图片不存在则用资源包自带的默认地图生成
                ResourceManager manager = Minecraft.getInstance().getResourceManager();
                InputStream inputStream = manager.getResource(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"default_map.png")).get().open();
                image = ImageIO.read(inputStream);
                System.out.println("reading default_map");
            }else {
                image = ImageIO.read(file);
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
            isImage = true;
            return map;

        } catch (Exception e) {
            //FIXME 想办法输出信息
            e.printStackTrace();
            System.out.println("维度地图图片文件异常！将以默认预设生成地图");
            if(!GraphicsEnvironment.isHeadless())
                JOptionPane.showMessageDialog(null,"维度地图图片文件异常！将以默认预设生成地图","The Casket Of Reveries：提示",JOptionPane.INFORMATION_MESSAGE);
            isImage = false;
            //如果还是不行就输出默认噪声地图
            return createNoiseMap(generator);
        }
    }


    public static double[][] createNoiseMapStatic(NoiseMapGenerator generator){
        INSTANCE = new BiomeMap();
        INSTANCE.generator = generator;
        return INSTANCE.createNoiseMap(generator);
//        generator.setSeed(new Random().nextInt(3));//TODO: 改成世界种子
//        generator.setLength(SIZE);
//        generator.setWidth(SIZE);
//        generator.setLacunarity(12);//TODO 调整合适大小
//        generator.setPersistence(0.5);
//        generator.setOctaves(8);
//        double[][] map = generator.generateNoiseMap();
//        map = generator.divide(map);
//        map = generator.addCenter(map);
//        return map;
    }

    public static double[][] createImageMapStatic(NoiseMapGenerator generator){
//        //FIXME 还是会nullPointerException
//        if(INSTANCE != null){
//            //平坦世界也会用，重复加载浪费时间，所以这里直接对接一下。
//            generator = INSTANCE.generator;
//            return INSTANCE.map;
//        }
        INSTANCE = new BiomeMap();
        INSTANCE.generator = generator;
        INSTANCE.map = INSTANCE.createImageMap(generator);
        return INSTANCE.map;
//        try {
//            File dir = new File(DIR);
//            if(!dir.exists()){
//                System.out.println("mkdir:"+dir.mkdirs());
//            }
//            System.out.println(dir.getAbsolutePath());
//            File readme = new File(README);
//            if(!readme.exists()){
//                System.out.println("mk ReadMe:"+readme.createNewFile());
//            }
//
//            BufferedImage image = ImageIO.read(new File(FILE));
//            int height = image.getHeight();
//            int width = image.getWidth();
//            double[][] map = new double[height][width];
//            for(int i = 0;i < height; i++){
//                for(int j = 0;j < width; j++){
//                    map[i][j] = ((image.getRGB(i,j) == 0xffffff || ((image.getRGB(i,j)>>24)&0xff) < 50) ? 0 : 1);//白色或近透明则为空域
//                }
//            }
//
//            map = generator.divide(map);
//            map = generator.addCenter(map);
//            return map;
//
//        } catch (Exception e) {
//            //FIXME 想办法输出信息
//            System.out.println("维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图");
//            if(!GraphicsEnvironment.isHeadless())
//                JOptionPane.showMessageDialog(null,"维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图","The Casket Of Reveries：提示",JOptionPane.INFORMATION_MESSAGE);
//            return createNoiseMapStatic(generator);
//        }
    }

    public static void main(String args[]) {
        for(double a[] : BiomeMap.createImageMapStatic(new NoiseMapGenerator())){
            for (double b:a){
                System.out.print(b+" ");
            }
            System.out.println();
        }
    }

}
