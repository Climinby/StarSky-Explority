package com.climinby.starsky_e;

import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.block.SSEInkTypes;
import com.climinby.starsky_e.entity.SSEBlockEntities;
import com.climinby.starsky_e.item.SSEItemGroups;
import com.climinby.starsky_e.item.SSEItems;
import com.climinby.starsky_e.planet.Galaxies;
import com.climinby.starsky_e.planet.Planets;
import com.climinby.starsky_e.recipe.AnalysisRecipes;
import com.climinby.starsky_e.registry.SSERegistries;
import com.climinby.starsky_e.registry.SSERegistryKeys;
import com.climinby.starsky_e.screen.SSEScreenHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarSkyExplority implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "starsky_explority";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		SSERegistryKeys.initialize();
		SSERegistries.initialize();

		SSEItems.initialize();
		SSEBlocks.initialize();
		SSEItemGroups.initialize();
		SSEBlockEntities.initialize();
		SSEScreenHandlers.initialize();
		SSEInkTypes.initialize();
		Galaxies.initialize();
		Planets.initialize();
		AnalysisRecipes.initialize();

		LOGGER.info("Hello Fabric world!");
	}
}