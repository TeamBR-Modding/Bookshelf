package com.teambr.bookshelf.api.jei.drawables;

import com.teambr.bookshelf.util.RenderUtils;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

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
public class SlotDrawable implements IDrawable {

    // Variables
    private int zLevel = 0;
    private boolean isLarge;
    private int x, y;

    /**
     * Creates a slot
     * @param xPos The slot x position
     * @param yPos The slot y position
     * @param large Is this a large slot
     */
    public SlotDrawable(int xPos, int yPos, boolean large) {
        x = xPos;
        y = yPos;
        isLarge = large;
    }

    /*******************************************************************************************************************
     * IDrawable                                                                                                       *
     *******************************************************************************************************************/

    @Override
    public int getWidth() {
        return isLarge ? 26 : 18;
    }

    @Override
    public int getHeight() {
        return isLarge ? 26 : 18;
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft) {
        draw(minecraft, isLarge ? x - 4 : x, isLarge ? y - 4 : y);
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        GlStateManager.pushMatrix();
        RenderUtils.bindGuiComponentsSheet();
        Gui.drawModalRectWithCustomSizedTexture(xOffset, yOffset, 0, isLarge ? 3 : 20, getWidth(), getHeight(), 256, 256);
        GlStateManager.popMatrix();
    }
}
