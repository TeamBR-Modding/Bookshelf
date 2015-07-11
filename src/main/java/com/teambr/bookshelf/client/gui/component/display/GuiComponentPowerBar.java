package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.util.RenderUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class GuiComponentPowerBar extends BaseComponent {

    protected static final int u = 0;
    protected static final int v = 80;

    protected int width;
    protected int height;

    protected Color barColor;

    NinePatchRenderer renderer = new NinePatchRenderer(u, v, 3);

    /**
     * Default Constructor using x and y coords
     */
    public GuiComponentPowerBar(int x, int y) {
        this(x, y, 18, 74, new Color(255, 255, 255));
    }

    /**
     * Preferred Constructor
     * @param x X Position
     * @param y Y Position
     * @param w Width
     * @param h Height
     * @param color The color of the bar
     */
    public GuiComponentPowerBar(int x, int y, int w, int h, Color color) {
        super(x, y);
        this.width = w;
        this.height = h;
        this.barColor = color;
    }

    public abstract int getEnergyPercent(int scale);

    @Override
    public void initialize() {}

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(guiLeft + xPos, guiTop + yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        renderer.render(this, 0, 0, width, height);

        GL11.glPushMatrix();
        GL11.glTranslated(1, height - getEnergyPercent(height), 0);
        GL11.glScaled(width - 2, height - 2 + getEnergyPercent(height), 1);
        RenderUtils.setColor(barColor);
        drawTexturedModalRect(0, 0, 6, 86, 1, 1);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {}

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
