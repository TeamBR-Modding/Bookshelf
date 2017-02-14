package com.teambr.bookshelf.helper;

import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import static net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUsage;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class GuiHelper {

    /*******************************************************************************************************************
     * Sound                                                                                                           *
     *******************************************************************************************************************/

    /**
     * Used to play the button sound in a GUI
     */
    public static void playButtonSound() {
        Minecraft.getMinecraft().getSoundHandler()
                .playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    /*******************************************************************************************************************
     * Render Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to set the color of the GL stack from an int value
     * @param color The color value eg 0x000000
     */
    public static void setGLColorFromInt(int color) {
        float red   = (color >> 16 & 255) / 255F;
        float green = (color >> 8 & 255) / 255F;
        float blue  = (color & 255) / 255F;
        GlStateManager.color(red, green, blue, 1.0F);
    }

    /**
     * Draws the given icon with optional cut
     *
     * @param icon The texture
     * @param x X pos
     * @param y Y pos
     * @param width keep width of icon
     * @param height keep height of icon
     * @param cut 0 is full icon, 16 is full cut
     */
    public static void drawIconWithCut(TextureAtlasSprite icon, int x, int y, int width, int height, int cut) {
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer renderer = tess.getBuffer();

        VertexFormat POSITION_TEXT_NORMAL_F = new VertexFormat();
        VertexFormatElement NORMAL_3F = new VertexFormatElement(0, EnumType.FLOAT, EnumUsage.NORMAL, 3);
        POSITION_TEXT_NORMAL_F.addElement(DefaultVertexFormats.POSITION_3F);
        POSITION_TEXT_NORMAL_F.addElement(DefaultVertexFormats.TEX_2F);
        POSITION_TEXT_NORMAL_F.addElement(NORMAL_3F);

        renderer.begin(GL11.GL_QUADS, POSITION_TEXT_NORMAL_F);
        renderer.pos(x, y + height, 0).tex(icon.getMinU(), icon.getInterpolatedV(height)).normal(0, -1, 0).endVertex();
        renderer.pos(x + width, y + height, 0).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(height)).normal(0, -1, 0).endVertex();
        renderer.pos(x + width, y + cut, 0).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(cut)).normal(0, -1, 0).endVertex();
        renderer.pos(x, y + cut, 0).tex(icon.getMinU(), icon.getInterpolatedV(cut)).normal(0, -1, 0).endVertex();
        tess.draw();
    }

    /**
     * Renders a fluid from the given tank
     * @param tank The tank
     * @param x The x pos
     * @param y The y pos
     * @param maxHeight Max height
     * @param maxWidth Max width
     */
    public static void renderFluid(FluidTank tank, int x, int y, int maxHeight, int maxWidth) {
        FluidStack fluid = tank.getFluid();
        if(fluid != null) {
            GL11.glPushMatrix();
            int level = (fluid.amount * maxHeight) / tank.getCapacity();
            TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill(fluid).toString());
            RenderUtils.bindMinecraftBlockSheet();
            setGLColorFromInt(fluid.getFluid().getColor(fluid));

            double timesW = Math.floor(maxWidth / 16);
            int cutW = 16;

            for(int j = 0; j < timesW; j++) {
                if(j == timesW)
                    cutW = maxWidth % 16;
                if(level >= 16) {
                    double times = Math.floor(level / 16);
                    for(int i = 1; i < times; i++) {
                        drawIconWithCut(icon, x + (j * 16), y - (16 * i), cutW, 16, 0);
                    }
                    int cut = level % 16;
                    drawIconWithCut(icon, x + (j * 16), (int) (y - (16 * (times + 1))), cutW, 16, 16 - cut);
                } else {
                    int cut = level % 16;
                    drawIconWithCut(icon, x + (j * 16), y - 16, cutW, 16, 16 - cut);
                }
            }
            GL11.glPopMatrix();
        }
    }

    /*******************************************************************************************************************
     * Helper Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Test if location is in bounds
     *
     * @param x xLocation
     * @param y yLocation
     * @param a Rectangle point a
     * @param b Rectangle point b
     * @param c Rectangle point c
     * @param d Rectangle point d
     *        (A,B)------------------
     *          -                  -
     *          -----------------(C,D)
     * @return True if in bounds
     */
    public static boolean isInBounds(int x, int y, int a, int b, int c, int d) {
        return x >= a && x <= c && y >= b && y <= d;
    }
}
