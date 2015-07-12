package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.RenderUtils;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentFlame extends BaseComponent {
    protected static final int u = 0;
    protected static final int v = 242;

    /**
     * Constructor
     * @param x X Position
     * @param y Y Position
     */
    public GuiComponentFlame(int x, int y) {
        super(x, y);
    }

    /**
     * Used to get the current burn time
     * @return How complete, scaled to 14
     */
    public abstract int getCurrentBurn();

    @Override
    public void initialize() {}

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        drawTexturedModalRect(0, 0, u, v, getWidth(), getHeight());
        drawTexturedModalRect(0, 14 - getCurrentBurn(), u + 14, v + 14 - getCurrentBurn(), getWidth(), getCurrentBurn());

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {}

    @Override
    public int getWidth() {
        return 14;
    }

    @Override
    public int getHeight() {
        return 14;
    }
}
