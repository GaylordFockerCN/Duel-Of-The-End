package com.p1nero.dote.worldgen.biome;

import com.p1nero.dote.DuelOfTheEndMod;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

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
public class DOTEBiomeProvider extends BiomeSource {

    public static final Codec<DOTEBiomeProvider> TCR_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RegistryOps.retrieveElement(DOTEBiomes.AIR),
            RegistryOps.retrieveElement(DOTEBiomes.M_BIOME),
            RegistryOps.retrieveElement(DOTEBiomes.P_BIOME)
    ).apply(instance, instance.stable(DOTEBiomeProvider::new)));

    public static final Logger LOGGER = LogUtils.getLogger();

    //地图名，用于加载世界窗口绘制。
    public static String mapName = "";
    //存档名，用于分文件存储。
    public static String worldName = "";

    public static final File DIR = FMLPaths.CONFIGDIR.get().resolve(DuelOfTheEndMod.MOD_ID).toFile();
    private static File mapFile;

    private final Holder<Biome> biomeHolder0;
    private final Holder<Biome> biomeHolder1;
    private final Holder<Biome> biomeHolder2;

    private final List<Holder<Biome>> biomeList;

    public static DOTEBiomeProvider create(HolderGetter<Biome> pBiomeGetter) {

        return new DOTEBiomeProvider(
                pBiomeGetter.getOrThrow(DOTEBiomes.AIR),
                pBiomeGetter.getOrThrow(DOTEBiomes.M_BIOME),
                pBiomeGetter.getOrThrow(DOTEBiomes.P_BIOME)
                );
    }

    public DOTEBiomeProvider(Holder<Biome> biomeHolder0, Holder<Biome> biomeHolder1, Holder<Biome> biomeHolder2) {
        this.biomeHolder0 = biomeHolder0;
        this.biomeHolder1 = biomeHolder1;
        this.biomeHolder2 = biomeHolder2;
        biomeList = new ArrayList<>();
        biomeList.add(biomeHolder0);
        biomeList.add(biomeHolder1);
        biomeList.add(biomeHolder2);
    }

    /**
     * 这里需要给出所有可能的群系。
     * 成员变量最好在这里初始化
     */
    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        if(BiomeMap.getInstance() == null){
            updateBiomeMap(worldName);
        }

        return Stream.of(biomeHolder0, biomeHolder1, biomeHolder2);
    }

    /**
     * 从图片创建BiomeMap并保存
     * @param mapFile
     */
    private static void createBiomeMap(File mapFile){
        try {
            BiomeMap.init();
            mapFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mapFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mapName);
            oos.writeObject(BiomeMap.getInstance());
            oos.close();
            fos.close();
        } catch (IOException e) {
            LOGGER.error("Failed to save map", e);
            mapName = "SAVE_ERROR!!";
        }

    }

    /**
     * 为了防止有人不重启而直接换存档玩而写的。。
     * 二次进游戏它不会重置BiomeProvider，对于原版来说只要seed不一样即可。。
     * @param levelID 世界的id，可以理解为存档名。
     */
    public static void updateBiomeMap(String levelID){
        if(!DIR.exists()){
            LOGGER.info("try mkdir : " + DIR.mkdir());
        }
        worldName = levelID;
        mapFile = getLevelFile();
        //二次进入游戏从文件直接读取数组较快，否则每次进世界都得加载，地图大的话很慢
        if(mapFile.exists()){
            try {
                LOGGER.info("Loading existing map data form : " + mapFile.getAbsolutePath());
                FileInputStream fis = new FileInputStream(mapFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                mapName = (String) ois.readObject();
                BiomeMap.load((BiomeMap) ois.readObject());
                ois.close();
                fis.close();
            } catch (Exception e) {
                createBiomeMap(mapFile);
            }

        }else {
            createBiomeMap(mapFile);
        }
    }

    public static boolean deleteCache(String levelName){
        return getLevelFile(levelName).delete();
    }

    public static File getLevelFile(){
        return new File(DIR + "/" + worldName + ".dat");
    }

    public static File getLevelFile(String worldName){
        return new File(DIR + "/" + worldName + ".dat");
    }


    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return TCR_CODEC;
    }

    /**
     *
     * @param x x坐标（并非1：1！！）
     * @param y y坐标（并非1：1！！）
     * @param z z坐标（并非1：1！！）
     * @return 根据自己的噪声地图返回对应位置的群系
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        x = getCorrectValue(x);
        z = getCorrectValue(z);
        if(0 <= x && x < BiomeMap.getInstance().getMapLength() && 0 <= z && z < BiomeMap.getInstance().getMapWidth()){
            int index = (int)BiomeMap.getInstance().getMap()[x][z];
            if(index < biomeList.size())
                return biomeList.get((int)BiomeMap.getInstance().getMap()[x][z]);
        }
        return biomeHolder0;
    }

    /**
     * 因为实际出生地是(0,0)，所以应该进行偏移
     * 对于chunkX，应该使用getCorrectValue(chunkX<<2)
     * 对于blockPosX，应该使用getCorrectValue(blockPosX>>2)
    * */
    public int getCorrectValue(int biomeXorZ){
        return biomeXorZ + BiomeMap.getInstance().getR();
    }

    /**
     * 偏移回去
     */
    public int deCorrectValue(int biomeXorZ){
        return biomeXorZ - BiomeMap.getInstance().getR();
    }

}
