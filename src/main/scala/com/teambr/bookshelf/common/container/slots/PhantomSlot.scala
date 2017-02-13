package com.teambr.bookshelf.common.container.slots

import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.items.{IItemHandler, SlotItemHandler}

/**
  * This file was created for com.teambr.bookshelf.Bookshelf
  *
  * com.teambr.bookshelf.Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/21/2016
  */
class PhantomSlot(inventory : IItemHandler, index : Int, posX : Int, posY : Int) extends
    SlotItemHandler(inventory, index, posX, posY) with IPhantomSlot {
    //Can this slot change?
    override def canAdjust: Boolean = true

    override def canTakeStack(player : EntityPlayer) : Boolean = false
}
