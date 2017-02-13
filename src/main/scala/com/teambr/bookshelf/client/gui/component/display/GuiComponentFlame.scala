package com.teambr.bookshelf.client.gui.component.display

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
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
abstract class GuiComponentFlame(x: Int, y: Int) extends BaseComponent(x, y) {
    protected val u: Int = 0
    protected val v: Int = 242

    /**
     * Used to get the current burn time
     * @return How complete, scaled to 14
     */
    def getCurrentBurn: Int

    override def initialize() : Unit = {}

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        drawTexturedModalRect(0, 0, u, v, getWidth, getHeight)
        drawTexturedModalRect(0, 14 - getCurrentBurn, u + 14, v + 14 - getCurrentBurn, getWidth, getCurrentBurn)
        GL11.glPopMatrix()
    }

    def renderOverlay(i: Int, i1: Int, mouseX : Int, mouseY : Int) {}

    def getWidth: Int = 14

    def getHeight: Int = 14
}
