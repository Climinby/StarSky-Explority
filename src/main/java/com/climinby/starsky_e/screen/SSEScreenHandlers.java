package com.climinby.starsky_e.screen;

import com.climinby.starsky_e.StarSkyExplority;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class SSEScreenHandlers {
    public static final ScreenHandlerType<AnalyzerScreenHandler> ANALYZER_SCREEN_HANDLER;

    static {
        ANALYZER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(
                new Identifier(StarSkyExplority.MOD_ID, "analyzer"),
                AnalyzerScreenHandler::new
        );
//        ANALYZER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(
//                new Identifier(StarSkyExplority.MOD_ID, "analyzer"),
//                (syncId, inventory, buf) -> {
//                    BlockPos pos = buf.readBlockPos();
//                    BlockEntity blockEntity = inventory.player.getWorld().getBlockEntity(pos);
//                    if (blockEntity instanceof AnalyzerBlockEntity) {
//                        return new AnalyzerScreenHandler(syncId, inventory, (AnalyzerBlockEntity) blockEntity);
//                    } else {
//                        throw new IllegalStateException("Unexpected BlockEntity!");
//                    }
//                }
//        );
    }

    public static void initialize() {}
}
