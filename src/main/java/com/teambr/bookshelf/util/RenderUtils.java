package com.teambr.bookshelf.util;

import com.teambr.bookshelf.common.blocks.BaseBlock;
import com.teambr.bookshelf.common.blocks.BlockBase;
import com.teambr.bookshelf.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

public class RenderUtils {

    public static final ResourceLocation GUI_COMPONENTS = new ResourceLocation(Reference.MODID, "textures/gui/guiComponents.png");
    public static final ResourceLocation MC_BLOCKS = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation MC_ITEMS = new ResourceLocation("textures/atlas/items.png");

    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    public static void bindMinecraftItemSheet() {
        bindTexture(MC_ITEMS);
    }

    public static void bindMinecraftBlockSheet() {
        bindTexture(MC_BLOCKS);
    }

    public static void bindGuiComponentsSheet() {
        bindTexture(GUI_COMPONENTS);
    }

    public static void setColor(Color color) {
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static void prepareRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    public static void restoreRenderState() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Used to render a block in an inventory
     * @param block {@link BlockBase} to render
     * @param renderer RenderBlocks object
     */
    public static void render3DInventory(BaseBlock block, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;

        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getBottom());
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getTop());
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getRight());
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getLeft());
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getBack());
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextures().getFront());
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    /**
     * Used to render a block in an inventory
     * @param block {@link BlockBase} to render
     * @param icon The icon to render instead
     * @param renderer RenderBlocks object
     */
    public static void render3DInventory(BaseBlock block, IIcon icon, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;

        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static void renderCube(Tessellator tes, double x1, double y1, double z1, double x2, double y2, double z2) {
        tes.startDrawingQuads();
        tes.addVertex(x1, y1, z1);
        tes.addVertex(x1, y2, z1);
        tes.addVertex(x2, y2, z1);
        tes.addVertex(x2, y1, z1);

        tes.addVertex(x1, y1, z2);
        tes.addVertex(x2, y1, z2);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x1, y2, z2);

        tes.addVertex(x1, y1, z1);
        tes.addVertex(x1, y1, z2);
        tes.addVertex(x1, y2, z2);
        tes.addVertex(x1, y2, z1);

        tes.addVertex(x2, y1, z1);
        tes.addVertex(x2, y2, z1);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x2, y1, z2);

        tes.addVertex(x1, y1, z1);
        tes.addVertex(x2, y1, z1);
        tes.addVertex(x2, y1, z2);
        tes.addVertex(x1, y1, z2);

        tes.addVertex(x1, y2, z1);
        tes.addVertex(x1, y2, z2);
        tes.addVertex(x2, y2, z2);
        tes.addVertex(x2, y2, z1);
        tes.draw();
    }

    public static void renderCubeWithUV(Tessellator tes, double x1, double y1, double z1, double x2, double y2, double z2, float u1, float v1, float u2, float v2) {
        tes.startDrawingQuads();
        tes.addVertexWithUV(x1, y1, z1, u1, v1);
        tes.addVertexWithUV(x1, y2, z1, u1, v2);
        tes.addVertexWithUV(x2, y2, z1, u2, v2);
        tes.addVertexWithUV(x2, y1, z1, u2, v1);

        tes.addVertexWithUV(x1, y1, z2, u1, v1);
        tes.addVertexWithUV(x2, y1, z2, u1, v2);
        tes.addVertexWithUV(x2, y2, z2, u2, v2);
        tes.addVertexWithUV(x1, y2, z2, u2, v1);

        tes.addVertexWithUV(x1, y1, z1, u1, v1);
        tes.addVertexWithUV(x1, y1, z2, u1, v2);
        tes.addVertexWithUV(x1, y2, z2, u2, v2);
        tes.addVertexWithUV(x1, y2, z1, u2, v1);

        tes.addVertexWithUV(x2, y1, z1, u1, v1);
        tes.addVertexWithUV(x2, y2, z1, u1, v2);
        tes.addVertexWithUV(x2, y2, z2, u2, v2);
        tes.addVertexWithUV(x2, y1, z2, u2, v1);

        tes.addVertexWithUV(x1, y1, z1, u1, v1);
        tes.addVertexWithUV(x2, y1, z1, u1, v2);
        tes.addVertexWithUV(x2, y1, z2, u2, v2);
        tes.addVertexWithUV(x1, y1, z2, u2, v1);

        tes.addVertexWithUV(x1, y2, z1, u1, v1);
        tes.addVertexWithUV(x1, y2, z2, u1, v2);
        tes.addVertexWithUV(x2, y2, z2, u2, v2);
        tes.addVertexWithUV(x2, y2, z1, u2, v1);
        tes.draw();
    }
}