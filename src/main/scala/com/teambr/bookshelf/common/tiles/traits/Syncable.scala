package com.teambr.bookshelf.common.tiles.traits

import com.teambr.bookshelf.network.{SyncableFieldPacket, PacketManager}
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/16/2016
  */
trait Syncable extends UpdatingTile {

    /**
      * Used to set the value of a field
      * @param id The field id
      * @param value The value of the field
      */
    def setVariable(id : Int, value : Double)

    /**
      * Used to get the field on the server, this will fetch the server value and overwrite the current
      * @param id The field id
      * @return The value on the server, now set to ourselves
      */
    def getVariable(id : Int) : Double

    /**
      * Sends the value to the server, you should probably only call this from the client
      */
    def sendValueToServer(id : Int, value : Double) = {
        PacketManager.net.sendToServer(new SyncableFieldPacket(false, id, value, getPos))
    }

    /**
      * Will get the value from the server and set it to our current value
      */
    def updateClientValueFromServer(id : Int) : Unit = {
        PacketManager.net.sendToServer(new SyncableFieldPacket(true, id, 0, getPos))
    }

    /**
      * Sends the value to the client
      */
    def sendValueToClient(id : Int, value : Double) = {
        PacketManager.net.sendToAllAround(new SyncableFieldPacket(false, id, value, getPos),
            new TargetPoint(getWorld.provider.getDimension, getPos.getX, getPos.getY, getPos.getZ, 25))
    }
}
