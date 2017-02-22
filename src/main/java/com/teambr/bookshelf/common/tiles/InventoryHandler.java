package com.teambr.bookshelf.common.tiles;

import com.teambr.bookshelf.common.container.IInventoryCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public abstract class InventoryHandler extends Syncable implements IItemHandlerModifiable {

    // Variables
    // A list to hold all callback objects
    private List<IInventoryCallback> callBacks = new ArrayList<>();
    // List of Inventory contents
    public Stack<ItemStack> inventoryContents = new Stack<>();

    public InventoryHandler() {
        inventoryContents.setSize(getInitialSize());
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * The initial size of the inventory
     *
     * @return How big to make the inventory on creation
     */
    protected abstract int getInitialSize();

    /**
     * Used to define if an item is valid for a slot
     *
     * @param index The slot id
     * @param stack The stack to check
     * @return True if you can put this there
     */
    protected abstract boolean isItemValidForSlot(int index, ItemStack stack);

    /*******************************************************************************************************************
     * InventoryHandler                                                                                                *
     *******************************************************************************************************************/

    /**
     * Add a callback to this inventory
     * @param iInventoryCallback The callback you wish to add
     * @return This object, to enable chaining
     */
    public InventoryHandler addCallback(IInventoryCallback iInventoryCallback) {
        callBacks.add(iInventoryCallback);
        return this;
    }

    /**
     * Called when the inventory has a change
     *
     * @param slot The slot that changed
     */
    protected void onInventoryChanged(int slot) {
        callBacks.forEach((IInventoryCallback callback) -> {
            callback.onInventoryChanged(this, slot);
        });
    }

    /**
     * Used to add just one stack into the end of the inventory
     *
     * @param stack The stack to push
     */
    public void addInventorySlot(ItemStack stack) {
        inventoryContents.push(stack);
    }

    /**
     * Used to push x amount of slots into the inventory
     *
     * @param count How many slots to add
     */
    public void addInventorySlots(int count) {
        for(int i = 0; i < count; i++)
            addInventorySlot(null);
    }

    /**
     * Used to remove the last element of the stack
     *
     * @return The last stack, now popped
     */
    public ItemStack removeInventorySlot() {
        return inventoryContents.pop();
    }

    /**
     * Used to remove a specific amount of items
     *
     * @param count The count of slots to remove
     * @return The array of the stacks that were there
     */
    public ItemStack[] removeInventorySlots(int count) {
        ItemStack[] poppedStacks = new ItemStack[count];
        for(int i = 0; i < count; i++)
            poppedStacks[i] = removeInventorySlot();
        return poppedStacks;
    }

    /**
     * Used to copy from an existing inventory
     *
     * @param inventory The inventory to copy from
     */
    public void copyFrom(IItemHandler inventory) {
        for(int i = 0; i < inventory.getSlots(); i++) {
            if(i < inventoryContents.size()) {
                ItemStack stack = inventory.getStackInSlot(i);
                if(stack != null)
                    inventoryContents.set(i, stack.copy());
                else
                    inventoryContents.set(i, null);
            }
        }
    }

    /**
     * Makes sure this slot is within our range
     * @param slot Which slot
     */
    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= inventoryContents.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inventoryContents.size() + ")");
    }

    /**
     * Gets the stack limit of a stack
     * @param slot The slot
     * @param stack The stack
     * @return Max stack size
     */
    protected int getStackLimit(int slot, ItemStack stack)
    {
        return stack.getMaxStackSize();
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    /**
     * Used to save the inventory to an NBT tag
     *
     * @param compound The tag to save to
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return writeToNBT(compound, "");
    }

    /**
     * Used to save the inventory to an NBT tag
     *
     * @param compound The tag to save to
     * @param inventoryName The name, in case you have more than one
     */
    public NBTTagCompound writeToNBT(NBTTagCompound compound, String inventoryName) {
        super.writeToNBT(compound);

        // Set the size
        compound.setInteger("Size:" + inventoryName, inventoryContents.size());

        // Write the inventory
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < inventoryContents.size(); i++) {
            if(inventoryContents.get(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot:" + inventoryName, (byte) i);
                inventoryContents.get(i).writeToNBT(stackTag);
                tagList.appendTag(stackTag);
            }
        }
        compound.setTag("Items:" + inventoryName, tagList);
        return compound;
    }

    /**
     * Used to read the inventory from an NBT tag compound
     *
     * @param compound The tag to read from
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readFromNBT(compound, "");
    }

    /**
     * Used to read the inventory from an NBT tag compound
     *
     * @param compound The tag to read from
     * @param inventoryName The inventory name
     */
    public void readFromNBT(NBTTagCompound compound, String inventoryName) {
        super.readFromNBT(compound);

        // Read Items
        NBTTagList tagList = compound.getTagList("Items:" + inventoryName, 10);
        inventoryContents = new Stack<>();
        if(compound.hasKey("Size:" + inventoryName))
            inventoryContents.setSize(compound.getInteger("Size:" + inventoryName));
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot:" + inventoryName);
            if(slot >= 0 && slot < inventoryContents.size())
                inventoryContents.set(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }
    }

    /**
     * Tests if this object has a certain capability
     * @param capability The questioned capability
     * @param facing Which face
     * @return Only true if Item Handler capability
     */
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    /**
     * Used to access the capability
     * @param capability The capability
     * @param facing Which face
     * @param <T> The object to case
     * @return Us as INSTANCE of T
     */
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this;
        else
            return super.getCapability(capability, facing);
    }

    /*******************************************************************************************************************
     * IItemHandlerModifiable                                                                                          *
     *******************************************************************************************************************/

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        if (ItemStack.areItemStacksEqual(this.inventoryContents.get(slot), stack))
            return;
        this.inventoryContents.set(slot, stack);
        onInventoryChanged(slot);
    }

    /*******************************************************************************************************************
     * IItemHandler                                                                                                    *
     *******************************************************************************************************************/

    @Override
    public int getSlots() {
        return inventoryContents.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return inventoryContents.get(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null || stack.stackSize == 0 || !isItemValidForSlot(slot, stack))
            return null;

        validateSlotIndex(slot);

        ItemStack existing = this.inventoryContents.get(slot);

        int limit = getStackLimit(slot, stack);

        if (existing != null) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.stackSize;
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.stackSize > limit;

        if (!simulate) {
            if (existing == null) {
                this.inventoryContents.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else {
                existing.stackSize += reachedLimit ? limit : stack.stackSize;
            }
            onInventoryChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return null;

        validateSlotIndex(slot);

        ItemStack existing = this.inventoryContents.get(slot);

        if (existing == null)
            return null;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.stackSize <= toExtract) {
            if (!simulate) {
                this.inventoryContents.set(slot, null);
                onInventoryChanged(slot);
            }
            return existing;
        }
        else {
            if (!simulate) {
                this.inventoryContents.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.stackSize - toExtract));
                onInventoryChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }
}
