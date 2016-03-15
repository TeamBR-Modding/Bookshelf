package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.util.{ColorUtils, RenderUtils}
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer

/**
  * This file was created for NeoTech
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 2/18/2016
  */
abstract class GuiComponentPowerBarGradient(x: Int, y: Int, width: Int, height: Int, colorFull: Color)
        extends GuiComponentPowerBar(x, y, width, height, colorFull) {

    val colors = new ArrayBuffer[Color]()
    addColor(colorFull)

    /**
      * Adds a color to the gradient, this will be a stage of the gradient. So if you only add one, it will go from it
      * to the defined color, three will go from the last entered, through the list, and to the first
      *
      * @param color
      */
    def addColor(color : Color) : Unit = {
        colors += color
    }

    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        renderer.render(this, 0, 0, width, height)

        for(position <- 0 to getEnergyPercent(height) - 3) {
            GL11.glPushMatrix()
            GL11.glTranslated(1, height - 2 - position, 0)
            GL11.glScaled(width - 2, 1, 1)
            RenderUtils.setColor(getColorForPosition(position))
            drawTexturedModalRect(0, 0, 6, 86, 1, 1)
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
            GL11.glPopMatrix()
        }
        GL11.glPopMatrix()
    }

    def getColorForPosition(pos : Int) : Color = {
        // Get how far into the gradient
        var colorPosition = pos * colors.size / height
        colorPosition = colors.size - 1 - colorPosition
        val colorStart = colors(Math.min(colorPosition, colors.size - 1))
        val colorEnd = if(colorPosition - 1 >= 0) colors(colorPosition - 1) else colors(colorPosition)
        ColorUtils.getColorBetween(colorStart, colorEnd, (pos - ((height / colors.size) * (pos * colors.size / height))) * 1F / (height / colors.size).toFloat)
    }
}
