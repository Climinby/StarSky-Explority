package com.climinby.starsky_e.registry;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.item.Sample;
import com.climinby.starsky_e.material.MaterialType;
import com.climinby.starsky_e.planet.Galaxy;
import com.climinby.starsky_e.planet.Planet;
import com.climinby.starsky_e.recipe.AnalysisRecipe;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class SSERegistries {
    public static final Registry<Registry<?>> REGISTRIES = new SimpleRegistry<>(RegistryKey.ofRegistry(new Identifier(StarSkyExplority.MOD_ID, "registries")),
            Lifecycle.experimental(),
            false);
    public static final Registry<Planet> PLANET = register(SSERegistryKeys.PLANET, Lifecycle.experimental(), false);
    public static final Registry<Galaxy> GALAXY = register(SSERegistryKeys.GALAXY, Lifecycle.experimental(), false);
    //public static final Registry<AnalysisRecipe> ANALYSIS_RECIPE = register(SSERegistryKeys.ANALYSIS_RECIPE, Lifecycle.experimental(), false);
    public static final Registry<Sample> SAMPLE_TYPE = register(SSERegistryKeys.SAMPLE_TYPE, Lifecycle.experimental(), false);
    public static final Registry<MaterialType> MATERIAL_TYPE = register(SSERegistryKeys.MATERIAL_TYPE, Lifecycle.experimental(), false);

    private static <T> Registry<T> register(RegistryKey<? extends Registry<T>> key, Lifecycle lifecycle, boolean intrusive) {
        Identifier registryID = key.getValue();
        SimpleRegistry<T> simpleRegistry = new SimpleRegistry<>(key, lifecycle, intrusive);
        return Registry.register(REGISTRIES, registryID, simpleRegistry);
    }

    public static void initialize() {}
}
