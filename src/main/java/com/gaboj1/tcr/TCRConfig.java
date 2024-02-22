package com.gaboj1.tcr;

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

    //阵营判断
    public static final ForgeConfigSpec.BooleanValue IS_WHITE = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".is_white")
            .define("is_white", true);


    //是否启用自定义地图生成时缩放
    public static final ForgeConfigSpec.BooleanValue ENABLE_SCALING = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".enable_scaling")
            .define("enable_scaling", false);

    //基础树脂的修复值
    public static final ForgeConfigSpec.IntValue REPAIR_VALUE = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".repair_value")
            .defineInRange("repair_value",1,1, Integer.MAX_VALUE);

    //树灵法杖参数
    //是的我知道小写很不规范，但是复制粘贴真的很香。
    public static final ForgeConfigSpec.IntValue tree_spirit_wand_hungry_consume = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".tree_spirit_wand_hungry_consume")
            .defineInRange("tree_spirit_wand_hungry_consume",2,1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue tree_spirit_wand_heal = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".tree_spirit_wand_heal")
            .defineInRange("tree_spirit_wand_heal",2,1, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue spirit_log_consume = BUILDER
            .translation("config."+TheCasketOfReveriesMod.MOD_ID+".spirit_log_consume")
            .defineInRange("spirit_log_consume",1,1, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj){
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event){

    }
}
