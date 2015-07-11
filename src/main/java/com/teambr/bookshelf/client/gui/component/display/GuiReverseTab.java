package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

public class GuiReverseTab extends GuiTab {

    protected NinePatchRenderer boxRenderer = new NinePatchRenderer() {
        @Override
        protected void renderTopRightCorner(Gui gui, int width) {}

        @Override
        protected void renderBottomRightCorner(Gui gui, int width, int height) {}

        @Override
        protected void renderRightEdge(Gui gui, int width, int height) {}
    };

    public GuiReverseTab(GuiBase gui, int x, int y, int maxWidth, int maxHeight, Color c, ItemStack stack) {
        super(gui, x, y, maxWidth, maxHeight, c, stack);
    }

    public GuiReverseTab(GuiBase gui, int x, int y, int maxWidth, int maxHeight, Color c) {
        this(gui, x, y, maxWidth, maxHeight, c, null);
    }

    @Override
    public void render(int x, int y) {
        GL11.glPushMatrix();
        double targetWidth = active? expandedWidth : FOLDED_WIDTH;
        double targetHeight = active? expandedHeight : FOLDED_HEIGHT;
        if (currentWidth != targetWidth) dWidth += (targetWidth - dWidth) / 4;
        if (currentHeight != targetHeight) dHeight += (targetHeight - dHeight) / 4;

        currentWidth = (int)Math.round(dWidth);
        currentHeight = (int)Math.round(dHeight);

        RenderUtils.bindGuiComponentsSheet();
        boxRenderer.render(this, parent.getGuiLeft() + xPos - currentWidth, parent.getGuiTop() + yPos, currentWidth, currentHeight, color);

        GL11.glColor4f(1, 1, 1, 1);
        if(icon != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), icon,
                    parent.getGuiLeft() + xPos - 21, parent.getGuiTop() + yPos + 3);
            GL11.glColor3f(1, 1, 1);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        if(areChildrenActive()) {
            for (BaseComponent component : children)
                component.render(parent.getGuiLeft() + xPos - currentWidth, parent.getGuiTop() + yPos);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {
        if(areChildrenActive()) {
            for (BaseComponent component : children)
                component.renderOverlay(i - currentWidth, i1);
        }
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPos - currentWidth && mouseX < xPos && mouseY >= yPos && mouseY < yPos + getHeight();
    }
}
