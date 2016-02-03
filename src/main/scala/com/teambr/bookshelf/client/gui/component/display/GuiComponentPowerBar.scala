package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.client.gui.component.{NinePatchRenderer, BaseComponent}
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
abstract class GuiComponentPowerBar(x: Int, y: Int, var width: Int, var height: Int, var barColor : Color)
    extends BaseComponent(x, y) {
    protected val u: Int = 0
    protected val v: Int = 80

    var renderer: NinePatchRenderer = new NinePatchRenderer(u, v, 3)

    /**
     * Used to get the level full
     * @param scale The height, or what we want to scale the completeness to
     * @return How full, scaled to scale
     */
    def getEnergyPercent(scale: Int): Int

    def setDynamicColor() : Unit = {}

    override def initialize() : Unit = {}

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        setDynamicColor()
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        renderer.render(this, 0, 0, width, height)
        GL11.glPushMatrix()
        GL11.glTranslated(1, height - getEnergyPercent(height) + 1, 0)
        GL11.glScaled(width - 2, getEnergyPercent(height) - 2, 1)
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
