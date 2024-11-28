package com.climinby.starsky_e.client.entity;

import com.climinby.starsky_e.entity.SSEEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SSEEntityRenderers {
    public static void init() {
        EntityRendererRegistry.register(
                SSEEntities.LUNARIAN_ENTITY,
                LunarianEntityRenderer::new
        );
    }
}
