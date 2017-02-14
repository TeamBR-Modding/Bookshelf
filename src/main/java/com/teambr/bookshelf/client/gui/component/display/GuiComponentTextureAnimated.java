package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import net.minecraft.client.renderer.GlStateManager;

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
public abstract class GuiComponentTextureAnimated extends GuiComponentTexture {
    // Variables
    protected ANIMATION_DIRECTION animationDirection;

    /**
     * Tells the component which way to render the texture
     */
    public enum ANIMATION_DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Creates a textured area
     *
     * @param parent      The parent GUI
     * @param x           The x pos
     * @param y           The y pos
     * @param texU        The texture u
     * @param texV        The texture v
     * @param imageWidth  The image width
     * @param imageHeight The image height
     */
    public GuiComponentTextureAnimated(GuiBase<?> parent, int x, int y, int texU, int texV, int imageWidth, int imageHeight, ANIMATION_DIRECTION dir) {
        super(parent, x, y, texU, texV, imageWidth, imageHeight);
        this.animationDirection = dir;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Get the current scale, scaled to the width
     * @param scale What to scale to
     * @return How far along 0-scale in current animation
     */
    protected abstract int getCurrentProgress(int scale);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 0);
        switch (animationDirection) {
            case RIGHT:
                drawTexturedModalRect(0, 0, u, v, getCurrentProgress(width), height);
                break;
            case DOWN:
                drawTexturedModalRect(0, 0, u, v, width, getCurrentProgress(height));
                break;
            case LEFT:
                drawTexturedModalRect(-width + getCurrentProgress(width), 0, u, v, getCurrentProgress(width), height);
                break;
            case UP:
                drawTexturedModalRect(0, -height + getCurrentProgress(height), u, v, width, getCurrentProgress(height));
                break;
        }
        GlStateManager.popMatrix();
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public ANIMATION_DIRECTION getAnimationDirection() {
        return animationDirection;
    }

    public void setAnimationDirection(ANIMATION_DIRECTION animationDirection) {
        this.animationDirection = animationDirection;
    }
}
