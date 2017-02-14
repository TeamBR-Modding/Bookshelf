package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class WailaModPlugin implements IWailaDataProvider {

    /*******************************************************************************************************************
     * Class Methods                                                                                                   *
     *******************************************************************************************************************/

    public static void registerClientCallback(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new WailaModPlugin(), IWaila.class);
        registrar.registerHeadProvider(new WailaModPlugin(), IWaila.class);
        registrar.registerBodyProvider(new WailaModPlugin(), IWaila.class);
        registrar.registerTailProvider(new WailaModPlugin(), IWaila.class);
        registrar.registerNBTProvider(new WailaModPlugin(), IWaila.class);
    }

    public static void registerServerCallback(IWailaRegistrar registrar) {
        registrar.registerNBTProvider(new WailaModPlugin(), IWaila.class);
    }

    /*******************************************************************************************************************
     * IWailaDataProvider                                                                                              *
     *******************************************************************************************************************/

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof IWaila) {
            IWaila wailaProvider = (IWaila) accessor.getTileEntity();
            return wailaProvider.getWailaStack(accessor, config);
        }
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof IWaila) {
            IWaila wailaProvider = (IWaila) accessor.getTileEntity();
            return wailaProvider.getWailaHead(itemStack, currenttip, accessor, config);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof IWaila) {
            IWaila wailaProvider = (IWaila) accessor.getTileEntity();
            return wailaProvider.getWailaBody(itemStack, currenttip, accessor, config);
        }
        return currenttip;    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if(accessor.getTileEntity() instanceof IWaila) {
            IWaila wailaProvider = (IWaila) accessor.getTileEntity();
            return wailaProvider.getWailaTail(itemStack, currenttip, accessor, config);
        }
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if(te instanceof IWaila) {
            NBTTagCompound nbt = ((IWaila)te).getNBTData(player, te, tag, world, pos);
            if(nbt != null)
                return nbt;
        }
        return tag;
    }
}
