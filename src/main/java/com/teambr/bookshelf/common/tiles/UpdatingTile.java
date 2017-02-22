package com.teambr.bookshelf.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class UpdatingTile extends TileEntity implements ITickable {


    /*******************************************************************************************************************
     * UpdatingTile                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Called only on the client side tick. Override for client side operations
     */
    protected void onClientTick() {}

    /**
     * Called only on the server side tick. Override for server side operations
     */
    protected void onServerTick() {}

    /**
     * Call to mark this block for update in the world
     * @param flags 6 to avoid re-render, 3 to force client changes
     */
    public void markForUpdate(int flags) {
        getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), flags);
        markDirty();
    }

    /*******************************************************************************************************************
     * ITickable                                                                                                       *
     *******************************************************************************************************************/

    @Override
    public void update() {
        if(getWorld().isRemote)
            onClientTick();
        else
            onServerTick();
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    /**
     * We want the update tag to take in outside info
     * @return Our tag
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    /**
     * We only want a refresh when the block actually changes
     */
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    /**
     * Cause tile to read new info
     */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Case data packet to send our info
     */
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(getPos(), 1, tag);
    }
}
