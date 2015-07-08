package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiComponentText extends BaseComponent {
    protected String text;
    protected final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    int hexColor;

    public GuiComponentText(String label, int x, int y, int color) {
        super(x, y);
        text = StatCollector.translateToLocal(label);
        this.hexColor = color;
    }

    public GuiComponentText(String label, int x, int y) {
        this(label, x, y, 4210752);
    }

    @Override
    public void initialize() {}

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(guiLeft + xPos, guiTop + yPos, 0);
        fontRenderer.drawString(text, 0, 0, hexColor);

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
