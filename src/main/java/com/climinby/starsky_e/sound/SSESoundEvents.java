package com.climinby.starsky_e.sound;

import com.climinby.starsky_e.StarSkyExplority;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SSESoundEvents {
    public static final SoundEvent ENTITY_TSUKI_NO_TAMI_SIGHT_LOCK = register("tsuki_no_tami_sight_lock");

    private static SoundEvent register(String id) {
        Identifier soundId = new Identifier(StarSkyExplority.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, soundId, SoundEvent.of(soundId));
    }

    public static void init() {}
}
