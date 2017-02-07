package com.teambr.bookshelf.api.jei.drawables;

import com.teambr.bookshelf.client.gui.component.control.GuiComponentItemStackButton;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

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
public class GuiComponentItemStackButtonJEI extends GuiComponentItemStackButton implements IDrawable {

    /**
     * Creates the object
     * @param x The x pos
     * @param y The y pos
     * @param stack The display stack
     */
    public GuiComponentItemStackButtonJEI(int x, int y, ItemStack stack) {
        super(x, y, stack);
    }

    /*******************************************************************************************************************
     * GuiComponentItemStackButton                                                                                     *
     *******************************************************************************************************************/

    @Override
    public void doAction() {
        // No-Op
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
        render(xOffset, yOffset, 0, 0);
        if(stack() != null)
            renderOverlay(xOffset, yOffset, 0, 0);
    }
}
