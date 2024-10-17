package com.climinby.starsky_e;

import com.climinby.starsky_e.client.gui.screen.AnalyzerScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class DataReceiver {
    public static void initialize() {
        analyzerData();
    }

    private static void analyzerData() {
        ClientPlayNetworking.registerGlobalReceiver(SSENetworkingConstants.DATA_ANALYZER_INK, (client, handler, buf, responseSender) -> {
            int ink = buf.readInt();
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> {
                if(client.currentScreen instanceof AnalyzerScreen) {
                    AnalyzerScreen screen = (AnalyzerScreen) client.currentScreen;
                    if(screen.getPos().equals(pos)) {
                        screen.setInk(ink);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(SSENetworkingConstants.DATA_ANALYZER_INK_TYPE, (client, handler, buf, responseSender) -> {
            Item inkType = buf.readItemStack().getItem();
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> {
                if(client.currentScreen instanceof AnalyzerScreen) {
                    AnalyzerScreen screen = (AnalyzerScreen) client.currentScreen;
                    if(pos.equals(screen.getPos())) {
                        screen.setInkType(inkType);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(SSENetworkingConstants.DATA_ANALYZER_CURRENT_SAMPLE, (client, handler, buf, responseSender) -> {
            Item currentSample = buf.readItemStack().getItem();
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> {
                if(client.currentScreen instanceof AnalyzerScreen) {
                    AnalyzerScreen screen = (AnalyzerScreen) client.currentScreen;
                    if(pos.equals(screen.getPos())) {
                        screen.setCurrentSample(currentSample);
                    }
                }
            });
        });
    }
}
