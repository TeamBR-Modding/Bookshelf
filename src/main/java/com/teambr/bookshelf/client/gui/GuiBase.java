package com.teambr.bookshelf.client.gui;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.common.container.ICustomSlot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public abstract class GuiBase<T extends Container> extends GuiContainer {
    protected String title;
    protected T inventory;
    protected NinePatchRenderer background = new NinePatchRenderer();
    protected ArrayList<BaseComponent> components;

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
    }

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
}