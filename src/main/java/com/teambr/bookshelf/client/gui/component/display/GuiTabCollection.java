package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiTabCollection extends BaseComponent {

    protected List<GuiTab> tabs;
    protected GuiTab activeTab;
    protected GuiBase parent;

    public GuiTabCollection(GuiBase gui, int x) {
        super(x, 2);
        tabs = new ArrayList<>();
        setMouseEventListener(new TabMouseListener());
        parent = gui;
    }

    /**
     * Add a standard tab to this wrapper
     * @param components The components to add to the tab
     * @param maxWidth The max width of the tab
     * @param maxHeight The max height of the tab
     * @param color The color of the tab
     * @param stack What tab to display on the tab
     */
    public void addTab(List<BaseComponent> components, int maxWidth, int maxHeight, Color color, ItemStack stack) {
        GuiTab tab;
        tab = new GuiTab(parent, this.xPos - 5, this.yPos + (this.yPos + (tabs.size() * 24)), maxWidth, maxHeight, color, stack);
        for(BaseComponent c : components)
            tab.addChild(c);
        tabs.add(tab);
    }

    /**
     * Add a reverse tab to this wrapper
     * @param components The components to add to the tab
     * @param maxWidth The max width of the tab
     * @param maxHeight The max height of the tab
     * @param color The color of the tab
     * @param stack What tab to display on the tab
     */
    public void addReverseTab(List<BaseComponent> components, int maxWidth, int maxHeight, Color color, ItemStack stack) {
        GuiTab tab;
        tab = new GuiReverseTab(parent, this.xPos + 5, this.yPos + (this.yPos + (tabs.size() * 24)), maxWidth, maxHeight, color, stack);
        for(BaseComponent c : components) {
            tab.addChild(c);
        }
        tabs.add(tab);
    }

    /**
     * Get the list of tabs in this wrapper
     * @return
     */
    public List<GuiTab> getTabs() {
        return tabs;
    }

    @Override
    public void initialize() {}

    @Override
    public void render(int i, int i1) {
        realignTabsVertically();
        for(GuiTab tab : tabs) {
            GL11.glPushMatrix();
            RenderUtils.prepareRenderState();
            GL11.glTranslated(tab.getXPos(), tab.getYPos(), 0);
            tab.render(0, 0);
            RenderUtils.restoreRenderState();
            GL11.glPopMatrix();
        }
    }

    /**
     * Move the tabs to fit the expansion of one
     */
    private void realignTabsVertically() {
        int y = this.yPos;
        for(GuiTab tab : tabs) {
            tab.setYPos(y);
            y += tab.getHeight();
        }
    }

    @Override
    public void renderOverlay(int mouseX, int mouseY) {
        for(GuiTab tab : tabs) {
            GL11.glPushMatrix();
            RenderUtils.prepareRenderState();
            GL11.glTranslated(tab.getXPos(), tab.getYPos(), 0);
            tab.renderOverlay(0, 0);
            RenderUtils.restoreRenderState();
            GL11.glPopMatrix();
        }
    }

    /**
     * Render the tooltip if you can
     * @param mouseX Mouse X
     * @param mouseY Mouse Y
     */
    public void renderToolTip(int mouseX, int mouseY, GuiScreen parent) {
        for(GuiTab tab : tabs) {
            if(tab.isMouseOver(mouseX - this.parent.getGuiLeft(), mouseY - this.parent.getGuiTop())) {
                tab.renderToolTip(mouseX, mouseY, parent);
            }
        }
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    public void keyTyped(char letter, int keyCode) {
        for(GuiTab tab : tabs)
            tab.keyTyped(letter, keyCode);
    }

    @Override
    public int getWidth() {
        return 24;
    }

    @Override
    public int getHeight() {
        return 5 + (tabs.size() * 24);
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        for(GuiTab tab : tabs) {
            if(tab.isMouseOver(mouseX, mouseY))
                return true;
        }
        return false;
    }

    private class TabMouseListener implements IMouseEventListener {

        @Override
        public void onMouseDown(BaseComponent baseComponent, int x, int y, int i2) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(x, y)) {
                    if (tab.getMouseEventListener() == null) {
                        if(!tab.mouseDownActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5 : x - parent.getXSize() + 5, y - (i * 24) - 2, i2)) {
                            if (activeTab != tab) {
                                if (activeTab != null)
                                    activeTab.setActive(false);
                                activeTab = tab;
                                activeTab.setActive(true);
                            } else if (tab.isMouseOver(x, y)) {
                                tab.setActive(false);
                                activeTab = null;
                            }
                        }
                    } else
                        tab.mouseDown(x, y, i2);
                    return;
                }
            }
        }

        @Override
        public void onMouseUp(BaseComponent baseComponent, int x, int y, int i2) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(x, y)) {
                    tab.mouseUpActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5: x - parent.getXSize() + 5, y - (i * 24) - 2, i2);
                    return;
                }
            }
        }

        @Override
        public void onMouseDrag(BaseComponent baseComponent, int x, int y, int i2, long l) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(x, y)) {
                    tab.mouseDragActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5 : x - parent.getXSize() + 5, y - (i * 24) - 2, i2, l);
                    return;
                }
            }
        }
    }
}
