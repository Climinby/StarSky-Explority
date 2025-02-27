package com.climinby.starsky_e.client.render;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.world.dimension.SSEDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

public class SSEDimensionRenderer {
    private static final Identifier THE_EARTH_TEXTURE = new Identifier(StarSkyExplority.MOD_ID, "textures/environment/earth.png");
    private static final Identifier THE_SUN_TEXTURE = new Identifier(StarSkyExplority.MOD_ID, "textures/environment/sun.png");
    private static final List<Double> randoms = new ArrayList<>();
    private static int pointer = 0;

    private static int getMoonStarColor(int rand) {
        int newRand = rand % 32;
        if(newRand == 0) {
            return 0xFFD86455;
        } else if(newRand <= 5) {
            return 0xFF95D1F4;
        } else if(newRand <= 17) {
            return 0xFFFEFFE9;
        } else {
            return 0xFFFBFBFB;
        }
    }

    private static double getRandom() {
        double rand = randoms.get(pointer);
        pointer++;
        if(pointer == randoms.size()) {
            pointer = 0;
        }
        return rand;
    }

    private static void renderCelestialObject(Identifier texture, Matrix4f matrix, float size, float distance) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, -size,-size,-distance).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(matrix, size,-size,-distance).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(matrix, size,size,-distance).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(matrix, -size,size,-distance).texture(0.0F, 0.0F).next();
        tessellator.draw();

        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void renderStar(Matrix4f matrix, float distance) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        float size = 0.2F;
        int diff = 15;
        int argb = getMoonStarColor((int) (getRandom() * 16));
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, -size,-size,-distance).color(argb).next();
        bufferBuilder.vertex(matrix, size,-size,-distance).color(argb).next();
        bufferBuilder.vertex(matrix, size,size,-distance).color(argb).next();
        bufferBuilder.vertex(matrix, -size,size,-distance).color(argb).next();
        tessellator.draw();

        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public static void init() {
        DimensionRenderingRegistry.registerDimensionEffects(SSEDimensions.THE_MOON_ID, new TheMoonDimensionEffects());

        DimensionRenderingRegistry.SkyRenderer theMoonSkyRenderer = context -> {
            if(randoms.isEmpty()){
                Random random = MinecraftClient.getInstance().world.getRandom();
                for (int i = 0; i < 1009; i++) {
                    randoms.add(random.nextDouble());
                }
            }

            MatrixStack matrixStack = context.matrixStack();
            Quaternionf rotation = null;
            Matrix4f matrices = null;

            int density = 700;
            pointer = 103;
            for(int i = 0; i < density; i++) {
                double z = getRandom() * 360.0F;
                double y = getRandom() * 360.0F;
                double x = getRandom() * 360.0F;
                matrixStack.push();
                rotation = new Quaternionf()
                        .rotateZ((float) Math.toRadians(z))
                        .rotateY((float) Math.toRadians(y))
                        .rotateX((float) Math.toRadians(x));
                matrixStack.multiply(rotation);
                matrices = matrixStack.peek().getPositionMatrix();
                renderStar(matrices, 196);
                matrixStack.pop();
            }

            matrixStack.push();
            long time = MinecraftClient.getInstance().world.getTimeOfDay();
            rotation = new Quaternionf().rotateX((float) Math.toRadians(360.0F * time / 24000));
            matrixStack.multiply(rotation);
            matrices = matrixStack.peek().getPositionMatrix();
            renderCelestialObject(THE_SUN_TEXTURE, matrices, 65.0F, 196);
            matrixStack.pop();

            matrixStack.push();
            rotation = new Quaternionf()
                    .rotateY((float) Math.toRadians(90))
                    .rotateX((float) Math.toRadians(40));
            matrixStack.multiply(rotation);
            matrices = matrixStack.peek().getPositionMatrix();
            renderCelestialObject(THE_EARTH_TEXTURE, matrices, 80.0F, 196);
            matrixStack.pop();
        };

        DimensionRenderingRegistry.registerSkyRenderer(
                RegistryKeys.toWorldKey(SSEDimensions.THE_MOON_KEY),
                theMoonSkyRenderer
        );
    }
}
