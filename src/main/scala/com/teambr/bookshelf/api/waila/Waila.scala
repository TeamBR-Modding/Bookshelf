package com.teambr.bookshelf.api.waila

import java.util

import mcp.mobius.waila.api.{IWailaConfigHandler, IWailaDataAccessor}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.World

/**
  * This file was created for Bookshelf API
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * Extend this trait if your block has a Waila tag. You should be able to change any info you want, all are optional
  * so pick and choose what you need
  *
  * @author Dyonovan
  * @since 12/19/2015
  */
trait Waila {

    /**
      * This is the top line of the description of the block. This will always be pushed to the top
      *
      * @param tipList The current list of strings in the tag
      * @return Add what you want to the list and send it back
      */
    def returnWailaHead(tipList: util.List[String]) : java.util.List[String] = tipList

    /**
      * This is the middle line of the description of the block. This will always be pushed to the middle
      *
      * @param tipList The current list of strings in the tag
      * @return Add what you want to the list and send it back
      */
    def returnWailaBody(tipList: java.util.List[String]) : java.util.List[String] = tipList

    /**
      * This is the bottom line of the description of the block. This will always be pushed to the bottom
      *
      * @param tipList The current list of strings in the tag
      * @return Add what you want to the list and send it back
      */
    def returnWailaTail(tipList: java.util.List[String]) : java.util.List[String] = tipList

    /**
      * This is what stack and name will actually be displayed.
      * @return The stack you want to display
      */
    def returnWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler) : ItemStack = null

    /**
      * Allows you to edit the NBT tag for the itemstack displayed
      * @return The tag you want to display
      */
    def returnNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound, world: World, pos: BlockPos) : NBTTagCompound = tag
}
