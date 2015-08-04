package com.teambr.bookshelf.common.container

import com.teambr.bookshelf.util.InventoryUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{IInventory, Slot, Container}
import net.minecraft.item.ItemStack

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
abstract class BaseContainer(val playerInventory: IInventory, val inventory: IInventory) extends Container {
    
    protected class RestrictedSlot(inventory: IInventory, slot: Int, x: Int, y: Int) extends Slot(inventory, slot, x, y) {
        val inventoryIndex = slot
        override def isItemValid(itemstack: ItemStack): Boolean = inventory.isItemValidForSlot(inventoryIndex, itemstack)
    }
    
    val inventorySize = inventory.getSizeInventory
    
    def addInventoryGrid(xOffset : Int, yOffset : Int, width : Int) : Unit = {
        val height = Math.ceil(inventorySize.toDouble / width).asInstanceOf[Int]
        var slotId = 0
        for(y <- 0 until height) {
            for(x <- 0 until width) {
                addSlotToContainer(new RestrictedSlot(inventory, slotId, xOffset + x * 18, yOffset + y * 18))
                slotId += 1
            }
        }
    }
    
    protected def addInventoryLine(xOffset: Int, yOffset: Int, start: Int, count: Int) {
        addInventoryLine(xOffset, yOffset, start, count, 0)
    }
    
    def addInventoryLine(xOffset: Int, yOffset: Int, start: Int, count: Int, margin: Int) : Unit = {
        var slotId = 0
        for(x <- start until count) {
            addSlotToContainer(new RestrictedSlot(inventory, slotId, xOffset + x * (18 + margin), yOffset))
            slotId += 1
        }
    }
    
    def addPlayerInventorySlots(offsetY: Int) {
        addPlayerInventorySlots(8, offsetY)
    }
    
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
    
    override def canInteractWith(entityPlayer: EntityPlayer): Boolean = inventory.isUseableByPlayer(entityPlayer)

    protected def mergeItemStackSafe(stackToMerge: ItemStack, start: Int, stop: Int, reverse: Boolean): Boolean = {
        var inventoryChanged: Boolean = false
        val delta: Int = if (reverse) -1 else 1
        val slots: java.util.List[Slot] = getSlots
        if (stackToMerge.isStackable) {
            var slotId: Int = if (reverse) stop - 1 else start
            while (stackToMerge.stackSize > 0 && ((!reverse && slotId < stop) || (reverse && slotId >= start))) {
                val slot: Slot = slots.get(slotId)
                if (slot.isItemValid(stackToMerge)) {
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
                if (slot.isItemValid(stackToMerge)) {
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
        val slot: Slot = inventorySlots.get(slotId).asInstanceOf[Slot]
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

    def getSlots : java.util.List[Slot] = inventorySlots.asInstanceOf[java.util.List[Slot]]
}

