package com.teambr.bookshelf.common.tiles.traits

import com.teambr.bookshelf.collections.SidedInventoryWrapper
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandler}

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/27/2016
  */
trait InventorySided extends Inventory {
    def getSlotsForFace(side: EnumFacing): Array[Int]
    def canInsertItem(slot: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean
    def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean

    val handlerTop: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.UP)
    val handlerBottom: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.DOWN)
    val handlerWest: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.WEST)
    val handlerEast: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.EAST)
    val handlerNorth: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.NORTH)
    val handlerSouth: IItemHandler = new SidedInventoryWrapper(this, net.minecraft.util.EnumFacing.SOUTH)

    override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            facing match {
                case EnumFacing.UP => return handlerTop.asInstanceOf[T]
                case EnumFacing.DOWN => return handlerBottom.asInstanceOf[T]
                case EnumFacing.WEST => return handlerWest.asInstanceOf[T]
                case EnumFacing.EAST => return handlerEast.asInstanceOf[T]
                case EnumFacing.NORTH => return handlerNorth.asInstanceOf[T]
                case EnumFacing.SOUTH => return handlerSouth.asInstanceOf[T]
                case _ => return handlerWest.asInstanceOf[T]
            }
        }
        super.getCapability[T](capability, facing)
    }
}
