package com.teambr.bookshelf.client.gui.component

import java.awt.Rectangle

import com.teambr.bookshelf.client.gui.component.listeners.{IKeyboardListener, IMouseEventListener}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.{FontRenderer, GuiScreen, Gui}
import net.minecraft.client.renderer.RenderHelper
import org.lwjgl.opengl.{GL12, GL11}

import scala.collection.mutable.ArrayBuffer

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
abstract class BaseComponent(var xPos : Int, var yPos : Int) extends Gui {
    var toolTip = new ArrayBuffer[String]

    var mouseEventListener : IMouseEventListener = null
    var keyboardEventListener : IKeyboardListener = null

    initialize()

    /**
     * Called from constructor. Set up anything needed here
     */
    def initialize() : Unit

    /**
     * Called to render the component
     */
    def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) : Unit

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    def renderOverlay(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) : Unit

    /**
     * Used to find how wide this is
      *
      * @return How wide the component is
     */
    def getWidth: Int

    /**
     * Used to find how tall this is
      *
      * @return How tall the component is
     */
    def getHeight: Int

    /**
      * Used to get what area is being displayed, mainly used for JEI
      */
    def getArea(guiLeft : Int, guiTop : Int) : Rectangle =
        new Rectangle(xPos + guiLeft, yPos + guiTop, getWidth, getHeight)

    /**
     * Set the handler for the mouse input
      *
      * @param listener The listener to set
     */
    def setMouseEventListener(listener: IMouseEventListener) : Unit = mouseEventListener = listener

    /**
     * Get the Mouse Listener
      *
      * @return The listener, null if none is assigned
     */
    def getMouseEventListener: IMouseEventListener = mouseEventListener

    /**
     * Set the handler for the keyboard input
      *
      * @param listener The keyboard listener
     */
    def setKeyBoardListener(listener: IKeyboardListener) : Unit = keyboardEventListener = listener

    /**
     * Get the keyboard listener
      *
      * @return Keyboard listener, null if none is assigned
     */
    def getKeyBoardListener: IKeyboardListener = keyboardEventListener

    /**
     * Render the tooltip if you can
      *
      * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    def renderToolTip(mouseX: Int, mouseY: Int, parent: GuiScreen) : Unit = {
        if (toolTip != null && toolTip.nonEmpty) {
            drawHoveringText(toolTip, mouseX, mouseY, parent, Minecraft.getMinecraft.fontRendererObj)
        }
        else if (getDynamicToolTip(mouseX, mouseY) != null) drawHoveringText(getDynamicToolTip(mouseX, mouseY), mouseX, mouseY, parent, Minecraft.getMinecraft.fontRendererObj)
    }

    /**
     * Called when the mouse is pressed
      *
      * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    def mouseDown(x: Int, y: Int, button: Int) : Unit = {
        if (mouseEventListener != null) mouseEventListener.onMouseDown(this, x, y, button)
    }

    /**
     * Called when the mouse button is over the component and released
      *
      * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    def mouseUp(x: Int, y: Int, button: Int) : Unit = {
        if (mouseEventListener != null) mouseEventListener.onMouseUp(this, x, y, button)
    }

    /**
     * Called when the user drags the component
      *
      * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    def mouseDrag(x: Int, y: Int, button: Int, time: Long) : Unit = {
        if (mouseEventListener != null) mouseEventListener.onMouseDrag(this, x, y, button, time)
    }

    /**
     * Used when a key is pressed
      *
      * @param letter The letter
     * @param keyCode The code
     */
    def keyTyped(letter: Char, keyCode: Int) : Unit = {
        if (keyboardEventListener != null) keyboardEventListener.keyTyped(this, letter, keyCode)
    }

    /**
     * Used to check if the mouse if currently over the component
     *
     * You must have the getWidth() and getHeight() functions defined for this to work properly
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @return True if mouse if over the component
     */
    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        mouseX >= xPos && mouseX < xPos + getWidth && mouseY >= yPos && mouseY < yPos + getHeight
    }

    /**
     * Get the X Position
      *
      * @return X Position
     */
    def getXPos: Int = xPos

    /**
     * Set the X Position
      *
      * @param xPos Position
     */
    def setXPos(xPos: Int) : Unit = this.xPos = xPos

    /**
     * Returns the Y Position of this component
      *
      * @return Y Position
     */
    def getYPos: Int = yPos

    /**
     * Set the Y Position
      *
      * @param yPos Position
     */
    def setYPos(yPos: Int) : Unit = this.yPos = yPos

    /**
     * Get the Tool Tip for this component
      *
      * @return A list of strings that is the tooltip
     */
    def getToolTip: ArrayBuffer[String] = toolTip

    /**
     * Overwrite this when declaring the component to have this called for the tooltip
      *
      * @return The tooltip list
     */
    def getDynamicToolTip(mouseX: Int, mouseY: Int): ArrayBuffer[String] = null

    /**
     * Set the tooltip to the given list
      *
      * @param tip The new tooltip
     */
    def setToolTip(tip: ArrayBuffer[String]) : Unit = toolTip = tip

    /**
     * Used to draw a tooltip over this component
      *
      * @param tip The list of strings to render
     * @param mouseX The mouse x Position
     * @param mouseY The mouse Y position
     * @param parent The parent GUI
     * @param font The font renderer
     */
    protected def drawHoveringText(tip: ArrayBuffer[String], mouseX: Int, mouseY: Int, parent: GuiScreen, font: FontRenderer) {
        if (tip.nonEmpty) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
            RenderHelper.disableStandardItemLighting()
            GL11.glDisable(GL11.GL_LIGHTING)
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            var k: Int = 0
            for (aTip <- tip) {
                val s: String = aTip.asInstanceOf[String]
                val l: Int = font.getStringWidth(s)
                if (l > k) {
                    k = l
                }
            }
            var j2: Int = mouseX + 12
            var k2: Int = mouseY - 12
            var i1: Int = 8
            if (tip.size > 1) {
                i1 += 2 + (tip.size - 1) * 10
            }
            if (j2 + k > parent.width) {
                j2 -= 28 + k
            }
            if (k2 + i1 + 6 > parent.height) {
                k2 = this.getHeight - i1 - 6
            }
            this.zLevel = 300.0F
            val j1: Int = -267386864
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1)
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1)
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1)
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1)
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1)
            val k1: Int = 1347420415
            val l1: Int = (k1 & 16711422) >> 1 | k1 & -16777216
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1)
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1)
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1)
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1)

            for(i2 <- tip.indices) {
                val s1 = tip(i2)
                font.drawStringWithShadow(s1, j2, k2, -1)
                if(i2 == 0)
                    k2 += 2
                k2 += 10
            }

            this.zLevel = 0.0F
            GL11.glEnable(GL11.GL_LIGHTING)
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            RenderHelper.enableStandardItemLighting()
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
        }
    }
}
