package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.ITaggedList.ITipList;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataAccessorServer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * This file was created for Modular-Systems
 * <p/>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 10, 2015
 */
public interface IWaila {

    void returnWailaHead(ITipList tipList);

    void returnWailaBody(ITipList tipList);

    void returnWailaTail(ITipList tipList);

    ItemStack returnWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);

    NBTTagCompound returnNBTData(TileEntity te, NBTTagCompound tag, IWailaDataAccessorServer accessor);
}
