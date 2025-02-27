package com.climinby.starsky_e;

import com.climinby.starsky_e.datagen.StarSkyExplorityWorldGen;
import com.climinby.starsky_e.world.biome.SSEBiomes;
import com.climinby.starsky_e.world.dimension.SSEDimensions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class StarSkyExplorityDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(StarSkyExplorityWorldGen::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
		registryBuilder.addRegistry(RegistryKeys.BIOME, SSEBiomes::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, SSEDimensions::bootstrapType);
	}
}
