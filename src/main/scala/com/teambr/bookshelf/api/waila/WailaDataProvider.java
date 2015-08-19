package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * This file was created for Modular-Systems
 *
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 10, 2015
 */
public class WailaDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            return tile.returnWailaStack(accessor, config);
        }
        return null;
    }

    @Override
    public ITaggedList.ITipList getWailaHead(ItemStack itemStack, ITaggedList.ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaHead(currenttip);
        }
        return currenttip;
    }

    @Override
    public ITaggedList.ITipList getWailaBody(ItemStack itemStack, ITaggedList.ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaBody(currenttip);
        }
        return currenttip;
    }

    @Override
    public ITaggedList.ITipList getWailaTail(ItemStack itemStack, ITaggedList.ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaTail(currenttip);
        }
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(TileEntity te, NBTTagCompound tag, IWailaDataAccessorServer accessor) {
        if (te instanceof Waila) {
            NBTTagCompound returnTag = ((Waila) te).returnNBTData(te, tag, accessor);
            if (returnTag != null)
                return returnTag;
        }
        return tag;
    }

    @SuppressWarnings("unused")
    public static void callBackRegisterClient(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(new WailaDataProvider(), Waila.class);
        registrar.registerBodyProvider(new WailaDataProvider(), Waila.class);
        registrar.registerTailProvider(new WailaDataProvider(), Waila.class);
        registrar.registerStackProvider(new WailaDataProvider(), Waila.class);
        registrar.registerNBTProvider(new WailaDataProvider(), Waila.class);
    }

    @SuppressWarnings("unused")
    public static void callBackRegisterServer(IWailaRegistrar registrar) {
        registrar.registerNBTProvider(new WailaDataProvider(), Waila.class);
    }
}
