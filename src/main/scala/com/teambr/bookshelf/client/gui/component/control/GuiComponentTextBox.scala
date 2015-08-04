package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.BaseComponent
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
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
abstract class GuiComponentTextBox(x: Int, y: Int, var width: Int, var height: Int)
    extends BaseComponent(x, y) {

    protected var textField = new GuiTextField(0, Minecraft.getMinecraft.fontRendererObj, x, y, width, height)
    /**
     * Called when the value has changed
     * @param value The current text in the field
     */
    def fieldUpdated (value: String) : Unit

    /**
     * Get the current text
     * @return What is in the field
     */
    def getValue : String = textField.getText

    /**
     * Set the value in the field
     * @param str The new string
     */
    def setValue(str: String) : Unit = textField.setText(str)

    /**
     * Get the text field
     * @return The text field (vanilla version)
     */
    def getTextField: GuiTextField = textField

    override def initialize() : Unit = {}

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, y: Int, button: Int) {
        textField.mouseClicked(mouseX, y, button)
        if (button == 1 && textField.isFocused) {
            textField.setText("")
            fieldUpdated(textField.getText)
        }
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    override def keyTyped(letter: Char, keyCode: Int) {
        if (textField.isFocused) {
            textField.textboxKeyTyped(letter, keyCode)
            fieldUpdated(textField.getText)
        }
    }

    override def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        textField.drawTextBox()
        GL11.glDisable(GL11.GL_ALPHA_TEST)
        GL11.glPopMatrix()
    }

    override def renderOverlay(guiLeft: Int, guiTop: Int) : Unit = {}

    def getWidth: Int = width

    def getHeight: Int = height

    override def isMouseOver(mouseX: Int, mouseY: Int): Boolean = true
}
