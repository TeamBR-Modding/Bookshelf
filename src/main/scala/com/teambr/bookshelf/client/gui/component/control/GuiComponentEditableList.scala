package com.teambr.bookshelf.client.gui.component.control

import java.awt.Color

import com.teambr.bookshelf.client.gui.component.{NinePatchRenderer, BaseComponent}
import com.teambr.bookshelf.helper.GuiHelper
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.StatCollector
import org.lwjgl.opengl.GL11

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
abstract class GuiComponentEditableList[T <: AnyRef](x : Int, y : Int, var list : ArrayBuffer[T])
        extends BaseComponent(x, y) {

    protected var width = 135
    protected var height = 120

    var currentScroll = 0

    var selected : T = _

    var renderer = new NinePatchRenderer(0, 80, 3)

    var inputBox = new GuiComponentTextBox(xPos, yPos, 100, 16) {
        def fieldUpdated(value: String) : Unit = {}
    }

    var saveButton = new GuiComponentButton(xPos + 105, yPos - 3, 30, 20, StatCollector.translateToLocal("Save")) {
        def doAction() {
            valueSaved(list, inputBox.getValue)
            inputBox.getTextField.setText("")
            GuiHelper.playButtonSound
        }
    }

    var scrollBar = new GuiComponentScrollBar(xPos + 121, yPos + 20, 100) {
        def onScroll(position: Float) {
            currentScroll = {
                currentPosition = (position * (this.getHeight - 2)).toInt
                currentPosition
            }
        }
    }

    /**
     * Called when the user selects to save the current input
     *
     * Convert the string to what you need here and save it to the list
     *
     * @param listToSaveTo What you should save the new entry into
     * @param valuePassed What was in the text field when the user pressed save
     */
    def valueSaved(listToSaveTo: ArrayBuffer[T], valuePassed: String)

    /**
     * Called when the user selects to delete something, it is then up to you to remove things
     * @param listFrom What we are removing from
     * @param valueDeleted The value being deleted
     */
    def valueDeleted(listFrom: ArrayBuffer[T], valueDeleted: T)

    /**
     * This is where you should convert the object into what you want to display
     *
     * toString() is not always what you want, so here you can convert things
     * @param object The object to convert to string
     * @return What you want to display
     */
    def convertObjectToString(`object`: T): String

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, mouseY: Int, button: Int) {
        inputBox.mouseDown(mouseX, mouseY, button)
        if (scrollBar.isMouseOver(mouseX, mouseY)) scrollBar.mouseDown(mouseX, mouseY, button)
        if (saveButton.isMouseOver(mouseX, mouseY)) saveButton.mouseDown(mouseX, mouseY, button)
        var selectionY: Int = mouseY - yPos - 20
        selectionY -= selectionY % 20
        if (GuiHelper.isInBounds(mouseX, mouseY, 0, 60, 115, 160)) {
            if (list.nonEmpty) {
                val test: Int = currentScroll / list.size + selectionY / 20
                if (list.size > test) {
                    selected = list(test)
                    GuiHelper.playButtonSound
                }
            }
        }
        val mouseY1 = mouseY - 60
        if (GuiHelper.isInBounds(mouseX, mouseY1, 115, selectionY, 130, selectionY + 20)) {
            if (selected != null) {
                valueDeleted(list, selected)
                GuiHelper.playButtonSound
            }
        }
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    override def mouseDrag(x: Int, y: Int, button: Int, time: Long) {
        inputBox.mouseDrag(x, y, button, time)
        if (scrollBar.isMouseOver(x, y)) scrollBar.mouseDrag(x, y, button, time)
        if (saveButton.isMouseOver(x, y)) saveButton.mouseDrag(x, y, button, time)
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseUp(x: Int, y: Int, button: Int) {
        inputBox.mouseUp(x, y, button)
        if (scrollBar.isMouseOver(x, y)) scrollBar.mouseUp(x, y, button)
        if (saveButton.isMouseOver(x, y)) saveButton.mouseUp(x, y, button)
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    override def keyTyped(letter: Char, keyCode: Int) {
        if (keyCode == 28) {
            valueSaved(list, inputBox.getValue)
            inputBox.getTextField.setText("")
            GuiHelper.playButtonSound
        }
        else inputBox.keyTyped(letter, keyCode)
    }

    override def initialize(): Unit = {}

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        RenderUtils.bindGuiComponentsSheet()
        RenderUtils.prepareRenderState()
        renderer.render(this, xPos - 1, yPos + 20, 120, 100, new Color(100, 100, 100))
        RenderUtils.setColor(new Color(255, 255, 255))
        inputBox.render(guiLeft, guiTop, mouseX, mouseY)
        saveButton.render(guiLeft, guiTop, mouseX, mouseY)
        scrollBar.render(guiLeft, guiTop, mouseX, mouseY)
        if (list.nonEmpty) {
            GL11.glTranslated(xPos + 3, yPos + 23, 0)
            var renderY: Int = 0
            val currentElement: Int = currentScroll / list.size

            for(i <- currentElement until list.length) {
                if (renderY <= 81) {}
                if(selected != null && selected.equals(list(i)))
                    renderSelection(renderY)
                Minecraft.getMinecraft.fontRendererObj.drawString(this.convertObjectToString(list(i)), 1, renderY + 3, 0xBBBBBB)
                RenderUtils.restoreRenderState()
                renderY += 20
            }
        }

        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        RenderUtils.bindGuiComponentsSheet()
        RenderUtils.prepareRenderState()
        inputBox.renderOverlay(guiLeft, guiTop, mouseX, mouseY)
        saveButton.renderOverlay(guiLeft, guiTop, mouseX, mouseY)
        scrollBar.renderOverlay(guiLeft, guiTop, mouseX, mouseY)
        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    def getWidth: Int = width

    def getHeight: Int = height

    def getScrollBar: GuiComponentScrollBar = scrollBar

    def getInputBox: GuiComponentTextBox = inputBox

    private def renderSelection(selectionY: Int) {
        GL11.glPushMatrix()

        val mc: Minecraft = Minecraft.getMinecraft
        val scale: Int = new ScaledResolution(mc).getScaleFactor
        GL11.glLineWidth(scale * 1.3F)

        GL11.glTranslated(-3, -1, 5)
        RenderUtils.setColor(new Color(235, 235, 235))

        GL11.glBegin(GL11.GL_LINES)
        GL11.glVertex2d(0, selectionY)
        GL11.glVertex2d(117, selectionY)
        GL11.glVertex2d(117, selectionY)
        GL11.glVertex2d(117, selectionY + 17)
        GL11.glVertex2d(117, selectionY + 17)
        GL11.glVertex2d(0, selectionY + 17)
        GL11.glVertex2d(1, selectionY + 17)
        GL11.glVertex2d(1, selectionY)

        RenderUtils.setColor(new Color(255, 10, 10))
        GL11.glVertex2d(99, selectionY + 3)
        GL11.glVertex2d(109, selectionY + 13)

        GL11.glVertex2d(99, selectionY + 13)
        GL11.glVertex2d(109, selectionY + 3)
        GL11.glEnd()

        RenderUtils.setColor(new Color(255, 255, 255))
        GL11.glPopMatrix()
    }
}
