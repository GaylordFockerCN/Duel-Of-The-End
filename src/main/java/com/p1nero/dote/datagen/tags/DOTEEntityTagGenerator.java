package com.p1nero.dote.datagen.tags;

import com.p1nero.dote.DuelOfTheEndMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DOTEEntityTagGenerator extends EntityTypeTagsProvider {
    public static final TagKey<EntityType<?>> MOB_IN_DENSE_FOREST = create(DuelOfTheEndMod.prefix("mob_in_dense_forest"));

    public DOTEEntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, DuelOfTheEndMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        super.addTags(pProvider);
    }

    private static TagKey<EntityType<?>> create(ResourceLocation rl) {
        return TagKey.create(Registries.ENTITY_TYPE, rl);
    }
    @Override
    public @NotNull String getName() {
        return "Duel Of The End Entity Tags";
    }

}
