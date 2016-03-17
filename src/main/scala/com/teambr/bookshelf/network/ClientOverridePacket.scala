package com.teambr.bookshelf.network

import io.netty.buffer.ByteBuf
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
class ClientOverridePacket extends IMessage with IMessageHandler[ClientOverridePacket, IMessage] {
    var blockPosition = new BlockPos(0, 0, 0)
    var tag = new NBTTagCompound

    def this(blockPos : BlockPos, nbt : NBTTagCompound) {
        this()
        blockPosition = blockPos
        tag = nbt
    }

    override def fromBytes(buffer : ByteBuf): Unit = {
        blockPosition = BlockPos.fromLong(buffer.readLong())
        tag = new PacketBuffer(buffer).readNBTTagCompoundFromBuffer
    }

    override def toBytes(buffer : ByteBuf): Unit = {
        buffer.writeLong(blockPosition.toLong)
        new PacketBuffer(buffer).writeNBTTagCompoundToBuffer(tag)
    }

    def onMessage(message: ClientOverridePacket, ctx: MessageContext) : IMessage = {
        if (ctx.side.isServer) {
            if (message.tag != null) {
                val world: World = ctx.getServerHandler.playerEntity.worldObj
                if (world.getTileEntity(message.blockPosition) != null) {
                    world.getTileEntity(message.blockPosition).readFromNBT(message.tag)
                    world.markBlockForUpdate(message.blockPosition)
                }
            }
        }
        null
    }
}
