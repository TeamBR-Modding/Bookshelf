package com.teambr.bookshelf.api.waila

import java.util

import mcp.mobius.waila.api.{IWailaConfigHandler, IWailaDataAccessor}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/1/2016
  */
trait IWaila {

    def returnWailaHead(tip: util.List[String])

    def returnWailaBody(tip: util.List[String])

    def returnWailaTail(tip: util.List[String])

    def returnWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack

    def returnNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound, world: World, pos: BlockPos): NBTTagCompound
}
