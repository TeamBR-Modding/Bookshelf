package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.ClientUtils;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nullable;
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
public class GuiComponentText extends BaseComponent {
    // Variables
    protected String label;
    protected int colorDefault = 4210752;
    protected Color color;

    /**
     * Creates the text component
     * @param parent The gui parent
     * @param x The x pos
     * @param y The y pos
     * @param label The string to render
     * @param color Optional color
     */
    public GuiComponentText(GuiBase<?> parent, int x, int y, String label, @Nullable Color color) {
        super(parent, x, y);
        this.label = ClientUtils.translate(label);
        this.color = color;
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // No Op
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(xPos, yPos, 0);
        RenderUtils.prepareRenderState();


        if(color != null)
            RenderUtils.setColor(color);
        else
            RenderUtils.restoreColor();

        fontRenderer.drawString(label, 0, 0, colorDefault);

        RenderUtils.restoreColor();
        RenderUtils.restoreRenderState();

        GlStateManager.popMatrix();
    }

    /**
     * Used to find how wide this is
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(label);
    }

    /**
     * Used to find how tall this is
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return 7;
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
