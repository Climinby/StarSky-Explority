package com.climinby.starsky_e.sound;

import com.climinby.starsky_e.StarSkyExplority;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SSESoundEvents {
    public static final SoundEvent ENTITY_LUNARIAN_SIGHT_LOCK = register("lunarian_sight_lock");
    public static final SoundEvent ENTITY_LUNARIAN_TELEPORT = register("lunarian_teleport");

    private static SoundEvent register(String id) {
        Identifier soundId = new Identifier(StarSkyExplority.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, soundId, SoundEvent.of(soundId));
    }

    public static void init() {}
}
