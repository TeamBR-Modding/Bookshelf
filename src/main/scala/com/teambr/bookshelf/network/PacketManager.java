package com.teambr.bookshelf.network;

import com.teambr.bookshelf.lib.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketManager {
    public static SimpleNetworkWrapper net;

    public static void initPackets() {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toUpperCase());
        registerMessage(ClientOverridePacket.class, ClientOverridePacket.class);
        registerMessage(SyncableFieldPacket.class, SyncableFieldPacket.class);
    }

    private static int nextPacketId = 0;

    @SuppressWarnings("unchecked")
    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
        net.registerMessage(packet, message, nextPacketId, Side.SERVER);
        nextPacketId++;
    }

    public static void updateTileWithClientInfo(TileEntity tile) {
        NBTTagCompound tag = new NBTTagCompound();
        tile.writeToNBT(tag);
        ClientOverridePacket updateMessage = new ClientOverridePacket(tile.getPos(), tag);
        net.sendToServer(updateMessage);
    }
}