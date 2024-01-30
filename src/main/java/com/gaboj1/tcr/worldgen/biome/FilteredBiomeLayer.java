package com.gaboj1.tcr.worldgen.biome;

import com.gaboj1.tcr.worldgen.biome.transformer.AreaTransformer2;
import com.gaboj1.tcr.worldgen.biome.transformer.DimensionOffset0Transformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.function.LongFunction;

public record FilteredBiomeLayer(ResourceKey<Biome> biomeFirst) implements AreaTransformer2, DimensionOffset0Transformer {
    @Override
    public ResourceKey<Biome> applyPixel(Context context, Area area1, Area area2, int x, int z) {
        ResourceKey<Biome> riverInputs = area2.getBiome(this.getParentX(x), this.getParentY(z));

        if (riverInputs == this.biomeFirst) {
            return riverInputs;
        } else {
            return area1.getBiome(this.getParentX(x), this.getParentY(z));
        }
    }

    public static final class Factory implements BiomeLayerFactory {
        public static final Codec<Factory> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
                ResourceKey.codec(Registries.BIOME).fieldOf("keep_biome").forGetter(Factory::filterKeep),
                BiomeLayerStack.HOLDER_CODEC.fieldOf("filtered_layer").forGetter(Factory::filteredLayer),
                BiomeLayerStack.HOLDER_CODEC.fieldOf("fallback_layer").forGetter(Factory::fallbackLayer)
        ).apply(inst, Factory::new));

        private final long salt;
        private final ResourceKey<Biome> filterKeep;
        private final Holder<BiomeLayerFactory> filteredLayer;
        private final Holder<BiomeLayerFactory> fallbackLayer;

        private final FilteredBiomeLayer filteredBiomeLayer;

        public Factory(long salt, ResourceKey<Biome> filterKeep, Holder<BiomeLayerFactory> filteredLayer, Holder<BiomeLayerFactory> fallbackLayer) {
            this.salt = salt;
            this.filterKeep = filterKeep;
            this.filteredLayer = filteredLayer;
            this.fallbackLayer = fallbackLayer;

            this.filteredBiomeLayer = new FilteredBiomeLayer(this.filterKeep);
        }

        @Override
        public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
            return this.filteredBiomeLayer.run(contextFactory.apply(this.salt), this.fallbackLayer.get().build(contextFactory), this.filteredLayer.get().build(contextFactory));
        }

        @Override
        public BiomeLayerType getType() {
            return BiomeLayerTypes.FILTERED.get();
        }

        public long salt() {
            return this.salt;
        }

        public ResourceKey<Biome> filterKeep() {
            return this.filterKeep;
        }

        public Holder<BiomeLayerFactory> fallbackLayer() {
            return this.fallbackLayer;
        }

        public Holder<BiomeLayerFactory> filteredLayer() {
            return this.filteredLayer;
        }
    }
}
