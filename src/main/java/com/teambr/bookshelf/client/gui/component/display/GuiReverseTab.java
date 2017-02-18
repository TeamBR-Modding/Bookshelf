package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nullable;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class GuiReverseTab extends GuiTab {

    /**
     * Creates a Gui Tab
     *
     * IMPORTANT: Texture should be a full nine patch renderer minus the right column of cells
     * See NinePatchRenderer construction for more info
     *
     * @param parent       The parent GUI
     * @param x            The x pos
     * @param y            The y pos
     * @param u            The texture u, this is the first cell for the nine patch renderer
     * @param v            The texture v, this is the first cell for the nine patch renderer
     * @param exWidth      The expanded width
     * @param exHeight     The expanded height
     * @param displayStack The stack to display
     */
    public GuiReverseTab(GuiBase<?> parent, int x, int y, int u, int v, int exWidth, int exHeight, @Nullable ItemStack displayStack) {
        super(parent, x, y, u, v, exWidth, exHeight, displayStack);
        tabRenderer = new NinePatchRenderer(u, v, 8, parent.textureLocation);
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();

        // Set targets to stun
        double targetWidth  = isActive ? expandedWidth  : FOLDED_SIZE;
        double targetHeight = isActive ? expandedHeight : FOLDED_SIZE;

        // Move size
        if(currentWidth != targetWidth)
            dWidth += (targetWidth - dWidth);
        if(currentHeight != targetHeight)
            dHeight += (targetHeight - dHeight);

        // Set size
        currentWidth  = dWidth;
        currentHeight = dHeight;

        // Render the tab
        tabRenderer.render(this, -currentWidth, 0, currentWidth, currentHeight);

        // Render the stack, if available
        RenderUtils.restoreColor();
        if(stack != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRenderer.renderItemAndEffectIntoGUI(stack, -21, 3);
            RenderUtils.restoreColor();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        // Render the children
        if(areChildrenActive()) {
            GlStateManager.translate(-expandedWidth, 0, 0);
            children.forEach((component -> {
                RenderUtils.prepareRenderState();
                component.render(-expandedWidth, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
                RenderUtils.restoreRenderState();
            }));
        }

        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // Render the children
        if(areChildrenActive()) {
            GlStateManager.translate(-expandedWidth, 0, 0);
            children.forEach((component -> {
                RenderUtils.prepareRenderState();
                component.renderOverlay(-expandedWidth, 0, mouseX, mouseY);
                RenderUtils.restoreColor();
                RenderUtils.restoreRenderState();
            }));
        }
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
    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPos - currentWidth && mouseX < xPos && mouseY >= yPos && mouseY < yPos + getHeight();
    }
}
