package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.TCRConfig;
import com.gaboj1.tcr.TheCasketOfReveriesMod;
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
import net.minecraftforge.fml.loading.FMLPaths;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 研究暮色源码获得灵感，仿照末地{@link net.minecraft.world.level.biome.TheEndBiomeSource}写的。
 * 并且在此实现对地图生成的控制，对地图数据的读写。
 * 重点方法是getNoiseBiome，这个方法实现了从自己的map读取什么位置是什么群系，然后返回给ChunkGenerator。
 * @author LZY
 */
public class TCRBiomeProvider extends BiomeSource {

    public static final Codec<TCRBiomeProvider> TCR_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
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

    //地图名，用于加载世界窗口绘制。
    public static String mapName = "";
    //存档名，用于分文件存储。
    public static String worldName = "";
    private int[][] peakMap;
    private static NoiseMapGenerator generator;
    private int R;//总体半径和中心群系噪声半径
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

    /**
     * 这里需要给出所有可能的群系。
     * 成员变量最好在这里初始化
     */
    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        File dir = FMLPaths.CONFIGDIR.get().resolve(TheCasketOfReveriesMod.MOD_ID).toFile();
        if(!dir.exists()){
            TheCasketOfReveriesMod.LOGGER.info("try mkdir : " + dir.mkdir());
        }
        File mapFile = new File(dir + "/" + worldName + ".dat");
        //二次进入游戏从文件直接读取数组较快，否则每次进世界都得加载，地图大的话很慢
        if(mapFile.exists()){
            try {
                TheCasketOfReveriesMod.LOGGER.info("Loading existing map data form : " + mapFile.getAbsolutePath());
                FileInputStream fis = new FileInputStream(mapFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                generator = (NoiseMapGenerator) ois.readObject();
                mapName = (String) ois.readObject();
                BiomeMap.init(generator);
                isImage = generator.isImage();
                ois.close();
                fis.close();
            } catch (Exception e) {
                createBiomeMap(mapFile);
            }

        }else {
            createBiomeMap(mapFile);
        }
        int mountainsR = (generator.getaCenterR()<<2);
        //第二群系山的高度图
        peakMap = RandomMountainGenerator.getMountains(mountainsR,mountainsR);

        return Stream.of(biomeHolder0,biomeHolder1,biomeHolder2,biomeHolder3,biomeHolder4,biomeHolder5,biomeHolder6,biomeHolder7,biomeHolder8,biomeHolder9);
    }

    private void createBiomeMap(File mapFile){
        //因为存在平和不平两种世界，所以避免重复读取。
        if(generator != null){
            BiomeMap.init(generator);
            isImage = generator.isImage();
            return;
        }
        generator = new NoiseMapGenerator();
        BiomeMap.createImageMapStatic(generator);
        try {
            mapFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mapFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(generator);
            oos.writeObject(mapName);
            oos.close();
            fos.close();
        } catch (IOException e) {
            TheCasketOfReveriesMod.LOGGER.error("Failed to save map", e);
            mapName = "SAVE_ERROR!!";
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
        if(0 <= x && x <generator.getMap().length && 0 <= z && z < generator.getMap()[0].length ){
            int index = (int)generator.getMap()[x][z];
            if(index < biomeList.size())
                return biomeList.get((int)generator.getMap()[x][z]);
        }
        return biomeHolder0;
    }

    /**
     * 因为实际出生地是(0,0)，所以应该进行偏移
     * 对于chunkX，应该使用getCorrectValue(chunkX<<2)
     * 对于blockPosX，应该使用getCorrectValue(blockPosX>>2)
    * */
    public int getCorrectValue(int biomeXorZ){
        if(!isImage || TCRConfig.ENABLE_SCALING.get()){
            biomeXorZ *= (int) (SCALE * generator.getMap().length / BiomeMap.SIZE);//数组不能放大，只能这里放大（你就说妙不妙）缺点就是图衔接处有点方。。
        }
        return biomeXorZ+R;
    }

    /**
     * 偏移回去
     * @param biomeXorZ
     * @return
     */
    public int deCorrectValue(int biomeXorZ){
        if(!isImage || TCRConfig.ENABLE_SCALING.get()){
            biomeXorZ /= (int) (SCALE * generator.getMap().length / BiomeMap.SIZE);//数组不能放大，只能这里放大（你就说妙不妙）缺点就是图衔接处有点方。。
        }
        return biomeXorZ-R;
    }

    /**
     * 仅针对第二群系，获取具体坐标对应位置的山的高度值。
     * 先转换为正确的坐标（群系坐标），再去peakMap中查找对应的点。
     */
    public int getMountainHeight(BlockPos pos){
        int offsetX = pos.getX()+R*4-(((generator.getCenter2().x)*4) - (generator.getaCenterR()*2));
        int offsetZ = pos.getZ()+R*4-(((generator.getCenter2().y)*4) - (generator.getaCenterR()*2));
        if(offsetX > 0 && offsetX < peakMap.length && offsetZ > 0 && offsetZ < peakMap[0].length){
            return Math.abs(peakMap[offsetX][offsetZ]);
        }
        return 0;
    }

}
