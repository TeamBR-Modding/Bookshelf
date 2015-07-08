package com.teambr.bookshelf.client.gui.component;

import com.teambr.bookshelf.util.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class NinePatchRenderer {

    private int u;
    private int v;

    private ResourceLocation patchLocation;

    /**
     * Default Constructor
     */
    public NinePatchRenderer() {
        u = 0;
        v = 0;
    }

    /**
     * Used to specify a nine patch that isn't the default sheet
     * @param resource Location of the patch
     * @param resourceU Resource U
     * @param resourceV Resource V
     */
    public NinePatchRenderer(ResourceLocation resource, int resourceU, int resourceV) {
        u = resourceU;
        v = resourceV;

        patchLocation = resource;
    }

    /**
     * Partial Rendering Code
     *
     * This can be overwritten in a new instance of the class to disable certain parts from rendering or to give them a
     * different behavior. One instance would be for a tab, you can prevent the left edge from rendering in that way
     */

    //Corners
    protected void renderTopLeftCorner(Gui gui) {
        gui.drawTexturedModalRect(0, 0, u, v, 4, 4);
    }

    protected void renderTopRightCorner(Gui gui, int width) {
        gui.drawTexturedModalRect(width - 4, 0, u + 7, v, 4, 4);
    }

    protected void renderBottomLeftCorner(Gui gui, int height) {
        gui.drawTexturedModalRect(0, height - 4, u, v + 7, 4, 4);
    }

    protected void renderBottomRightCorner(Gui gui, int width, int height) {
        gui.drawTexturedModalRect(width - 4, height - 4, u + 7, v + 7, 4, 4);
    }

    //Edges
    protected void renderTopEdge(Gui gui, int width) {
        GL11.glPushMatrix();
        GL11.glTranslatef(4, 0, 0);
        GL11.glScalef(width - 8, 1, 0);
        gui.drawTexturedModalRect(0, 0, u + 5, v, 1, 3);
        GL11.glPopMatrix();
    }

    protected void renderBottomEdge(Gui gui, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(4, height - 3, 0);
        GL11.glScalef(width - 8, 1, 0);
        gui.drawTexturedModalRect(0, 0, u + 5, v + 8, 1, 3);
        GL11.glPopMatrix();
    }

    protected void renderLeftEdge(Gui gui, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 4, 0);
        GL11.glScalef(1, height - 8, 0);
        gui.drawTexturedModalRect(0, 0, u, v + 5, 3, 1);
        GL11.glPopMatrix();
    }

    protected void renderRightEdge(Gui gui, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(width - 3, 4, 0);
        GL11.glScalef(1, height - 8, 0);
        gui.drawTexturedModalRect(0, 0, u + 8, v + 5, 3, 1);
        GL11.glPopMatrix();
    }

    //Background
    protected void renderBackground(Gui gui, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(3, 3, 0);
        GL11.glScalef(width - 6, height - 6, 0);
        gui.drawTexturedModalRect(0, 0, u + 5, v + 5, 1, 1);
        GL11.glPopMatrix();
    }

    public void render(Gui gui, int x, int y, int width, int height) {
        render(gui, x, y, width, height, null);
    }

    /**
     * Main render call. This must be called in the parent gui to render the box.
     *
     * WARNING: Will bind texture to sheet, make sure you rebind afterwards or do this first
     *
     * @param gui The screen being rendered onto
     * @param x Screen X Position
     * @param y Screen Y Position
     * @param width Width
     * @param height Height
     * @param color Color to render
     */
    public void render(Gui gui, int x, int y, int width, int height, Color color) {
        GL11.glPushMatrix();

        if(color != null)
            setColor(color);

        if(patchLocation != null)
            Minecraft.getMinecraft().getTextureManager().bindTexture(patchLocation);
        else
            RenderUtils.bindGuiComponentsSheet();

        GL11.glTranslatef(x, y, 0);
        renderBackground(gui, width, height);

        renderTopEdge(gui, width);
        renderBottomEdge(gui, width, height);
        renderRightEdge(gui, width, height);
        renderLeftEdge(gui, height);

        renderTopLeftCorner(gui);
        renderTopRightCorner(gui, width);
        renderBottomLeftCorner(gui, height);
        renderBottomRightCorner(gui, width, height);

        GL11.glPopMatrix();
    }

    public static void setColor(Color color) {
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }
}