package com.gaboj1.tcr;

import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.common.ForgeConfigSpec;

public class TCRConfig {
    // 是否启用自定义地图生成时缩放
    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING;
    // 是否启用更多空洞的世界（地图更像空岛，类似天境，但是陆地较少）
    public static final ForgeConfigSpec.BooleanValue MORE_HOLE;
    // 更好的结构方块是否立即刷新（默认开启，开发时关闭）
    public static final ForgeConfigSpec.BooleanValue ENABLE_BETTER_STRUCTURE_BLOCK_LOAD;
    // 游戏内对话框是否使用打字机效果（对话逐字出现）
    public static final ForgeConfigSpec.BooleanValue ENABLE_TYPEWRITER_EFFECT;
    // 打字机效果的速度（一次出现几个字）
    public static final ForgeConfigSpec.IntValue TYPEWRITER_EFFECT_SPEED;
    // 打字机效果间隔（几个tick更新一次）
    public static final ForgeConfigSpec.IntValue TYPEWRITER_EFFECT_INTERVAL;
    // 基础树脂的修复值
    public static final ForgeConfigSpec.IntValue REPAIR_VALUE;
    // 树灵法杖参数
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HUNGRY_CONSUME;
    // 树灵法杖恢复值
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HEAL;
    // 树灵木消耗值
    public static final ForgeConfigSpec.IntValue SPIRIT_LOG_CONSUME;
    // 配置规格
    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Game Setting");
        ENABLE_SCALING = createBool(builder, "enable_scaling", false);
        MORE_HOLE = createBool(builder, "more_hole", false);
        ENABLE_BETTER_STRUCTURE_BLOCK_LOAD = createBool(builder, "enable_better_structure_block_load", true);
        ENABLE_TYPEWRITER_EFFECT = createBool(builder, "enable_typewriter_effect", true);
        TYPEWRITER_EFFECT_SPEED = createInt(builder, "typewriter_effect_speed", 1, 1);
        TYPEWRITER_EFFECT_INTERVAL = createInt(builder, "typewriter_effect_interval", 2, 1);
        builder.pop();

        builder.push("Attribute Value");
        REPAIR_VALUE = createInt(builder, "repair_value", 1, 1);
        TREE_SPIRIT_WAND_HUNGRY_CONSUME = createInt(builder, "tree_spirit_wand_hungry_consume", 2, 1);
        TREE_SPIRIT_WAND_HEAL = createInt(builder, "tree_spirit_wand_heal", 10, 1);
        SPIRIT_LOG_CONSUME = createInt(builder, "spirit_log_consume", 1, 1);
        builder.pop();

        SPEC = builder.build();
    }

    private static ForgeConfigSpec.BooleanValue createBool(ForgeConfigSpec.Builder builder, String key, boolean defaultValue) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .define(key, defaultValue);
    }

    private static ForgeConfigSpec.IntValue createInt(ForgeConfigSpec.Builder builder, String key, int defaultValue, int min) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .defineInRange(key, defaultValue, min, Integer.MAX_VALUE);
    }
}
