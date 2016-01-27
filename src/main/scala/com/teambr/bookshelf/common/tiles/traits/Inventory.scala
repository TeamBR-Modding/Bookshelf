package com.teambr.bookshelf.common.tiles.traits

import java.util

import com.teambr.bookshelf.common.container.InventoryCallback
import com.teambr.bookshelf.traits.NBTSavable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{ NBTTagCompound, NBTTagList }
import net.minecraft.util.{ ChatComponentText, IChatComponent, StatCollector }
import net.minecraftforge.items.wrapper.InvWrapper
import net.minecraftforge.items.{IItemHandlerModifiable, ItemHandlerHelper, IItemHandler}

import scala.collection.mutable.ArrayBuffer

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis pauljoda
  * @since August 03, 2015
  */
trait Inventory extends IItemHandlerModifiable with NBTSavable {

    val callBacks = new ArrayBuffer[InventoryCallback]()
    var inventoryContents = new util.Stack[ItemStack]()
    inventoryContents.setSize(initialSize)

    /***
      * The initial size of the inventory
      *
      * @return How big to make the inventory on creation
      */
    def initialSize : Int

    /**
      * Used to add a callback to this inventory
      *
      * @param iInventory What you want to be called
      * @return This inventory
      */
    def addCallback(iInventory: InventoryCallback) : Inventory = {
        callBacks += iInventory
        this
    }

    /**
      * Called when the inventory has a change
      *
      * @param slot The slot that changed
      */
    def onInventoryChanged(slot : Int) =
        callBacks.foreach((callBack : InventoryCallback) => callBack.onInventoryChanged(this, slot))

    /**
      * Used to add just one stack into the end of the inventory
      *
      * @param stack The stack to push
      */
    def addInventorySlot(stack : ItemStack) = inventoryContents.push(stack)

    /**
      * Used to push x amount of slots into the inventory
      *
      * @param count How many slots to add
      */
    def addInventorySlots(count : Int) = for(i <- 0 until count) addInventorySlot(null)

    /**
      * Used to remove the last element of the stack
      *
      * @return The last stack, now popped
      */
    def removeInventorySlot() : ItemStack = inventoryContents.pop()

    /**
      * Used to remove a specific amount of items
      *
      * @param count The count of slots to remove
      * @return The array of the stacks that were there
      */
    def removeInventorySlots(count : Int) : Array[ItemStack] = {
        val poppedStack = new Array[ItemStack](count)
        for(i <- 0 until count) poppedStack(i) = removeInventorySlot()
        poppedStack
    }

    /**
      * Used to copy from an existing inventory
      *
      * @param inventory The inventory to copy from
      */
    def copyFrom(inventory : IItemHandler) = {
        for(i <- 0 until inventory.getSlots) {
            if (i < getSizeInventory) {
                val stack = inventory.getStackInSlot(i)
                if(stack != null) setInventorySlotContents(i, stack.copy) else setInventorySlotContents(i, null)
            }
        }
    }

    /**
      * Used to save the inventory to an NBT tag
      *
      * @param tag The tag to save to
      */
    def writeToNBT(tag : NBTTagCompound) : Unit = writeToNBT(tag, "")

    /**
      * Used to save the inventory to an NBT tag
      *
      * @param tag The tag to save to
      * @param inventoryName The name, in case you have more than one
      */
    def writeToNBT(tag : NBTTagCompound, inventoryName : String) = {
        tag.setInteger("Size:" + inventoryName, getSizeInventory)
        val nbttaglist = new NBTTagList
        for(i <- 0 until inventoryContents.size()) {
            if(inventoryContents.get(i) != null) {
                val stackTag = new NBTTagCompound
                stackTag.setByte("Slot:" + inventoryName, i.asInstanceOf[Byte])
                inventoryContents.get(i).writeToNBT(stackTag)
                nbttaglist.appendTag(stackTag)
            }
        }
        tag.setTag("Items:" + inventoryName, nbttaglist)
    }

    /**
      * Used to read the inventory from an NBT tag compound
      *
      * @param tag The tag to read from
      */
    def readFromNBT(tag : NBTTagCompound) : Unit = readFromNBT(tag, "")

    /**
      * Used to read the inventory from an NBT tag compound
      *
      * @param tag The tag to read from
      * @param inventoryName The inventory name
      */
    def readFromNBT(tag : NBTTagCompound, inventoryName : String) = {
        val nbttaglist = tag.getTagList("Items:" + inventoryName, 10)
        inventoryContents = new util.Stack[ItemStack]
        if(tag.hasKey("Size:" + inventoryName)) inventoryContents.setSize(tag.getInteger("Size:" + inventoryName))
        for(i <- 0 until nbttaglist.tagCount()) {
            val stacktag = nbttaglist.getCompoundTagAt(i)
            val j = stacktag.getByte("Slot:" + inventoryName)
            if(j >= 0 && j < inventoryContents.size())
                inventoryContents.set(j, ItemStack.loadItemStackFromNBT(stacktag))
        }
    }


    /*******************************************************************************************************************
      ****************************************** IItemHandler Methods ****************************************************
      ********************************************************************************************************************/
    /**
      * Returns the number of slots available
      *
      * @return The number of slots available
      **/
    override def getSlots: Int = getSizeInventory

    /**
      * Inserts an ItemStack into the given slot and return the remainder.
      * Note: This behaviour is subtly different from IFluidHandlers.fill()
      *
      * @param slot             Slot to insert into.
      * @param originalStack    ItemStack to insert
      * @param simulate         If true, the insertion is only simulated
      * @return                 The remaining ItemStack that was not inserted
      *                             (if the entire stack is accepted, then return null)
      **/
    override def insertItem(slot: Int, originalStack: ItemStack, simulate: Boolean): ItemStack = {
        if (originalStack == null) return null

        if (!isItemValidForSlot(slot, originalStack)) return originalStack

        val stackInSlot: ItemStack = getStackInSlot(slot)

        var minimum: Int = 0
        if (stackInSlot != null) {
            if (!ItemHandlerHelper.canItemStacksStack(originalStack, stackInSlot)) return originalStack
            minimum = Math.min(originalStack.getMaxStackSize, getInventoryStackLimit) - stackInSlot.stackSize
            if (originalStack.stackSize <= minimum) {
                if (!simulate) {
                    val stackCopy: ItemStack = originalStack.copy
                    stackCopy.stackSize += stackInSlot.stackSize
                    setInventorySlotContents(slot, stackCopy)
                    markDirty()
                }
                null
            } else {
                if (!simulate) {
                    val stackCopy: ItemStack = originalStack.splitStack(minimum)
                    stackCopy.stackSize += stackInSlot.stackSize
                    setInventorySlotContents(slot, stackCopy)
                    markDirty()
                    originalStack
                } else {
                    originalStack.stackSize -= minimum
                    originalStack
                }
            }
        } else {
            minimum = Math.min(originalStack.getMaxStackSize, getInventoryStackLimit)
            if (minimum < originalStack.stackSize) {
                if (!simulate) {
                    setInventorySlotContents(slot, originalStack.splitStack(minimum))
                    markDirty()
                    originalStack
                } else {
                    originalStack.stackSize -= minimum
                    originalStack
                }
            } else {
                if (!simulate) {
                    setInventorySlotContents(slot, originalStack)
                    markDirty()
                }
                null
            }
        }
    }

    /**
      * Extracts an ItemStack from the given slot. The returned value must be null
      * if nothing is extracted, otherwise it's stack size must not be greater than amount or the
      * itemstacks getMaxStackSize().
      *
      * @param extractSlot  Slot to extract from.
      * @param amount       Amount to extract (may be greater than the current stacks max limit)
      * @param simulate     If true, the extraction is only simulated
      * @return             ItemStack extracted from the slot, must be null, if nothing can be extracted
      **/
    override def extractItem(extractSlot: Int, amount: Int, simulate: Boolean): ItemStack = {
        if (amount == 0) return null

        val stackInSlot: ItemStack = getStackInSlot(extractSlot)

        if (stackInSlot == null) return null

        if (simulate) {
            if (stackInSlot.stackSize < amount) {
                stackInSlot.copy
            } else {
                val copy: ItemStack = stackInSlot.copy
                copy.stackSize = amount
                copy
            }
        } else {
            val m: Int = Math.min(stackInSlot.stackSize, amount)
            val decrStackSizeVal: ItemStack = decrStackSize(extractSlot, m)
            markDirty()
            decrStackSizeVal
        }
    }

    /**
      * Returns the ItemStack in a given slot.
      *
      * The result's stack size may be greater than the itemstacks max size.
      *
      * If the result is null, then the slot is empty.
      * If the result is not null but the stack size is zero, then it represents
      * an empty slot that will only accept* a specific itemstack.
      *
      * <p/>
      * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for
      * altering an inventories contents. Any implementers who are able to detect
      * modification through this method should throw an exception.
      * <p/>
      * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
      *
      * @param index Slot to query
      * @return ItemStack in given slot. May be null.
      **/

    override def getStackInSlot(index: Int): ItemStack = {
        if(index < inventoryContents.size())
            inventoryContents.get(index)
        else
            null
    }

    /**
      * Overrides the stack in the given slot. This method is used by the
      * standard Forge helper methods and classes. It is not intended for
      * general use by other mods, and the handler may throw an error if it
      * is called unexpectedly.
      *
      * @param slot  Slot to modify
      * @param stack ItemStack to set slot to (may be null)
      * @throws RuntimeException if the handler is called in a way that the handler
      *                          was not expecting.
      **/
    override def setStackInSlot(slot : Int, stack : ItemStack) = setInventorySlotContents(slot, stack)

    /*******************************************************************************************************************
      ************************************ IInventory (Legacy) Methods *************************************************
      ******************************************************************************************************************/

    /**
      * The code below is remaining from the IInventory. We no longer use IInventory, but the code handles inventories
      * well internally so we are keeping them. You should never use these if you don't absolutely have to. You should
      * try to use the IItemHandler methods when at all possible but if you need these, they are available to you.
      *
      * DO NOT USE FOR CROSS MOD INTERACTION. THIS SHOULD ONLY BE USED WITHIN YOU OWN INVENTORY CODE
      */

    /**
      * Called to decrease a stack in the inventory
      *
      * @param slot The slot to decrease
      * @param amount The amount to remove
      * @return The new stack (the one picked up)
      */
    def decrStackSize(slot : Int, amount : Int) : ItemStack = {
        if(inventoryContents.get(slot) != null) {
            var stack : ItemStack = null

            if(this.inventoryContents.get(slot).stackSize <= amount) {
                stack = this.inventoryContents.get(slot)
                this.inventoryContents.set(slot, null)
                onInventoryChanged(slot)
                return stack
            }

            stack = this.inventoryContents.get(slot).splitStack(amount)

            if(this.inventoryContents.get(slot).stackSize <= 0)
                this.inventoryContents.set(slot, null)

            onInventoryChanged(slot)
            return stack
        }
        null
    }

    /**
      * Gets the stack limit of the inventory
      *
      * @return How big this can stack
      */
    def getInventoryStackLimit: Int = 64

    /**
      * Get the size of this inventory
      *
      * @return How big this is
      */
    def getSizeInventory: Int = inventoryContents.size()

    /**
      * Removes a stack from the given slot and returns it.
      */
    def removeStackFromSlot (index: Int) : ItemStack = {
        val stack = inventoryContents.get(index).copy()
        inventoryContents.set(index, null)
        stack
    }

    /**
      * Used to get the stack when closing. Used when you drop items on the floor ie a workbench
      *
      * @param index The slot
      * @return What was in that stack, now null
      */
    def getStackInSlotOnClosing(index: Int): ItemStack = {
        if(index >= this.inventoryContents.size()) return null
        if(this.inventoryContents.get(index) != null) {
            val stack = this.inventoryContents.get(index)
            this.inventoryContents.set(index, null)
            return stack.asInstanceOf[ItemStack]
        }
        null
    }

    /**
      * Used to define if an item is valid for a slot
      *
      * @param index The slot id
      * @param stack The stack to check
      * @return True if you can put this there
      */
    def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = true

    /**
      * Used to see if the player can interact with this inventory
      *
      * @param player The player trying to access
      * @return True if the player is allowed
      */
    def isUseableByPlayer(player: EntityPlayer): Boolean = true

    /**
      * Set the slot contents
      *
      * @param index The index
      * @param stack The set to set
      */
    def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
        this.inventoryContents.set(index, stack)

        if(stack != null && stack.stackSize > getInventoryStackLimit)
            stack.stackSize  = getInventoryStackLimit

        onInventoryChanged(index)
    }

    /**
      * Going to go ahead and implement this, but don't trust it to update. Minecraft won't call enough to really follow
      */
    def markDirty(): Unit = onInventoryChanged(0)

    /**
      * Delete all the things
      */
    def clear(): Unit = for(i <- 0 until inventoryContents.size) inventoryContents.set(i, null)

    /**
      * Called when the player opens the inventory.
      *
      * An example would be the chest, signaling the render to move the lid
      *
      * @param player The player
      */
    def openInventory(player: EntityPlayer): Unit = {}

    /**
      * Called when the player closes the inventory.
      *
      * An example would be the chest, signaling the render to move the lid
      */
    def closeInventory(player: EntityPlayer): Unit = {}
}
