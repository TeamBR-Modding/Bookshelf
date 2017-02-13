package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
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
  *
  * @param label What to display
  * @param x X Position
  * @param y Y Position
  */
class GuiComponentText(var label : String, var x : Int, var y : Int) extends BaseComponent(x, y) {

    protected val fontRenderer: FontRenderer = Minecraft.getMinecraft.fontRendererObj
    var colorDefault = 4210752
    var color : Color = null

    /**
      * @param textColor HEX value of the color, eg 0x000000 for white
      */
    def this(text : String, X : Int, Y : Int, textColor : Color) {
        this(text, X, Y)
        this.color = textColor
    }

    /**
      * Called from constructor. Set up anything needed here
      */
    override def initialize(): Unit = {}

    /**
      * Called to render the component
      */
    override def render(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int): Unit = {}

    /**
      * Called after base render, is already translated to guiLeft and guiTop, just move offset
      */
    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX : Int, mouseY : Int): Unit = {
        GL11.glPushMatrix()

        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.prepareRenderState()
        if(color != null)
            RenderUtils.setColor(color)
        else
            RenderUtils.restoreColor()
        fontRenderer.drawString(label, 0, 0, colorDefault)
        RenderUtils.restoreColor()
        GL11.glPopMatrix()
    }

    /**
      * Used to find how wide this is
      * @return How wide the component is
      */
    override def getWidth: Int = fontRenderer.getStringWidth(label)

    /**
      * Used to find how tall this is
      * @return How tall the component is
      */
    override def getHeight: Int = 7

    def setText(text : String) = {
        label = text
    }
}
