package com.dyonovan.brlib.util;

import com.dyonovan.brlib.common.blocks.BaseBlock;
import com.dyonovan.brlib.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static final ResourceLocation GUI_COMPONENTS = new ResourceLocation(Constants.MODID, "textures/gui/guiComponents.png");
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

    /**
     * Used to render a block in an inventory
     * @param block {@link BaseBlock} to render
     * @param metadata Block metadata
     * @param modelID Model id. Not really needed at the moment
     * @param renderer RenderBlocks object
     */
    public static void render3DInventory(BaseBlock block, int metadata, int modelID, RenderBlocks renderer) {
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
}