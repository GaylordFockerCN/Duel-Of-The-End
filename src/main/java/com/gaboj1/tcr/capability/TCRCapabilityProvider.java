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

    private TCRPlayer tcrPlayer = null;
    
    private final LazyOptional<TCRPlayer> optional = LazyOptional.of(this::createTCRPlayer);

    private TCRPlayer createTCRPlayer() {
        if(this.tcrPlayer == null){
            this.tcrPlayer = new TCRPlayer();
        }

        return this.tcrPlayer;
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
        createTCRPlayer().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createTCRPlayer().loadNBTData(tag);
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
            event.getOriginal().reviveCaps();//。。。怎么之前没加这个也可以，现在没加不行
            if(event.isWasDeath()) {
                event.getOriginal().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(oldStore -> {
                    event.getEntity().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(newStore -> {
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
