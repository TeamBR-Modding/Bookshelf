package com.teambr.bookshelf.network;

import com.teambr.bookshelf.Bookshelf;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/8/2017
 */
public class SyncableFieldPacket implements IMessage, IMessageHandler<SyncableFieldPacket, IMessage> {

    public boolean returnValue;
    public int id;
    public double value;
    public BlockPos blockPosition;

    /**
     * Stub to allow registration
     */
    public SyncableFieldPacket() {}

    /**
     * Creates the packet
     * @param ping Send value back
     * @param i The field id
     * @param v The value
     * @param pos The position to write
     */
    public SyncableFieldPacket(boolean ping, int i, double v, BlockPos pos) {
        super();
        returnValue = ping;
        id = i;
        value = v;
        blockPosition = pos;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void fromBytes(ByteBuf buf) {
    returnValue = buf.readBoolean();
    id = buf.readInt();
    value = buf.readDouble();
    blockPosition = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(returnValue);
        buf.writeInt(id);
        buf.writeDouble(value);
        buf.writeLong(blockPosition.toLong());
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    @Override
    public IMessage onMessage(SyncableFieldPacket message, MessageContext ctx) {
        if(ctx.side.isServer())
            return Bookshelf.proxy.doSyncableMessage(message.returnValue, message.id, message.value,
                    message.blockPosition, ctx.getServerHandler().playerEntity.worldObj);
         else
            return Bookshelf.proxy.doSyncableMessage(message.returnValue, message.id, message.value, message.blockPosition, null);
    }
}
