package com.gaboj1.tcr.datagen;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.block.TCRModBlocks;
import com.gaboj1.tcr.item.TCRModItems;
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

public class ModItemModelProvider extends ItemModelProvider {

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

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        eggItem(TCRModItems.JELLY_CAT_SPAWN_EGG);
        eggItem(TCRModItems.SMALL_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRModItems.MIDDLE_TREE_MONSTER_SPAWN_EGG);
        eggItem(TCRModItems.TREE_GUARDIAN_SPAWN_EGG);
        eggItem(TCRModItems.YGGDRASIL_SPAWN_EGG);
        eggItem(TCRModItems.PASTORAL_PLAIN_VILLAGER_SPAWN_EGG);
        eggItem(TCRModItems.PASTORAL_PLAIN_VILLAGER_ELDER_SPAWN_EGG);
        eggItem(TCRModItems.SPRITE_SPAWN_EGG);
        eggItem(TCRModItems.TIGER_EGG);
        eggItem(TCRModItems.BOXER_EGG);
        eggItem(TCRModItems.BIG_HAMMER_EGG);
        eggItem(TCRModItems.SNOW_SWORDMAN_SPAWN_EGG);
        eggItem(TCRModItems.SWORD_CONTROLLER_SPAWN_EGG);

        simpleItem(TCRModItems.DREAMSCAPE_COIN);
        simpleItem(TCRModItems.DREAMSCAPE_COIN_PLUS);
        simpleItem(TCRModItems.ORICHALCUM);
        simpleItem(TCRModItems.RAW_ORICHALCUM);
        //handheldItem()会变很大个，适合用于武器什么的
        simpleItem(TCRModItems.BLUE_BANANA);
        simpleItem(TCRModItems.DREAM_TA);
        simpleItem(TCRModItems.BEER);
        simpleItem(TCRModItems.COOKIE);
        simpleItem(TCRModItems.ELDER_CAKE);
        //说出来你可能不信，以下的小物品是训练chatGPT3.5仿写后修改的（大力解放生产力！）
        simpleItem(TCRModItems.EDEN_APPLE);
        simpleItem(TCRModItems.DRINK1);
        simpleItem(TCRModItems.DRINK2);
        simpleItem(TCRModItems.GOLDEN_WIND_AND_DEW);
        simpleItem(TCRModItems.GREEN_BANANA);
        simpleItem(TCRModItems.HOT_CHOCOLATE);
        simpleItem(TCRModItems.JUICE_TEA);
        simpleItem(TCRModItems.MAO_DAI);
        simpleItem(TCRModItems.PINE_CONE);
        simpleItem(TCRModItems.RED_WINE);
        simpleBlockItemBlockTexture(TCRModBlocks.DENSE_FOREST_SPIRIT_FLOWER);
        saplingItem(TCRModBlocks.DENSE_FOREST_SPIRIT_SAPLING);

        trimmedArmorItem(TCRModItems.ORICHALCUM_HELMET);
        trimmedArmorItem(TCRModItems.ORICHALCUM_CHESTPLATE);
        trimmedArmorItem(TCRModItems.ORICHALCUM_LEGGINGS);
        trimmedArmorItem(TCRModItems.ORICHALCUM_BOOTS);

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
