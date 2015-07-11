package com.teambr.bookshelf.client.gui;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.client.gui.component.display.GuiReverseTab;
import com.teambr.bookshelf.client.gui.component.display.GuiTab;
import com.teambr.bookshelf.client.gui.component.display.GuiTabCollection;
import com.teambr.bookshelf.common.container.ICustomSlot;
import cpw.mods.fml.common.Optional;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({
        @Optional.Interface(iface = "codechicken.nei.cofh.api.INEIGuiHandler", modid = "NotEnoughItems")
})
public abstract class GuiBase<T extends Container> extends GuiContainer implements INEIGuiHandler {
    protected String title;
    protected T inventory;
    protected NinePatchRenderer background = new NinePatchRenderer();
    protected ArrayList<BaseComponent> components;
    protected GuiTabCollection rightTabs;
    protected GuiTabCollection leftTabs;

    /**
     * Constructor for All Guis
     * @param container Inventory Container
     * @param width XSize
     * @param height YSize
     * @param name The inventory title
     */
    public GuiBase(T container, int width, int height, String name) {
        super(container);
        this.inventory = container;
        this.xSize = width;
        this.ySize = height;
        this.title = name;

        components = new ArrayList<>();
        addComponents();

        rightTabs = new GuiTabCollection(this, xSize);
        leftTabs = new GuiTabCollection(this, 0);
        addRightTabs(rightTabs);
        addLeftTabs(leftTabs);
        components.add(rightTabs);
        components.add(leftTabs);
    }

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiTabs in
     */
    public void addRightTabs(GuiTabCollection tabs) {}

    /**
     * Add the tabs to the left. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiReverseTabs in
     */
    public void addLeftTabs(GuiTabCollection tabs) {}

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    public abstract void addComponents();

    /**
     * Used to get the guiLeft
     * @return Where the gui starts
     */
    public int getGuiLeft() {
        return guiLeft;
    }

    /**
     * Used to get guiTop
     * @return Where the gui starts
     */
    public int getGuiTop() {
        return guiTop;
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        for(BaseComponent component : components)
            if(component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseDown(x - this.guiLeft, y - this.guiTop, button);
    }

    @Override
    protected void mouseMovedOrUp(int x, int y, int button) {
        super.mouseMovedOrUp(x, y, button);
        for(BaseComponent component : components)
            if(component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseUp(x - this.guiLeft, y - this.guiTop, button);
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        super.mouseClickMove(x, y, button, time);
        for(BaseComponent component : components)
            if(component.isMouseOver(x - this.guiLeft, y - this.guiTop)) component.mouseDrag(x - this.guiLeft, y - this.guiTop, button, time);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(StatCollector.translateToLocal(title), xSize / 2 - fontRendererObj.getStringWidth(StatCollector.translateToLocal(title)) / 2, 6, 4210752);
        for(BaseComponent component : components)
            component.renderOverlay(guiLeft, guiTop);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        //Render the Background
        background.render(this, x, y, xSize, ySize);

        //Render Slots for inventory
        for(Object obj : inventory.inventorySlots) {
            if(obj instanceof ICustomSlot) {
                ICustomSlot slot = (ICustomSlot)obj;
                if(slot.getSlotSize() == ICustomSlot.SLOT_SIZE.LARGE)
                    this.drawTexturedModalRect(x + slot.getPoint().getFirst(), y + slot.getPoint().getSecond(), 0, 38, 26, 26);
                else
                    this.drawTexturedModalRect(x + slot.getPoint().getFirst(), y + slot.getPoint().getSecond(), 0, 20, 18, 18);
            } else if(obj instanceof Slot) {
                Slot slot = (Slot)obj;
                if(isSlotLarge(slot))
                    this.drawTexturedModalRect(x + slot.xDisplayPosition - 5, y + slot.yDisplayPosition - 5, 0, 38, 26, 26);
                else
                    this.drawTexturedModalRect(x + slot.xDisplayPosition - 1, y + slot.yDisplayPosition - 1, 0, 20, 18, 18);
            }
        }

        //Render Components
        for(BaseComponent component : components) {
            component.render(guiLeft, guiTop);
        }
    }

    private boolean isSlotLarge(Slot slot) {
        return slot instanceof SlotFurnace;
    }

    /*******************************************************************************************************************
     ********************************************** NEI ****************************************************************
     *******************************************************************************************************************/

    @Override
    public VisiblityData modifyVisiblity(GuiContainer guiContainer, VisiblityData visiblityData) {
        return null;
    }

    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer guiContainer, ItemStack itemStack) {
        return null;
    }

    @Override
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer guiContainer) {
        return null;
    }

    @Override
    public boolean handleDragNDrop(GuiContainer guiContainer, int i, int i1, ItemStack itemStack, int i2) {
        return false;
    }

    @Override
    @Optional.Method(modid = "NotEnoughItems")
    public boolean hideItemPanelSlot(GuiContainer gc, int x, int y, int w, int h) {
        int xMin = guiLeft + xSize;
        int yMin = guiTop;
        int xMax = xMin;
        int yMax = yMin;

        for(GuiTab tab : rightTabs.getTabs()) {
            if(tab instanceof GuiReverseTab)
                continue;
            else {
                if(tab.getWidth() > 24) {
                    xMax += tab.getWidth() + 10;
                    yMax += tab.getHeight() + 20;
                } else
                    yMax += 24;
            }
        }
        return ((x+w) > xMin && (x+w) < xMax && (y+h) > yMin && (y+h) < yMax) || ((x+w) < xMin + 30 && (x+w) > xMin);
    }
}