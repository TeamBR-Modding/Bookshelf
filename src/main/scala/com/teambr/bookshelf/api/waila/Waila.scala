package com.teambr.bookshelf.api.waila

import mcp.mobius.waila.api.ITaggedList.ITipList
import mcp.mobius.waila.api.{ITaggedList, IWailaDataAccessor, IWailaConfigHandler, IWailaDataAccessorServer}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

/**
 * This file was created for NeoTech
 *
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 19, 2015
 */
trait Waila {

    def returnWailaHead(tipList: ITaggedList.ITipList): ITipList = tipList

    def returnWailaBody(tipList: ITaggedList.ITipList): ITipList = tipList

    def returnWailaTail(tipList: ITaggedList.ITipList): ITipList = tipList

    def returnWailaStack(accessor: IWailaDataAccessor, config: IWailaConfigHandler): ItemStack = null

    def returnNBTData(te: TileEntity, tag: NBTTagCompound, accessor: IWailaDataAccessorServer): NBTTagCompound = tag
}
