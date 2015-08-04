package com.teambr.bookshelf.util

import java.awt.Color

import com.teambr.bookshelf.lib.Reference
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.{GL12, GL11}

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
 * This helps with tedious GL stuff and binding texture sheets. Just makes things a lot easier in the end
 */
object RenderUtils {
    val GUI_COMPONENTS: ResourceLocation = new ResourceLocation(Reference.MODID, "textures/gui/guiComponents.png")
    val MC_BLOCKS: ResourceLocation = TextureMap.locationBlocksTexture
    val MC_ITEMS: ResourceLocation = new ResourceLocation("textures/atlas/items.png")

    /**
     * Used to bind a specific sheet
     * @param resource The resource
     */
    def bindTexture(resource: ResourceLocation) = {
        Minecraft.getMinecraft.getTextureManager.bindTexture(resource)
    }

    def bindMinecraftItemSheet() = {
        bindTexture(MC_ITEMS)
    }

    def bindMinecraftBlockSheet() = {
        bindTexture(MC_BLOCKS)
    }

    def bindGuiComponentsSheet() = {
        bindTexture(GUI_COMPONENTS)
    }

    /**
     * Set the GL color. You should probably reset it after this
     * @param color The color to set
     */
    def setColor(color: Color) = {
        GL11.glColor4f(color.getRed / 255F, color.getGreen / 255F, color.getBlue / 255F, color.getAlpha / 255F)
    }

    def restoreColor() = {
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0)
    }

    /**
     * Used to prepare the rendering state. For basic stuff that you want things to behave on
     */
    def prepareRenderState() = {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        GL11.glDisable(GL12.GL_RESCALE_NORMAL)
        RenderHelper.disableStandardItemLighting()
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_ALPHA_TEST)
    }

    /**
     * Un-does the prepare state
     */
    def restoreRenderState() = {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        GL11.glEnable(GL12.GL_RESCALE_NORMAL)
        GL11.glEnable(GL11.GL_LIGHTING)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        RenderHelper.enableStandardItemLighting()
        GL11.glDisable(GL11.GL_ALPHA_TEST)
    }
}
