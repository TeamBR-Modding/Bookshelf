package com.teambr.bookshelf.api.waila

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
  * @author Dyonovan
  * @since 12/19/2015
  */
trait Waila {

    def returnWailaHead(tipList: java.util.List[String]): java.util.List[String] = tipList

    def returnWailaBody(tipList: java.util.List[String]): java.util.List[String] = tipList

    def returnWailaTail(tipList: java.util.List[String]): java.util.List[String] = tipList

    def returnWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack = null

    def returnNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound, world: World, pos: BlockPos): NBTTagCompound = tag
}
