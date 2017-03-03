package com.teambr.bookshelf.common.container;

import com.teambr.bookshelf.common.container.slots.IPhantomSlot;
import com.teambr.bookshelf.util.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public abstract class BaseContainer extends ContainerGeneric {
    // Variables
    protected IInventory playerInventory;
    protected IItemHandler inventory;

    protected int inventorySize;

    /**
     * Creates the contianer object
     * @param playerInventory The players inventory
     * @param inventory The tile/object inventory
     */
    public BaseContainer(IInventory playerInventory, IItemHandler inventory) {
        this.playerInventory = playerInventory;
        this.inventory = inventory;

        inventorySize = inventory.getSlots();
    }

    /**
     * Get the size of the inventory that isn't the players
     * @return The inventory size that doesn't count the player inventory
     */
    public int getInventorySizeNotPlayer() {
        return inventorySize;
    }

    /**
     * Adds the player offset with Y offset
     *
     * @param offsetY How far down
     */
    protected void addPlayerInventorySlots(int offsetY) {
        addPlayerInventorySlots(8, offsetY);
    }

    /**
     * Adds player inventory at location, includes space between normal and hotbar
     *
     * @param offsetX X offset
     * @param offsetY Y offset
     */
    protected void addPlayerInventorySlots(int offsetX, int offsetY) {
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 9; column++)
                addSlotToContainer(new Slot(playerInventory,
                        column + row * 9 + 9,
                        offsetX + column * 18,
                        offsetY + row * 18));
        }

        for(int slot = 0; slot < 9; slot++)
            addSlotToContainer(new Slot(playerInventory, slot, offsetX + slot * 18, offsetY + 58));
    }

    /**
     * Adds a line of slots
     *
     * @param xOffset X offset
     * @param yOffset Y offset
     * @param start start slot number
     * @param count how many slots
     */
    protected void addInventoryLine(int xOffset, int yOffset, int start, int count) {
        addInventoryLine(xOffset, yOffset, start, count, 0);
    }

    /**
     * Adds a line of inventory slots with a margin around them
     *
     * @param xOffset X Offset
     * @param yOffset Y Offset
     * @param start The start slot id
     * @param count The count of slots
     * @param margin How much to pad the slots
     */
    protected void addInventoryLine(int xOffset, int yOffset, int start, int count, int margin) {
        int slotID = start;
        for(int x = 0; x < count; x++) {
            addSlotToContainer(new SlotItemHandler(inventory, slotID, xOffset + x * (18 + margin), yOffset));
            slotID++;
        }
    }

    /**
     * Adds an inventory grid to the container
     *
     * @param xOffset X pixel offset
     * @param yOffset Y pixel offset
     * @param width How many wide
     * @param start The start slot id
     */
    protected void addInventoryGrid(int xOffset, int yOffset, int width, int start) {
        int height = (int) Math.ceil(inventorySize / width);
        int slotID = start;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                addSlotToContainer(new SlotItemHandler(inventory, slotID, xOffset + x * 18, yOffset + y * 18));
                slotID++;
            }
        }
    }

    /**
     * The logic for when a phantom slot is clicked
     * @param slot The slot
     * @param mouseButton The mouse button
     * @param modifier The modifier
     * @param player The player
     * @return The stack
     */
    private ItemStack slotClickPhantom(Slot slot, int mouseButton, ClickType modifier, EntityPlayer player) {
        ItemStack stack = null;

        if(mouseButton == 2) {
            if(((IPhantomSlot)slot).canAdjust())
                slot.putStack(null);
        } else if(mouseButton == 0 || mouseButton == 1) {
            InventoryPlayer playerInv = player.inventory;
            slot.onSlotChanged();
            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();

            if(stackSlot != null)
                stack = stackSlot.copy();

            if(stackSlot == null) {
                if(stackHeld != null && slot.isItemValid(stackHeld))
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
            } else if(stackHeld == null) {
                adjustPhantomSlot(slot, mouseButton, modifier);
                slot.onTake(player, playerInv.getItemStack());
            } else if(slot.isItemValid(stackHeld)) {
                if(InventoryUtils.canStacksMerge(stackSlot, stackHeld))
                    adjustPhantomSlot(slot, mouseButton, modifier);
                else
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
            }
        }

        return stack;
    }

    /**
     * Used to adjust the items in the phantom slot
     * @param slot The slot being clicked
     * @param mouseButton The mouse button
     * @param modifier The modifier
     */
    private void adjustPhantomSlot(Slot slot, int mouseButton, ClickType modifier) {
        if(!((IPhantomSlot)slot).canAdjust())
            return;

        ItemStack stackSlot = slot.getStack();
        int stackSize = 0;
        if(modifier == ClickType.QUICK_MOVE)
            stackSize = (mouseButton == 0) ? (stackSlot.getCount() + 1) / 2 : stackSlot.getCount() * 2;
        else
            stackSize =  (mouseButton == 0) ? stackSlot.getCount() - 1 : stackSlot.getCount() + 1;

        if(stackSize > slot.getSlotStackLimit())
            stackSize = slot.getSlotStackLimit();

        stackSlot.setCount(stackSize);

        if(stackSlot.getCount() <= 0)
            slot.putStack(null);
    }

    /**
     * Fills the phantom slot with the given slot, not consuming the held stack
     * @param slot The slot
     * @param stackHeld The stack held
     * @param mouseButton The mouse button
     * @param modifier The modifier
     */
    private void fillPhantomSlot(Slot slot, ItemStack stackHeld, int mouseButton, ClickType modifier) {
        if(!((IPhantomSlot)slot).canAdjust())
            return;

        int stackSize = (mouseButton == 0) ? stackHeld.getCount() : 1;

        if(stackSize > slot.getSlotStackLimit())
            stackSize = slot.getSlotStackLimit();

        ItemStack phantomStack = stackHeld.copy();
        phantomStack.setCount(stackSize);

        slot.putStack(phantomStack);
    }

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    /**
     * The logic for when a slot is clicked
     * @param slotId The slot
     * @param dragType The mouse button
     * @param clickTypeIn The modifier
     * @param player The player
     * @return The stack
     */
    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        Slot slot = (slotId < 0) ? null : inventorySlots.get(slotId);
        if(slot != null) {
            if(slot instanceof IPhantomSlot)
                return slotClickPhantom(slot, dragType, clickTypeIn, player);
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        if(index < 0 || index > inventorySlots.size())
            return super.transferStackInSlot(playerIn, index);
        Slot slot = inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemToTransfer = slot.getStack();
            ItemStack copy = itemToTransfer.copy();

            if(index < getInventorySizeNotPlayer()) {
                if (!mergeItemStack(itemToTransfer, getInventorySizeNotPlayer(), inventorySlots.size(), true))
                    return null;
            } else if(!mergeItemStack(itemToTransfer, 0, getInventorySizeNotPlayer(), false))
                return null;

            if(itemToTransfer.getCount() == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if(itemToTransfer.getCount() != copy.getCount())
                return copy;
        }
        return null;
    }
}
