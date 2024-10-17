package com.climinby.starsky_e.block;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.block.entity.AnalyzerBlockEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class SSEInkTypes {
    public static void initialize() {
        AnalyzerBlockEntity.registerInkType(Items.INK_SAC, 20, new Identifier(StarSkyExplority.MOD_ID, "textures/gui/container/ordinary_ink.png"));
        AnalyzerBlockEntity.registerInkType(Items.GLOW_INK_SAC, 10, new Identifier(StarSkyExplority.MOD_ID, "textures/gui/container/glow_ink.png"));
    }
}
