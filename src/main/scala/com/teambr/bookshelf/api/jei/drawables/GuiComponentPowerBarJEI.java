package com.teambr.bookshelf.api.jei.drawables;

import com.teambr.bookshelf.client.gui.component.display.GuiComponentPowerBarGradient;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.awt.*;

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
public class GuiComponentPowerBarJEI extends GuiComponentPowerBarGradient implements IDrawableAnimated {

    // Variables
    private ITickTimer ticker;

    /**
     * Creates the object
     * @param x The x pos
     * @param y The y pos
     * @param width The width of the bar
     * @param height The height of the bar
     * @param jeiHelpers The IJeiHelpers object
     * @param colors The colors to add, in order lowest to highest. Must pass at least one
     */
    public GuiComponentPowerBarJEI(int x, int y, int width, int height, IJeiHelpers jeiHelpers, @Nonnull Color ... colors) {
        super(x, y, width, height, colors[colors.length - 1]);
        // Add more colors if more than one
        if(colors.length > 1) {
            for(int i = 0; i < colors.length - 1; i++) {
                addColor(colors[i]);
            }
        }
    }

    /*******************************************************************************************************************
     * GuiComponentPowerBarGradient                                                                                    *
     *******************************************************************************************************************/

    @Override
    public int getEnergyPercent(int scale) {
        return Math.max(0, (ticker.getValue() * scale) / ticker.getMaxValue());
    }

    /*******************************************************************************************************************
     * IDrawable                                                                                                       *
     *******************************************************************************************************************/

    @Override
    public void draw(@Nonnull Minecraft minecraft) {
        draw(minecraft, xPos(), yPos());
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        render(xOffset, yOffset, Mouse.getX(), Mouse.getY());
        renderOverlay(xOffset, yOffset, Mouse.getX(), Mouse.getY());
    }
}
