package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

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
 * @since 2/13/2017
 */
public class GuiTabCollection extends BaseComponent {
    // Variables
    protected List<GuiTab> tabs;
    protected  GuiTab activeTab;

    /**
     * Main constructor for all components
     *
     * @param parentGui The parent Gui
     * @param x         The x position
     */
    public GuiTabCollection(GuiBase<?> parentGui, int x) {
        super(parentGui, x, 2);
        tabs = new ArrayList<>();
        setMouseEventListener(new TabMouseListener());
    }

    /**
     * Adds a tab to the collection
     * @param components The components to add to the tab
     * @param maxWidth The max width of the tab
     * @param maxHeight The max height of the tab
     * @param textureU The tabs u texture
     * @param textureV The tabs v texture
     * @param stack The display stack, can be null
     * @return This, to enable chaining
     */
    public GuiTabCollection addTab(List<BaseComponent> components, int maxWidth, int maxHeight,
                                   int textureU, int textureV, @Nullable ItemStack stack) {
        GuiTab tab = new GuiTab(parent, xPos - 5, yPos + (yPos + (tabs.size()) * 24),
                textureU, textureV, maxWidth, maxHeight, stack);
        components.forEach((tab::addChild));
        tabs.add(tab);
        return this;
    }

    /**
     * Adds a reverse tab to the collection
     * @param components The components to add to the tab
     * @param maxWidth The max width of the tab
     * @param maxHeight The max height of the tab
     * @param textureU The tabs u texture
     * @param textureV The tabs v texture
     * @param stack The display stack, can be null
     * @return This, to enable chaining
     */
    public GuiTabCollection addReverseTab(List<BaseComponent> components, int maxWidth, int maxHeight,
                                   int textureU, int textureV, @Nullable ItemStack stack) {
        GuiTab tab = new GuiReverseTab(parent, xPos + 5, yPos + (yPos + (tabs.size()) * 24),
                textureU, textureV, maxWidth, maxHeight, stack);
        components.forEach((tab::addChild));
        tabs.add(tab);
        return this;
    }

    /**
     * Move the tabs to fit the expansion of one
     */
    private void realignTabsVertically() {
        int y = yPos;
        for(GuiTab tab : tabs) {
            tab.setYPos(y);
            y += tab.getHeight();
        }
    }

    /**
     * Gets the areas covered by the tab collection
     * @param guiLeft The gui left of the parent
     * @param guiTop The gui top of the parent
     * @return A list of covered areas
     */
    public List<Rectangle> getAreasCovered(int guiLeft, int guiTop) {
        List<Rectangle> list = new ArrayList<>();
        tabs.forEach((guiTab -> {
            if(guiTab instanceof GuiReverseTab)
                list.add(new Rectangle(guiLeft + guiTab.getXPos() - getWidth(), guiTop + guiTab.getYPos(),
                        guiTab.getWidth(), guiTab.getHeight()));
            else
                list.add(new Rectangle(guiLeft + guiTab.getXPos(), guiTop + guiTab.getYPos(),
                        guiTab.getWidth(), guiTab.getHeight()));
        }));
        return list;
    }

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is scrolled
     * @param dir 1 for positive, -1 for negative
     */
    @Override
    public void mouseScrolled(int dir) {
        tabs.forEach((guiTab -> guiTab.mouseScrolledTab(dir)));
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
        for(GuiTab tab : tabs) {
            if(tab.isMouseOver(mouseX, mouseY))
                return true;
        }
        return false;
    }

    /**
     * Used when a key is pressed
     *
     * @param letter The letter
     * @param keyCode The code
     */
    @Override
    public void keyTyped(char letter, int keyCode) {
        tabs.forEach((guiTab -> guiTab.keyTyped(letter, keyCode)));
    }

    /**
     * Render the tooltip if you can
     *
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    @Override
    public void renderToolTip(int mouseX, int mouseY) {
        tabs.forEach((guiTab -> {
            if(guiTab.isMouseOver(mouseX - parent.getGuiLeft(), mouseY - parent.getGuiTop()))
                guiTab.renderToolTip(mouseX, mouseY);
        }));
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        realignTabsVertically();
        for(GuiTab tab : tabs) {
            GlStateManager.pushMatrix();
            RenderUtils.prepareRenderState();
            GlStateManager.translate(tab.getXPos(), tab.getYPos(), 0);
            tab.render(0, 0, mouseX - tab.getXPos(), mouseY - tab.getYPos());
            tab.moveSlots();
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
            GlStateManager.popMatrix();
        }
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        for(GuiTab tab : tabs) {
            GlStateManager.pushMatrix();
            RenderUtils.prepareRenderState();
            GlStateManager.translate(tab.getXPos(), tab.getYPos(), 0);
            tab.renderOverlay(0, 0, mouseX, mouseY);
            RenderUtils.restoreRenderState();
            RenderUtils.restoreColor();
            GlStateManager.popMatrix();
        }
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return 24;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return 5 + (tabs.size() * 24);
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public List<GuiTab> getTabs() {
        return tabs;
    }

    public void setTabs(List<GuiTab> tabs) {
        this.tabs = tabs;
    }

    /*******************************************************************************************************************
     * Classes                                                                                                         *
     *******************************************************************************************************************/

    /**
     * Private class to hold all mouse event logic
     */
    private class TabMouseListener implements IMouseEventListener {

        /**
         * Called when the mouse clicks on the component
         * @param component The component to be clicked
         * @param mouseX X position of the mouse
         * @param mouseY Y position of the mouse
         * @param button Which button was clicked
         */
        @Override
        public void onMouseDown(BaseComponent component, int mouseX, int mouseY, int button) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(mouseX, mouseY)) {
                    if(tab.getMouseEventListener() == null) {
                        if(!tab.mouseDownActivated(
                                (tab instanceof GuiReverseTab) ? mouseX + tab.expandedWidth - 5 : mouseX - parent.getXSize() + 5,
                                mouseY - (i * 24) - 2, button)) {
                            if(activeTab != null &&
                                    activeTab != tab) {
                                if(activeTab != null)
                                    activeTab.setActive(false);
                                activeTab = tab;
                                activeTab.setActive(true);
                                return;
                            } else if(activeTab == tab && tab.areChildrenActive()) {
                                activeTab.setActive(false);
                                activeTab = null;
                                return;
                            } else {
                                activeTab = tab;
                                activeTab.setActive(true);
                                return;
                            }
                        }
                    } else
                        tab.mouseDown(mouseX, mouseY, button);
                    return;
                }
            }
        }

        /**
         * Called when the mouse releases the component
         * @param component The component to be clicked
         * @param mouseX X position of the mouse
         * @param mouseY Y position of the mouse
         * @param button Which button was clicked
         */
        @Override
        public void onMouseUp(BaseComponent component, int mouseX, int mouseY, int button) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(mouseX, mouseY)) {
                    tab.mouseUpActivated((tab instanceof GuiReverseTab) ? mouseX + tab.expandedWidth - 5 : mouseX - parent.getXSize() + 5,
                            mouseY - (i * 24) - 2, button);
                    return;
                }
            }
        }

        /**
         * Called when the mouse drags an item
         * @param component The component to be clicked
         * @param mouseX X position of the mouse
         * @param mouseY Y position of the mouse
         * @param button Which button was clicked
         * @param time How long its been clicked
         */
        @Override
        public void onMouseDrag(BaseComponent component, int mouseX, int mouseY, int button, long time) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(mouseX, mouseY)) {
                    tab.mouseDragActivated((tab instanceof GuiReverseTab) ? mouseX + tab.expandedWidth - 5 : mouseX - parent.getXSize() + 5,
                            mouseY - (i * 24) - 2, button, time);
                    return;
                }
            }
        }
    }
}
