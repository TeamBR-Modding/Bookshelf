package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
import org.lwjgl.opengl.GL11

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 3/16/2016
  */
class GuiComponentColoredZone(x: Int, y: Int, var width: Int, var height: Int, var barColor : Color)
        extends BaseComponent(x, y) {
    protected val u: Int = 0
    protected val v: Int = 80

    def getDynamicColor: Color = barColor

    override def initialize() : Unit = {}

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        barColor = getDynamicColor
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        GL11.glPushMatrix()
        GL11.glScaled(width, height, 1)
        RenderUtils.setColor(barColor)
        drawTexturedModalRect(0, 0, 6, 86, 1, 1)
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        GL11.glPopMatrix()
        GL11.glPopMatrix()
    }

    def renderOverlay(i: Int, i1: Int, mouseX : Int, mouseY : Int) {}

    def getWidth: Int = width

    def getHeight: Int = height
}