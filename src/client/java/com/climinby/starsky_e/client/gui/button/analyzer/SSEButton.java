package com.climinby.starsky_e.client.gui.button.analyzer;

import com.climinby.starsky_e.StarSkyExplority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AnalyzerButton extends ButtonWidget {
    private static final Identifier TEXTURE = new Identifier(StarSkyExplority.MOD_ID, "textures/gui/container/analyzer.png");
    private final int textureX;
    private final int textureY;

    public AnalyzerButton(int x, int y, int width, int height, int textureX, int textureY, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.textureX = textureX;
        this.textureY = textureY;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);

        boolean isHovered = this.isMouseOver(mouseX, mouseY);
        this.hovered = isHovered;
        boolean isFocused = this.isFocused();
        this.setFocused(isFocused);
        int textureX = this.textureX;
        int textureY = this.textureY;
        int textColor;
        if(!this.active) {
            textureY = textureY + 2 * this.height;
            textColor = 0x555555;
        } else if(this.isSelected()) {
            textureY = textureY + this.height;
            textColor = 0xFFFFFF;
        } else {
            textureY = 195;
            textColor = 0xFFFFFF;
        }

        context.drawTexture(TEXTURE, this.getX(), this.getY(), textureX, textureY, this.width, this.height);
        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, textColor);
    }
}
