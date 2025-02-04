package com.p1nero.dote.datagen;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.item.DOTEItems;
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

public class DOTEItemModelProvider extends ItemModelProvider {
    public DOTEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DuelOfTheEndMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(DOTEItems.M_KEY);
        simpleItem(DOTEItems.P_KEY);
        simpleItem(DOTEItems.U_KEY);
        simpleItem(DOTEItems.ADGRAIN);
        simpleItem(DOTEItems.ADVENTURESPAR);
        simpleItem(DOTEItems.IMMORTALESSENCE);
        simpleItem(DOTEItems.STAR_CORE);
        simpleItem(DOTEItems.TIESTONEH);
        simpleItem(DOTEItems.TIESTONEC);
        simpleItem(DOTEItems.TIESTONEL);
        simpleItem(DOTEItems.TIESTONES);
        trimmedArmorItem(DOTEItems.TIESTONEH);
        trimmedArmorItem(DOTEItems.TIESTONEC);
        trimmedArmorItem(DOTEItems.TIESTONEL);
        trimmedArmorItem(DOTEItems.TIESTONES);
        simpleItem(DOTEItems.HOLY_RADIANCE_SEED);
        simpleItem(DOTEItems.WKNIGHT_INGOT);
        simpleItem(DOTEItems.WKNIGHT_HELMET);
        simpleItem(DOTEItems.WKNIGHT_CHESTPLATE);
        simpleItem(DOTEItems.WKNIGHT_LEGGINGS);
        simpleItem(DOTEItems.WKNIGHT_BOOTS);
        trimmedArmorItem(DOTEItems.WKNIGHT_HELMET);
        trimmedArmorItem(DOTEItems.WKNIGHT_CHESTPLATE);
        trimmedArmorItem(DOTEItems.WKNIGHT_LEGGINGS);
        trimmedArmorItem(DOTEItems.WKNIGHT_BOOTS);
        simpleItem(DOTEItems.CORE_OF_HELL);
        simpleItem(DOTEItems.NETHERROT_INGOT);
        simpleItem(DOTEItems.NETHERITESS);
        simpleItem(DOTEItems.WITHERC);
        simpleItem(DOTEItems.NETHERITEROT_BOOTS);
        simpleItem(DOTEItems.NETHERITEROT_CHESTPLATE);
        simpleItem(DOTEItems.NETHERITEROT_HELMET);
        simpleItem(DOTEItems.NETHERITEROT_LEGGINGS);
        trimmedArmorItem(DOTEItems.NETHERITEROT_BOOTS);
        trimmedArmorItem(DOTEItems.NETHERITEROT_CHESTPLATE);
        trimmedArmorItem(DOTEItems.NETHERITEROT_HELMET);
        trimmedArmorItem(DOTEItems.NETHERITEROT_LEGGINGS);
        simpleItem(DOTEItems.BOOK_OF_ENDING);
        simpleItem(DOTEItems.DRAGONSTEEL_INGOT);
        simpleItem(DOTEItems.GOLDEN_DRAGON_HELMET);
        simpleItem(DOTEItems.GOLDEN_DRAGON_CHESTPLATE);
        simpleItem(DOTEItems.GOLDEN_DRAGON_LEGGINGS);
        simpleItem(DOTEItems.GOLDEN_DRAGON_BOOTS);

        eggItem(DOTEItems.DOTE_ZOMBIE_SPAWN_EGG);
        eggItem(DOTEItems.DOTE_ZOMBIE2_SPAWN_EGG);
        eggItem(DOTEItems.DOTE_PIGLIN_SPAWN_EGG);
    }

    //Thank you kaupenjoe & El_Redstoniano!
    // Shoutout to El_Redstoniano for making this
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
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = DuelOfTheEndMod.MOD_ID; // Change this to your mod x

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {

                float trimValue = value;

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
                new ResourceLocation(DuelOfTheEndMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DuelOfTheEndMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(DuelOfTheEndMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(DuelOfTheEndMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(DuelOfTheEndMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(DuelOfTheEndMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(DuelOfTheEndMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DuelOfTheEndMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DuelOfTheEndMod.MOD_ID,"block/" + item.getId().getPath()));
    }
}
