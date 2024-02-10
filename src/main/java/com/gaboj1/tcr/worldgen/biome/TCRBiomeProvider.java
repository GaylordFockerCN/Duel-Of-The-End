package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.worldgen.noise.NoiseMapGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author LZY
 * 学暮色写了个BiomeProvider来用自己生成的map给指定位置生成指定的群系，具体群系长什么样可以运行{@link NoiseMapGenerator#main(String[])}
 */
public class TCRBiomeProvider extends BiomeSource {

    public static final Codec<TCRBiomeProvider> TCR_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
//            Codec.INT.fieldOf("seed").forGetter((o) -> o.seed),//如果编码进去的话地图会固定住
            RegistryOps.retrieveElement(TCRBiomes.finalBiome),
            RegistryOps.retrieveElement(TCRBiomes.biome1),
            RegistryOps.retrieveElement(TCRBiomes.biome2),
            RegistryOps.retrieveElement(TCRBiomes.biome3),
            RegistryOps.retrieveElement(TCRBiomes.biome4),
            RegistryOps.retrieveElement(TCRBiomes.biome1Center),
            RegistryOps.retrieveElement(TCRBiomes.biome2Center),
            RegistryOps.retrieveElement(TCRBiomes.biome3Center),
            RegistryOps.retrieveElement(TCRBiomes.biome4Center),
            RegistryOps.retrieveElement(TCRBiomes.biomeBorder)
    ).apply(instance, instance.stable(TCRBiomeProvider::new)));

    private double[][] map;
    private final int SIZE = 320;
    private final int R = SIZE/2;

    private final Holder<Biome> biomeHolder0;
    private final Holder<Biome> biomeHolder1;
    private final Holder<Biome> biomeHolder2;
    private final Holder<Biome> biomeHolder3;
    private final Holder<Biome> biomeHolder4;
    private final Holder<Biome> biomeHolder5;
    private final Holder<Biome> biomeHolder6;
    private final Holder<Biome> biomeHolder7;
    private final Holder<Biome> biomeHolder8;
    //TODO：边境之地
    private final Holder<Biome> biomeHolder9;

    private final List<Holder<Biome>> biomeList;

    public static TCRBiomeProvider create(HolderGetter<Biome> pBiomeGetter) {

        return new TCRBiomeProvider(
                pBiomeGetter.getOrThrow(TCRBiomes.finalBiome),
                pBiomeGetter.getOrThrow(TCRBiomes.biome1),
                pBiomeGetter.getOrThrow(TCRBiomes.biome2),
                pBiomeGetter.getOrThrow(TCRBiomes.biome3),
                pBiomeGetter.getOrThrow(TCRBiomes.biome4),
                pBiomeGetter.getOrThrow(TCRBiomes.biome1Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome2Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome3Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biome4Center),
                pBiomeGetter.getOrThrow(TCRBiomes.biomeBorder)
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

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        //不在这里生成的话map会被清空，但是这里生成不知道有什么bug...
        NoiseMapGenerator generator = new NoiseMapGenerator();
        generator.setSeed(new Random().nextInt(100));//TODO: 改成世界种子
        generator.setLength(SIZE);
        generator.setWidth(SIZE);
        generator.setLacunarity(12);//TODO 调整合适大小
        generator.setOctaves(8);
        map = generator.generateNoiseMap();
        map = generator.divide(map);
        map = generator.addCenter(map);

//        //生成缩小版预览
//        int size = 180;
//        generator.setLength(size);
//        generator.setWidth(size);
//        double[][] sMap = generator.generateNoiseMap();
//        sMap = generator.divide(sMap);
//        sMap =  generator.addCenter(sMap);
//        for(int i = 0 ; i < size ; i++){
//            for(int j = 0 ; j < size ; j++){
////                System.out.print(String.format("%.0f ",map[i][j]));
//                if(sMap[i][j] == 1){
//                    System.out.print("@ ");
//                }else if(sMap[i][j] == 2){
//                    System.out.print("# ");
//                }else if(sMap[i][j] == 3){
//                    System.out.print("^ ");
//                }else if(sMap[i][j] == 4){
//                    System.out.print("* ");
//                }else {
//                    System.out.print("- ");
//                }
//            }
//            System.out.println();
//        }

        return Stream.of(biomeHolder0,biomeHolder1,biomeHolder2,biomeHolder3,biomeHolder4,biomeHolder5,biomeHolder6,biomeHolder7,biomeHolder8,biomeHolder9);
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

        x*=0.2;
        z*=0.2;//数组不能放大，只能这里放大（你就说妙不妙）缺点就是图衔接处有点方。。
        if(0 <= x+R && x+R <map.length && 0 <= z+R && z+R < map[0].length ){
            int index = (int)map[x+R][z+R];
            if(index < biomeList.size())
                return biomeList.get((int)map[x+R][z+R]);
        }
        return biomeHolder0;
    }

}
