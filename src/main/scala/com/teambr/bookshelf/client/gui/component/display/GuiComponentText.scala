package com.teambr.bookshelf.client.gui.component.display

import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
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
 *
 * @param label What to display
 * @param x X Position
 * @param y Y Position
 */
class GuiComponentText(var label : String, var x : Int, var y : Int) extends BaseComponent(x, y) {

    protected val fontRenderer: FontRenderer = Minecraft.getMinecraft.fontRendererObj
    var color = 4210752

    /**
     * @param hex HEX value of the color, eg 0x000000 for white
     */
    def this(text : String, X : Int, Y : Int, hex : Int) {
        this(text, X, Y)
        color = hex
    }

    /**
     * Called from constructor. Set up anything needed here
     */
    override def initialize(): Unit = {}

    /**
     * Called to render the component
     */
    override def render(guiLeft: Int, guiTop: Int): Unit = {}

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    override def renderOverlay(guiLeft: Int, guiTop: Int): Unit = {
        GL11.glPushMatrix()

        GL11.glTranslated(xPos, yPos, 0)
        RenderUtils.prepareRenderState()
        fontRenderer.drawString(label, 0, 0, color)
        GL11.glDisable(GL11.GL_ALPHA_TEST)

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
}
