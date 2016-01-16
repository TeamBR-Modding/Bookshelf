package com.teambr.bookshelf.network

import com.teambr.bookshelf.common.tiles.traits.Syncable
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint
import net.minecraftforge.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}

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
class SyncableFieldPacket extends IMessage with IMessageHandler[SyncableFieldPacket, IMessage] {

    var returnValue : Boolean = false
    var id : Int = -1
    var value : Double = 0
    var blockPosition : BlockPos = new BlockPos(0, 0, 0)

    def this(ping : Boolean, i : Int, v : Double, pos : BlockPos) {
        this()
        returnValue = ping
        id = i
        value = v
        blockPosition = pos
    }

    override def toBytes(buf: ByteBuf): Unit = {
        buf.writeBoolean(returnValue)
        buf.writeInt(id)
        buf.writeDouble(value)
        buf.writeLong(blockPosition.toLong)
    }

    override def fromBytes(buf: ByteBuf): Unit = {
        returnValue = buf.readBoolean()
        id = buf.readInt()
        value = buf.readDouble()
        blockPosition = BlockPos.fromLong(buf.readLong())
    }

    override def onMessage(message: SyncableFieldPacket, ctx: MessageContext): IMessage = {
        if(ctx.side.isServer) {
            if(ctx.getServerHandler.playerEntity.worldObj.getTileEntity(message.blockPosition) == null)
                return null
            if(!ctx.getServerHandler.playerEntity.worldObj.getTileEntity(message.blockPosition).isInstanceOf[Syncable])
                return null

            if(message.returnValue)
                PacketManager.net.sendToAllAround(new SyncableFieldPacket(false, message.id,
                    ctx.getServerHandler.playerEntity.worldObj.getTileEntity(message.blockPosition)
                            .asInstanceOf[Syncable].getVariable(message.id), message.blockPosition),
                    new TargetPoint(ctx.getServerHandler.playerEntity.worldObj.provider.getDimensionId,
                        message.blockPosition.getX, message.blockPosition.getY, message.blockPosition.getZ, 25))
            else
                ctx.getServerHandler.playerEntity.worldObj.getTileEntity(message.blockPosition)
                        .asInstanceOf[Syncable].setVariable(message.id, message.value)
        } else {
            if(Minecraft.getMinecraft.theWorld.getTileEntity(message.blockPosition) == null)
                return null
            if(!Minecraft.getMinecraft.theWorld.getTileEntity(message.blockPosition).isInstanceOf[Syncable])
                return null

            if(message.returnValue)
                PacketManager.net.sendToServer(new SyncableFieldPacket(false, message.id, message.value, message.blockPosition))
            else
                Minecraft.getMinecraft.theWorld.getTileEntity(message.blockPosition)
                        .asInstanceOf[Syncable].setVariable(message.id, message.value)
        }
        null
    }
}
