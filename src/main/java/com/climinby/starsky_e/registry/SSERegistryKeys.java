package com.climinby.starsky_e.registry;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.registry.ink.InkType;
import com.climinby.starsky_e.registry.sample.Sample;
import com.climinby.starsky_e.registry.material.MaterialType;
import com.climinby.starsky_e.registry.planet.Galaxy;
import com.climinby.starsky_e.registry.planet.Planet;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class SSERegistryKeys {
    public static final RegistryKey<Registry<Planet>> PLANET = of("planet");
    public static final RegistryKey<Registry<Galaxy>> GALAXY = of("galaxy");
    public static final RegistryKey<Registry<Sample>> SAMPLE_TYPE = of("sample_type");
    public static final RegistryKey<Registry<MaterialType>> MATERIAL_TYPE = of("material_type");
    public static final RegistryKey<Registry<InkType>> INK_TYPE = of("ink_type");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(StarSkyExplority.MOD_ID, id));
    }

    public static void initialize() {}
}
