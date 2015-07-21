package com.teambr.bookshelf.api.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public interface IWaila {
    void returnWailaHead(List<String> tip);

    void returnWailaBody(List<String> tip);

    void returnWailaTail(List<String> tip);

    ItemStack returnWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);

    NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z);
}