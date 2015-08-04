package com.teambr.bookshelf.client.gui.component

import java.awt.Color

import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.{SideOnly, Side}
import org.lwjgl.opengl.GL11

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
@SideOnly (Side.CLIENT)
class NinePatchRenderer {
    var u = 0
    var v = 0

    var cellSize = 4

    var patchLocation : ResourceLocation = null

    def this(U : Int, V : Int, size : Int) {
        this()
        u = U
        v = V
        cellSize = size
    }

    /**
     * Partial Rendering Code
     *
     * This can be overwritten in a new instance of the class to disable certain parts from rendering or to give them a
     * different behavior. One instance would be for a tab, you can prevent the left edge from rendering in that way
     */

    //Corners
    protected def renderTopLeftCorner (gui: Gui) : Unit = {
        gui.drawTexturedModalRect(0, 0, u, v, cellSize, cellSize)
    }

    protected def renderTopRightCorner (gui : Gui, width : Int) : Unit =  {
        gui.drawTexturedModalRect(width - cellSize, 0, u + cellSize + 1, v, cellSize, cellSize)
    }

    protected def renderBottomLeftCorner (gui : Gui, height : Int) : Unit =  {
        gui.drawTexturedModalRect(0, height - cellSize, u, v + cellSize + 1, cellSize, cellSize)
    }

    protected def renderBottomRightCorner (gui : Gui, width : Int, height : Int) {
        gui.drawTexturedModalRect(width - cellSize, height - cellSize, u + cellSize + 1, v + cellSize + 1, cellSize, cellSize)
    }

    //Edges
    protected def renderTopEdge (gui: Gui, width: Int) : Unit = {
        GL11.glPushMatrix()
        GL11.glTranslatef(cellSize, 0, 0)
        GL11.glScalef(width - (cellSize * 2), 1, 0)
        gui.drawTexturedModalRect(0, 0, u + cellSize, v, 1, cellSize)
        GL11.glPopMatrix()
    }

    protected def renderBottomEdge (gui: Gui, width: Int, height : Int) : Unit = {
        GL11.glPushMatrix()
        GL11.glTranslatef(cellSize, height - cellSize, 0)
        GL11.glScalef(width - (cellSize * 2), 1, 0);
        gui.drawTexturedModalRect(0, 0, u + cellSize, v + cellSize + 1, 1, cellSize)
        GL11.glPopMatrix()
    }

    protected def renderLeftEdge (gui: Gui, height : Int) : Unit = {
        GL11.glPushMatrix()
        GL11.glTranslatef(0, cellSize, 0)
        GL11.glScalef(1, height - (cellSize * 2), 0)
        gui.drawTexturedModalRect(0, 0, u, v + cellSize, cellSize, 1)
        GL11.glPopMatrix()
    }

    protected def renderRightEdge (gui: Gui, width: Int, height : Int) : Unit = {
        GL11.glPushMatrix()
        GL11.glTranslatef(width - cellSize, cellSize, 0)
        GL11.glScalef(1, height - (cellSize * 2), 0)
        gui.drawTexturedModalRect(0, 0, u + cellSize + 1, v + cellSize, cellSize, 1)
        GL11.glPopMatrix()
    }

    //Background
    protected def renderBackground (gui: Gui, width: Int, height: Int) : Unit = {
        GL11.glPushMatrix()
        GL11.glTranslatef(cellSize - 1, cellSize - 1, 0)
        GL11.glScalef(width - (cellSize * 2) + 2, height - (cellSize * 2) + 2, 0)
        gui.drawTexturedModalRect(0, 0, u + cellSize, v + cellSize, 1, 1)
        GL11.glPopMatrix()
    }

    def render(gui: Gui, x: Int, y: Int, width: Int, height: Int) {
        render(gui, x, y, width, height, null)
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
    def render(gui: Gui, x: Int, y: Int, width: Int, height: Int, color: Color) {
        GL11.glPushMatrix()
        if (color != null) RenderUtils.setColor(color)
        if (patchLocation != null) Minecraft.getMinecraft.getTextureManager.bindTexture(patchLocation)
        else RenderUtils.bindGuiComponentsSheet()
        GL11.glTranslatef(x, y, 0)
        renderBackground(gui, width, height)
        renderTopEdge(gui, width)
        renderBottomEdge(gui, width, height)
        renderRightEdge(gui, width, height)
        renderLeftEdge(gui, height)
        renderTopLeftCorner(gui)
        renderTopRightCorner(gui, width)
        renderBottomLeftCorner(gui, height)
        renderBottomRightCorner(gui, width, height)
        GL11.glPopMatrix()
    }
}
