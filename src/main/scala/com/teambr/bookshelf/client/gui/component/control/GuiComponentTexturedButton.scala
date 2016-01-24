package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.util.RenderUtils
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
abstract class GuiComponentTexturedButton(x: Int, y: Int, var iconU: Int, var iconV: Int, var iconW: Int, var iconH: Int, width: Int, height: Int)
    extends GuiComponentButton(x, y, width, height, " ") {

    override def render(guiLeft: Int, guiTop: Int) {
        super.render(guiLeft, guiTop)
        GL11.glPushMatrix()
        RenderUtils.prepareRenderState()
        GL11.glTranslated(xPos + 1, yPos + 1, 0)
        RenderUtils.bindGuiComponentsSheet()
        drawTexturedModalRect(0, 0, iconU, iconV, iconW, iconH)
        RenderUtils.restoreRenderState()
        GL11.glPopMatrix()
    }

    def setUV(uv : (Int, Int)) = {
        iconU = uv._1
        iconV = uv._2
    }
}
