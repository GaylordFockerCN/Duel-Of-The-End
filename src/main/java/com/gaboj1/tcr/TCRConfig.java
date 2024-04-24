package com.gaboj1.tcr;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //是否启用自定义地图生成时缩放
    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING = createBool("enable_scaling",false);
    //是否启用更多空洞的世界（地图更像空岛，类似天境，但是陆地较少）
    public static final ForgeConfigSpec.BooleanValue MORE_HOLE = createBool("more_hole",false);
    //更好的结构方块是否立即刷新（默认开启，开发时关闭）
    public static final ForgeConfigSpec.BooleanValue ENABLE_BETTER_STRUCTURE_BLOCK_LOAD = createBool("enable_better_structure_block_load",true);
    //游戏内对话框是否使用打字机效果（对话逐字出现）
    public static final ForgeConfigSpec.BooleanValue ENABLE_TYPEWRITER_EFFECT = createBool("enable_typewriter_effect",true);
    //打字机效果的速度（一次出现几个字）
    public static final ForgeConfigSpec.IntValue TYPEWRITER_EFFECT_SPEED = createInt("typewriter_effect_speed",1,1);

    //基础树脂的修复值
    public static final ForgeConfigSpec.IntValue REPAIR_VALUE = createInt("repair_value",1,1);

    //树灵法杖参数
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HUNGRY_CONSUME = createInt("tree_spirit_wand_hungry_consume",2,1);
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HEAL = createInt("tree_spirit_wand_heal",5,1);
    public static final ForgeConfigSpec.IntValue SPIRIT_LOG_CONSUME = createInt("spirit_log_consume",1,1);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static ForgeConfigSpec.BooleanValue createBool(String key, boolean defaultValue){
        return BUILDER
                .comment(I18n.get("config."+TheCasketOfReveriesMod.MOD_ID+"."+key))
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+"."+key)
                .define(key, defaultValue);
    }

    private static ForgeConfigSpec.IntValue createInt(String key, int defaultValue, int min){
        return BUILDER
                .comment(I18n.get("config."+TheCasketOfReveriesMod.MOD_ID+"."+key))
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+"."+key)
                .defineInRange(key,defaultValue,min, Integer.MAX_VALUE);
    }

    private static ForgeConfigSpec.IntValue createInt(String key, int defaultValue, int min, int max){
        return BUILDER
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+"."+key)
                .defineInRange(key,defaultValue,min,max);
    }

    private static boolean validateItemName(final Object obj){
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){

    }
}
