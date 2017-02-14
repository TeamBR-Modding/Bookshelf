package com.teambr.bookshelf.client.gui.component;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.listeners.IKeyboardListener;
import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public abstract class BaseComponent extends Gui {
    // Variables
    protected int xPos, yPos;
    protected GuiBase<?> parent;

    protected List<String> toolTip = new ArrayList<>();

    protected IMouseEventListener mouseEventListener;
    protected IKeyboardListener   keyboardEventListener;

    protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    /**
     * Main constructor for all components
     * @param parentGui The parent Gui
     * @param x The x position
     * @param y The y position
     */
    public BaseComponent(GuiBase<?> parentGui, int x, int y) {
        parent = parentGui;
        xPos = x;
        yPos = y;
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    public abstract void render(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    public abstract void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    public abstract int getWidth();

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    public abstract int getHeight();

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Used to determine if a dynamic tooltip is needed at runtime
     * @param mouseX Mouse X Pos
     * @param mouseY Mouse Y Pos
     * @return A list of string to display
     */
    @Nullable
    public List<String> getDynamicToolTip(int mouseX, int mouseY) {
        return null;
    }

    /**
     * Render the tooltip if you can
     *
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    public void renderToolTip(int mouseX, int mouseY) {
        if(toolTip != null && !toolTip.isEmpty())
            drawHoveringText(toolTip, mouseX, mouseY, Minecraft.getMinecraft().fontRendererObj);
        else if(getDynamicToolTip(mouseX, mouseY) != null)
            drawHoveringText(getDynamicToolTip(mouseX, mouseY), mouseX, mouseY, Minecraft.getMinecraft().fontRendererObj);

    }

    /**
     * Used to get what area is being displayed, mainly used for JEI
     */
    public Rectangle getArea(int guiLeft, int guiTop) {
        return new Rectangle(xPos + guiLeft, yPos + guiTop, getWidth(), getHeight());
    }

    /*******************************************************************************************************************
     * Input Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseDown(this, x, y, button);
    }

    /**
     * Called when the mouse button is over the component and released
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseUp(this, x, y, button);
    }

    /**
     * Called when the user drags the component
     *
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    public void mouseDrag(int x, int y, int button, long time) {
        if(mouseEventListener != null)
            mouseEventListener.onMouseDrag(this, x, y, button, time);
    }

    /**
     * Called when the mouse is scrolled
     * @param dir 1 for positive, -1 for negative
     */
    public void mouseScrolled(int dir) {}

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
        return  mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight();
    }

    /**
     * Used when a key is pressed
     *
     * @param letter The letter
     * @param keyCode The code
     */
    public void keyTyped(char letter, int keyCode) {
        if(keyboardEventListener != null)
            keyboardEventListener.keyTyped(this, letter, keyCode);
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public GuiBase getParent() {
        return parent;
    }

    public void setParent(GuiBase parent) {
        this.parent = parent;
    }

    public List<String> getToolTip() {
        return toolTip;
    }

    public void setToolTip(List<String> toolTip) {
        this.toolTip = toolTip;
    }

    public IMouseEventListener getMouseEventListener() {
        return mouseEventListener;
    }

    public void setMouseEventListener(IMouseEventListener mouseEventListener) {
        this.mouseEventListener = mouseEventListener;
    }

    public IKeyboardListener getKeyboardEventListener() {
        return keyboardEventListener;
    }

    public void setKeyboardEventListener(IKeyboardListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    /*******************************************************************************************************************
     * Helper Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Used to draw a tooltip over this component
     *
     * @param tip The list of strings to render
     * @param mouseX The mouse x Position
     * @param mouseY The mouse Y position
     * @param font The font renderer
     */
    protected void drawHoveringText(List<String> tip, int mouseX, int mouseY, FontRenderer font) {
        if (!tip.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, 0.0, 5);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            for (String s : tip) {
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
            if (j2 + k > parent.width) {
                j2 -= 28 + k;
            }
            if (k2 + i1 + 6 > parent.height) {
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

            for(String s1 : tip) {
                font.drawStringWithShadow(s1, j2, k2, -1);
                if(s1.equals(tip.get(0)))
                    k2 += 2;
                k2 += 10;
            }

            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

}
