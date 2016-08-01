package com.teambr.bookshelf.api.waila

import java.util

import mcp.mobius.waila.api.{IWailaConfigHandler, IWailaDataAccessor, IWailaDataProvider, IWailaRegistrar}
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
object WailaDataProvider {

    def callbackRegisterClient(registrar: IWailaRegistrar): Unit = {
        registrar.registerHeadProvider(new WailaDataProvider, classOf[IWaila])
        registrar.registerBodyProvider(new WailaDataProvider, classOf[IWaila])
        registrar.registerTailProvider(new WailaDataProvider, classOf[IWaila])
        registrar.registerStackProvider(new WailaDataProvider, classOf[IWaila])
        registrar.registerNBTProvider(new WailaDataProvider, classOf[IWaila])
    }

    def callbackRegisterServer(registrar: IWailaRegistrar): Unit = {
        registrar.registerNBTProvider(new WailaDataProvider, classOf[IWaila])
    }
}

class WailaDataProvider extends IWailaDataProvider {
    override def getWailaHead(itemStack: ItemStack, tip: util.List[String], accessor: IWailaDataAccessor,
                              iWailaConfigHandler: IWailaConfigHandler): util.List[String] = {
        accessor.getTileEntity match {
            case tile: IWaila =>
                tile.returnWailaHead(tip)
            case _ =>
        }
        tip
    }

    override def getWailaBody(itemStack: ItemStack, tip: util.List[String], accessor: IWailaDataAccessor,
                              iWailaConfigHandler: IWailaConfigHandler): util.List[String] = {
        accessor.getTileEntity match {
            case tile: IWaila =>
                tile.returnWailaBody(tip)
            case _ =>
        }
        tip
    }

    override def getWailaTail(itemStack: ItemStack, tip: util.List[String], accessor: IWailaDataAccessor,
                              iWailaConfigHandler: IWailaConfigHandler): util.List[String] = {
        accessor.getTileEntity match {
            case tile: IWaila =>
                tile.returnWailaTail(tip)
            case _ =>
        }
        tip
    }

    override def getWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack = {
        accessor.getTileEntity match {
            case tile: IWaila =>
                return tile.returnWailaStack(accessor, config)
            case _ =>
        }
        null
    }

    override def getNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound,
                            world: World, blockPos: BlockPos): NBTTagCompound = {
        te match {
            case tile: IWaila =>
                val returnTag = tile.returnNBTData(player, tile, tag, world, blockPos)
                if (returnTag != null) return returnTag
            case _ =>
        }
        tag
    }
}
