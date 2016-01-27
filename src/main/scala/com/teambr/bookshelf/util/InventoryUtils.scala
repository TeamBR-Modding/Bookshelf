package com.teambr.bookshelf.util

import java.util

import net.minecraft.inventory.{IInventory, ISidedInventory}
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.{ItemHandlerHelper, IItemHandler}
import net.minecraftforge.items.wrapper.{InvWrapper, SidedInvWrapper}

/**
  * This file was created for Bookshelf
  *
  * Bookshelf if licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis pauljoda
  * @since August 02, 2015
  */
object InventoryUtils {

    /**
      * Used to move items from one inventory to another. Must come from an IItemHandler! You can wrap it if you have to
      *     but since you'll be calling from us, there shouldn't be an issue
      *
      * @param fromInventory The IItemHandler to come from
      * @param fromSlot The from slot, -1 for any
      * @param target The target inventory, can be IInventory, ISideInventory, or preferably IItemHandler
      * @param intoSlot The slot to move into the target, -1 for any
      * @param maxAmount The max amount to move/extract
      * @param dir The direction moving into, so the face of the fromInventory
      * @param doMove True to actually do the move, false to simulate
      * @return True if something was moved
      */
    def moveItemInto(fromInventory : IItemHandler, fromSlot : Int, target : AnyRef, intoSlot : Int, maxAmount : Int, dir : EnumFacing, doMove : Boolean) : Boolean = {
        var otherInv : IItemHandler = null

        if(!target.isInstanceOf[IItemHandler]) {
            target match {
                case iInventory: IInventory if !iInventory.isInstanceOf[ISidedInventory] => otherInv = new InvWrapper(iInventory)
                case iSided: ISidedInventory => otherInv = new SidedInvWrapper(iSided, dir.getOpposite)
                case _ => return false //Not an inventory or IItemHandler
            }
        } else target match { //If we are a ItemHandler, we want to make sure not to wrap, it can be both IInventory and IItemHandler
            case itemHandler: IItemHandler => otherInv = itemHandler
            case _ => return false //Nothing else, somehow?
        }

        val fromSlots = new util.ArrayList[Int]()

        //Add from slots
        if(fromSlot != -1)
            fromSlots.add(fromSlot)
        else
            for(x <- 0 until fromInventory.getSlots)
                fromSlots.add(x)


        val toSlots = new util.ArrayList[Int]()

        //Add to slots
        if(intoSlot != -1)
            toSlots.add(intoSlot)
        else
            for(x <- 0 until otherInv.getSlots)
                toSlots.add(x)

        for(x <- 0 until fromSlots.size) { //Cycle the from inventory
            if(fromInventory.getStackInSlot(fromSlots.get(x)) != null) { //If something does exist
                val fromStack = fromInventory.extractItem(fromSlots.get(x), maxAmount, true) //Simulate so we can see changes
                if (fromStack != null) { //Make sure something was extracted
                    for (j <- 0 until toSlots.size()) { //Try to put it somewhere
                        val slotID = toSlots.get(j) //Get the slot to put into
                        val beforeStack = if(otherInv.getStackInSlot(slotID) != null) otherInv.getStackInSlot(slotID).copy() else null //First Copy
                        val movedStack = otherInv.insertItem(slotID, fromStack.copy(), !doMove) //Try to insert
                        val afterStack = if(otherInv.getStackInSlot(slotID) != null) otherInv.getStackInSlot(slotID).copy() else null  //Second Copy
                        if (!ItemStack.areItemStacksEqual(beforeStack, afterStack)) { //If the insert changed the stack
                            fromInventory.extractItem(fromSlots.get(x),
                                if(movedStack != null) fromStack.stackSize - movedStack.stackSize else maxAmount,
                                !doMove) //We need to pull if we are told
                            return true //We did it!
                        }
                    }
                }
            }
        }
        false //Nothing was moved
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

    def canStacksMerge(stack1 : ItemStack, stack2 : ItemStack) : Boolean = {
        if(stack1 == null || stack2 == null)
            return false
        if(!stack1.isItemEqual(stack2))
            return false
        if(!ItemStack.areItemStackTagsEqual(stack1, stack2))
            return false
        true
    }
}
