package com.teambr.bookshelf.api.jei.drawables;

import com.teambr.bookshelf.client.gui.component.display.GuiComponentFluidTank;
import com.teambr.bookshelf.util.RenderUtils;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
public class GuiComponentBox extends GuiComponentFluidTank implements IDrawable {

    /**
     * Constructor for box, passes to fluid tank with no tank for just the background
     * @param x The x pos
     * @param y The y pos
     * @param width The width
     * @param height The height
     */
    public GuiComponentBox(int x, int y, int width, int height) {
        super(x, y, width, height, null);
    }

    /*******************************************************************************************************************
     * GuiComponentFluidTank                                                                                           *
     *******************************************************************************************************************/

    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos(), yPos(), 0);
        RenderUtils.bindGuiComponentsSheet();
        renderer().render(this, 0, 0, width(), height());
        GlStateManager.popMatrix();
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
        render(xPos(), yPos(), Mouse.getX(), Mouse.getY());
    }
}
