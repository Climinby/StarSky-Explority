package com.climinby.starsky_e.item;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.block.SSEBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SSEItemGroups {
    public static final RegistryKey<ItemGroup> SSE_TOOLS_AND_WEAPONS_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(StarSkyExplority.MOD_ID, "tools_and_weapons")
    );
    public static final RegistryKey<ItemGroup> SSE_INGREDIENTS_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(StarSkyExplority.MOD_ID, "ingredients")
    );
    public static final RegistryKey<ItemGroup> SSE_FOOD_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(StarSkyExplority.MOD_ID, "food")
    );
    public static final RegistryKey<ItemGroup> SSE_BLOCKS_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            new Identifier(StarSkyExplority.MOD_ID, "blocks")
    );

    public static final ItemGroup SSE_TOOLS_AND_WEAPONS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SSEItems.DIAMOND_COLLECTOR))
            .displayName(Text.translatable("itemGroup.starsky_explority.tools_and_weapons"))
            .build();
    public static final ItemGroup SSE_INGREDIENTS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SSEItems.STELLARIUM_INGOT))
            .displayName(Text.translatable("itemGroup.starsky_explority.ingredients"))
            .build();
    public static final ItemGroup SSE_FOOD = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SSEItems.MOON_CAKE))
            .displayName((Text.translatable("itemGroup.starsky_explority.food")))
            .build();
    public static final ItemGroup SSE_BLOCKS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SSEBlocks.MOON_SOIL))
            .displayName((Text.translatable("itemGroup.starsky_explority.blocks")))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, SSE_TOOLS_AND_WEAPONS_KEY, SSE_TOOLS_AND_WEAPONS);
        ItemGroupEvents.modifyEntriesEvent(SSE_TOOLS_AND_WEAPONS_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(SSEItems.IRON_COLLECTOR);
                    itemGroup.add(SSEItems.DIAMOND_COLLECTOR);
                    itemGroup.add(SSEItems.NETHERITE_COLLECTOR);
                });

        Registry.register(Registries.ITEM_GROUP, SSE_INGREDIENTS_KEY, SSE_INGREDIENTS);
        ItemGroupEvents.modifyEntriesEvent(SSE_INGREDIENTS_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(SSEItems.SAMPLE_EMPTY);
                    itemGroup.add(SSEItems.SAMPLE_EARTH);
                    itemGroup.add(SSEItems.SAMPLE_MOON);
                    itemGroup.add(SSEItems.SAMPLE_MARS);
                    itemGroup.add(SSEItems.SAMPLE_VENUS);
                    itemGroup.add(SSEItems.SAMPLE_MERCURY);
                    itemGroup.add(SSEItems.ALUMINIUM_INGOT);
                    itemGroup.add(SSEItems.SILVER_INGOT);
                    itemGroup.add(SSEItems.STELLARIUM_INGOT);
                    itemGroup.add(SSEItems.RESEARCH_SCROLL);
                    itemGroup.add(SSEItems.RESEARCH_SCROLL_ALUMINIUM);
                    itemGroup.add(SSEItems.RESEARCH_SCROLL_SILVER);
                    itemGroup.add(SSEItems.RESEARCH_SCROLL_STELLARIUM);
                });

        Registry.register(Registries.ITEM_GROUP, SSE_FOOD_KEY, SSE_FOOD);
        ItemGroupEvents.modifyEntriesEvent(SSE_FOOD_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(SSEItems.MOON_CAKE);
                });

        Registry.register(Registries.ITEM_GROUP, SSE_BLOCKS_KEY, SSE_BLOCKS);
        ItemGroupEvents.modifyEntriesEvent(SSE_BLOCKS_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(SSEBlocks.MOON_SOIL);
                    itemGroup.add(SSEBlocks.ANALYZER);
                });
    }
}
