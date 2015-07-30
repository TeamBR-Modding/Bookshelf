package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.util.RenderUtils;
import org.lwjgl.opengl.GL11;

/**
 * Bookshelf
 * Created by Paul Davis on 7/30/2015
 */
public abstract class GuiComponentTexturedButton extends GuiComponentButton {

    protected int iconU;
    protected int iconV;

    protected int iconW;
    protected int iconH;

    public GuiComponentTexturedButton(int x, int y, int u, int v, int textureW, int textureH, int width, int height) {
        super(x, y, width, height, "");
        iconU = u;
        iconV = v;
        iconW = textureW;
        iconH = textureH;
    }

    @Override
    public void render(int guiLeft, int guiTop) {
        super.render(guiLeft, guiTop);
        GL11.glPushMatrix();

        RenderUtils.prepareRenderState();

        GL11.glTranslated(xPos + 1, yPos + 1, 0);
        RenderUtils.bindGuiComponentsSheet();
        drawTexturedModalRect(0, 0, iconU, iconV, iconW, iconH);

        RenderUtils.restoreRenderState();

        GL11.glPopMatrix();
    }
}
