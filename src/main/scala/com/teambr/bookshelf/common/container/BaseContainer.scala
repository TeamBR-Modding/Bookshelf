package com.teambr.bookshelf.common.container

import com.teambr.bookshelf.common.container.slots.IPhantomSlot
import com.teambr.bookshelf.util.InventoryUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{ClickType, Container, IInventory, Slot}
import net.minecraft.item.ItemStack
import net.minecraftforge.items.{IItemHandler, SlotItemHandler}

/**
 * This file was created for com.teambr.bookshelf.Bookshelf
 *
 * com.teambr.bookshelf.Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
abstract class BaseContainer(val playerInventory: IInventory, val inventory: IItemHandler) extends Container {

    val inventorySize = getInventorySizeNotPlayer

    //Sometimes we have two inventories that aren't the players
    def getInventorySizeNotPlayer : Int = inventory.getSlots

    /**
      * Adds an inventory grid to the container
      *
      * @param xOffset X pixel offset
      * @param yOffset Y pixel offset
      * @param width How many wide
      */
    def addInventoryGrid(xOffset : Int, yOffset : Int, width : Int) : Unit = {
        val height = Math.ceil(inventorySize.toDouble / width).asInstanceOf[Int]
        var slotId = 0
        for(y <- 0 until height) {
            for(x <- 0 until width) {
                addSlotToContainer(new SlotItemHandler(inventory, slotId, xOffset + x * 18, yOffset + y * 18))
                slotId += 1
            }
        }
    }

    /**
      * Adds a line of slots
      *
      * @param xOffset X offset
      * @param yOffset Y offset
      * @param start start slot number
      * @param count how many slots
      */
    protected def addInventoryLine(xOffset: Int, yOffset: Int, start: Int, count: Int) {
        addInventoryLine(xOffset, yOffset, start, count, 0)
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
    def addInventoryLine(xOffset: Int, yOffset: Int, start: Int, count: Int, margin: Int) : Unit = {
        var slotId = start
        for(x <- 0 until count) {
            addSlotToContainer(new SlotItemHandler(inventory, slotId, xOffset + x * (18 + margin), yOffset))
            slotId += 1
        }
    }

    /**
      * Adds the player offset with Y offset
      *
      * @param offsetY How far down
      */
    def addPlayerInventorySlots(offsetY: Int) {
        addPlayerInventorySlots(8, offsetY)
    }

    /**
      * Adds player inventory at location, includes space between normal and hotbar
      *
      * @param offsetX X offset
      * @param offsetY Y offset
      */
    def addPlayerInventorySlots (offsetX: Int, offsetY: Int) : Unit = {
        for(row <- 0 until 3) {
            for(column <- 0 until 9) {
                addSlotToContainer(new Slot(playerInventory,
                    column + row * 9 + 9,
                    offsetX + column * 18,
                    offsetY + row * 18))
            }
        }
        
        for(slot <- 0 until 9) {
            addSlotToContainer(new Slot(playerInventory, slot, offsetX + slot * 18, offsetY + 58))
        }
    }
    
    override def canInteractWith(entityPlayer: EntityPlayer): Boolean = true

    override def slotClick(slotNumber : Int, mouseButton : Int, modifier : ClickType, player : EntityPlayer) : ItemStack = {
        val slot = if (slotNumber < 0) null else this.inventorySlots.get(slotNumber)
        if(slot.isInstanceOf[IPhantomSlot])
             slotClickPhantom(slot, mouseButton, modifier, player)
        else
             super.slotClick(slotNumber, mouseButton, modifier, player)
    }

    def slotClickPhantom(slot : Slot, mouseButton : Int, modifier : ClickType, player : EntityPlayer) : ItemStack = {
        var stack : ItemStack = null

        if(mouseButton == 2) {
            if (slot.asInstanceOf[IPhantomSlot].canAdjust)
                slot.putStack(null)
        } else if(mouseButton == 0 || mouseButton == 1) {
            val playerInv = player.inventory
            slot.onSlotChanged()
            val stackSlot = slot.getStack
            val stackHeld = playerInv.getItemStack

            if(stackSlot != null)
                stack = stackSlot.copy

            if(stackSlot == null) {
                if(stackHeld != null && slot.isItemValid(stackHeld))
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier)
            } else if(stackHeld == null) {
                adjustPhantomSlot(slot, mouseButton, modifier)
                slot.onPickupFromSlot(player, playerInv.getItemStack)
            } else if(slot.isItemValid(stackHeld)) {
                if(InventoryUtils.canStacksMerge(stackSlot, stackHeld))
                    adjustPhantomSlot(slot, mouseButton, modifier)
                else
                    fillPhantomSlot(slot, stackHeld, mouseButton, modifier)
            }
        }
        stack
    }

    def adjustPhantomSlot(slot : Slot, mouseButton : Int, modifier : ClickType) : Unit = {
        if(!slot.asInstanceOf[IPhantomSlot].canAdjust)
            return

        val stackSlot = slot.getStack
        var stackSize : Int = 0
        if(modifier == ClickType.QUICK_MOVE) //TODO Unsure
            stackSize = if(mouseButton == 0) (stackSlot.stackSize + 1) / 2 else stackSlot.stackSize * 2
        else
            stackSize = if(mouseButton == 0) stackSlot.stackSize - 1 else stackSlot.stackSize + 1

        if(stackSize > slot.getSlotStackLimit)
            stackSize = slot.getSlotStackLimit

        stackSlot.stackSize = stackSize

        if(stackSlot.stackSize <= 0)
            slot.putStack(null)
    }

    def fillPhantomSlot(slot : Slot, stackHelf : ItemStack, mouseButton : Int, modifier : ClickType) : Unit = {
        if(!slot.asInstanceOf[IPhantomSlot].canAdjust)
            return

        var stackSize = if(mouseButton == 0) stackHelf.stackSize else 1
        if(stackSize > slot.getSlotStackLimit)
            stackSize  = slot.getSlotStackLimit

        val phantomStack = stackHelf.copy()
        phantomStack.stackSize = stackSize

        slot.putStack(phantomStack)
    }

    protected def mergeItemStackSafe(stackToMerge: ItemStack, start: Int, stop: Int, reverse: Boolean): Boolean = {
        var inventoryChanged: Boolean = false
        val delta: Int = if (reverse) -1 else 1
        val slots: java.util.List[Slot] = getSlots
        if (stackToMerge.isStackable) {
            var slotId: Int = if (reverse) stop - 1 else start
            while (stackToMerge.stackSize > 0 && ((!reverse && slotId < stop) || (reverse && slotId >= start))) {
                val slot: Slot = slots.get(slotId)
                if (slot.isItemValid(stackToMerge) && !slot.isInstanceOf[IPhantomSlot]) {
                    val stackInSlot: ItemStack = slot.getStack
                    if (InventoryUtils.tryMergeStacks(stackToMerge, stackInSlot)) {
                        slot.onSlotChanged()
                        inventoryChanged = true
                    }
                }
                slotId += delta
            }
        }
        if (stackToMerge.stackSize > 0) {
            var slotId: Int = if (reverse) stop - 1 else start
            while ((!reverse && slotId < stop) || (reverse && slotId >= start)) {
                val slot: Slot = slots.get(slotId)
                if (slot.isItemValid(stackToMerge) && !slot.isInstanceOf[IPhantomSlot]) {
                    val stackInSlot: ItemStack = slot.getStack
                    if (stackInSlot == null) {
                        slot.putStack(stackToMerge.copy)
                        slot.onSlotChanged()
                        stackToMerge.stackSize = 0
                        return true
                    }
                }
                slotId += delta
            }
        }
        inventoryChanged
    }

    override def transferStackInSlot(player: EntityPlayer, slotId: Int): ItemStack = {
        val slot: Slot = inventorySlots.get(slotId)
        if (slot != null && slot.getHasStack) {
            val itemToTransfer: ItemStack = slot.getStack
            val copy: ItemStack = itemToTransfer.copy
            if (slotId < inventorySize) {
                if (!mergeItemStackSafe(itemToTransfer, inventorySize, inventorySlots.size, reverse = true)) return null
            }
            else if (!mergeItemStackSafe(itemToTransfer, 0, inventorySize, reverse = false)) return null
            if (itemToTransfer.stackSize == 0) slot.putStack(null)
            else slot.onSlotChanged()
            if (itemToTransfer.stackSize != copy.stackSize) return copy
        }
        null
    }

    def getInventorySize: Int = inventorySize

    def getSlots : java.util.List[Slot] = inventorySlots
}

