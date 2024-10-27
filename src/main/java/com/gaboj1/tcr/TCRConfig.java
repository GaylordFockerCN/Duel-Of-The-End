package com.gaboj1.tcr;

import net.minecraftforge.common.ForgeConfigSpec;

public class TCRConfig {
    public static final ForgeConfigSpec.IntValue MIN_CHUNK_BETWEEN_STRUCTURE;
    public static final ForgeConfigSpec.BooleanValue FAST_MOD;
    public static final ForgeConfigSpec.BooleanValue NO_PLOT_MODE;
    // 是否启用自定义地图生成时缩放
    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING;
    // 是否启用更多空洞的世界（地图更像空岛，类似天境，但是陆地较少）
//    public static final ForgeConfigSpec.BooleanValue MORE_HOLE;
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
    //Boss是否显示血条
    public static final ForgeConfigSpec.BooleanValue SHOW_BOSS_HEALTH;
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
    public static final ForgeConfigSpec.DoubleValue ELITE_MOB_HEALTH_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue ELITE_MOB_DAMAGE_MULTIPLIER;
    public static final ForgeConfigSpec.BooleanValue BOSS_HEALTH_AND_LOOT_MULTIPLE;
    public static final ForgeConfigSpec.IntValue BOSS_HEALTH_AND_LOOT_MULTIPLE_MAX;
    public static final ForgeConfigSpec.DoubleValue TEST_X, TEST_Y, TEST_Z;
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Game Setting");
        MIN_CHUNK_BETWEEN_STRUCTURE = createInt(builder, "min_chunk_between_structure", 3, 0, "小建筑和主建筑的最小区块间距");
        FAST_MOD = createBool(builder, "fast_mod", false, "快速模式，开启后将直接获得可以进入维度的装备");
        NO_PLOT_MODE = createBool(builder, "no_plot_mode", false, "无剧情模式，设为true后将直接挑战boss而没有主线对话。");//TODO 未实现
        ENABLE_SCALING = createBool(builder, "enable_scaling", false, "启用后将对图生图的图片进行缩放，强制地图大小。");
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
        ELITE_MOB_HEALTH_MULTIPLIER = createDouble(builder, "elite_mob_health_multiplier", 3.0, 1.0, "精英怪的血量加倍");
        ELITE_MOB_DAMAGE_MULTIPLIER = createDouble(builder, "elite_mob_damage_multiplier", 1.5, 1.0, "精英怪的伤害加倍");
        BOSS_HEALTH_AND_LOOT_MULTIPLE = createBool(builder, "boss_health_and_loot_multiple", true, "是否在多人模式下boss血量增加，且重要战利品也增加");
        BOSS_HEALTH_AND_LOOT_MULTIPLE_MAX = createInt(builder, "boss_health_and_loot_multiple_max", 5, 1, "多人模式下boss血量增加的最大倍数");
        builder.pop();

        builder.push("Test");
        TEST_X = createDouble(builder, "test_x", 1.0, -Double.MIN_VALUE, "测试用x， 方便实时调某个数值");
        TEST_Y = createDouble(builder, "test_y", 1.0, -Double.MIN_VALUE, "测试用y， 方便实时调某个数值");
        TEST_Z = createDouble(builder, "test_z", 1.0, -Double.MIN_VALUE, "测试用z， 方便实时调某个数值");
        ENABLE_BETTER_STRUCTURE_BLOCK_LOAD = createBool(builder, "enable_better_structure_block_load", true, "结构方块是否立即加载（开发人员用）");
        ENABLE_BOSS_SPAWN_BLOCK_LOAD = createBool(builder, "enable_boss_spawn_block_load", true, "boss生成方块是否立即加载（开发人员用）");
        builder.pop();
        SPEC = builder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        ENABLE_TYPEWRITER_EFFECT = createBool(clientBuilder, "enable_typewriter_effect", true, "剧情对话是否使用打字机效果");
        TYPEWRITER_EFFECT_SPEED = createInt(clientBuilder, "typewriter_effect_speed", 2, 1, "打字机效果打字速度");
        TYPEWRITER_EFFECT_INTERVAL = createInt(clientBuilder, "typewriter_effect_interval", 2, 1, "打字机效果打字间隔");
        SHOW_BOSS_HEALTH = createBool(clientBuilder, "show_boss_health", true, "是否显示Boss血量");

        CLIENT_SPEC = clientBuilder.build();
    }

    private static ForgeConfigSpec.BooleanValue createBool(ForgeConfigSpec.Builder builder, String key, boolean defaultValue, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .comment(comment)
                .define(key, defaultValue);
    }

    private static ForgeConfigSpec.IntValue createInt(ForgeConfigSpec.Builder builder, String key, int defaultValue, int min, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .comment(comment)
                .defineInRange(key, defaultValue, min, Integer.MAX_VALUE);
    }

    private static ForgeConfigSpec.DoubleValue createDouble(ForgeConfigSpec.Builder builder, String key, double defaultValue, double min, String... comment) {
        return builder
                .translation("config."+TheCasketOfReveriesMod.MOD_ID+".common."+key)
                .comment(comment)
                .defineInRange(key, defaultValue, min, Double.MAX_VALUE);
    }

}
