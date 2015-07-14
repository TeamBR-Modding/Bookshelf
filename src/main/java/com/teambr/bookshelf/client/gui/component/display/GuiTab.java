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
    protected int currentWidth;
    protected int currentHeight;
    protected static final int FOLDED_WIDTH = 24;
    protected static final int FOLDED_HEIGHT = 24;
    protected double dWidth = FOLDED_WIDTH;
    protected double dHeight = FOLDED_HEIGHT;
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

    /**
     * GuiTab constructor
     * @param gui The gui this is attached to
     * @param x The x Position
     * @param y The y position
     * @param maxWidth The max width, when expanded
     * @param maxHeight The max height, when expanded
     * @param c The color of the tab
     * @param stack The stack to display on the tab
     */
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

    /**
     * Used when you don't want to display a stack
     */
    public GuiTab(GuiBase gui, int x, int y, int maxWidth, int maxHeight, Color c) {
        this(gui, x, y, maxWidth, maxHeight, c, null);
    }

    /**
     * Can the tab render the children
     * @return True if expanded and can render
     */
    protected boolean areChildrenActive() {
        return active && currentWidth == expandedWidth && currentHeight == expandedHeight;
    }

    /**
     * Add a component to render
     * @param component What to add
     */
    protected void addChild(BaseComponent component) {
        children.add(component);
    }

    @Override
    public void initialize() {}

    @Override
    public void render(int x, int y) {
        GL11.glPushMatrix();
        double targetWidth = active ? expandedWidth : FOLDED_WIDTH;
        double targetHeight = active ? expandedHeight : FOLDED_HEIGHT;
        if (currentWidth != targetWidth) dWidth += (targetWidth - dWidth) / 4;
        if (currentHeight != targetHeight) dHeight += (targetHeight - dHeight) / 4;

        currentWidth = (int)Math.round(dWidth);
        currentHeight = (int)Math.round(dHeight);

        RenderUtils.bindGuiComponentsSheet();
        boxRenderer.render(this, 0, 0, currentWidth, currentHeight, color);

        GL11.glColor4f(1, 1, 1, 1);
        if(icon != null) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRenderer.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), icon,
                    4, 3);
            GL11.glColor3f(1, 1, 1);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        if(areChildrenActive()) {
            RenderUtils.prepareRenderState();
            for (BaseComponent component : children) {
                component.render(0, 0);
                RenderUtils.restoreRenderState();
            }
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int i, int i1) {
        if(areChildrenActive()) {
            RenderUtils.prepareRenderState();
            for (BaseComponent component : children) {
                component.renderOverlay(0, 0);
                RenderUtils.restoreRenderState();
            }
        }
    }

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public boolean mouseDownActivated(int x, int y, int button) {
        if(areChildrenActive()) {
            for (BaseComponent component : children)
                if(component.isMouseOver(x, y)) {
                    component.mouseDown(x, y, button);
                    return true;
                }
        }
        return false;
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public boolean mouseUpActivated(int x, int y, int button) {
        if(areChildrenActive()) {
            for (BaseComponent component : children)
                if(component.isMouseOver(x, y)) {
                    component.mouseUp(x, y, button);
                    return true;
                }
        }
        return false;
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    public boolean mouseDragActivated(int x, int y, int button, long time) {
        if(areChildrenActive()) {
            for (BaseComponent component : children)
                if(component.isMouseOver(x, y)) {
                    component.mouseDrag(x, y, button, time);
                    return true;
                }
        }
        return false;
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    public void keyTyped(char letter, int keyCode) {
        for(BaseComponent component : children)
            component.keyTyped(letter, keyCode);
    }

    @Override
    public int getWidth() {
        return currentWidth;
    }

    @Override
    public int getHeight() {
        return currentHeight;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public void setIcon(ItemStack icon) { this.icon = icon; }

    public ItemStack getIcon() {
        return icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
