package com.climinby.starsky_e.entity;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.block.entity.AnalyzerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SSEBlockEntities {
    public static final BlockEntityType<AnalyzerBlockEntity> ANALYZER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(StarSkyExplority.MOD_ID, "analyzer"),
            FabricBlockEntityTypeBuilder.create((AnalyzerBlockEntity::new), SSEBlocks.ANALYZER).build()
    );

    public static void initialize() {}
}
