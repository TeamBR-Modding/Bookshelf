package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.RenderUtils;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentArrow extends BaseComponent {
    protected static final int u = 28;
    protected static final int v = 239;

    /**
     * Constructor
     * @param x X Position
     * @param y Y Position
     */
    public GuiComponentArrow(int x, int y) {
        super(x, y);
    }

    /**
     * Used to get how far along the process is, scale to 24
     * @return How complete, scaled to 24
     */
    public abstract int getCurrentProgress();

    @Override
    public void initialize() {}

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        drawTexturedModalRect(0, 0, u, v, getWidth(), getHeight());
        drawTexturedModalRect(-1, 0, u + getWidth(), v, getCurrentProgress() + 1, getHeight());

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {}

    @Override
    public int getWidth() {
        return 23;
    }

    @Override
    public int getHeight() {
        return 17;
    }
}