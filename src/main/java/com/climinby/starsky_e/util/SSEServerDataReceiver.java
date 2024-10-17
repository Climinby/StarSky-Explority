package com.climinby.starsky_e;

import com.climinby.starsky_e.block.entity.AnalyzerBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SSEServerDataReceiver {
    public static void initialize() {
        analyzerData();
    }

    private static void analyzerData() {
        ServerPlayNetworking.registerGlobalReceiver(SSENetworkingConstants.DATA_ANALYZER_ANALYSE_IS_WORKING, (server, player, handler, buf, responseSender) -> {
            boolean isClicked = buf.readBoolean();
            BlockPos pos = buf.readBlockPos();
            server.execute(() -> {
                World world = player.getWorld();
                if(world.getBlockEntity(pos) instanceof AnalyzerBlockEntity analyzer) {
                    analyzer.setAnalyseClicked(isClicked);
                    analyzer.markDirty();
                    world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 1);
                }
            });
        });
    }
}
