package com.gaboj1.tcr.capability;

import com.gaboj1.tcr.TheCasketOfReveriesMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TCRPlayer> TCR_PLAYER = CapabilityManager.get(new CapabilityToken<>() {});

    private TCRPlayer TCRPlayer = null;
    
    private final LazyOptional<TCRPlayer> optional = LazyOptional.of(this::createSSPlayer);

    private TCRPlayer createSSPlayer() {
        if(this.TCRPlayer == null){
            this.TCRPlayer = new TCRPlayer();
        }

        return this.TCRPlayer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == TCR_PLAYER){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createSSPlayer().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createSSPlayer().loadNBTData(tag);
    }

    @Mod.EventBusSubscriber(modid = TheCasketOfReveriesMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
               if(!event.getObject().getCapability(TCRCapabilityProvider.TCR_PLAYER).isPresent()){
                   event.addCapability(new ResourceLocation(TheCasketOfReveriesMod.MOD_ID, "tcr_player"), new TCRCapabilityProvider());
               }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if(event.isWasDeath()) {
                event.getOriginal().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(TCRPlayer.class);
        }

    }


}
