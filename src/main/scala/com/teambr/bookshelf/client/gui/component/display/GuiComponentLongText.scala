package com.teambr.bookshelf.client.gui.component.display

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.helper.GuiHelper
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.text.translation.I18n
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

/**
  * This file was created for com.teambr.bookshelf.Bookshelf
  *
  * com.teambr.bookshelf.Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 2/2/2016
  */
class GuiComponentLongText(x : Int, y : Int, var text : String, width : Int, height : Int, textScale : Int = 100)
        extends BaseComponent(x, y) {

    var upSelected = false
    var downSelected = false

    lazy val fontRender = Minecraft.getMinecraft.fontRendererObj
    var colorDefault = 4210752
    lazy val lineWidth = (100 / textScale) * (width - 18)
    var currentLine = 0
    lazy val lines = new ArrayBuffer[String]()

    /**
      * Used to create the list of lines we need
      */
    def setupLines() : Unit = {
        text = I18n.translateToLocal(text)
        if(fontRender.getStringWidth(text) < lineWidth)
            lines += text
        else {
            var string = text
            while(fontRender.getStringWidth(string) > lineWidth) {
                var trimmed = fontRender.trimStringToWidth(string, lineWidth)

                val lastSpace = trimmed.lastIndexOf(" ") //Ensure full words
                if(lastSpace != -1)
                    trimmed = trimmed.splitAt(lastSpace)._1

                val newLine = trimmed.indexOf("\n") //Break for new lines
                if(newLine != -1)
                    trimmed = trimmed.splitAt(newLine)._1

                lines += trimmed

                string = string.replace(trimmed, "")
                if(string.charAt(0) == '\n') //Clear leading new lines
                    string = string.replaceFirst("\n", "")
                if(string.charAt(0) == ' ') //Clear leading spaces
                    string = string.replaceFirst(" ", "")

            }
            lines += string
        }
    }

    /**
      * Called from constructor. Set up anything needed here
      */
    override def initialize(): Unit = setupLines()

    /**
      * Called after base render, is already translated to guiLeft and guiTop, just move offset
      */
    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int): Unit = {
        GL11.glPushMatrix()

        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.prepareRenderState()

        val uniCode = fontRender.getUnicodeFlag
        fontRender.setUnicodeFlag(false)

        breakable {
            var yPos = -9
            var actualY = 0
            GlStateManager.scale(textScale / 100.toDouble, textScale / 100.toDouble, textScale / 100.toDouble)
            for (x <- currentLine until lines.size) {
                if(actualY + ((textScale * 9) / 100) > height)
                    break
                RenderUtils.restoreColor()
                val label = lines(x)

                fontRender.drawString(label, 0, yPos + 9,  0xFFFFFF)
                yPos += 9
                actualY += (textScale * 9) / 100
            }
        }

        fontRender.setUnicodeFlag(uniCode)
        GL11.glPopMatrix()
    }

    /**
      * Called to render the component
      */
    override def render(guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int): Unit = {
        GL11.glPushMatrix()
        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.bindGuiComponentsSheet()
        drawTexturedModalRect(width - 15, 0, 56, 24, 15, 8)
        drawTexturedModalRect(width - 15, height - 7, 56, 40, 15, 8)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        GL11.glPopMatrix()
    }

    /**
      * Called when the mouse is pressed
      *
      * @param mouseX Mouse X Position
      * @param y Mouse Y Position
      * @param button Mouse Button
      */
    override def mouseDown(mouseX: Int, y: Int, button: Int) {
        if (GuiHelper.isInBounds(mouseX, y, xPos + width - 15, yPos, xPos + width, yPos + 8)) {
            upSelected = true
            currentLine -= 1
            if(currentLine < 0)
                currentLine = 0
            GuiHelper.playButtonSound
        }
        else if (GuiHelper.isInBounds(mouseX, y, xPos + width - 15, yPos + height - 8, xPos + width, yPos + height)) {
            downSelected = true
            currentLine += 1
            if(currentLine > getLastLineToRender)
                currentLine = getLastLineToRender
            GuiHelper.playButtonSound
        }
    }

    override def mouseScrolled(dir : Int) : Unit = {
        currentLine += dir
        if(currentLine < 0)
            currentLine = 0
        if(currentLine > getLastLineToRender)
            currentLine = getLastLineToRender
    }

    def getLastLineToRender: Int = {
        val maxOnScreen = height / ((textScale * 9) / 100)
        if(lines.size - maxOnScreen > 0) lines.size - maxOnScreen else 0
    }

    /**
      * Called when the mouse button is over the component and released
      *
      * @param x Mouse X Position
      * @param y Mouse Y Position
      * @param button Mouse Button
      */
    override def mouseUp(x: Int, y: Int, button: Int) {
        upSelected = {
            downSelected = false
            downSelected
        }
    }

    /**
      * Used to find how wide this is
      *
      * @return How wide the component is
      */
    override def getWidth: Int = width

    /**
      * Used to find how tall this is
      *
      * @return How tall the component is
      */
    override def getHeight: Int = height
}
