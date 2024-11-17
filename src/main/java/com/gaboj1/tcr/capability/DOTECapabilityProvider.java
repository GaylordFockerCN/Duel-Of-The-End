package com.gaboj1.tcr.capability;

import com.gaboj1.tcr.DuelOfTheEndMod;
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

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTECapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DOTEPlayer> DOTE_PLAYER = CapabilityManager.get(new CapabilityToken<>() {});

    private DOTEPlayer DOTEPlayer = null;
    
    private final LazyOptional<DOTEPlayer> optional = LazyOptional.of(this::createTCRPlayer);

    private DOTEPlayer createTCRPlayer() {
        if(this.DOTEPlayer == null){
            this.DOTEPlayer = new DOTEPlayer();
        }

        return this.DOTEPlayer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == DOTE_PLAYER){
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

    @Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
               if(!event.getObject().getCapability(DOTECapabilityProvider.DOTE_PLAYER).isPresent()){
                   event.addCapability(new ResourceLocation(DuelOfTheEndMod.MOD_ID, "tcr_player"), new DOTECapabilityProvider());
               }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            event.getOriginal().reviveCaps();//。。。怎么之前没加这个也可以，现在没加不行
            if(event.isWasDeath()) {
                event.getOriginal().getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DOTECapabilityProvider.DOTE_PLAYER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(DOTEPlayer.class);
        }

    }


}
