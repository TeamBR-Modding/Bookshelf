package com.teambr.bookshelf.util;

import com.teambr.bookshelf.client.shapes.DrawableShape;
import com.teambr.bookshelf.client.shapes.TexturedCylinder;
import com.teambr.bookshelf.client.shapes.TexturedSphere;
import com.teambr.bookshelf.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.nio.FloatBuffer;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class RenderUtils {
    // Resource Locations
    public static final ResourceLocation GUI_COMPONENTS_RESOURCE_LOCATION =
            new ResourceLocation(Reference.MOD_ID, "textures/gui/guiComponents.png");
    public static final ResourceLocation MC_BLOCKS_RESOURCE_LOCATION =
            TextureMap.LOCATION_BLOCKS_TEXTURE;
    public static final ResourceLocation MC_ITEMS_RESOURCE_LOCATION =
            new ResourceLocation("textures/atlas/items.png");

    /*******************************************************************************************************************
     * Render Helpers                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to bind a texture to the render manager
     * @param resource The resource to bind
     */
    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    /**
     * Used to bind the MC item sheet
     */
    public static void bindMinecraftItemSheet() {
        bindTexture(MC_ITEMS_RESOURCE_LOCATION);
    }

    /**
     * Used to bind the MC blocks sheet
     */
    public static void bindMinecraftBlockSheet() {
        bindTexture(MC_BLOCKS_RESOURCE_LOCATION);
    }

    /**
     * Used to bind our texture sheet for Gui Components
     */
    public static void bindGuiComponentsSheet() {
        bindTexture(GUI_COMPONENTS_RESOURCE_LOCATION);
    }

    /**
     * Set the GL color. You should probably reset it after this
     *
     * @param color The color to set
     */
    public static void setColor(Color color) {
        GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F,
                color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    /**
     * Sets the color back to full white (normal)
     */
    public static void restoreColor() {
        setColor(new Color(255, 255, 255));
    }

    /**
     * Used to prepare the rendering state. For basic stuff that you want things to behave on
     */
    public static void prepareRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Un-does the prepare state
     */
    public static void restoreRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    /*******************************************************************************************************************
     * Matrix Helpers                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Loads the matrix
     * @param transform The transform
     */
    public static void loadMatrix(Matrix4f transform) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        transform.store(matrixBuffer);
        matrixBuffer.flip();
        GL11.glMultMatrix(matrixBuffer);
    }

    /**
     * Creates a rotation matrix for the entity
     * @param entity The looking entity
     * @return A rotation matrix representing what the entity sees
     */
    public static Matrix4f createEntityRotateMatrix(Entity entity) {
        double yaw   = Math.toRadians(entity.rotationYaw - 180);
        double pitch = Math.toRadians(entity.rotationPitch);

        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.rotate((float) pitch, new Vector3f(1, 0, 0));
        rotationMatrix.rotate((float) yaw,   new Vector3f(0, 1, 0));
        return rotationMatrix;
    }

    /*******************************************************************************************************************
     * Shape Rendering                                                                                                 *
     *******************************************************************************************************************/

    /**
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
    public static void renderCubeWithTexture(double x1, double y1, double z1, double x2, double y2, double z2,
                                 double u, double v, double u1, double v1) {
        VertexBuffer tes = Tessellator.getInstance().getBuffer();

        VertexFormat POSITION_TEX_NORMALF = new VertexFormat();
        VertexFormatElement NORMAL_3F =
                new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.NORMAL, 3);
        POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.POSITION_3F);
        POSITION_TEX_NORMALF.addElement(DefaultVertexFormats.TEX_2F);
        POSITION_TEX_NORMALF.addElement(NORMAL_3F);

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z2).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x2, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y1, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z1).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y1, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x1, y1, z2).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();

        tes.begin(GL11.GL_QUADS, POSITION_TEX_NORMALF);
        tes.pos(x1, y2, z1).tex(u, v).normal(0, -1, 0).endVertex();
        tes.pos(x1, y2, z2).tex(u, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z2).tex(u1, v1).normal(0, -1, 0).endVertex();
        tes.pos(x2, y2, z1).tex(u1, v).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();
    }

    /**
     * Draws a sphere, bind texture first!
     * @param radius The radius of the sphere
     * @param stacks How many squares in a slice
     * @param slices How manay slices (try to match stacks)
     * @param tex The texture to place
     * @param drawMode The draw mode
     * @param color The color to apply
     */
    public static void renderSphere(float radius, int stacks, int slices, TextureAtlasSprite tex,
                                    DrawableShape.TEXTURE_MODE drawMode, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        setColor(color);
        GL11.glEnable(GL11. GL_ALPHA_TEST);

        TexturedSphere sphere = new TexturedSphere();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        sphere.setDrawStyle(GLU.GLU_FILL);
        sphere.setNormals(GLU.GLU_SMOOTH);
        sphere.setTextureFlag(true);
        sphere.setTextureMode(drawMode);
        sphere.setOrientation(GLU.GLU_OUTSIDE);
        GL11.glTexCoord4f(tex.getMinU(), tex.getMaxU(), tex.getMinV(), tex.getMaxV());
        sphere.draw(radius, slices, stacks, tex.getMinU(), tex.getMaxU(), tex.getMinV(), tex.getMaxV());

        GL11.glPopMatrix();
    }

    public static void renderCylinder(float radius, int stacks, int slices, TextureAtlasSprite tex,
                                      DrawableShape.TEXTURE_MODE drawMode, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        setColor(color);
        GL11.glEnable(GL11. GL_ALPHA_TEST);

        TexturedCylinder cylinder = new TexturedCylinder();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        cylinder.setDrawStyle(GLU.GLU_FILL);
        cylinder.setNormals(GLU.GLU_SMOOTH);
        cylinder.setTextureFlag(true);
        cylinder.setTextureMode(drawMode);
        cylinder.setOrientation(GLU.GLU_OUTSIDE);
        GL11.glTexCoord4f(tex.getMinU(), tex.getMaxU(), tex.getMinV(), tex.getMaxV());
        cylinder.draw(radius, slices, stacks, tex.getMinU(), tex.getMaxU(), tex.getMinV(), tex.getMaxV());

        GL11.glPopMatrix();
    }
}
