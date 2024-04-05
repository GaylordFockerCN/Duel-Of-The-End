package com.gaboj1.tcr.datagen.lang;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import java.util.function.Supplier;

public abstract class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, TheCasketOfReveriesMod.MOD_ID, "en_us");
    }

    public void addBiome(ResourceKey<Biome> biome, String name) {
        this.add("biome."+TheCasketOfReveriesMod.MOD_ID+"." + biome.location().getPath(), name);
    }

    public void addSapling(String woodPrefix, String saplingName) {
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + woodPrefix + "_sapling", saplingName);
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + "potted_" + woodPrefix + "_sapling", "Potted " + saplingName);
    }

    public void createLogs(String woodPrefix, String woodName) {
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_log", woodName + " Log");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_wood", woodName + " Wood");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".stripped_" + woodPrefix + "_log", "Stripped " + woodName + " Log");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".stripped_" + woodPrefix + "_wood", "Stripped " + woodName + " Wood");
        this.createHollowLogs(woodPrefix, woodName, false);
    }

    public void createHollowLogs(String woodPrefix, String woodName, boolean stem) {
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".hollow_" + woodPrefix + (stem ? "_stem" : "_log") + "_horizontal", "Hollow " + woodName + (stem ? " Stem" : " Log"));
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".hollow_" + woodPrefix + (stem ? "_stem" : "_log") + "_vertical", "Hollow " + woodName + (stem ? " Stem" : " Log"));
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".hollow_" + woodPrefix + (stem ? "_stem" : "_log") + "_climbable", "Hollow " + woodName + (stem ? " Stem" : " Log"));
    }

    public void createWoodSet(String woodPrefix, String woodName) {
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_planks", woodName + " Planks");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_slab", woodName + " Slab");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_stairs", woodName + " Stairs");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_button", woodName + " Button");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_fence", woodName + " Fence");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_fence_gate", woodName + " Fence Gate");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_pressure_plate", woodName + " Pressure Plate");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_trapdoor", woodName + " Trapdoor");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_door", woodName + " Door");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_sign", woodName + " Sign");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_wall_sign", woodName + " Wall Sign");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_banister", woodName + " Banister");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_chest", woodName + " Chest");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_boat", woodName + " Boat");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_chest_boat", woodName + " Chest Boat");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_hanging_sign", woodName + " Hanging Sign");
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + woodPrefix + "_wall_hanging_sign", woodName + " Wall Hanging Sign");
    }

    public void addBannerPattern(String patternPrefix, String patternName) {
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + patternPrefix + "_banner_pattern", "Banner Pattern");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + patternPrefix + "_banner_pattern.desc", patternName);
        for (DyeColor color : DyeColor.values()) {
            this.add("block.minecraft.banner."+TheCasketOfReveriesMod.MOD_ID+"." + patternPrefix + "." + color.getName(), WordUtils.capitalize(color.getName().replace('_', ' ')) + " " + patternName);
        }
    }

    public void addStoneVariants(String blockKey, String blockName) {
        this.add("block."+TheCasketOfReveriesMod.MOD_ID+"." + blockKey, blockName);
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".cracked_" + blockKey, "Cracked " + blockName);
        this.add("block."+TheCasketOfReveriesMod.MOD_ID + ".mossy_" + blockKey, "Mossy " + blockName);
    }

    public void addArmor(String itemKey, String item) {
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_helmet", item + " Helmet");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_chestplate", item + " Chestplate");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_leggings", item + " Leggings");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_boots", item + " Boots");
    }

    public void addTools(String itemKey, String item) {
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_sword", item + " Sword");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_pickaxe", item + " Pickaxe");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_axe", item + " Axe");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_shovel", item + " Shovel");
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + itemKey + "_hoe", item + " Hoe");
    }

    public void addMusicDisc(Supplier<Item> disc, String description) {
        this.addItem(disc, "Music Disc");
        this.add(disc.get().getDescriptionId() + ".desc", description);
    }

    public void addStructure(ResourceKey<Structure> biome, String name) {
        this.add("structure."+TheCasketOfReveriesMod.MOD_ID+"." + biome.location().getPath(), name);
    }

    public void addAdvancement(String key, String title, String desc) {
        this.add("advancement."+TheCasketOfReveriesMod.MOD_ID+"." + key, title);
        this.add("advancement."+TheCasketOfReveriesMod.MOD_ID+"." + key + ".desc", desc);
    }

    public void addEnchantment(String key, String title, String desc) {
        this.add("enchantment."+TheCasketOfReveriesMod.MOD_ID+"." + key, title);
        this.add("enchantment."+TheCasketOfReveriesMod.MOD_ID+"." + key + ".desc", desc);
    }

    public void addEntityAndEgg(RegistryObject<? extends EntityType<?>> entity, String name) {
        this.addEntityType(entity, name);
        this.add("item."+TheCasketOfReveriesMod.MOD_ID+"." + entity.getId().getPath() + "_spawn_egg", name + "刷怪蛋");
    }

    public void addVillagerChat(RegistryObject<? extends EntityType<?>> villager, int index, boolean isFWord, String text) {
        this.add(villager.get()+(isFWord?".fuck_chat":".chat")+index, text);
    }

    public void addDialog(RegistryObject<? extends EntityType<?>> entity, int i, String text) {
        this.add(entity.get()+".dialog" + i, text);
    }
    public void addDialogChoice(RegistryObject<? extends EntityType<?>> entity, int i, String text) {
        this.add(entity.get()+".choice" + i, text);
    }
    public void addDialogChoice(RegistryObject<? extends EntityType<?>> entity, String choice, String text) {
        this.add(entity.get()+".choice." + choice, text);
    }

    public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
        String[] splitSoundName  = sound.getId().getPath().split("\\.", 3);
        this.add("subtitles."+TheCasketOfReveriesMod.MOD_ID+"." + splitSoundName[0] + "." + splitSoundName[2], name);
    }

    public void addDeathMessage(String key, String name) {
        this.add("death.attack."+TheCasketOfReveriesMod.MOD_ID+"." + key, name);
    }

    public void addStat(String key, String name) {
        this.add("stat."+TheCasketOfReveriesMod.MOD_ID+"." + key, name);
    }

    public void addMessage(String key, String name) {
        this.add("misc."+TheCasketOfReveriesMod.MOD_ID+"." + key, name);
    }

    public void addCommand(String key, String name) {
        this.add("commands.tcrfeature." + key, name);
    }

    public void addTrim(String key, String name) {
        this.add("trim_material."+TheCasketOfReveriesMod.MOD_ID+"." + key, name + " Material");
    }

    public void addBookAndContents(String bookKey, String bookTitle, String... pages) {
        this.add(TheCasketOfReveriesMod.MOD_ID+".book.author." + bookKey, "无名氏");
        this.add(TheCasketOfReveriesMod.MOD_ID+".book." + bookKey, bookTitle);
        int pageCount = 0;
        for (String page : pages) {
            pageCount++;
            this.add(TheCasketOfReveriesMod.MOD_ID+".book." + bookKey + "." + pageCount, page);
        }
    }

    public void addBookAndAuthorAndContents(String bookKey, String author, String bookTitle, String... pages) {
        this.add(TheCasketOfReveriesMod.MOD_ID+".book.author." + bookKey, author);
        this.add(TheCasketOfReveriesMod.MOD_ID+".book." + bookKey, bookTitle);
        int pageCount = 0;
        for (String page : pages) {
            pageCount++;
            this.add(TheCasketOfReveriesMod.MOD_ID+".book." + bookKey + "." + pageCount, page);
        }
    }

    public void addScreenMessage(String key, String name) {
        this.add("gui."+TheCasketOfReveriesMod.MOD_ID+"." + key, name);
    }

    public void addConfig(String key, String name) {
        this.add("config."+TheCasketOfReveriesMod.MOD_ID+"." + key, name);
    }

    public void addDrinkName(Item item, String name) {
        this.add(item.getDescriptionId()+".effect.empty", name);
    }
    public void addDrinkName(Item item, String effect,String name) {
        this.add(item.getDescriptionId()+".effect."+effect, name);
    }


    public void addItemInfo(Item item, String key, String name) {
        this.add(item.getDescriptionId()+"." + key, name);
    }

    public void addItemUsageInfo(Item item, String name) {
        this.add(item.getDescriptionId()+".usage", name);
    }
    public void addItemUsageInfo(Item item, String name, int index) {
        this.add(item.getDescriptionId()+".usage"+index, name);
    }

}
