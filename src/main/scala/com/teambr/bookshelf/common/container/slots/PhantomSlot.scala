package com.teambr.bookshelf.common.container.slots

import com.teambr.bookshelf.common.tiles.traits.Inventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{IInventory, Slot}
import net.minecraftforge.items.SlotItemHandler

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/21/2016
  */
class PhantomSlot(inventory : Inventory, index : Int, posX : Int, posY : Int) extends
    SlotItemHandler(inventory, index, posX, posY) with IPhantomSlot {
    //Can this slot change?
    override def canAdjust: Boolean = true

    override def canTakeStack(player : EntityPlayer) : Boolean = false
}
