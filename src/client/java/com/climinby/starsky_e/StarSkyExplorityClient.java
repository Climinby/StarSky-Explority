package com.climinby.starsky_e;

import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.client.entity.SSEEntityRenderers;
import com.climinby.starsky_e.client.gui.screen.AnalyzerScreen;
import com.climinby.starsky_e.client.gui.screen.ExtractorScreen;
import com.climinby.starsky_e.client.render.SSEDimensionRenderer;
import com.climinby.starsky_e.client.render.TheMoonDimensionEffects;
import com.climinby.starsky_e.screen.SSEScreenHandlers;
import com.climinby.starsky_e.world.dimension.SSEDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.RegistryKeys;

@Environment(EnvType.CLIENT)
public class StarSkyExplorityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SSEClientDataReceiver.initialize();

		SSEDimensionRenderer.init();

		SSEEntityRenderers.init();
		ScreenRegistry.register(SSEScreenHandlers.ANALYZER_SCREEN_HANDLER, AnalyzerScreen::new);
		ScreenRegistry.register(SSEScreenHandlers.PROFILE_SCREEN_HANDLER, AnalyzerScreen.ProfileScreen::new);
		ScreenRegistry.register(SSEScreenHandlers.EXTRACTOR_SCREEN_HANDLER, ExtractorScreen::new);

		BlockRenderLayerMap.INSTANCE.putBlock(SSEBlocks.ANALYZER, RenderLayer.getSolid());
	}
}