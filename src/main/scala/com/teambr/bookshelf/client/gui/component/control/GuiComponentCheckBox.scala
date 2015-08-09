package com.teambr.bookshelf.client.gui.component.control

import java.awt.Color

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.StatCollector
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
abstract class GuiComponentCheckBox(x : Int, y : Int, var label : String, var selected : Boolean) extends BaseComponent(x, y) {
    label = StatCollector.translateToLocal(label)

    /**
     * Called when there is a change in state, use this to set the value on what this controls
     * @param bool The current value of this component
     */
    def setValue (bool : Boolean) : Unit

    override def initialize() : Unit = {}

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX >= xPos + 50 && mouseX < xPos + 60 && mouseY >= yPos && mouseY < yPos + 10) {
            selected = !selected
            setValue(selected)
        }
    }

    override def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        GL11.glPushMatrix()
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glTranslated(50, 0, 0)
        drawTexturedModalRect(0, 0, if (selected) 48 else 40, 0, 8, 8)
        GL11.glEnable(GL11.GL_LIGHTING)
        GL11.glPopMatrix()
        GL11.glPopMatrix()
    }

    override def renderOverlay(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.setColor(new Color(0, 0, 0)) //Minecraft doesn't play nice with GL, so we will just set our own color
        Minecraft.getMinecraft.fontRendererObj.drawString(label, 0, 0, 0x000000)
        GL11.glPopMatrix()
    }

    def getWidth: Int = 58

    def getHeight: Int = 8
}
