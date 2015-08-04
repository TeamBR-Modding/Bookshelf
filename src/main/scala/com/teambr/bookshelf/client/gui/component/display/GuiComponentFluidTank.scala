package com.teambr.bookshelf.client.gui.component.display

import com.teambr.bookshelf.client.gui.component.{NinePatchRenderer, BaseComponent}
import com.teambr.bookshelf.helper.GuiHelper
import com.teambr.bookshelf.util.RenderUtils
import net.minecraftforge.fluids.FluidTank
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
class GuiComponentFluidTank(x: Int, y: Int, var width: Int, var height: Int, var tank: FluidTank)
    extends BaseComponent(x, y) {
    protected val u: Int = 0
    protected val v: Int = 80

    var renderer: NinePatchRenderer = new NinePatchRenderer(u, v, 3)

    override def initialize() : Unit = {}

    override def render(guiLeft: Int, guiTop: Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        renderer.render(this, 0, 0, width, height)
        GuiHelper.renderFluid(tank, 1, height - 1, height - 2, width - 2)
        GL11.glPopMatrix()
    }

    def renderOverlay(guiLeft: Int, guiTop: Int) {}

    def getWidth: Int = width

    def getHeight: Int = height
}
