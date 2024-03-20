package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.worldgen.noise.NoiseMapGenerator;
import com.gaboj1.tcr.util.map.RandomMountainGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author LZY
 * 学暮色写了个BiomeProvider来用自己生成的map给指定位置生成指定的群系，具体群系长什么样可以运行{@link NoiseMapGenerator#main(String[])}
 */
public class TCRBiomeProvider extends BiomeSource {

    public static final Codec<TCRBiomeProvider> TCR_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
//            Codec.INT.fieldOf("seed").forGetter((o) -> o.seed),//如果编码进去的话地图会固定住
            RegistryOps.retrieveElement(TCRBiomes.biomeBorder),
            RegistryOps.retrieveElement(TCRBiomes.biome1),
            RegistryOps.retrieveElement(TCRBiomes.biome2),
            RegistryOps.retrieveElement(TCRBiomes.biome3),
            RegistryOps.retrieveElement(TCRBiomes.biome4),
            RegistryOps.retrieveElement(TCRBiomes.biome1Center),
            RegistryOps.retrieveElement(TCRBiomes.biome2Center),
            RegistryOps.retrieveElement(TCRBiomes.biome3Center),
            RegistryOps.retrieveElement(TCRBiomes.biome4Center),
            RegistryOps.retrieveElement(TCRBiomes.finalBiome)
    ).apply(instance, instance.stable(TCRBiomeProvider::new)));

    private double[][] map;
    private int[][] peakMap;

    private int R,aCenterR;//总体半径和中心群系噪声半径
    public static final double SCALE = 0.2;
    private boolean isImage = true;

    private final Holder<Biome> biomeHolder0;
    private final Holder<Biome> biomeHolder1;
    private final Holder<Biome> biomeHolder2;
    private final Holder<Biome> biomeHolder3;
    private final Holder<Biome> biomeHolder4;
    private final Holder<Biome> biomeHolder5;
    private final Holder<Biome> biomeHolder6;
    private final Holder<Biome> biomeHolder7;
    private final Holder<Biome> biomeHolder8;
    private final Holder<Biome> biomeHolder9;

    public Point getCenter1() {
        return center1;
    }

    public Point getCenter2() {
        return center2;
    }

    public Point getCenter3() {
        return center3;
    }

    public Point getCenter4() {
        return center4;
    }

    public Point getMainCenter() {
        return mainCenter;
    }

    private Point center1, center2, center3, center4, mainCenter;

    private final List<Holder<Biome>> biomeList;

    public static TCRBiomeProvider create(HolderGetter<Biome> pBiomeGetter) {

        return new TCRBiomeProvider(
                pBiomeGetter.getOrThrow(TCRBiomes.biomeBorder),
                pBiomeGetter.getOrThrow(TCRBiomes.biome1),
                pBiomeGetter.getOrThrow(TCRBiomes.biome2),
                pBiomeGetter.getOrThrow(TCRBiomes.biome3),
                pBiomeGetter.getOrThrow(TCRBiomes.biome4),
                pBiomeGetter.getOrThrow(TCRBiomes.biome1Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome2Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome3Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome4Center),
                pBiomeGetter.getOrThrow(TCRBiomes.finalBiome)
                );
    }

    public TCRBiomeProvider( Holder<Biome> biomeHolder0, Holder<Biome> biomeHolder1, Holder<Biome> biomeHolder2, Holder<Biome> biomeHolder3, Holder<Biome> biomeHolder4, Holder<Biome> biomeHolder5, Holder<Biome> biomeHolder6, Holder<Biome> biomeHolder7, Holder<Biome> biomeHolder8, Holder<Biome> biomeHolder9) {
        this.biomeHolder0 = biomeHolder0;
        this.biomeHolder1 = biomeHolder1;
        this.biomeHolder2 = biomeHolder2;
        this.biomeHolder3 = biomeHolder3;
        this.biomeHolder4 = biomeHolder4;
        this.biomeHolder5 = biomeHolder5;
        this.biomeHolder6 = biomeHolder6;
        this.biomeHolder7 = biomeHolder7;
        this.biomeHolder8 = biomeHolder8;
        this.biomeHolder9 = biomeHolder9;
        biomeList = new ArrayList<>();
        biomeList.add(biomeHolder0);
        biomeList.add(biomeHolder1);
        biomeList.add(biomeHolder2);
        biomeList.add(biomeHolder3);
        biomeList.add(biomeHolder4);
        biomeList.add(biomeHolder5);
        biomeList.add(biomeHolder6);
        biomeList.add(biomeHolder7);
        biomeList.add(biomeHolder8);
        biomeList.add(biomeHolder9);

    }

    //不在这里生成map的话map会被清空
    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
//        String levelName = Minecraft.getInstance().getCurrentServer().name;服务端还没创建，无法获取名字......
        //FIXME 换成存档名字，很重要！！

        File mapFile = new File(Minecraft.getInstance().gameDirectory.getAbsoluteFile() +"Map.dat");
//        boolean mapExist = mapFile.exists();
        boolean mapExist = false;//先默认不用读取的

        //二次进入游戏从文件直接读取数组较快，否则每次进世界都得加载，地图大的话很慢 TODO:有bug，二次读取会出现很大偏移

        if(mapExist){
            try {
                FileInputStream fis = new FileInputStream(mapFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (double[][]) ois.readObject();
                center1 = (Point) ois.readObject();
                center2 = (Point) ois.readObject();
                center3 = (Point) ois.readObject();
                center4 = (Point) ois.readObject();
                mainCenter = (Point) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                createBiomeMap(mapFile);
            }
        }else {
            createBiomeMap(mapFile);
        }
        aCenterR = (int) (center1.distance(mainCenter)*NoiseMapGenerator.SCALE_OF_A_CENTER_R);
        int mountainsR = (aCenterR<<2);
        System.out.println(mountainsR);
        peakMap = RandomMountainGenerator.getMountains(mountainsR,mountainsR,128);

        return Stream.of(biomeHolder0,biomeHolder1,biomeHolder2,biomeHolder3,biomeHolder4,biomeHolder5,biomeHolder6,biomeHolder7,biomeHolder8,biomeHolder9);
    }

    private void createBiomeMap(File mapFile){
        NoiseMapGenerator generator = new NoiseMapGenerator();
        BiomeMap biomeMap = new BiomeMap();
        map = biomeMap.createImageMap(generator);
        isImage = biomeMap.isImage;
        R = map[0].length / 2;
        //以便获取主建筑摆放位置
        center1 = generator.getCenter1();
        center2 = generator.getCenter2();
        center3 = generator.getCenter3();
        center4 = generator.getCenter4();
        mainCenter = generator.getCenter();

        try {
//            new File(BiomeMap.DIR).mkdir();
            mapFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mapFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.writeObject(center1);
            oos.writeObject(center2);
            oos.writeObject(center3);
            oos.writeObject(center4);
            oos.writeObject(mainCenter);
            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Codec<? extends BiomeSource> codec() {
        return TCR_CODEC;
    }

    /**
     *
     * @param x x坐标（并非1：1！！）
     * @param y y坐标（并非1：1！！）
     * @param z z坐标（并非1：1！！）
     * @param sampler
     * @return 根据自己的噪声地图返回对应位置的群系
     */
    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        x = getCorrectValue(x);
        z = getCorrectValue(z);
        if(0 <= x && x <map.length && 0 <= z && z < map[0].length ){
            int index = (int)map[x][z];
            if(index < biomeList.size())
                return biomeList.get((int)map[x][z]);
        }
        return biomeHolder0;
    }

    /**
     * 对于chunkX，应该使用getCorrectValue(chunkX<<2)
     * 对于blockPosX，应该使用getCorrectValue(blockPosX>>2)
    * */
    public int getCorrectValue(int biomeX){
        if(!isImage || TCRConfig.ENABLE_SCALING.get()){
            biomeX *= SCALE * map.length / BiomeMap.SIZE;//数组不能放大，只能这里放大（你就说妙不妙）缺点就是图衔接处有点方。。
        }
        return biomeX+R;
    }

    //还原回去
    public int deCorrectValue(int biomeX){
        if(!isImage || TCRConfig.ENABLE_SCALING.get()){
            biomeX /= SCALE * map.length / BiomeMap.SIZE;//数组不能放大，只能这里放大（你就说妙不妙）缺点就是图衔接处有点方。。
        }
        return biomeX-R;
    }

    /**
     * 仅针对第二群系
     */
    public int getMountainHeight(BlockPos pos){
        int offsetX = pos.getX()+R*4-(((center2.x)*4) - (aCenterR*2));
        int offsetZ = pos.getZ()+R*4-(((center2.y)*4) - (aCenterR*2));
        if(offsetX > 0 && offsetX < peakMap.length && offsetZ > 0 && offsetZ < peakMap[0].length){
            return Math.abs(peakMap[offsetX][offsetZ]);
        }
        return 0;
    }

}
