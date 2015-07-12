package com.teambr.bookshelf.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public class GuiHelper {

    /**
     * Render the fluid in the inventory
     * @param tank Tank containing the liquid
     * @param x X Position in Gui (move from guiLeft beforehand)
     * @param y Y Position in Gui (move from guiTop beforehand
     * @param maxHeight The max height of the tank render in Pixels
     */
    public static void renderFluid(FluidTank tank, int x, int y, int maxHeight) {
        FluidStack fluid = tank.getFluid();
        if(fluid != null) {
            GL11.glPushMatrix();
            int level = (fluid.amount * maxHeight) / tank.getCapacity();
            IIcon icon = fluid.getFluid().getIcon(fluid);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            setGLColorFromInt(fluid.getFluid().getColor(fluid));
            if(level >= 16) {
                int times = (int) Math.floor(level / 16);
                for(int i = 1; i <= times; i++) {
                    drawIconWithCut(icon, x, y - (16 * i), 16, 16, 0);
                }
                int cut = level % 16;
                drawIconWithCut(icon, x, y - (16 * (times + 1)), 16, 16, 16 - cut);
            }
            else {
                int cut = level % 16;
                drawIconWithCut(icon, x, y - 16, 16, 16, 16 - cut);
            }
            GL11.glPopMatrix();
        }
    }

    /**
     * Draws the given icon with optional cut
     * @param icon
     * @param x
     * @param y
     * @param width keep width of icon
     * @param height keep height of icon
     * @param cut 0 is full icon, 16 is full cut
     */
    private static void drawIconWithCut(IIcon icon, int x, int y, int width, int height, int cut) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + height, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + cut, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
        tess.addVertexWithUV(x, y + cut, 0, icon.getMinU(), icon.getInterpolatedV(cut));
        tess.draw();
    }

    private static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }

    /**
     * Test if location is in bounds
     * @param x xLocation
     * @param y yLocation
     * @param a Rectangle point a
     * @param b Rectangle point b
     * @param c Rectangle point c
     * @param d Rectangle point d
     * @return
     */
    public static boolean isInBounds(int x, int y, int a, int b, int c, int d) {
        return (x >= a && x <= c && y >= b && y <=d);
    }

    public enum GuiColor {
        BLACK(0),
        BLUE(1),
        GREEN(2),
        CYAN(3),
        RED(4),
        PURPLE(5),
        ORANGE(6),
        LIGHTGRAY(7),
        GRAY(8),
        LIGHTBLUE(9),
        LIME(10),
        TURQUISE(11),
        PINK(12),
        MAGENTA(13),
        YELLOW(14),
        WHITE(15);

        private int number;
        GuiColor(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "\u00a7" + Integer.toHexString(number);
        }
    }
}
