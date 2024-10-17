package com.climinby.starsky_e.item;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.planet.Planets;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SSEItems {
    public static final Item IRON_COLLECTOR = register("iron_collector", new CollectorItem(ToolMaterials.IRON, new Item.Settings()
            .maxCount(1)
            .maxDamage(120)));
    public static final Item DIAMOND_COLLECTOR = register("diamond_collector", new CollectorItem(ToolMaterials.DIAMOND, new Item.Settings()
            .maxCount(1)
            .maxDamage(360)));
    public static final Item NETHERITE_COLLECTOR = register("netherite_collector", new CollectorItem(ToolMaterials.NETHERITE, new Item.Settings()
            .maxCount(1)
            .maxDamage(630)));

    public static final Item SILVER_INGOT = register("silver_ingot", new Item(new Item.Settings()));
    public static final Item ALUMINIUM_INGOT = register("aluminium_ingot", new Item(new Item.Settings()));
    public static final Item STELLARIUM_INGOT = register("stellarium_ingot", new StellariumItem(new Item.Settings()));
    public static final Item SAMPLE_EMPTY = register("sample_empty", new SampleItem(new Item.Settings(), Planets.EMPTY));
    public static final Item SAMPLE_EARTH = register("sample_earth", new SampleItem(new Item.Settings(), Planets.EARTH));
    public static final Item SAMPLE_MOON = register("sample_moon", new SampleItem(new Item.Settings(), Planets.MOON));
    public static final Item SAMPLE_MARS = register("sample_mars", new SampleItem(new Item.Settings(), Planets.MARS));
    public static final Item SAMPLE_VENUS = register("sample_venus", new SampleItem(new Item.Settings(), Planets.VENUS));
    public static final Item SAMPLE_MERCURY = register("sample_mercury", new SampleItem(new Item.Settings(), Planets.MERCURY));
    public static final Item RESEARCH_SCROLL = register("research_scroll_empty", new ScrollItem(new Item.Settings()));
    public static final Item RESEARCH_SCROLL_SILVER = register("research_scroll_silver", new ScrollItem(SILVER_INGOT,0.125F ,new Item.Settings()));
    public static final Item RESEARCH_SCROLL_ALUMINIUM = register("research_scroll_aluminium", new ScrollItem(ALUMINIUM_INGOT, 0.5F, new Item.Settings()));
    public static final Item RESEARCH_SCROLL_STELLARIUM = register("research_scroll_stellarium", new ScrollItem(STELLARIUM_INGOT, 0.015625F,new Item.Settings()));

    public static final Item MOON_CAKE = register("moon_cake", new Item(new Item.Settings()
            .food(new FoodComponent.Builder()
                    .hunger(6)
                    .saturationModifier(5.5F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 180 * 20, 1), 1.0F)
                    .build())));

    public static Item register(String id, Item item) {
        Identifier itemID = Identifier.of(StarSkyExplority.MOD_ID, id);
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);
        return registeredItem;
    }

    public static void initialize() {}
}
