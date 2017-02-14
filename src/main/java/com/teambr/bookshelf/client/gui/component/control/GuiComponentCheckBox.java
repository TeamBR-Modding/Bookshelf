package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.ClientUtils;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public abstract class GuiComponentCheckBox extends BaseComponent {
    // Variables
    protected int u, v;
    protected boolean selcted;
    protected String label;

    /**
     * Main constructor for check boxes
     *
     * IMPORTANT: You must put the selected texture directly to the right of this one in the texture for it to work
     *
     * @param parent The parent
     * @param x The x pos
     * @param y The y pos
     * @param u The texture u pos
     * @param v The texture v pos
     * @param text The text to display to the right
     */
    public GuiComponentCheckBox(GuiBase<?> parent, int x, int y, int u, int v, boolean initialValue, String text) {
        super(parent, x, y);
        this.u = u;
        this.v = v;
        selcted = initialValue;
        label = ClientUtils.translate(text);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when there is a change in state, use this to set the value on what this controls
     *
     * @param value The current value of this component
     */
    protected abstract void setValue(boolean value);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(int x, int y, int button) {
        if(x > xPos && x < xPos + 10 && y > yPos && y < yPos + 10) {
            selcted = !selcted;
            setValue(selcted);
        }
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 0);
        GlStateManager.disableLighting();
        drawTexturedModalRect(0, 0, selcted ? u + 10 : u, v, 10, 10);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos + 10, yPos, 0);
        RenderUtils.setColor(Color.darkGray);//Minecraft doesn't play nice with GL, so we will just set our own color
        fontRenderer.drawString(label, 0, 0, Color.darkGray.getRGB());
        RenderUtils.restoreColor();
        GlStateManager.popMatrix();
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return 10 + fontRenderer.getStringWidth(label);
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return 10;
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
