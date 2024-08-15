package com.gaboj1.tcr;

import net.minecraftforge.common.ForgeConfigSpec;

public class TCRConfig {
    // 是否启用自定义地图生成时缩放
    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING;
    // 是否启用更多空洞的世界（地图更像空岛，类似天境，但是陆地较少）
    public static final ForgeConfigSpec.BooleanValue MORE_HOLE;
    // Boss生成方块是否有效（默认开启，开发时关闭）
    public static final ForgeConfigSpec.BooleanValue ENABLE_BOSS_SPAWN_BLOCK_LOAD;
    // 更好的结构方块是否立即刷新（默认开启，开发时关闭）
    public static final ForgeConfigSpec.BooleanValue ENABLE_BETTER_STRUCTURE_BLOCK_LOAD;
    // 游戏内对话框是否使用打字机效果（对话逐字出现）
    public static final ForgeConfigSpec.BooleanValue ENABLE_TYPEWRITER_EFFECT;
    // 打字机效果的速度（一次出现几个字）
    public static final ForgeConfigSpec.IntValue TYPEWRITER_EFFECT_SPEED;
    // 打字机效果间隔（几个tick更新一次）
    public static final ForgeConfigSpec.IntValue TYPEWRITER_EFFECT_INTERVAL;
    //武器随着世界等级提升的提升倍率
    public static final ForgeConfigSpec.DoubleValue WEAPON_MULTIPLIER_WHEN_WORLD_LEVEL_UP;
    // 基础树脂的修复值
    public static final ForgeConfigSpec.IntValue REPAIR_VALUE;
    // 树灵法杖参数
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HUNGRY_CONSUME;
    // 树灵法杖恢复值
    public static final ForgeConfigSpec.IntValue TREE_SPIRIT_WAND_HEAL;
    // 树灵木消耗值
    public static final ForgeConfigSpec.IntValue SPIRIT_LOG_CONSUME;
    //怪物随着世界等级提升的提升倍率
    public static final ForgeConfigSpec.DoubleValue MOB_MULTIPLIER_WHEN_WORLD_LEVEL_UP;
    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Game Setting");
        ENABLE_SCALING = createBool(builder, "enable_scaling", false);
        MORE_HOLE = createBool(builder, "more_hole", false);
        ENABLE_BETTER_STRUCTURE_BLOCK_LOAD = createBool(builder, "enable_better_structure_block_load", true);
        ENABLE_BOSS_SPAWN_BLOCK_LOAD = createBool(builder, "enable_boss_spawn_block_load", true);
        ENABLE_TYPEWRITER_EFFECT = createBool(builder, "enable_typewriter_effect", true);
        TYPEWRITER_EFFECT_SPEED = createInt(builder, "typewriter_effect_speed", 1, 1);
        TYPEWRITER_EFFECT_INTERVAL = createInt(builder, "typewriter_effect_interval", 2, 1);
        builder.pop();

        builder.push("Attribute Value");
        REPAIR_VALUE = createInt(builder, "repair_value", 1, 1);
        TREE_SPIRIT_WAND_HUNGRY_CONSUME = createInt(builder, "tree_spirit_wand_hungry_consume", 2, 1);
        TREE_SPIRIT_WAND_HEAL = createInt(builder, "tree_spirit_wand_heal", 10, 1);
        SPIRIT_LOG_CONSUME = createInt(builder, "spirit_log_consume", 1, 1);
        WEAPON_MULTIPLIER_WHEN_WORLD_LEVEL_UP = createDouble(builder, "weapon_multiplier_when_world_level_up", 1.2, 1.0);
        builder.pop();
        builder.push("Monster Setting");
        MOB_MULTIPLIER_WHEN_WORLD_LEVEL_UP = createDouble(builder, "mob_multiplier_when_world_level_up", 1.2, 1.0);
        builder.pop();

        SPEC = builder.build();
    }

    private static ForgeConfigSpec.BooleanValue createBool(ForgeConfigSpec.Builder builder, String key, boolean defaultValue, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .define(key, defaultValue);
    }

    private static ForgeConfigSpec.IntValue createInt(ForgeConfigSpec.Builder builder, String key, int defaultValue, int min, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .defineInRange(key, defaultValue, min, Integer.MAX_VALUE);
    }

    private static ForgeConfigSpec.DoubleValue createDouble(ForgeConfigSpec.Builder builder, String key, double defaultValue, double min, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .comment(comment)
                .defineInRange(key, defaultValue, min, Double.MAX_VALUE);
    }

}
