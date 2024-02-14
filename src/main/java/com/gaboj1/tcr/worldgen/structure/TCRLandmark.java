//package com.gaboj1.tcr.worldgen.structure;
//
//import com.google.common.collect.ImmutableMap;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.DataResult;
//import net.minecraft.Util;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.chunk.ChunkGenerator;
//import net.minecraft.world.level.levelgen.structure.BoundingBox;
//import net.minecraft.world.level.levelgen.structure.StructurePiece;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class TCRLandmark {
//    public static final TCRLandmark NOTHING = new TCRLandmark( 0, "no_feature", false, false);
//    public static final TCRLandmark LICH_TOWER = new TCRLandmark( 1, "lich_tower" ) {
//        @Override
//        public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
//            return new TowerMainComponent(rand, 0, x, y, z);
//        }
//    };
//
//    public static final TCRLandmark QUEST_ISLAND = new TCRLandmark( 1, "quest_island", false, false);
//    //public static final TFLandmark DRUID_GROVE    = new TFLandmark( 1, "druid_grove"   , false, false);
//    //public static final TFLandmark FLOATING_RUINS = new TFLandmark( 3, "floating_ruins", false, false);
//    //public static final TFLandmark WORLD_TREE = new TFLandmark( 3, "world_tree", false, false);
//
//    public final int size;
//    public final String name;
//    // Tells the chunkgenerator if there's an associated structure.
//    public final boolean isStructureEnabled;
//    // Tells the chunkgenerator the terrain changes around the structure.
//    public final boolean requiresTerraforming;
//
//    private static int maxPossibleSize;
//
//    private TCRLandmark(int size, String name) {
//        this(size, name, true, false);
//    }
//
//    private TCRLandmark(int size, String name, boolean isStructureEnabled, boolean requiresTerraforming) {
//        this.size = size;
//        this.name = name;
//        this.isStructureEnabled = isStructureEnabled;
//        this.requiresTerraforming = requiresTerraforming;
//
//        maxPossibleSize = Math.max(this.size, maxPossibleSize);
//    }
//
//    @Deprecated // Not good practice - TODO The root need for this method can be fixed
//    public static int getMaxSearchSize() {
//        return maxPossibleSize;
//    }
//
//    @Deprecated // TODO Deleting this method will break maps - best to wait until new MC version before committing to it.
//    @Nullable
//    public StructurePiece provideFirstPiece(StructureTemplateManager structureManager, ChunkGenerator chunkGenerator, RandomSource rand, int x, int y, int z) {
//        return null;
//    }
//
//    @NotNull
//    public static BoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ, int spanX, int spanY, int spanZ, @Nullable Direction dir, boolean centerBounds) {
//        // CenterBounds is true for ONLY Hollow Hills, Hydra Lair, & Yeti Caves
//        if(centerBounds) {
//            x += (spanX + minX) / 4;
//            y += (spanY + minY) / 4;
//            z += (spanZ + minZ) / 4;
//        }
//        return switch (dir) {
//            case WEST -> // '\001'
//                    new BoundingBox(x - spanZ + minZ, y + minY, z + minX, x + minZ, y + spanY + minY, z + spanX + minX);
//            case NORTH -> // '\002'
//                    new BoundingBox(x - spanX - minX, y + minY, z - spanZ - minZ, x - minX, y + spanY + minY, z - minZ);
//            case EAST -> // '\003'
//                    new BoundingBox(x + minZ, y + minY, z - spanX, x + spanZ + minZ, y + spanY + minY, z + minX);
//            default -> // '\0'
//                    new BoundingBox(x + minX, y + minY, z + minZ, x + spanX + minX, y + spanY + minY, z + spanZ + minZ);
//        };
//    }
//
//    private static final ImmutableMap<String, TCRLandmark> NAME_2_TYPE = Util.make(() -> ImmutableMap.<String, TCRLandmark>builder()
//            .put("mushroom_tower", TCRLandmark.MUSHROOM_TOWER)
//            .put("small_hollow_hill", TCRLandmark.SMALL_HILL)
//            .put("medium_hollow_hill", TCRLandmark.MEDIUM_HILL)
//            .put("large_hollow_hill", TCRLandmark.LARGE_HILL)
//            .put("hedge_maze", TCRLandmark.HEDGE_MAZE)
//            .put("quest_grove", TCRLandmark.QUEST_GROVE)
//            .put("quest_island", TCRLandmark.QUEST_ISLAND)
//            .put("naga_courtyard", TCRLandmark.NAGA_COURTYARD)
//            .put("lich_tower", TCRLandmark.LICH_TOWER)
//            .put("hydra_lair", TCRLandmark.HYDRA_LAIR)
//            .put("labyrinth", TCRLandmark.LABYRINTH)
//            .put("dark_tower", TCRLandmark.DARK_TOWER)
//            .put("knight_stronghold", TCRLandmark.KNIGHT_STRONGHOLD)
//            .put("yeti_lairs", TCRLandmark.YETI_CAVE)
//            .put("ice_tower", TCRLandmark.ICE_TOWER)
//            .put("troll_lairs", TCRLandmark.TROLL_CAVE)
//            .put("final_castle", TCRLandmark.FINAL_CASTLE)
//            .build());
//
//    public static final Codec<TCRLandmark> CODEC = Codec.STRING.comapFlatMap(
//            name -> TCRLandmark.NAME_2_TYPE.containsKey(name) ? DataResult.success(TCRLandmark.NAME_2_TYPE.get(name)) : DataResult.error(() -> "Landmark " + name + " not recognized!"),
//            tfFeature -> tfFeature.name
//    );
//}
