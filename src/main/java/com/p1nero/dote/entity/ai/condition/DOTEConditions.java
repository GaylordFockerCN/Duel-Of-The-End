package com.p1nero.dote.entity.ai.condition;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.data.conditions.Condition;

import java.util.function.Supplier;

public class DOTEConditions {
    public static final DeferredRegister<Supplier<Condition<?>>> CONDITIONS = DeferredRegister.create(new ResourceLocation("epicfight", "conditions"), DuelOfTheEndMod.MOD_ID);
    private static final RegistryObject<Supplier<Condition<?>>> WORLD_LEVEL_CHECK_CONDITION = CONDITIONS.register((new ResourceLocation(DuelOfTheEndMod.MOD_ID, "world_level_check")).getPath(), () -> WorldLevelCheckCondition::new);

}
