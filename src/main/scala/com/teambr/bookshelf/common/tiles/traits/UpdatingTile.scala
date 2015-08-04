package com.teambr.bookshelf.common.tiles.traits

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.tileentity.TileEntity

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 *
 * This trait will make sure when you mark the block to update, it will read and write the NBT tag.
 * If you want something synced, make sure it is sent in the tag and it will sync
 */
trait UpdatingTile extends TileEntity with IUpdatePlayerListBox {

    /**
     * Used to write data to the tag
     * @param tag The tag to write to
     */
    override def writeToNBT(tag : NBTTagCompound)

    /**
     * Used to read data from the tag
     * @param tag The tag to read from
     */
    override def readFromNBT(tag : NBTTagCompound)

    /**
     * Called on the client side only
     */
    def onClientTick() : Unit

    /**
     * Called on the server side only
     */
    def onServerTick() : Unit

    /**
     * Used to identify the packet that will get called on update
     * @return The packet to send
     */
    override def getDescriptionPacket: Packet = {
        val tag = new NBTTagCompound
        this.writeToNBT(tag)
        new S35PacketUpdateTileEntity(getPos, 1, tag)
    }

    /**
     * Called when a packet is received
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
