package com.teambr.bookshelf.collections;

import com.teambr.bookshelf.common.tiles.traits.InventorySided;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * This file was created for NeoTech
 *
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since 1/27/2016
 */
public class SidedInventoryWrapper implements IItemHandlerModifiable {
    protected final InventorySided inv;
    protected final EnumFacing side;

    public SidedInventoryWrapper(InventorySided inv, EnumFacing side) {
        this.inv = inv;
        this.side = side;
    }

    public static int getSlot(InventorySided inv, int slot, EnumFacing side) {
        int[] slots = inv.getSlotsForFace(side);
        if (slot < slots.length)
            return slots[slot];
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SidedInventoryWrapper that = (SidedInventoryWrapper)o;

        return inv.equals(that.inv) && side == that.side;
    }

    @Override
    public int hashCode() {
        int result = inv.hashCode();
        result = 31 * result + side.hashCode();
        return result;
    }

    @Override
    public int getSlots()
    {
        return inv.getSlotsForFace(side).length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        int i = getSlot(inv, slot, side);
        return i == -1 ? null : inv.getStackInSlot(i);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {

        if (stack == null)
            return null;

        int slot1 = getSlot(inv, slot, side);

        if (slot1 == -1)
            return stack;

        if (!inv.isItemValidForSlot(slot1, stack) || !inv.canInsertItem(slot1, stack, side))
            return stack;

        ItemStack stackInSlot = inv.getStackInSlot(slot1);

        int m;
        if (stackInSlot != null) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            m = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit()) - stackInSlot.stackSize;

            if (stack.stackSize <= m) {
                if (!simulate) {
                    ItemStack copy = stack.copy();
                    copy.stackSize += stackInSlot.stackSize;
                    inv.setInventorySlotContents(slot1, copy);
                }

                return null;
            } else {
                if (!simulate) {
                    ItemStack copy = stack.splitStack(m);
                    copy.stackSize += stackInSlot.stackSize;
                    inv.setInventorySlotContents(slot1, copy);
                    return stack;
                } else {
                    stack.stackSize -= m;
                    return stack;
                }
            }
        } else {
            m = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit());
            if (m < stack.stackSize) {
                if (!simulate) {
                    inv.setInventorySlotContents(slot1, stack.splitStack(m));
                    return stack;
                } else {
                    stack.stackSize -= m;
                    return stack;
                }
            } else {
                if (!simulate)
                    inv.setInventorySlotContents(slot1, stack);
                return null;
            }
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        inv.setInventorySlotContents(slot, stack);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return null;

        int slot1 = getSlot(inv, slot, side);

        if (slot1 == -1)
            return null;

        ItemStack stackInSlot = inv.getStackInSlot(slot1);

        if (stackInSlot == null)
            return null;

        if (!inv.canExtractItem(slot1, stackInSlot, side))
            return null;

        if (simulate) {
            if (stackInSlot.stackSize < amount) {
                return stackInSlot.copy();
            } else {
                ItemStack copy = stackInSlot.copy();
                copy.stackSize = amount;
                return copy;
            }
        } else {
            int m = Math.min(stackInSlot.stackSize, amount);
            return inv.decrStackSize(slot1, m);
        }
    }
}