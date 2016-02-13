package com.teambr.bookshelf.common.tiles.traits

import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{INetHandler, NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, ITickable}
import net.minecraft.world.World

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 03, 2015
 *
 * This trait will make sure when you mark the block to update, it will read and write the NBT tag.
 * If you want something synced, make sure it is sent in the tag and it will sync
 */
trait UpdatingTile extends TileEntity with ITickable {

    /**
     * Used to write data to the tag
      *
      * @param tag The tag to write to
     */
    override def writeToNBT(tag : NBTTagCompound)

    /**
     * Used to read data from the tag
      *
      * @param tag The tag to read from
     */
    override def readFromNBT(tag : NBTTagCompound)

    /**
     * Called on the client side only
     */
    def onClientTick() : Unit = {}

    /**
     * Called on the server side only
     */
    def onServerTick() : Unit = {}

    /**
      * Lets the world know if the tile should be remade, usually not. We are going to just check by block, forge
      * defaults to state
      * @param world
      * @param pos
      * @param oldState
      * @param newSate
      * @return
      */
    override def shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newSate: IBlockState) =
        oldState.getBlock ne newSate.getBlock

    /**
     * Used to identify the packet that will get called on update
      *
      * @return The packet to send
     */
    override def getDescriptionPacket: Packet[_ <: INetHandler] = {
        val tag = new NBTTagCompound
        this.writeToNBT(tag)
        new S35PacketUpdateTileEntity(getPos, 1, tag)
    }

    /**
     * Called when a packet is received
      *
      * @param net The manager sending
     * @param pkt The packet received
     */
    override def onDataPacket(net : NetworkManager, pkt : S35PacketUpdateTileEntity) = this.readFromNBT(pkt.getNbtCompound)

    /**
     * Called when the tile updates
     */
    override def update() = {
        if(getWorld.isRemote)
            onClientTick()
        else
            onServerTick()
    }
}
