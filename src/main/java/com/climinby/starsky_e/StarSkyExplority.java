package com.climinby.starsky_e;

import com.climinby.starsky_e.attribute.entity.SSEEntityDefaultAttributes;
import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.registry.ink.InkTypes;
import com.climinby.starsky_e.command.SSECommands;
import com.climinby.starsky_e.entity.SSEEntities;
import com.climinby.starsky_e.entity.effect.SSEStatusEffects;
import com.climinby.starsky_e.item.SSEItemGroups;
import com.climinby.starsky_e.item.SSEItems;
import com.climinby.starsky_e.registry.sample.SampleTypes;
import com.climinby.starsky_e.registry.material.MaterialTypes;
import com.climinby.starsky_e.registry.planet.Galaxies;
import com.climinby.starsky_e.registry.planet.Planets;
import com.climinby.starsky_e.recipe.SSERecipeType;
import com.climinby.starsky_e.recipe.SSERecipiSerializer;
import com.climinby.starsky_e.registry.SSERegistries;
import com.climinby.starsky_e.registry.SSERegistryKeys;
import com.climinby.starsky_e.screen.SSEScreenHandlers;
import com.climinby.starsky_e.sound.SSESoundEvents;
import com.climinby.starsky_e.util.SSEBlockExtend;
import com.climinby.starsky_e.util.SSEServerDataReceiver;
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
		SSEServerDataReceiver.initialize();
		SSEBlockExtend.init();

		SSEItems.initialize();
		SSEBlocks.initialize();
		SSEItemGroups.initialize();
		SSEEntities.initialize();
		SSESoundEvents.init();
		SSEScreenHandlers.initialize();
		InkTypes.initialize();
		Galaxies.initialize();
		Planets.initialize();
		SampleTypes.initialize();
		MaterialTypes.init();
		SSERecipiSerializer.init();
		SSERecipeType.init();
		SSEEntityDefaultAttributes.init();
		SSEStatusEffects.init();

		SSECommands.init();

		LOGGER.info("Hello Fabric world!");
	}
}