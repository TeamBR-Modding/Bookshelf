package com.teambr.bookshelf.client.gui.component.control

import com.teambr.bookshelf.util.RenderUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{GlStateManager, RenderHelper}
import net.minecraft.item.ItemStack

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 2/18/2016
  */
abstract class GuiComponentItemStackButton(x : Int, y : Int, var stack : ItemStack)
        extends GuiComponentButton(x, y, 20, 20, " ") {

    override def renderOverlay(guiLeft: Int, guiTop: Int, mouseX: Int, mouseY: Int) {
        super.renderOverlay(guiLeft, guiTop, mouseX, mouseY)
        GlStateManager.pushMatrix()
        GlStateManager.pushAttrib()
        GlStateManager.translate(x + 2, y + 2, 1.0)

        RenderHelper.enableGUIStandardItemLighting()

        Minecraft.getMinecraft.getRenderItem.renderItemAndEffectIntoGUI(stack, 0, 0)

        RenderHelper.disableStandardItemLighting()

        GlStateManager.popAttrib()
        GlStateManager.popMatrix()
        RenderUtils.restoreColor()
    }

    def setStack(stackIn : ItemStack): Unit = {
        stack = stackIn
    }
}
