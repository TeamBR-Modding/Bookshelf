package com.teambr.bookshelf.api.waila

import mcp.mobius.waila.api.{IWailaDataAccessor, IWailaConfigHandler}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * This file was created for the Bookshelf
 * API. The source is available at: 
 * https://github.com/TeamBR-Modding/Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
trait WailaTile extends TileEntity with IWaila {
    def returnWailaHead(tip: List[String]) = {}

    def returnWailaBody(tip: List[String]) = {}

    def returnWailaTail(tip: List[String]) = {}

    def returnWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack =
        new ItemStack(getWorld.getBlockState(pos).getBlock, 1, getWorld.getBlockState(pos).getBlock.getDamageValue(getWorld, pos))


    def returnNBTData(player: EntityPlayerMP, te: TileEntity, tag: NBTTagCompound, world: World, x: Int, y: Int, z: Int): NBTTagCompound = tag

}
