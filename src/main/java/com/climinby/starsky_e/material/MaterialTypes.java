package com.climinby.starsky_e.material;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.item.SSEItems;
import com.climinby.starsky_e.item.Sample;
import com.climinby.starsky_e.item.ScrollItem;
import com.climinby.starsky_e.registry.SSERegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MaterialTypes {
    public static final MaterialType ALUMINIUM = register("aluminium", new MaterialType((ScrollItem) SSEItems.RESEARCH_SCROLL_ALUMINIUM, "research_aluminium"));
    public static final MaterialType SILVER = register("silver", new MaterialType((ScrollItem) SSEItems.RESEARCH_SCROLL_SILVER, "research_silver"));
    public static final MaterialType STELLARIUM = register("stellarium", new MaterialType((ScrollItem) SSEItems.RESEARCH_SCROLL_STELLARIUM, "research_stellarium"));

    public static MaterialType register(String id, MaterialType materialType) {
        MaterialType registeredMaterialType = Registry.register(SSERegistries.MATERIAL_TYPE, new Identifier(StarSkyExplority.MOD_ID, id), materialType);
        return registeredMaterialType;
    }

    public static void init() {}
}
