package com.teambr.bookshelf.common.tiles;

import com.teambr.bookshelf.network.PacketManager;
import com.teambr.bookshelf.network.SyncableFieldPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
public abstract class Syncable extends UpdatingTile {

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to set the value of a field
     * @param id The field id
     * @param value The value of the field
     */
    public abstract void setVariable(int id, double value);

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    public abstract Double getVariable(int id);

    /*******************************************************************************************************************
     * Syncable                                                                                                        *
     *******************************************************************************************************************/

    /**
     * Sends the value to the server, you should probably only call this from the client
     */
    public void sendValueToServer(int id, double value) {
        PacketManager.net.sendToServer(new SyncableFieldPacket(false, id, value, getPos()));
    }

    /**
     * Will get the value from the server and set it to our current value, call from client
     * Only use if you lose data and want to update from server. Few cases for this
     */
    public void updateClientValueFromServer(int id) {
        PacketManager.net.sendToServer(new SyncableFieldPacket(true, id, 0, getPos()));
    }

    /**
     * Sends the value to the clients nearby
     */
    public void sendValueToClient(int id, double value) {
        PacketManager.net.sendToAllAround(new SyncableFieldPacket(false, id, value, getPos()),
                new NetworkRegistry.TargetPoint(getWorld().provider.getDimension(),
                        getPos().getX(), getPos().getY(), getPos().getZ(),
                        25));
    }
}
