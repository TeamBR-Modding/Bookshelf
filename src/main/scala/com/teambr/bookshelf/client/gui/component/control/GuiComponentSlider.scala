package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
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
abstract class GuiComponentSlider[V](x: Int, y: Int, var width: Int, var selectables: List[V], var index: Int)
    extends BaseComponent(x, y) {

    private val BAR_U: Int = 32
    private val BAR_V: Int = 0
    private val BOX_U: Int = 32
    private val BOX_V: Int = 3

    var boxX = x + 1 + (((index * width) / selectables.size) + ((selectables.size / width) / 2))
    var isDragging = false
    var currentSelected = selectables(index)

    override def initialize() : Unit = {}

    /**
     * Called when something changes
     * @param value The current value
     */
    def onValueChanged(value: V)

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(x: Int, y: Int, button: Int) {
        isDragging = true
        boxX = x
        updateCurrentSelection()
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseUp(x: Int, y: Int, button: Int) {
        isDragging = false
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    override def mouseDrag(x: Int, y: Int, button: Int, time: Long) {
        boxX = x
        updateCurrentSelection()
    }

    def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        GL11.glPushMatrix()
        GL11.glTranslatef(0, 4, 0)
        drawTexturedModalRect(0, 0, BAR_U, BAR_V + 1, 1, 1)
        GL11.glPushMatrix()
        GL11.glTranslatef(1, -1, 0)
        GL11.glScaled(width - 2, 1, 0)
        drawTexturedModalRect(0, 0, BAR_U + 1, BAR_V, 1, 3)
        GL11.glPopMatrix()
        GL11.glTranslatef(width - 1, 0, 0)
        drawTexturedModalRect(0, 0, BAR_U + 2, BAR_V + 1, 1, 1)
        GL11.glPopMatrix()
        GL11.glTranslatef(boxX - xPos, 0, 0)
        drawTexturedModalRect(0, 0, BOX_U, BOX_V, 4, 9)
        GL11.glPopMatrix()
    }

    def renderOverlay(guiLeft: Int, guiTop: Int) {
        val fontRenderer: FontRenderer = Minecraft.getMinecraft.fontRendererObj
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        GL11.glPushMatrix()
        val currentSelect: String = currentSelected.toString
        GL11.glTranslated((width / 2) - (fontRenderer.getStringWidth(currentSelect) / 2), -8, 0)
        fontRenderer.drawString(currentSelect, 0, 0, 4210752)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        GL11.glScaled(0.5, 0.5, 0)
        GL11.glPushMatrix()
        val lower: String = selectables.head.toString
        GL11.glTranslated(5 - (fontRenderer.getStringWidth(lower) / 2), -9, 0)
        fontRenderer.drawString(lower, 0, 0, 4210752)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        val upper: String = selectables.last.toString
        GL11.glTranslated(((width * 2) - 8) - (fontRenderer.getStringWidth(upper) / 2), -9, 0)
        fontRenderer.drawString(upper, 0, 0, 4210752)
        GL11.glPopMatrix()
        GL11.glPopMatrix()
        GL11.glPopMatrix()
    }

    def updateCurrentSelection() {
        if (boxX <= xPos) boxX = xPos + 1
        else if (boxX >= xPos + width - 5) boxX = xPos + width - 5
        val newPosition: Int = Math.floor(((boxX - (xPos + 1)) * (selectables.size - 1)) / ((xPos + width - 5) - (xPos + 1))).toInt
        currentSelected = selectables(newPosition)
        onValueChanged(currentSelected)
    }

    def getCurrentSelected: V = currentSelected

    def getWidth: Int = width

    def getHeight: Int = 10

    override def isMouseOver(mouseX: Int, mouseY: Int): Boolean =
        (mouseX >= xPos && mouseX < xPos + getWidth && mouseY >= yPos && mouseY < yPos + getHeight) || isDragging

    def generateNumberList(start: Int, end: Int): List[Int] = List.range(start, end + 1)
}
