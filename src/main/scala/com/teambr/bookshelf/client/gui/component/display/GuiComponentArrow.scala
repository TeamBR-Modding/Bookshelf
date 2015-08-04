package com.teambr.bookshelf.client.gui.component.display

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
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
abstract class GuiComponentArrow(x: Int, y: Int) extends BaseComponent(x, y) {
    protected val u: Int = 28
    protected val v: Int = 239

    /**
     * Used to get how far along the process is, scale to 24
     * @return How complete, scaled to 24
     */
    def getCurrentProgress: Int

    override def initialize() : Unit = {}

    override def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        drawTexturedModalRect(0, 0, u, v, getWidth, getHeight)
        drawTexturedModalRect(-1, 0, u + getWidth, v, getCurrentProgress + 1, getHeight)
        GL11.glPopMatrix()
    }

    override def renderOverlay(i: Int, i1: Int) {}

    override def getWidth: Int = 23

    override def getHeight: Int = 17
}
