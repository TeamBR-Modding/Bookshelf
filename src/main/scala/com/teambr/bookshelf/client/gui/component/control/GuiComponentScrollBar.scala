package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.{BaseComponent, NinePatchRenderer}
import com.teambr.bookshelf.util.RenderUtils
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11

/**
 * This file was created for com.teambr.bookshelf.Bookshelf
 *
 * com.teambr.bookshelf.Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
abstract class GuiComponentScrollBar(x: Int, y: Int, var height: Int)
    extends BaseComponent(x, y) {

    var maxRange = height - 2 - 15
    var currentPosition = 0
    var isMoving = false
    val renderer = new NinePatchRenderer(0, 80, 3)

    var u = 78
    var v = 0

    /**
     * Called when the scroll box has moved.
     * @param position The position, 0 - 1 of how far along it is
     */
    def onScroll(position: Float) : Unit

    /**
     * Set the position of the box
     * @param position The position, 0 - 1 of how far along it is
     */
    def setPosition(position: Float) : Unit = this.currentPosition = (maxRange * position).toInt

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, y: Int, button: Int) {
        isMoving = true
        currentPosition = (y - yPos) - 7
        if (currentPosition > maxRange) currentPosition = maxRange
        else if (currentPosition < 0) currentPosition = 0
        onScroll(currentPosition.toFloat / maxRange)
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    override def mouseDrag(x: Int, y: Int, button: Int, time: Long) {
        currentPosition = (y - yPos) - 7
        if (currentPosition > maxRange) currentPosition = maxRange
        else if (currentPosition < 0) currentPosition = 0
        onScroll(currentPosition.toFloat / maxRange)
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseUp(x: Int, y: Int, button: Int) {
        isMoving = false
        onScroll(currentPosition.toFloat / maxRange)
    }

    override def initialize() {}

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        if (currentPosition > maxRange) currentPosition = maxRange
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.prepareRenderState()
        renderer.render(this, 0, 0, 14, height)
        GL11.glTranslated(1, currentPosition + 1, 0)
        RenderUtils.bindGuiComponentsSheet()
        if (isMoving && !Mouse.isButtonDown(0)) isMoving = false
        drawTexturedModalRect(0, 0, if (isMoving) u + 12 else u, v, 12, 15)
        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    def renderOverlay(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {}

    def getWidth: Int = 14

    def getHeight: Int = height
}
