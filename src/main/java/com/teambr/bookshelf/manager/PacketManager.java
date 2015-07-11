package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.lib.Constants;
import com.teambr.bookshelf.network.ClientTileUpdate;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketManager {
    public static SimpleNetworkWrapper net;

    public static void initPackets() {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID.toUpperCase());
        registerMessage(ClientTileUpdate.class, ClientTileUpdate.Message.class);
    }

    private static int nextPacketId = 0;

    @SuppressWarnings("unchecked")
    private static void registerMessage(Class packet, Class message)
    {   net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
        net.registerMessage(packet, message, nextPacketId, Side.SERVER);
        nextPacketId++;
    }
}