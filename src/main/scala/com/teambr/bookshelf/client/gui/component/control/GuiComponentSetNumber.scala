package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.helper.GuiHelper
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.opengl.GL11

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
abstract class GuiComponentSetNumber(x: Int, y: Int, var width: Int, var value: Int, var floor: Int, var ceiling: Int)
    extends BaseComponent(x, y) {

    val height = 16
    var textField = new GuiTextField(0, Minecraft.getMinecraft.fontRendererObj, x, y, width - 10, height)
    textField.setText(String.valueOf(value))
    var upSelected = false
    var downSelected = false

    /**
     * Called when the value is changed
     * @param i The value set
     */
    def setValue(i: Int)

    override def initialize() : Unit = {}

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, y: Int, button: Int) {
        if (GuiHelper.isInBounds(mouseX, y, xPos + width - 8, yPos - 1, xPos + width + 2, yPos + 7)) {
            upSelected = true

            if (value < ceiling)
                value += 1

            GuiHelper.playButtonSound
            setValue(value)
            textField.setText(String.valueOf(value))
        }
        else if (GuiHelper.isInBounds(mouseX, y, xPos + width - 8, yPos + 9, xPos + width + 2, yPos + 17)) {
            downSelected = true
            if (value > floor)
                value -= 1

            GuiHelper.playButtonSound
            setValue(value)
            textField.setText(String.valueOf(value))
        }
        textField.mouseClicked(mouseX, y, button)
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseUp(x: Int, y: Int, button: Int) {
        upSelected = {
            downSelected = false;
            downSelected
        }
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    override def keyTyped(letter: Char, keyCode: Int) {
        if (Character.isLetter(letter) && (keyCode != 8 && keyCode != 109)) return
        if (textField.isFocused) textField.textboxKeyTyped(letter, keyCode)
        if (textField.getText == null || (textField.getText == "") || !isNumeric(textField.getText)) {
            textField.setTextColor(0xE62E00)
            return
        }
        if (keyCode == 13) textField.setFocused(false)
        textField.setTextColor(0xFFFFFF)
        if (Integer.valueOf(textField.getText) > ceiling) textField.setText(String.valueOf(ceiling))
        else if (Integer.valueOf(textField.getText) < floor) textField.setText(String.valueOf(floor))
        value = Integer.valueOf(textField.getText)
        setValue(value)
    }

    def isNumeric(str: String): Boolean = {
        try {
            val d: Double = str.toDouble
        }
        catch {
            case nfe: NumberFormatException => {
                return false
            }
        }
        true
    }

    override def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        drawTexturedModalRect(width - 8, -1, if (upSelected) 67 else 56, 0, 11, 8)
        drawTexturedModalRect(width - 8, 9, if (downSelected) 67 else 56, 8, 11, 8)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        textField.drawTextBox()
        GL11.glPopMatrix()
    }

    def renderOverlay(guiLeft: Int, guiTop: Int) {}

    def getWidth: Int = 20000

    def getHeight: Int = 20000

    def getValue: Int = value
}
