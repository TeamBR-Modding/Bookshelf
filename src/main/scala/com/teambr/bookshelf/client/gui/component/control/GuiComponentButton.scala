package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.client.gui.component.{BaseComponent, NinePatchRenderer}
import com.teambr.bookshelf.helper.GuiHelper
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.text.translation.I18n
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
abstract class GuiComponentButton(x: Int, y: Int, var width: Int, var height: Int, var label: String) extends BaseComponent(x, y) {
    private var isOver = false

    val u = 0
    val v = 100
    var renderer: NinePatchRenderer = new NinePatchRenderer(u, v, 4)
    var rendererOver: NinePatchRenderer = new NinePatchRenderer(u, v + 9, 4)

    label = I18n.translateToLocal(label)

    /**
     * Called when button is pressed
     */
    def doAction() : Unit

    override def initialize() {}

    /**
     * Called when the mouse is pressed
 *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    override def mouseDown(mouseX: Int, mouseY: Int, button: Int) {
        if (mouseX >= xPos && mouseX < xPos + width && mouseY >= yPos && mouseY < yPos + height) {
            GuiHelper.playButtonSound
            doAction()
        }
    }

    override def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        if (mouseX >= xPos && mouseX < xPos + getWidth && mouseY >= yPos && mouseY < yPos + getHeight) {
            isOver = true
            return true
        }
        isOver = false
        false
    }

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        RenderUtils.prepareRenderState()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        if (isOver) rendererOver.render(this, 0, 0, width, height)
        else renderer.render(this, 0, 0, width, height)
        isOver = false
        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        val size: Int = Minecraft.getMinecraft.fontRendererObj.getStringWidth(label)
        GL11.glTranslated(xPos + (width / 2 - size / 2), yPos + 6, 0)
        RenderUtils.prepareRenderState()
        Minecraft.getMinecraft.fontRendererObj.drawString(label, 0, 0, 0xFFFFFF)
        GL11.glPopMatrix()
    }

    def getWidth: Int = width

    def getHeight: Int = height

    def setText(text : String) = {
        label = text
    }
}
