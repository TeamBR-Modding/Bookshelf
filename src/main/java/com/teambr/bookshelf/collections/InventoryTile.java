package com.teambr.bookshelf.collections;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Stack;

/**
 * This is the interface used for inventories. It makes things eaiser than using an array and allows to add and remove
 * on the fly
 */
public class InventoryTile {
    /**
     * The collection of itemstacks
     */
    private Stack<ItemStack> inventory;

    /**
     * Used to create the inventory
     * @param size Here you can set the initial size, if needed
     */
    public InventoryTile(int size) {
        inventory = new Stack<>();
        inventory.setSize(size);
    }

    /**
     * Get the stack in a particular slot
     * @param slot The slot to get
     * @return The itemstack in the slot, can be null
     */
    public ItemStack getStackInSlot(int slot) {
        return inventory.get(slot);
    }

    /**
     * Set the stack in a particular slot
     * @param stack The stack to set
     * @param slot The slot to set it in
     */
    public void setStackInSlot(ItemStack stack, int slot) {
        inventory.set(slot, stack);
    }

    /**
     * Get the size of the inventory
     * @return How big this inventory is
     */
    public int getSizeInventory() {
        return inventory.size();
    }

    /**
     * Get the stack
     * @return The itemstack stack
     */
    public Stack<ItemStack> getValues() {
        return inventory;
    }

    /**
     * Remove all stacks in the stack
     */
    public void clear() {
        inventory.removeAllElements();
    }

    /**
     * Used to push a stack into the stack
     * @param stack The stack to stack
     */
    public void push(ItemStack stack) {
        inventory.push(stack);
    }

    /**
     * Removes the top of the stack
     * @return The top of the stack, now removed
     */
    public ItemStack pop() {
        return inventory.pop();
    }

    /**
     * Used to load from the NBT tag
     * @param tagCompound The tag to read from
     */
    public void readFromNBT(NBTTagCompound tagCompound) {
        NBTTagList itemsTag = tagCompound.getTagList("Items", 10);
        this.inventory = new Stack<>();
        inventory.setSize(tagCompound.getInteger("Size"));
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbtTagCompound1 = itemsTag.getCompoundTagAt(i);
            NBTBase nbt = nbtTagCompound1.getTag("Slot");
            int j = -1;
            if ((nbt instanceof NBTTagByte)) {
                j = nbtTagCompound1.getByte("Slot") & 0xFF;
            } else {
                j = nbtTagCompound1.getShort("Slot");
            }
            if ((j >= 0)) {
                this.inventory.set(j, ItemStack.loadItemStackFromNBT(nbtTagCompound1));
            }
        }
    }

    /**
     * Used to store information on the tag
     * @param tagCompound The tag to write on
     */
    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagList nbtTagList = new NBTTagList();
        tagCompound.setInteger("Size", inventory.size());
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i) != null) {
                NBTTagCompound nbtTagCompound1 = new NBTTagCompound();
                nbtTagCompound1.setShort("Slot", (short)i);
                this.inventory.get(i).writeToNBT(nbtTagCompound1);
                nbtTagList.appendTag(nbtTagCompound1);
            }
        }
        tagCompound.setTag("Items", nbtTagList);
    }
}
