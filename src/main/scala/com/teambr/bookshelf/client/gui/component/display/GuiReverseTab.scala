package com.teambr.bookshelf.client.gui.component.display

import java.awt.Color

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer
import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.{GL11, GL12}

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
class GuiReverseTab(gui: GuiBase[_ <: Container], x : Int, y : Int, expandedWidth : Int, expandedHeight : Int, color : Color, stack: ItemStack)
        extends GuiTab(gui, x, y, expandedWidth, expandedHeight, color, stack) {

    boxRenderer = new NinePatchRenderer() {
        protected override def renderTopRightCorner(gui: Gui, width: Int) {
        }

        protected override def renderBottomRightCorner(gui: Gui, width: Int, height: Int) {
        }

        protected override def renderRightEdge(gui: Gui, width: Int, height: Int) {
        }
    }

    override def render(x: Int, y: Int, mouseX : Int, mouseY : Int) {
        GL11.glPushMatrix()
        val targetWidth: Double = if (active) expandedWidth else FOLDED_WIDTH
        val targetHeight: Double = if (active) expandedHeight else FOLDED_HEIGHT
        if (currentWidth != targetWidth) dWidth += (targetWidth - dWidth) / 4
        if (currentHeight != targetHeight) dHeight += (targetHeight - dHeight) / 4
        currentWidth = dWidth.round.toInt
        currentHeight = dHeight.round.toInt
        RenderUtils.bindGuiComponentsSheet()
        boxRenderer.render(this, -currentWidth, 0, currentWidth, currentHeight, color)
        GL11.glColor4f(1, 1, 1, 1)
        if (stack != null) {
            RenderHelper.enableGUIStandardItemLighting()
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
            itemRenderer.renderItemAndEffectIntoGUI(stack, -21, 3)
            GL11.glColor3f(1, 1, 1)
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
            GL11.glDisable(GL11.GL_LIGHTING)
        }
        if (areChildrenActive) {
            RenderUtils.prepareRenderState()
            GL11.glTranslated(-expandedWidth, 0, 0)
            for (component <- children) {
                component.render(-expandedWidth, 0, mouseX, mouseY)
                RenderUtils.restoreRenderState()
            }
        }
        GL11.glPopMatrix()
    }

    override def renderOverlay(i: Int, i1: Int, mouseX : Int, mouseY : Int) {
        if (areChildrenActive) {
            RenderUtils.prepareRenderState()
            GL11.glTranslated(-expandedWidth, 0, 0)
            for (component <- children) {
                component.renderOverlay(-expandedWidth, 0, mouseX, mouseY)
                RenderUtils.restoreRenderState()
            }
        }
    }

    override def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        return mouseX >= xPos - currentWidth && mouseX < xPos && mouseY >= yPos && mouseY < yPos + getHeight
    }
}
