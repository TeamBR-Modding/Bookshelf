package com.teambr.bookshelf.common.tiles.traits

import java.util

import com.teambr.bookshelf.common.container.IInventoryCallback
import com.teambr.bookshelf.traits.NBTSavable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{ NBTTagCompound, NBTTagList }
import net.minecraft.util.{ ChatComponentText, IChatComponent, StatCollector }

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
trait Inventory extends IInventory with NBTSavable {

    var inventoryName : String
    def initialSize : Int

    val callBacks = new ArrayBuffer[IInventoryCallback]()
    var inventoryContents = new util.Stack[ItemStack]()
    inventoryContents.setSize(initialSize)


    /**
      * Used to add a callback to this inventory
      * @param iInventory What you want to be called
      * @return This inventory
      */
    def addCallback(iInventory: IInventoryCallback) : Inventory = {
        callBacks += iInventory
        this
    }

    /**
      * Called when the inventory has a change
      * @param slot The slot that changed
      */
    def onInventoryChanged(slot : Int) = callBacks.foreach((callBack : IInventoryCallback) => callBack.onInventoryChanged(this, slot))

    /**
      * Used to add just one stack into the end of the inventory
      * @param stack The stack to push
      */
    def addInventorySlot(stack : ItemStack) = inventoryContents.push(stack)

    /**
      * Used to push x amount of slots into the inventory
      * @param count How many slots to add
      */
    def addInventorySlots(count : Int) = for(i <- 0 until count) addInventorySlot(null)

    /**
      * Used to remove the last element of the stack
      * @return The last stack, now popped
      */
    def removeInventorySlot() : ItemStack = inventoryContents.pop()

    /**
      * Used to remove a specific amount of items
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
      * @param inventory The inventory to copy from
      */
    def copyFrom(inventory : IInventory) = {
        for(i <- 0 until inventory.getSizeInventory) {
            if (i < getSizeInventory) {
                val stack = inventory.getStackInSlot(i)
                if(stack != null) setInventorySlotContents(i, stack.copy) else setInventorySlotContents(i, null)
            }
        }
    }

    /**
      * Used to save the inventory to an NBT tag
      * @param tag The tag to save to
      */
    def writeToNBT(tag : NBTTagCompound) : Unit = writeToNBT(tag, "")

    /**
      * Used to save the inventory to an NBT tag
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
      * @param tag The tag to read from
      */
    def readFromNBT(tag : NBTTagCompound) : Unit = readFromNBT(tag, "")

    /**
      * Used to read the inventory from an NBT tag compound
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
      *************************************** IInventory Methods ********************************************************
      *******************************************************************************************************************/

    /**
      * Called to decrease a stack in the inventory
      * @param slot The slot to decrease
      * @param amount The amount to remove
      * @return The new stack (the one picked up)
      */
    override def decrStackSize(slot : Int, amount : Int) : ItemStack = {
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
      * @return How big this can stack
      */
    override def getInventoryStackLimit: Int = 64

    /**
      * Get the size of this inventory
      * @return How big this is
      */
    override def getSizeInventory: Int = inventoryContents.size()

    /**
      * Get the stack in the given slot
      * @param index The slot id
      * @return THe stack in the slot
      */
    override def getStackInSlot(index: Int): ItemStack = {
        if(index < inventoryContents.size())
            inventoryContents.get(index)
        else
            null
    }

    /**
      * Removes a stack from the given slot and returns it.
      */
    override def removeStackFromSlot (index: Int) : ItemStack = {
        val stack = inventoryContents.get(index).copy()
        inventoryContents.set(index, null)
        stack
    }

    /**
      * Used to get the stack when closing. Used when you drop items on the floor ie a workbench
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
      * @param index The slot id
      * @param stack The stack to check
      * @return True if you can put this there
      */
    override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = true

    /**
      * Used to see if the player can interact with this inventory
      * @param player The player trying to access
      * @return True if the player is allowed
      */
    override def isUseableByPlayer(player: EntityPlayer): Boolean = true

    /**
      * Set the slot contents
      * @param index The index
      * @param stack The set to set
      */
    override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
        this.inventoryContents.set(index, stack)

        if(stack != null && stack.stackSize > getInventoryStackLimit)
            stack.stackSize  = getInventoryStackLimit

        onInventoryChanged(index)
    }

    /**
      * Going to go ahead and implement this, but don't trust it to update. Minecraft won't call enough to really follow
      */
    override def markDirty(): Unit = onInventoryChanged(0)

    /**
      * Delete all the things
      */
    override def clear(): Unit = for(i <- 0 until inventoryContents.size) inventoryContents.set(i, null)

    /**
      * Something new in 1.8. Might allow you to edit things from client
      */
    override def getFieldCount: Int = 0

    /**
      * Something new in 1.8. Might allow you to edit things from client
      */
    override def getField(id: Int): Int = 0

    /**
      * Something new in 1.8. Might allow you to edit things from client
      */
    override def setField(id: Int, value: Int): Unit = {}

    /**
      * Called when the player opens the inventory.
      *
      * An example would be the chest, signaling the render to move the lid
      * @param player The player
      */
    override def openInventory(player: EntityPlayer): Unit = {}

    /**
      * Called when the player closes the inventory.
      *
      * An example would be the chest, signaling the render to move the lid
      */
    override def closeInventory(player: EntityPlayer): Unit = {}


    /*******************************************************************************************************************
      ****************************************** INamable Methods ********************************************************
      ********************************************************************************************************************/

    /**
      * New in 1.8, gives a name to the inventory
      * @return The name
      */
    override def getName: String = inventoryName

    /**
      * Does this inventory has a custom name
      * @return True if there is a name (localized)
      */
    override def hasCustomName: Boolean

    /**
      * New in 1.8, gives a name to the inventory. Probably used in chat messages
      * @return The name
      */
    override def getDisplayName: IChatComponent = new ChatComponentText(if(hasCustomName) StatCollector.translateToLocal(inventoryName) else "Generic Inventory")
}
