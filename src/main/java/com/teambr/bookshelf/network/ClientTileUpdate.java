package com.teambr.bookshelf.network;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClientTileUpdate implements IMessageHandler<ClientTileUpdate.Message, IMessage> {

    @Override
    public IMessage onMessage(Message message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            if(message.tag != null) {
                World world = ctx.getServerHandler().playerEntity.worldObj;
                if(world.getTileEntity(message.x, message.y, message.z) != null) {
                    world.getTileEntity(message.x, message.y, message.z).readFromNBT(message.tag);
                }
            }
        }
        return null;
    }

    public static class Message implements IMessage {

        int x, y, z;
        protected NBTTagCompound tag;

        public Message(int xCoord, int yCoord, int zCoord, NBTTagCompound nbt) {
            x = xCoord;
            y = yCoord;
            z = zCoord;
            tag = nbt;
        }

        @Override
        public void fromBytes(ByteBuf buffer) {
            ByteBufInputStream stream = new ByteBufInputStream(buffer);
            int length = readVLI(stream);
            try {
                if (length > 0) {
                    tag = CompressedStreamTools.readCompressed(ByteStreams.limit(stream, length));
                } else {
                    tag = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void toBytes(ByteBuf buffer) {
            ByteBufOutputStream stream = new ByteBufOutputStream(buffer);
            try {
                if (tag != null) {
                    ByteArrayOutputStream buff = new ByteArrayOutputStream();
                    CompressedStreamTools.writeCompressed(tag, buff);

                    byte[] bytes = buff.toByteArray();
                    writeVLI(stream, bytes.length);
                    stream.write(bytes);
                } else {
                    stream.writeByte(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeVLI(DataOutput output, int value) {
        // I'm not touching signed integers.
        Preconditions.checkArgument(value >= 0, "Value cannot be negative");

        try {
            while (true) {
                int b = value & 0x7F;
                int next = value >> 7;
                if (next > 0) {
                    b |= 0x80;
                    output.writeByte(b);
                    value = next;
                } else {
                    output.writeByte(b);
                    break;
                }
            }
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    public static int readVLI(DataInput input) {
        int result = 0;
        int shift = 0;
        int b;
        try {
            do {
                b = input.readByte();
                result = result | ((b & 0x7F) << shift);
                shift += 7;
            } while (b < 0);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return result;
    }
}
