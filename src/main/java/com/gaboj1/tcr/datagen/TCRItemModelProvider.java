package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRBlocks;
import com.gaboj1.tcr.item.TCRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class TCRItemModelProvider extends ItemModelProvider {

    //Thank you kaupenjoe & El_Redstoniano!
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public TCRItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        handheldItem(TCRItems.HEALTH_WAND);

        eggItem(TCRItems.JELLY_CAT_SPAWN_EGG);
        eggItem(TCRItems.SQUIRREL_SPAWN_EGG);
        eggItem(TCRItems.CRAB_SPAWN_EGG);
        eggItem(TCRItems.SMALL_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRItems.MIDDLE_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRItems.TREE_GUARDIAN_SPAWN_EGG);
        eggItem(TCRItems.YGGDRASIL_SPAWN_EGG);
        eggItem(TCRItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG);
        eggItem(TCRItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG);
        eggItem(TCRItems.SPRITE_SPAWN_EGG);
        eggItem(TCRItems.TIGER_SPAWN_EGG);
        eggItem(TCRItems.BOXER_SPAWN_EGG);
        eggItem(TCRItems.BIG_HAMMER_EGG);
        eggItem(TCRItems.SNOW_SWORDMAN_SPAWN_EGG);
        eggItem(TCRItems.SWORD_CONTROLLER_SPAWN_EGG);
        eggItem(TCRItems.SECOND_BOSS_SPAWN_EGG);
        eggItem(TCRItems.CANG_LAN_SPAWN_EGG);

        simpleItem(TCRItems.DREAMSCAPE_COIN);
        simpleItem(TCRItems.DREAMSCAPE_COIN_PLUS);
        simpleItem(TCRItems.ORICHALCUM);
        simpleItem(TCRItems.RAW_ORICHALCUM);

        simpleItem(TCRItems.TREE_DEMON_HORN);
        simpleItem(TCRItems.TREE_DEMON_MASK);
        simpleItem(TCRItems.TREE_DEMON_BRANCH);
        simpleItem(TCRItems.TREE_DEMON_FRUIT);

        simpleItem(TCRItems.CATNIP);
        simpleItem(TCRItems.WITHERING_TOUCH);
        simpleItem(TCRItems.HEART_OF_THE_SAPLING);
        simpleItem(TCRItems.ESSENCE_OF_THE_ANCIENT_TREE);
        simpleItem(TCRItems.BARK_OF_THE_GUARDIAN);
        simpleItem(TCRItems.STARLIT_DEWDROP);
        simpleItem(TCRItems.DENSE_FOREST_CERTIFICATE);
        simpleItem(TCRItems.TIGER_SOUL_ICE);

        //handheldItem()会变很大个，适合用于武器什么的
        simpleItem(TCRItems.BLUE_BANANA);
        simpleItem(TCRItems.DREAM_TA);
        simpleItem(TCRItems.BEER);
        simpleItem(TCRItems.COOKIE);
        simpleItem(TCRItems.ELDER_CAKE);
        //说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！）
        simpleItem(TCRItems.EDEN_APPLE);
        simpleItem(TCRItems.DRINK1);
        simpleItem(TCRItems.DRINK2);
        simpleItem(TCRItems.GOLDEN_WIND_AND_DEW);
        simpleItem(TCRItems.GREEN_BANANA);
        simpleItem(TCRItems.HOT_CHOCOLATE);
        simpleItem(TCRItems.JUICE_TEA);
        simpleItem(TCRItems.MAO_DAI);
        simpleItem(TCRItems.PINE_CONE);
        simpleItem(TCRItems.RED_WINE);
        simpleItem(TCRItems.WAN_MING_PEARL);

        simpleItem(TCRItems.LIGHT_ELIXIR);
        simpleItem(TCRItems.ASCENSION_ELIXIR);
        simpleItem(TCRItems.LUCKY_ELIXIR);
        simpleItem(TCRItems.EVASION_ELIXIR);
        simpleItem(TCRItems.WATER_AVOIDANCE_ELIXIR);
        simpleItem(TCRItems.FIRE_AVOIDANCE_ELIXIR);
        simpleItem(TCRItems.COLD_AVOIDANCE_ELIXIR);
        simpleItem(TCRItems.THUNDER_AVOIDANCE_ELIXIR);
        simpleItem(TCRItems.POISON_AVOIDANCE_ELIXIR);
        simpleItem(TCRItems.NINE_TURN_REVIVAL_ELIXIR);
        simpleItem(TCRItems.STRENGTH_PILL);
        simpleItem(TCRItems.AQUA_GOLD_ELIXIR);

        simpleBlockItemBlockTexture(TCRBlocks.DENSE_FOREST_SPIRIT_FLOWER);
        simpleBlockItemBlockTexture(TCRBlocks.CATNIP);
        saplingItem(TCRBlocks.DENSE_FOREST_SPIRIT_SAPLING);
        fenceItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE, TCRBlocks.DENSE_FOREST_SPIRIT_TREE_PLANKS);
        simpleBlockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_DOOR);
        evenSimplerBlockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_STAIRS);
        evenSimplerBlockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_SLAB);
        evenSimplerBlockItem(TCRBlocks.DENSE_FOREST_SPIRIT_TREE_FENCE_GATE);

        trimmedArmorItem(TCRItems.ORICHALCUM_HELMET);
        trimmedArmorItem(TCRItems.ORICHALCUM_CHESTPLATE);
        trimmedArmorItem(TCRItems.ORICHALCUM_LEGGINGS);
        trimmedArmorItem(TCRItems.ORICHALCUM_BOOTS);

        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_1_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_1_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_2_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_2_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_3_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_3_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_4_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_4_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_5_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_5_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_6_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_6_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_7_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_7_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_8_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_8_SLAB);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_9_STAIRS);
        evenSimplerBlockItem(TCRBlocks.BOSS2_ROOM_9_SLAB);

//        simpleItem(TCRItems.ICE_TIGER_BOOTS);
//        simpleItem(TCRItems.ICE_TIGER_CHESTPLATE);
//        simpleItem(TCRItems.ICE_TIGER_HELMET);
//        simpleItem(TCRItems.ICE_TIGER_LEGGINGS);

    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = TheCasketOfReveriesMod.MOD_ID; // Change this to your mod x

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder eggItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(TheCasketOfReveriesMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheCasketOfReveriesMod.MOD_ID,"block/" + item.getId().getPath()));
    }
}
