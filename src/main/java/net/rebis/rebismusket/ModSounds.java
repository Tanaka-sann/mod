package net.rebis.rebismusket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rebis.rebismusket.RebisMusket;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RebisMusket.MOD_ID);

    public static final RegistryObject<SoundEvent> RATION_USE = SOUND_EVENTS.register("ration_use",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RebisMusket.MOD_ID, "ration_use")));

    public static final RegistryObject<SoundEvent> RATION_USE_ALL = SOUND_EVENTS.register("ration_use_all",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RebisMusket.MOD_ID, "ration_use_all")));

    public static void register(IEventBus eventBus) {
            SOUND_EVENTS.register(eventBus);
    }
}

