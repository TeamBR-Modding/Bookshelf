package com.teambr.bookshelf.util

import com.teambr.bookshelf.common.container.Inventory
import net.minecraft.init.Blocks
import net.minecraft.inventory.{IInventory, ISidedInventory, InventoryLargeChest}
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.{TileEntity, TileEntityChest}
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.{ILockableContainer, World}

import scala.collection.mutable.ArrayBuffer

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
object InventoryUtils {
    /** *
      * Try to merge the supplied stack into the supplied slot in the target
      * inventory
      *
      * @param targetInventory
     *  Although it doesn't return anything, it'll REDUCE the stack
      * size of the stack that you pass in
      *
      * @param slot
      * @param stack
      */
    def tryInsertStack(targetInventory: IInventory, slot: Int, stack: ItemStack, canMerge: Boolean) {
        if (targetInventory.isItemValidForSlot(slot, stack)) {
            val targetStack: ItemStack = targetInventory.getStackInSlot(slot)
            if (targetStack == null) {
                targetInventory.setInventorySlotContents(slot, stack.copy)
                stack.stackSize = 0
            }
            else if (canMerge) {
                if (targetInventory.isItemValidForSlot(slot, stack) && areMergeCandidates(stack, targetStack)) {
                    val space: Int = targetStack.getMaxStackSize - targetStack.stackSize
                    val mergeAmount: Int = Math.min(space, stack.stackSize)
                    val copy: ItemStack = targetStack.copy
                    copy.stackSize += mergeAmount
                    targetInventory.setInventorySlotContents(slot, copy)
                    stack.stackSize -= mergeAmount
                }
            }
        }
    }

    def tryMergeStacks(stackToMerge: ItemStack, stackInSlot: ItemStack): Boolean = {
        if (stackInSlot == null || !stackInSlot.isItemEqual(stackToMerge) || !ItemStack.areItemStackTagsEqual(stackToMerge, stackInSlot)) return false
        val newStackSize: Int = stackInSlot.stackSize + stackToMerge.stackSize
        val maxStackSize: Int = stackToMerge.getMaxStackSize
        if (newStackSize <= maxStackSize) {
            stackToMerge.stackSize = 0
            stackInSlot.stackSize = newStackSize
            return true
        }
        else if (stackInSlot.stackSize < maxStackSize) {
            stackToMerge.stackSize -= maxStackSize - stackInSlot.stackSize
            stackInSlot.stackSize = maxStackSize
            return true
        }
        false
    }

    def areItemAndTagEqual(stackA: ItemStack, stackB: ItemStack): Boolean = {
        stackA.isItemEqual(stackB) && ItemStack.areItemStackTagsEqual(stackA, stackB)
    }

    def areMergeCandidates(source: ItemStack, target: ItemStack): Boolean = {
        areItemAndTagEqual(source, target) && target.stackSize < target.getMaxStackSize
    }

    def insertItemIntoInventory(inventory: IInventory, stack: ItemStack) {
        insertItemIntoInventory(inventory, stack, EnumFacing.UP, -1)
    }

    def insertItemIntoInventory(inventory: IInventory, stack: ItemStack, side: EnumFacing, intoSlot: Int) {
        insertItemIntoInventory(inventory, stack, side, intoSlot, doMove = true)
    }

    def insertItemIntoInventory(inventory: IInventory, stack: ItemStack, side: EnumFacing, intoSlot: Int, doMove: Boolean) {
        insertItemIntoInventory(inventory, stack, side, intoSlot, doMove, canStack = true)
    }

    def insertItemIntoInventory(inventory: IInventory, stack: ItemStack, side: EnumFacing, intoSlot: Int, doMove: Boolean, canStack: Boolean) {
        if (stack == null) return

        var targetInventory = inventory

        //If we aren't really moving, just clone the inventory (for science)
        if (!doMove) {
            val copy: Inventory = new Inventory("temporary.inventory", false, targetInventory.getSizeInventory)
            copy.copyFrom(inventory)
            targetInventory = copy
        }

        val attemptSlots = new ArrayBuffer[Int]

        //We need to know if this is a sided inventory, if we don't case then just skip
        val isSidedInventory: Boolean = inventory.isInstanceOf[ISidedInventory] && side != EnumFacing.UP

        //If sided, just get the sides we can deal with
        if (isSidedInventory) {
            val accessibleSlots: Array[Int] = inventory.asInstanceOf[ISidedInventory].getSlotsForFace(side)
            for (slot <- accessibleSlots) attemptSlots += slot
        }
        else { //Just add everything then
            for(a <- 0 until inventory.getSizeInventory)
                attemptSlots += a
        }

        //If we have a specific slot, we shall use than. Otherwise trim the rest out
        if (intoSlot > -1) attemptSlots.filter((i : Int) => i == intoSlot)
        if (attemptSlots.isEmpty) return //Nothing to check
        for (slot <- attemptSlots) {
            if (stack.stackSize <= 0)  return //How did we get this far without noticing, leave now
            if (isSidedInventory && inventory.asInstanceOf[ISidedInventory].canInsertItem(slot, stack, side)) //Must be able to insert
                tryInsertStack(targetInventory, slot, stack, canStack)
            else //Who cares, go for it
                tryInsertStack(targetInventory, slot, stack, canStack)
        }
    }

    /** *
      * Move an item from the fromInventory, into the target. The target can be
      * an inventory or pipe.
      * Double checks are automagically wrapped. If you're not bothered what slot
      * you insert into, pass -1 for intoSlot. If you're passing false for
      * doMove, it'll create a dummy inventory and its calculations on that
      * instead
      *
      * @param fromInventory
      * the inventory the item is coming from
      * @param fromSlot
      * the slot the item is coming from
      * @param target
      *  the inventory you want the item to be put into. can be BC pipe
      * or IInventory
      * @param intoSlot
      * the target slot. Pass -1 for any slot
      * @param maxAmount
      * The maximum amount you wish to pass
      * @param direction
      * The direction of the move. Pass UNKNOWN if not applicable
      * @param doMove
      * @param canStack
      * @return The amount of items moved
      */
    def moveItemInto(fromInventory: IInventory, fromSlot: Int, target: AnyRef, intoSlot: Int, maxAmount: Int, direction: EnumFacing, doMove: Boolean, canStack: Boolean): Int = {
        val movedFrom = getInventory(fromInventory)

        //If there isn't anything from the source, nothing was moved. Duh
        val sourceStack: ItemStack = fromInventory.getStackInSlot(fromSlot)
        if (sourceStack == null) {
            return 0
        }

        movedFrom match { //Check if it is sided, we should break if we can't move further
            case inventory: ISidedInventory if !inventory.canExtractItem(fromSlot, sourceStack, direction) => return 0
            case _ =>
        }

        //Create a clone and make it maxSize or current size
        val clonedSourceStack: ItemStack = sourceStack.copy
        clonedSourceStack.stackSize = Math.min(clonedSourceStack.stackSize, maxAmount)
        val amountToMove: Int = clonedSourceStack.stackSize
        var inserted: Int = 0

        target match {
            case inventory: IInventory =>
                val targetInventory: IInventory = getInventory(inventory)
                val side = direction.getOpposite
                //Try and move into this. Then, remove how much was removed
                insertItemIntoInventory(targetInventory, clonedSourceStack, side, intoSlot, doMove, canStack)
                inserted = amountToMove - clonedSourceStack.stackSize
            case _ =>
        }

        //If we have finished our moving and should actually move things, change the stacks
        if (doMove) {
            val newSourcestack: ItemStack = sourceStack.copy
            newSourcestack.stackSize -= inserted
            if (newSourcestack.stackSize == 0)
                fromInventory.setInventorySlotContents(fromSlot, null)
            else
                fromInventory.setInventorySlotContents(fromSlot, newSourcestack)
        }

        inserted
    }

    private def doubleChestFix(te: TileEntity): IInventory = {
        val world = te.getWorld
        val pos = te.getPos
        if (world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock == Blocks.chest) return new InventoryLargeChest("Large chest", world.getTileEntity(pos.offset(EnumFacing.NORTH)).asInstanceOf[ILockableContainer], te.asInstanceOf[ILockableContainer])
        if (world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock == Blocks.chest) return new InventoryLargeChest("Large chest", world.getTileEntity(pos.offset(EnumFacing.SOUTH)).asInstanceOf[ILockableContainer], te.asInstanceOf[ILockableContainer])
        if (world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock  == Blocks.chest) return new InventoryLargeChest("Large chest", world.getTileEntity(pos.offset(EnumFacing.EAST)).asInstanceOf[ILockableContainer],  te.asInstanceOf[ILockableContainer])
        if (world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock  == Blocks.chest) return new InventoryLargeChest("Large chest", world.getTileEntity(pos.offset(EnumFacing.WEST)).asInstanceOf[ILockableContainer],  te.asInstanceOf[ILockableContainer])
        te match {
            case inventory: IInventory => inventory
            case _ => null
        }
    }

    def getInventory(world: World, blockPos : BlockPos): IInventory = {
        val tileEntity: TileEntity = world.getTileEntity(blockPos)
        if (tileEntity.isInstanceOf[TileEntityChest]) return doubleChestFix(tileEntity)
        tileEntity match {
            case inventory: IInventory => inventory
            case _ => null
        }
    }

    def getInventory(world: World, pos : BlockPos, direction: EnumFacing): IInventory = {
        val blockPos = new BlockPos(pos)
        if (direction != null)
            blockPos.offset(direction)
        getInventory(world, blockPos)
    }

    def getInventory(inventory: IInventory): IInventory = {
        if (inventory.isInstanceOf[TileEntityChest]) return doubleChestFix(inventory.asInstanceOf[TileEntity])
        return inventory
    }
}
