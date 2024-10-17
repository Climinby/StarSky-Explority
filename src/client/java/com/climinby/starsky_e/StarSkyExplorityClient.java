package com.climinby.starsky_e;

import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.client.gui.screen.AnalyzerScreen;
import com.climinby.starsky_e.screen.SSEScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class StarSkyExplorityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(SSEScreenHandlers.ANALYZER_SCREEN_HANDLER, AnalyzerScreen::new);

		DataReceiver.initialize();

		BlockRenderLayerMap.INSTANCE.putBlock(SSEBlocks.ANALYZER, RenderLayer.getSolid());
	}
}