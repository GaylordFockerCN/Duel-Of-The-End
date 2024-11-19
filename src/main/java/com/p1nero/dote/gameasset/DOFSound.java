//package com.p1nero.dote.gameasset;
//
//import com.p1nero.dote.DuelOfTheEndMod;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class DOFSound {
//    private static final Logger LOGGER = LogManager.getLogger();
//
//    public static final DeferredRegister<SoundEvent> SOUNDS =
//            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DuelOfTheEndMod.MOD_ID);
//
//    public static final RegistryObject<SoundEvent> YAMATO_STEP = registerSound("sfx.yamato_step");
//    public static final RegistryObject<SoundEvent> YAMATO_IN = registerSound("yamato.yamato_in");
//    public static final RegistryObject<SoundEvent> FORESIGHT = registerSound("sfx.foresight");
//    public static final RegistryObject<SoundEvent> EXECUTE = registerSound("sfx.execute");
//    public static final RegistryObject<SoundEvent> DAMAGE = registerSound("sfx.damage");
//    public static final RegistryObject<SoundEvent> DODGE = registerSound("sfx.dodge");
//    public static final RegistryObject<SoundEvent> SKILL = registerSound("sfx.skills");
//    public static final RegistryObject<SoundEvent> HURT = registerSound("sfx.hurt");
//
//    public DOFSound() {
//    }
//
//    private static RegistryObject<SoundEvent> registerSound(String name) {
//        LOGGER.debug("Registering corrupt sound: " + name);
//        return SOUNDS.register(name, () -> {
//            LOGGER.debug("Creating SoundEvent for: " + name);
//            return SoundEvent.createVariableRangeEvent(new ResourceLocation(DuelOfTheEndMod.MOD_ID, name));
//        });
//    }
//
//    public static void register(IEventBus eventBus) {
//        LOGGER.debug("Registering sounds to event bus");
//        SOUNDS.register(eventBus);
//    }
//}
