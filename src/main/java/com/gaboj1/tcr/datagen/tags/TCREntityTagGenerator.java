package com.gaboj1.tcr.datagen.tags;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import com.gaboj1.tcr.entity.TCREntities;
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

public class TCREntityTagGenerator extends EntityTypeTagsProvider {
    public static final TagKey<EntityType<?>> MOB_IN_DENSE_FOREST = create(TheCasketOfReveriesMod.prefix("mob_in_dense_forest"));

    public TCREntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, TheCasketOfReveriesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        super.addTags(pProvider);
        tag(MOB_IN_DENSE_FOREST).add(
                TCREntities.YGGDRASIL.get(),
                TCREntities.TREE_CLAW.get(),
                TCREntities.SMALL_TREE_MONSTER.get(),
                TCREntities.MIDDLE_TREE_MONSTER.get(),
                TCREntities.TREE_GUARDIAN.get(),
                TCREntities.UNKNOWN.get(),
                TCREntities.SPRITE.get()
        );
    }

    private static TagKey<EntityType<?>> create(ResourceLocation rl) {
        return TagKey.create(Registries.ENTITY_TYPE, rl);
    }
    @Override
    public @NotNull String getName() {
        return "The Casket Of Reveries Entity Tags";
    }

}
