package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

    public void addTab(List<BaseComponent> components, int maxWidth, int maxHeight, Color color, ItemStack stack, boolean reverse) {
        final GuiTab tab;
        if(reverse)
            tab = new GuiReverseTab(parent, this.xPos + 5, this.yPos + (this.yPos + (tabs.size() * 24)), maxWidth, maxHeight, color, stack);
        else
            tab = new GuiTab(parent, this.xPos - 5, this.yPos + (this.yPos + (tabs.size() * 24)), maxWidth, maxHeight, color, stack);
        for(BaseComponent c : components)
            tab.addChild(c);
        tab.setMouseEventListener(new IMouseEventListener() {
            @Override
            public void onMouseDown(BaseComponent baseComponent, int x, int y, int i2) {
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

            @Override
            public void onMouseUp(BaseComponent baseComponent, int i, int i1, int i2) {
            }

            @Override
            public void onMouseDrag(BaseComponent baseComponent, int i, int i1, int i2, long l) {
            }
        });
        tabs.add(tab);
    }

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
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            tab.render(i, i1);
            GL11.glPopMatrix();
        }
    }

    private void realignTabsVertically() {
        int y = this.yPos;
        for(GuiTab tab : tabs) {
            tab.setY(y);
            y += tab.getHeight();
        }
    }

    @Override
    public void renderOverlay(int x, int i1) {
        for (int i = 0; i < tabs.size(); i++) {
            GuiTab tab = tabs.get(i);
            tab.renderOverlay(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5 : x - parent.width - 5, i1 + (i * 24));
        }
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
                    if(!tab.mouseDownActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5 : x - parent.width - 5, y + (i * 24), i2))
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
                    tab.mouseUpActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5: x - parent.width - 5, y + (i * 24), i2);
                    return;
                }
            }
        }

        @Override
        public void onMouseDrag(BaseComponent baseComponent, int x, int y, int i2, long l) {
            for(int i = 0; i < tabs.size(); i++) {
                GuiTab tab = tabs.get(i);
                if(tab.isMouseOver(x, y)) {
                    tab.mouseDragActivated(tab instanceof GuiReverseTab ? x + tab.expandedWidth - 5 : x - parent.width - 5, y + (i * 24), i2, l);
                    return;
                }
            }
        }
    }
}
