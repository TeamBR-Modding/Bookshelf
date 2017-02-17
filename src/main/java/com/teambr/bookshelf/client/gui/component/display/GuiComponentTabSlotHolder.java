package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import net.minecraft.inventory.Slot;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/16/2017
 */
public class GuiComponentTabSlotHolder extends BaseComponent {
    protected Slot heldSlot;
    protected int shownX, shownY;
    protected GuiTab parentTab;
    protected GuiComponentTexture slotTexture;

    /**
     * Creates an object that will move the physical slot when should render
     *
     * This object will move the container slot, but also needs the texture to render
     *
     * @param parentGui  The parent gui
     * @param x          The component x
     * @param y          The component y
     * @param heldSlot   The slot to move about
     */
    public GuiComponentTabSlotHolder(GuiBase<?> parentGui, int x, int y, Slot heldSlot, int slotX, int slotY, int u, int v, GuiTab parentTab) {
        super(parentGui, x, y);
        this.heldSlot = heldSlot;
        this.shownX = slotX;
        this.shownY = slotY;
        this.parentTab = parentTab;
        slotTexture = new GuiComponentTexture(parent, x - 1, y - 1, u, v, 18, 18);
    }


    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        if(parentTab.isActive) {
            slotTexture.render(guiLeft, guiTop, mouseX, mouseY);
            heldSlot.xDisplayPosition = shownX;
            heldSlot.yDisplayPosition = shownY;
        } else {
            heldSlot.yDisplayPosition = -10000;
            heldSlot.xDisplayPosition = -10000;
        }
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
         // No op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return 18;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return 18;
    }
}
