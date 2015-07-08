package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GuiTab extends BaseComponent {
    protected boolean active;
    private int currentWidth;
    private int currentHeight;
    private static final int FOLDED_WIDTH = 24;
    private static final int FOLDED_HEIGHT = 24;
    private double dWidth = FOLDED_WIDTH;
    private double dHeight = FOLDED_HEIGHT;
    protected final int expandedWidth;
    protected final int expandedHeight;
    protected Color color;
    protected GuiBase parent;
    protected ItemStack icon;
    protected static RenderItem itemRenderer = new RenderItem();

    protected List<BaseComponent> children;

    protected NinePatchRenderer boxRenderer = new NinePatchRenderer() {
        @Override
        protected void renderTopLeftCorner(Gui gui) {}

        @Override
        protected void renderBottomLeftCorner(Gui gui, int height) {}

        @Override
        protected void renderLeftEdge(Gui gui, int height) {}
    };

    public GuiTab(GuiBase gui, int x, int y, int maxWidth, int maxHeight, Color c, ItemStack stack) {
        super(x, y);
        expandedWidth = maxWidth;
        expandedHeight = maxHeight;
        currentWidth = FOLDED_WIDTH;
        currentHeight = FOLDED_HEIGHT;
        children = new ArrayList<>();
        color = c;
        parent = gui;
        icon = stack;
    }

    public GuiTab(GuiBase gui, int x, int y, int maxWidth, int maxHeight, Color c) {
        this(gui, x, y, maxWidth, maxHeight, c, null);
    }

    protected boolean areChildrenActive() {
        return active && currentWidth == expandedWidth && currentHeight == expandedHeight;
    }

    protected void addChild(BaseComponent component) {
        children.add(component);
    }

    @Override
    public void initialize() {}

    @Override
    public void render(int x, int y) {
        double targetWidth = active? expandedWidth : FOLDED_WIDTH;
        double targetHeight = active? expandedHeight : FOLDED_HEIGHT;
        if (currentWidth != targetWidth) dWidth += (targetWidth - dWidth) / 4;
        if (currentHeight != targetHeight) dHeight += (targetHeight - dHeight) / 4;

        currentWidth = (int)Math.round(dWidth);
        currentHeight = (int)Math.round(dHeight);

        RenderUtils.bindGuiComponentsSheet();
        boxRenderer.render(this, parent.getGuiLeft() + xPos, parent.getGuiTop() + yPos, currentWidth, currentHeight, color);

        GL11.glColor4f(1, 1, 1, 1);
        if(icon != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), icon,
                    parent.getGuiLeft() + xPos + 4, parent.getGuiTop() + yPos + 3);
            GL11.glColor3f(1, 1, 1);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        if(areChildrenActive()) {
            for (BaseComponent component : children)
                component.render(parent.getGuiLeft() + xPos, parent.getGuiTop() + yPos);
        }

    }

    @Override
    public void renderOverlay(int i, int i1) {
        if(areChildrenActive()) {
            for (BaseComponent component : children)
                component.renderOverlay(i, i1);
        }
    }

    @Override
    public int getWidth() {
        return currentWidth;
    }

    @Override
    public int getHeight() {
        return currentHeight;
    }

    public boolean isOrigin(int x, int y) {
        return x < FOLDED_WIDTH && y < FOLDED_WIDTH;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public void setY(int y) {
        this.yPos = y;
    }
}
