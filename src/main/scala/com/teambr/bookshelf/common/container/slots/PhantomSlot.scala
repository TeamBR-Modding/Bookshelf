package com.teambr.bookshelf.common.container.slots

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{IInventory, Slot}

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
class PhantomSlot(inventory : IInventory, index : Int, posX : Int, posY : Int) extends
    Slot(inventory, index, posX, posY) with IPhantomSlot {
    //Can this slot change?
    override def canAdjust: Boolean = true

    override def canTakeStack(player : EntityPlayer) : Boolean = false
}
