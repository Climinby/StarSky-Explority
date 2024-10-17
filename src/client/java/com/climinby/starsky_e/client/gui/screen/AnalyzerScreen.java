package com.climinby.starsky_e.client.gui.screen;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.block.InkType;
import com.climinby.starsky_e.block.SSEBlocks;
import com.climinby.starsky_e.block.entity.AnalyzerBlockEntity;
import com.climinby.starsky_e.recipe.AnalysisRecipe;
import com.climinby.starsky_e.recipe.AnalysisResult;
import com.climinby.starsky_e.registry.SSERegistries;
import com.climinby.starsky_e.screen.AnalyzerScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerScreen extends HandledScreen<AnalyzerScreenHandler> {
    private int ink;
    private Item inkType;
    private BlockPos pos;
    private Item currentSample;

    private static final Identifier TEXTURE = new Identifier(
            StarSkyExplority.MOD_ID,
            "textures/gui/container/analyzer.png"
    );

    public AnalyzerScreen(AnalyzerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        pos = this.handler.getPos();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y - 29, 0, 0, backgroundWidth, backgroundHeight + 29);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        Slot hoveredSlot = this.focusedSlot;
        if(hoveredSlot != null && hoveredSlot.hasStack()) {
            ItemStack stack = hoveredSlot.getStack();
            if(hoveredSlot.getIndex() >= 3 && hoveredSlot.getIndex() <= 7 && !(this.focusedSlot.inventory instanceof PlayerInventory)) {
                List<Text> tooltip = new ArrayList<>();
                List<Text> originTips = this.getTooltipFromItem(stack);
                tooltip.add(originTips.remove(0));
                tooltip.add(Text.literal("Odds: " + getOdds(currentSample, stack.getItem()) + "%").withColor(0xFFB116));
                for(Text text : originTips) {
                    tooltip.add(text);
                }
                context.drawTooltip(textRenderer, tooltip, x, y);
            } else {
                if(this.handler.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) {
                    ItemStack itemStack = this.focusedSlot.getStack();
                    context.drawTooltip(this.textRenderer, this.getTooltipFromItem(itemStack), itemStack.getTooltipData(), x, y);
                }
            }
        }
    }

    private static float getOdds(Item currentSample, Item product) {
        float odds = -1.0F;
        for(AnalysisRecipe recipe : SSERegistries.ANALYSIS_RECIPE) {
            if(recipe.getMaterial() == currentSample) {
                for(AnalysisResult result : recipe.getResults()) {
                    if(result.getItemStack().isOf(product)) {
                        odds = ((float)result.getWeight()) / ((float)recipe.getSumWeight()) * 100;
                    }
                }
            }
        }
        return odds;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = ((backgroundWidth - textRenderer.getWidth(title)) / 2) + 3;
        titleY = backgroundHeight - 188;
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        String inkString = "Ink: " + ink + "%";
        for(InkType containedInkType : AnalyzerBlockEntity.INK_TYPES) {
            if(inkType == containedInkType.getItem()) {
                context.drawTexture(
                        containedInkType.getTexture(),
                        146,
                        19 + (100 - ink) / 2,
                        0,
                        0,
                        12,
                        ink / 2
                );
            }
        }
        context.drawTexture(
                TEXTURE,
                147,
                19,
                0,
                203,
                3,
                50
        );

        context.drawText(
                textRenderer,
                title,
                9,
                titleY,
                4210752,
                false);

        if(ink >= 20) {
//            context.drawText (
//                    textRenderer,
//                    inkString,
//                    124,
//                    -17,
//                    0xFFFFFF,
//                    true
//            );
        } else {
//            context.drawText (
//                    textRenderer,
//                    inkString,
//                    124,
//                    -17,
//                    0xE71212,
//                    false
//            );
            context.drawTexture(
                    TEXTURE,
                    130,
                    59,
                    0,
                    195,
                    11,
                    8
            );
        }
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752, false);
    }

    public void setInk(int ink) {
        this.ink = ink;
    }

    public void setInkType(Item inkType) {
        this.inkType = inkType;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setCurrentSample(Item currentSample) {
        this.currentSample = currentSample;
    }
}
