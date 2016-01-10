package com.teambr.bookshelf.util

import java.awt.Color

import com.teambr.bookshelf.lib.Reference
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{Tessellator, RenderHelper}
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormatElement, VertexFormat}
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.{GL12, GL11}

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 *
 * This helps with tedious GL stuff and binding texture sheets. Just makes things a lot easier in the end
 */
object RenderUtils {
    val GUI_COMPONENTS: ResourceLocation = new ResourceLocation(Reference.MODID, "textures/gui/guiComponents.png")
    val MC_BLOCKS: ResourceLocation = TextureMap.locationBlocksTexture
    val MC_ITEMS: ResourceLocation = new ResourceLocation("textures/atlas/items.png")

    val POSITION_TEX_NORMALF = new VertexFormat()
    val NORMAL_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.NORMAL, 3)
    POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.POSITION_3F)
    POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.TEX_2F)
    POSITION_TEX_NORMALF.addElement(NORMAL_3F)

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




    /***
      * Used to draw a 3d cube, provide opposite corners
      * @param x1 First X Position
      * @param y1 First Y Position
      * @param z1 First Z Position
      * @param x2 Second X Position
      * @param y2 Second Y Position
      * @param z2 Second Z Position
      * @param u Min U
      * @param v Min V
      * @param u1 Max U
      * @param v1 Max V
      */
    def renderCubeWithTexture(x1 : Double, y1 : Double, z1 : Double, x2 : Double, y2 : Double, z2 : Double, u : Double, v : Double, u1 : Double, v1 : Double): Unit = {
        val tes = Tessellator.getInstance().getWorldRenderer

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x1, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z1).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y1, z1).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x1, y1, z2).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x2, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x1, y2, z2).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x1, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x1, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x1, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x2, y1, z1).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x2, y1, z1).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y1, z2).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x1, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF)
        tes.pos(x1, y2, z1).tex(u, v).normal(0, -1, 0).endVertex()
        tes.pos(x1, y2, z2).tex(u, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex()
        tes.pos(x2, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex()
        Tessellator.getInstance().draw()
    }

    /***
      * Sets up the renderer for a Billboard effect (always facing the player)
      * Used to simulate a 3d ish icon with a 2d sprite
      * @param entity The Entity to Billboard to (usually the player)
      */
    def setupBillboard(entity : Entity) {
        GL11.glRotatef(-entity.rotationYaw, 0, 1, 0)
        GL11.glRotatef(entity.rotationPitch, 1, 0, 0)
    }
}
