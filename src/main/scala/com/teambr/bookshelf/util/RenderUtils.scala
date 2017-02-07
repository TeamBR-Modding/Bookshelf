package com.teambr.bookshelf.util

import java.awt.Color

import com.teambr.bookshelf.client.shapes.{DrawableShape, TexturedCylinder, TexturedSphere}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.{TextureAtlasSprite, TextureMap}
import net.minecraft.client.renderer.vertex.{DefaultVertexFormats, VertexFormat, VertexFormatElement}
import net.minecraft.client.renderer.{RenderHelper, Tessellator}
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL12}
import org.lwjgl.util.glu.GLU
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

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
    val MC_BLOCKS: ResourceLocation = TextureMap.LOCATION_BLOCKS_TEXTURE
    val MC_ITEMS: ResourceLocation = new ResourceLocation("textures/atlas/items.png")

    val POSITION_TEX_NORMALF = new VertexFormat()
    val NORMAL_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.NORMAL, 3)
    POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.POSITION_3F)
    POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.TEX_2F)
    POSITION_TEX_NORMALF.addElement(NORMAL_3F)

    /**
     * Used to bind a specific sheet
      *
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
      *
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
      *
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
        val tes = Tessellator.getInstance().getBuffer

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
      *
      * @param entity The Entity to Billboard to (usually the player)
      */
    def setupBillboard(entity : Entity) {
        GL11.glRotatef(-entity.rotationYaw, 0, 1, 0)
        GL11.glRotatef(entity.rotationPitch, 1, 0, 0)
    }

    /**
      * Draws a sphere, make sure you bind texture and translate etc before calling
      */
    def renderSphere(radius : Float, stacks : Int, slices : Int, tex : TextureAtlasSprite, drawMode : DrawableShape.TEXTURE_MODE, color : Color) : Unit = {
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        setColor(color)
        GL11.glEnable(GL11. GL_ALPHA_TEST)

        val sphere = new TexturedSphere()
        GL11.glShadeModel(GL11.GL_SMOOTH)
        sphere.setDrawStyle(GLU.GLU_FILL)
        sphere.setNormals(GLU.GLU_SMOOTH)
        sphere.setTextureFlag(true)
        sphere.setTextureMode(drawMode)
        sphere.setOrientation(GLU.GLU_OUTSIDE)
        GL11.glTexCoord4f(tex.getMinU, tex.getMaxU, tex.getMinV, tex.getMaxV)
        sphere.draw(radius, slices, stacks, tex.getMinU, tex.getMaxU, tex.getMinV, tex.getMaxV)

        GL11.glPopMatrix()
    }

    /**
      * Draws a cylinder, make sure you bind texture and translate etc before calling
      */
    def renderCylinder(radius : Float, stacks : Int, slices : Int, tex : TextureAtlasSprite, drawMode : DrawableShape.TEXTURE_MODE, color : Color) : Unit = {
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        setColor(color)
        GL11.glEnable(GL11. GL_ALPHA_TEST)

        val cylinder = new TexturedCylinder()
        GL11.glShadeModel(GL11.GL_SMOOTH)
        cylinder.setDrawStyle(GLU.GLU_FILL)
        cylinder.setNormals(GLU.GLU_SMOOTH)
        cylinder.setTextureFlag(true)
        cylinder.setTextureMode(drawMode)
        cylinder.setOrientation(GLU.GLU_OUTSIDE)
        GL11.glTexCoord4f(tex.getMinU, tex.getMaxU, tex.getMinV, tex.getMaxV)
        cylinder.draw(radius, slices, stacks, tex.getMinU, tex.getMaxU, tex.getMinV, tex.getMaxV)

        GL11.glPopMatrix()
    }

    val matrixBuffer = BufferUtils.createFloatBuffer(16)

    def loadMatrix(transform : Matrix4f) : Unit = {
        transform.store(matrixBuffer)
        matrixBuffer.flip()
        GL11.glMultMatrix(matrixBuffer)
    }

    def createEntityRotateMatrix(entity : Entity) : Matrix4f = {
        val yaw : Double = Math.toRadians(entity.rotationYaw - 180)
        val pitch : Double = Math.toRadians(entity.rotationPitch)

        val initial = new Matrix4f()
        initial.rotate(pitch.toFloat, new Vector3f(1, 0, 0))
        initial.rotate(yaw.toFloat  , new Vector3f(0, 1, 0))
        initial
    }
}
