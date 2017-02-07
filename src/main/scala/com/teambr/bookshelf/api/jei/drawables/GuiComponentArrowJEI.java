package com.teambr.bookshelf.api.jei.drawables;

import com.teambr.bookshelf.client.gui.component.display.GuiComponentArrow;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class GuiComponentArrowJEI extends GuiComponentArrow implements IDrawableAnimated {

    // Variables
    private ITickTimer ticker;

    /**
     * The main constructor
     * @param x The X position
     * @param y The Y position
     * @param jeiHelpers IJeiHelpers object
     */
    public GuiComponentArrowJEI(int x, int y, IJeiHelpers jeiHelpers) {
        super(x, y);
        ticker = jeiHelpers.getGuiHelper().createTickTimer(50, 50, false);
    }

    /*******************************************************************************************************************
     * GuiComponentArrow                                                                                               *
     *******************************************************************************************************************/

    /**
     * Use the ticker to move the arrow
     * @return A point in the animation
     */
    @Override
    public int getCurrentProgress() {
        return (int) Math.min(((ticker.getValue() * 24) / Math.max(ticker.getMaxValue(), 0.001)), 24);
    }

    /*******************************************************************************************************************
     * IDrawable                                                                                                       *
     *******************************************************************************************************************/

    /**
     * Call draw function
     * @param minecraft The MC object
     */
    @Override
    public void draw(@Nonnull Minecraft minecraft) {
        draw(minecraft, getXPos(), getYPos());
    }

    /**
     * Our render Call
     * @param minecraft The MC object
     * @param xOffset X offset
     * @param yOffset Y offset
     */
    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        render(xOffset, yOffset, Mouse.getX(), Mouse.getY());
    }
}
