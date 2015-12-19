package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * This file was created for Bookshelf API
 * <p/>
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 12/19/2015
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
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaHead(currenttip);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaBody(currenttip);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof Waila) {
            Waila tile = (Waila) accessor.getTileEntity();
            tile.returnWailaTail(currenttip);
        }
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if (te instanceof Waila) {
            NBTTagCompound returnTag = ((Waila) te).returnNBTData(player, te, tag, world, pos);
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
