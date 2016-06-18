package com.teambr.bookshelf.helper

import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.init.SoundEvents
import net.minecraftforge.fluids.FluidTank
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
object GuiHelper {

    def renderFluid(tank: FluidTank, x: Int, y: Int, maxHeight: Int, maxWidth: Int): Unit = {
        val fluid = tank.getFluid
        if(fluid != null) {
            GL11.glPushMatrix()
            val level = (fluid.amount * maxHeight) / tank.getCapacity
            val icon :TextureAtlasSprite = Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(fluid.getFluid.getStill(fluid).toString)
            RenderUtils.bindMinecraftBlockSheet()
            setGLColorFromInt(fluid.getFluid.getColor(fluid))

            val timesW = Math.floor(maxWidth / 16).asInstanceOf[Int]
            var cutW = 16

            for(j <- 0 to timesW) {
                if(j == timesW)
                    cutW = maxWidth % 16
                if(level >= 16) {
                    val times = Math.floor(level / 16).asInstanceOf[Int]
                    for(i <- 1 to times) {
                        drawIconWithCut(icon, x + (j * 16), y - (16 * i), cutW, 16, 0)
                    }
                    val cut = level % 16
                    drawIconWithCut(icon, x + (j * 16), y - (16 * (times + 1)), cutW, 16, 16 - cut)
                } else {
                    val cut = level % 16
                    drawIconWithCut(icon, x + (j * 16), y - 16, cutW, 16, 16 - cut)
                }
            }
            GL11.glPopMatrix()
        }
    }

    /**
     * Draws the given icon with optional cut
      *
      * @param icon
     * @param x
     * @param y
     * @param width keep width of icon
     * @param height keep height of icon
     * @param cut 0 is full icon, 16 is full cut
     */
    private def drawIconWithCut(icon: TextureAtlasSprite, x: Int, y: Int, width: Int, height: Int, cut: Int) {
        val tess = Tessellator.getInstance()
        val renderer = tess.getBuffer
        renderer.begin(GL11.GL_QUADS, RenderUtils.POSITION_TEX_NORMALF)
        renderer.pos(x, y + height, 0).tex(icon.getMinU, icon.getInterpolatedV(height)).normal(0, -1, 0).endVertex()
        renderer.pos(x + width, y + height, 0).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(height)).normal(0, -1, 0).endVertex()
        renderer.pos(x + width, y + cut, 0).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(cut)).normal(0, -1, 0).endVertex()
        renderer.pos(x, y + cut, 0).tex(icon.getMinU, icon.getInterpolatedV(cut)).normal(0, -1, 0).endVertex()
        tess.draw()
    }

    private def setGLColorFromInt(color: Int) {
        val red: Float = (color >> 16 & 255) / 255.0F
        val green: Float = (color >> 8 & 255) / 255.0F
        val blue: Float = (color & 255) / 255.0F
        GL11.glColor4f(red, green, blue, 1.0F)
    }

    /**
     * Test if location is in bounds
      *
      * @param x xLocation
     * @param y yLocation
     * @param a Rectangle point a
     * @param b Rectangle point b
     * @param c Rectangle point c
     * @param d Rectangle point d
     * @return
     */
    def isInBounds(x: Int, y: Int, a: Int, b: Int, c: Int, d: Int): Boolean = x >= a && x <= c && y >= b && y <= d

    /**
     * Plays the button click sounds
     */
    def playButtonSound : Unit =
        Minecraft.getMinecraft.getSoundHandler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F))
}
