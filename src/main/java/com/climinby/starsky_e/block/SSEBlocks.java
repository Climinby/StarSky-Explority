package com.climinby.starsky_e.block;

import com.climinby.starsky_e.StarSkyExplority;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;

import java.awt.*;

public class SSEBlocks {
    public static final Block MOON_SOIL = register("moon_soil", new ColoredFallingBlock(new ColorCode(new Color(90, 99, 102).getRGB()),
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.SAND)
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(0.6F)), true);

    public static final Block ANALYZER = register("analyzer", new AnalyzerBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.STONE)
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(3.0F, 9.0F)
                    .requiresTool()), true);

    public static final Block EXTRACTOR = register("extractor", new ExtractorBlock(
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.STONE)
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(3.0F, 9.0F)
                    .requiresTool()), true);

    public static Block register(String id, Block block, boolean shouldRegisterItem) {
        Identifier blockID = Identifier.of(StarSkyExplority.MOD_ID, id);

        if(shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, blockID, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockID, block);
    }

    public static void initialize() {}
}
