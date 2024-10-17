package com.climinby.starsky_e.registry;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.planet.Galaxy;
import com.climinby.starsky_e.planet.Planet;
import com.climinby.starsky_e.recipe.AnalysisRecipe;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class SSERegistryKeys {
    public static final RegistryKey<Registry<Planet>> PLANET = of("planet");
    public static final RegistryKey<Registry<Galaxy>> GALAXY = of("galaxy");
    public static final RegistryKey<Registry<AnalysisRecipe>> ANALYSIS_RECIPE = of("analysis_recipe");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(StarSkyExplority.MOD_ID, id));
    }

    public static void initialize() {}
}
