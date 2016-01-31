package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.{BaseComponent, NinePatchRenderer}
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.{Gui, GuiScreen}
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.{GL11, GL12}

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
class GuiTab(var gui: GuiBase[_<: Container], var x : Int, var y : Int, var expandedWidth : Int, var expandedHeight : Int, var color : Color, var stack: ItemStack)
        extends BaseComponent(x, y) {

    protected var active: Boolean = false
    protected var currentWidth: Int = 24
    protected var currentHeight: Int = 24
    protected val FOLDED_WIDTH: Int = 24
    protected val FOLDED_HEIGHT: Int = 24
    protected var dWidth: Double = FOLDED_WIDTH
    protected var dHeight: Double = FOLDED_HEIGHT

    protected var itemRenderer: RenderItem = Minecraft.getMinecraft.getRenderItem

    protected var children = new ArrayBuffer[BaseComponent]
    protected var boxRenderer = new NinePatchRenderer() {
        protected override def renderTopLeftCorner(gui: Gui) {}

        protected override def renderBottomLeftCorner(gui: Gui, height: Int) {}

        protected override def renderLeftEdge(gui: Gui, height: Int) {}
    }

    /**
     * Can the tab render the children
     * @return True if expanded and can render
     */
    def areChildrenActive: Boolean = active && currentWidth == expandedWidth && currentHeight == expandedHeight

    /**
     * Add a component to render
     * @param component What to add
     */
    def addChild(component: BaseComponent) : Unit = children += component

    override def initialize() : Unit =  {}

    override def render(x: Int, y: Int, mouseX : Int, mouseY : Int) : Unit = {
        GL11.glPushMatrix()
        val targetWidth: Double = if (active) expandedWidth else FOLDED_WIDTH
        val targetHeight: Double = if (active) expandedHeight else FOLDED_HEIGHT
        if (currentWidth != targetWidth) dWidth += (targetWidth - dWidth) / 4
        if (currentHeight != targetHeight) dHeight += (targetHeight - dHeight) / 4
        currentWidth = dWidth.round.toInt
        currentHeight = dHeight.round.toInt
        RenderUtils.bindGuiComponentsSheet()
        boxRenderer.render(this, 0, 0, currentWidth, currentHeight, color)
        GL11.glColor4f(1, 1, 1, 1)
        if (stack != null) {
            RenderHelper.enableGUIStandardItemLighting()
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
            itemRenderer.renderItemAndEffectIntoGUI(stack, 4, 3)
            GL11.glColor3f(1, 1, 1)
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
            GL11.glDisable(GL11.GL_LIGHTING)
        }
        if (areChildrenActive) {
            RenderUtils.prepareRenderState()
            for (component <- children) {
                component.render(0, 0, mouseX, mouseY)
                RenderUtils.restoreRenderState()
            }
        }
        GL11.glPopMatrix()
    }

    override def renderOverlay(i: Int, i1: Int, mouseX : Int, mouseY : Int) {
        if (areChildrenActive) {
            RenderUtils.prepareRenderState()
            for (component <- children) {
                component.renderOverlay(0, 0, mouseX, mouseY)
                RenderUtils.restoreRenderState()
            }
        }
    }

    /**
     * Render the tooltip if you can
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    override def renderToolTip(mouseX: Int, mouseY: Int, parent: GuiScreen) {
        if (areChildrenActive) {
            for (component <- children) {
                if (component.isMouseOver(mouseX - xPos - parent.asInstanceOf[GuiBase[_ <: Container]].getGuiLeft, mouseY - yPos - parent.asInstanceOf[GuiBase[_ <: Container]].getGuiTop)) component.renderToolTip(mouseX, mouseY, parent)
            }
        }
        else {
            super.renderToolTip(mouseX, mouseY, parent)
        }
    }

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    def mouseDownActivated(x: Int, y: Int, button: Int): Boolean = {
        if (areChildrenActive) {
            for (component <- children) if (component.isMouseOver(x, y)) {
                component.mouseDown(x, y, button)
                return true
            }
        }
        false
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    def mouseUpActivated(x: Int, y: Int, button: Int): Boolean = {
        if (areChildrenActive) {
            for (component <- children) if (component.isMouseOver(x, y)) {
                component.mouseUp(x, y, button)
                return true
            }
        }
        false
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    def mouseDragActivated(x: Int, y: Int, button: Int, time: Long): Boolean = {
        if (areChildrenActive) {
            for (component <- children) if (component.isMouseOver(x, y)) {
                component.mouseDrag(x, y, button, time)
                return true
            }
        }
        false
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    override def keyTyped(letter: Char, keyCode: Int) {
        for (component <- children) component.keyTyped(letter, keyCode)
    }

    /**
     * Used to find how wide this is
     * @return How wide the component is
     */
    override def getWidth: Int = currentWidth

    /**
     * Used to find how tall this is
     * @return How tall the component is
     */
    override def getHeight: Int = currentHeight

    def setActive(b: Boolean) : Unit = this.active = b
}
