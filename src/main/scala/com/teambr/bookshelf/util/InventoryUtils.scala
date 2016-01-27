package com.teambr.bookshelf.util

import java.util

import com.teambr.bookshelf.common.tiles.traits.Inventory
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
      * Used to move items from one inventory to another. You can wrap it if you have to
      *     but since you'll be calling from us, there shouldn't be an issue
      *
      * @param source The source inventory, can be IInventory, ISideInventory, or preferably IItemHandler
      * @param fromSlot The from slot, -1 for any
      * @param target The target inventory, can be IInventory, ISideInventory, or preferably IItemHandler
      * @param intoSlot The slot to move into the target, -1 for any
      * @param maxAmount The max amount to move/extract
      * @param dir The direction moving into, so the face of the fromInventory
      * @param doMove True to actually do the move, false to simulate
      * @return True if something was moved
      */
    def moveItemInto(source : AnyRef, fromSlot : Int, target : AnyRef, intoSlot : Int, maxAmount : Int, dir : EnumFacing, doMove : Boolean) : Boolean = {
        //Try to cast source
        var fromInventory : IItemHandler = null

        if(!source.isInstanceOf[IItemHandler]) {
            source match {
                case iInventory: IInventory if !iInventory.isInstanceOf[ISidedInventory] => fromInventory = new InvWrapper(iInventory)
                case iSided: ISidedInventory => fromInventory = new SidedInvWrapper(iSided, dir.getOpposite)
                case _ => return false //Not an inventory or IItemHandler
            }
        } else source match { //If we are a ItemHandler, we want to make sure not to wrap, it can be both IInventory and IItemHandler
            case itemHandler: IItemHandler => fromInventory = itemHandler
            case _ => return false //Nothing else, somehow?
        }

        //Try to case sink
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

        //Do actual Movement
        for(x <- 0 until fromSlots.size) { //Cycle the from inventory
            if(fromInventory.getStackInSlot(fromSlots.get(x)) != null) { //If something does exist
            val fromStack = fromInventory.extractItem(fromSlots.get(x), maxAmount, true) //Simulate so we can see changes
                if (fromStack != null) { //Make sure something was extracted
                    for (j <- 0 until toSlots.size()) { //Try to put it somewhere
                    val slotID = toSlots.get(j) //Get the slot to put into

                        var otherInvSimulate : IItemHandler = null //We are going to use a proxy, for simulations

                        if(!doMove) {
                            otherInvSimulate = otherInv //No need to make it something else, just use the orginal
                        } else {
                            otherInvSimulate = new Inventory { //Since we are simulating, we need to pretend
                                override var inventoryName: String = "TEMPINV"
                                override def hasCustomName: Boolean = false
                                override def initialSize: Int = otherInv.getSlots
                            }
                            otherInvSimulate.asInstanceOf[Inventory].copyFrom(otherInv) //Copy stacks
                        }

                        val beforeStack = if(otherInvSimulate.getStackInSlot(slotID) != null) otherInvSimulate.getStackInSlot(slotID).copy() else null //First Copy
                        var movedStack = otherInvSimulate.insertItem(slotID, fromStack.copy(), false) //Try to insert
                        val afterStack = if(otherInvSimulate.getStackInSlot(slotID) != null) otherInvSimulate.getStackInSlot(slotID).copy() else null  //Second Copy
                        if (!ItemStack.areItemStacksEqual(beforeStack, afterStack)) { //If the insert changed the stack
                            if(!doMove) { //If we should actually do the move lets do it
                                movedStack = otherInv.insertItem(slotID, fromStack.copy(), false) //Do insertion on original
                                fromInventory.extractItem(fromSlots.get(x),
                                    if (movedStack != null) fromStack.stackSize - movedStack.stackSize else maxAmount,
                                    false) //Do Extraction on original
                            }
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
