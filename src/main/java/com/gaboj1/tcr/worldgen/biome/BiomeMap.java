package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.worldgen.noise.NoiseMapGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * 统一在这里调参数，共有默认地图和噪声生成地图两种形式
 */

public class BiomeMap {

    public static final int SIZE = 320;

    boolean isImage;

    public static final String DIR = "config/"+TheCasketOfReveriesMod.MOD_ID+"/";
    public static final String README = DIR+"图片请命名为“map.png”，近透明或白色为空域其余为大陆。建议地图大小320x320。图片不存在则按默认预设生成地图";
    public static final String FILE = DIR+"map.png";

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
            File dir = new File(DIR);
            if(!dir.exists()){
                System.out.println("mkdir:"+dir.mkdirs());
            }
            System.out.println(dir.getAbsolutePath());
            File readme = new File(README);
            if(!readme.exists()){
                System.out.println("mk ReadMe:"+readme.createNewFile());
            }

            BufferedImage image = ImageIO.read(new File(FILE));
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
            System.out.println("维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图");
            if(!GraphicsEnvironment.isHeadless())
                JOptionPane.showMessageDialog(null,"维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图","The Casket Of Reveries：提示",JOptionPane.INFORMATION_MESSAGE);
            isImage = false;
            return createNoiseMapStatic(generator);
        }
    }


    public static double[][] createNoiseMapStatic(NoiseMapGenerator generator){
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

    public static double[][] createImageMapStatic(NoiseMapGenerator generator){
        try {
            File dir = new File(DIR);
            if(!dir.exists()){
                System.out.println("mkdir:"+dir.mkdirs());
            }
            System.out.println(dir.getAbsolutePath());
            File readme = new File(README);
            if(!readme.exists()){
                System.out.println("mk ReadMe:"+readme.createNewFile());
            }

            BufferedImage image = ImageIO.read(new File(FILE));
            int height = image.getHeight();
            int width = image.getWidth();
            double[][] map = new double[height][width];
            for(int i = 0;i < height; i++){
                for(int j = 0;j < width; j++){
                    map[i][j] = ((image.getRGB(i,j) == 0xffffff || ((image.getRGB(i,j)>>24)&0xff) < 50) ? 0 : 1);//白色或近透明则为空域
                }
            }

            map = generator.divide(map);
            map = generator.addCenter(map);
            return map;

        } catch (Exception e) {
            //FIXME 想办法输出信息
            System.out.println("维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图");
            if(!GraphicsEnvironment.isHeadless())
                JOptionPane.showMessageDialog(null,"维度地图图片\""+FILE+"\"文件异常！将以默认预设生成地图","The Casket Of Reveries：提示",JOptionPane.INFORMATION_MESSAGE);
            return createNoiseMapStatic(generator);
        }
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
