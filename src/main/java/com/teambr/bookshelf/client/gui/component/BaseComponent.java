package com.teambr.bookshelf.client.gui.component;

import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public abstract class BaseComponent extends Gui {

    protected int xPos;
    protected int yPos;

    protected List<String> toolTip;

    protected IMouseEventListener mouseEventListener;

    public BaseComponent(int x, int y) {
        xPos = x;
        yPos = y;

        initialize();
    }

    /**
     * Called from constructor. Set up anything needed here
     */
    public abstract void initialize();

    /**
     * Called to render the component
     */
    public abstract void render(int guiLeft, int guiTop);

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    public abstract void renderOverlay(int guiLeft, int guiTop);

    /**
     * Used to find how wide this is
     * @return How wide the component is
     */
    public abstract int getWidth();

    /**
     * Used to find how tall this is
     * @return How tall the comonent is
     */
    public abstract int getHeight();

    /**
     * Set the handler for the mouse input
     * @param listener The listener to set
     */
    public void setMouseEventListener(IMouseEventListener listener) {
        mouseEventListener = listener;
    }

    /**
     * Set the tooltip to be rendered. If you don't call this it won't have one
     * @param list The list of strings to display
     */
    public void setToolTip(List<String> list) {
        toolTip = list;
    }

    /**
     * Render the tooltip if you can
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    public void renderToolTip(int mouseX, int mouseY) {
        if(toolTip != null && !toolTip.isEmpty()) {
            drawHoveringText(toolTip, mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
        }
    }

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {
        if(mouseEventListener != null) mouseEventListener.onMouseDown(this, x, y, button);
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        if(mouseEventListener != null) mouseEventListener.onMouseUp(this, x, y, button);
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    public void mouseDrag(int x, int y, int button, long time) {
        if(mouseEventListener != null) mouseEventListener.onMouseDrag(this, x, y, button, time);
    }

    /**
     * Used to check if the mouse if currently over the component
     *
     * You must have the getWidth() and getHeight() functions defined for this to work properly
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @return True if mouse if over the component
     */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight();
    }

    protected void drawHoveringText(List tip, int mouseX, int mouseY, FontRenderer font) {
        if (!tip.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;

            for (Object aTip : tip) {
                String s = (String) aTip;
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int j2 = mouseX + 12;
            int k2 = mouseY - 12;
            int i1 = 8;

            if (tip.size() > 1) {
                i1 += 2 + (tip.size() - 1) * 10;
            }

            if (j2 + k > this.getWidth()) {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > this.getHeight()) {
                k2 = this.getHeight() - i1 - 6;
            }

            this.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < tip.size(); ++i2) {
                String s1 = (String)tip.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);
                if (i2 == 0) {
                    k2 += 2;
                }
                k2 += 10;
            }

            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}