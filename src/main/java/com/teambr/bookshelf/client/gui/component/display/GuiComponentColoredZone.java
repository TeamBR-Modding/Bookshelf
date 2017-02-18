package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class GuiComponentColoredZone extends BaseComponent {
    // Variables
    protected int width, height;
    protected Color color;

    /***
     * Creates the colored zone
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param w The width
     * @param h The height
     * @param color The color
     */
    public GuiComponentColoredZone(GuiBase<?> parent, int x, int y, int w, int h, Color color) {
        super(parent, x, y);
        this.width = w;
        this.height = h;
        this.color = color;
    }

    /**
     * Override this to change the color
     * @return The color, by default the passed color
     */
    protected Color getDynamicColor() {
        return color;
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        color = getDynamicColor();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.translate(xPos, yPos, 10);
        RenderUtils.setColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(0, 0, 0);
        GL11.glVertex3d(0, height, 0);
        GL11.glVertex3d(width, height, 0);
        GL11.glVertex3d(width, 0, 0);
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // Op OP, we want bars and stuff to render on top of this
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return height;
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
