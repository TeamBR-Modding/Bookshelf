package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.helper.GuiHelper;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.FluidTank;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class GuiComponentFluidTank extends BaseComponent {
    // Variables
    protected int width, height;
    protected FluidTank tank;

    /**
     * Creates a fluid tank renderer
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param w The width
     * @param h The height
     * @param fluidTank The fluid tank, has fluid to render
     */
    public GuiComponentFluidTank(GuiBase<?> parent, int x, int y, int w, int h, FluidTank fluidTank) {
        super(parent, x, y);
        this.width = w;
        this.height = h;
        this.tank = fluidTank;
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
        GuiHelper.renderFluid(tank, 0, height, height, width);
        RenderUtils.bindTexture(parent.textureLocation);
        GlStateManager.popMatrix();
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

    public FluidTank getTank() {
        return tank;
    }

    public void setTank(FluidTank tank) {
        this.tank = tank;
    }
}
