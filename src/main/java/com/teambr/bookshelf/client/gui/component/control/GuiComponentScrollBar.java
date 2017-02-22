package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public abstract class GuiComponentScrollBar extends BaseComponent {
    // Variables
    protected int nubU, nubV, height, maxRange;
    protected int currentPosition = 0;
    protected boolean isMoving = false;

    /**
     * Creates a scroll bar
     *
     * IMPORTANT: Scroll bar NUB for selected must be to the right of the normal one
     * NUB should be 12x15 pixels
     *
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param nU The nub texture u
     * @param nV The nub texture v
     * @param height The height of this scroll bar
     */
    public GuiComponentScrollBar(GuiBase<?> parent, int x, int y,int nU, int nV, int height) {
        super(parent, x, y);
        nubU = nU;
        nubV = nV;
        this.height = height;
        maxRange = height - 17;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when the scroll box has moved.
     * @param position The position, 0 - 1 of how far along it is
     */
    protected abstract void onScroll(float position);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(int x, int y, int button) {
        isMoving = true;
        currentPosition = (y - yPos) - 7;
        if(currentPosition > maxRange)
            currentPosition = maxRange;
        else if(currentPosition < 0)
            currentPosition = 0;
        onScroll(currentPosition / maxRange);
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    @Override
    public void mouseDrag(int x, int y, int button, long time) {
        currentPosition = (y - yPos) - 7;
        if(currentPosition > maxRange)
            currentPosition = maxRange;
        else if(currentPosition < 0)
            currentPosition = 0;
        onScroll(currentPosition / maxRange);
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseUp(int x, int y, int button) {
        isMoving = false;
        onScroll(currentPosition / maxRange);
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        if(currentPosition > maxRange)
            currentPosition = maxRange;

        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos + 1, yPos + currentPosition + 1, 0);
        if(isMoving && !Mouse.isButtonDown(0))
            isMoving = false;
        drawTexturedModalRect(0, 0, isMoving ? nubU + 12 : nubU, nubV, 12, 15);
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // No Op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return 14;
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

    public int getNubU() {
        return nubU;
    }

    public void setNubU(int nubU) {
        this.nubU = nubU;
    }

    public int getNubV() {
        return nubV;
    }

    public void setNubV(int nubV) {
        this.nubV = nubV;
    }

    public void setHeight(int height) {
        maxRange = height - 17;
        this.height = height;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentPosition(float currentPosition) {
        this.currentPosition = (int) (maxRange * currentPosition);
    }
}
