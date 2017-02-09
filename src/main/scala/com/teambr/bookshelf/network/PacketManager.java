package com.teambr.bookshelf.network;

import com.teambr.bookshelf.lib.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/8/2017
 */
public class PacketManager {
    // Our network wrapper
    public static SimpleNetworkWrapper net;

    /**
     * Registers all packets
     */
    public static void initPackets() {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toUpperCase());
        registerMessage(ClientOverridePacket.class, ClientOverridePacket.class);
        registerMessage(SyncableFieldPacket.class, SyncableFieldPacket.class);
    }

    // Local hold for next packet id
    private static int nextPacketId = 0;

    /**
     * Registers a message to the network registry
     * @param packet The packet class
     * @param message The return packet class
     */
    @SuppressWarnings("unchecked")
    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
        net.registerMessage(packet, message, nextPacketId, Side.SERVER);
        nextPacketId++;
    }

    /*******************************************************************************************************************
     * Helper Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Reverse of normal syncing, will send client side data to server to replace
     * @param tile The tile you wish to update to server
     */
    public static void updateTileWithClientInfo(TileEntity tile) {
        NBTTagCompound tag = new NBTTagCompound();
        tile.writeToNBT(tag);
        ClientOverridePacket updateMessage = new ClientOverridePacket(tile.getPos(), tag);
        net.sendToServer(updateMessage);
    }
}