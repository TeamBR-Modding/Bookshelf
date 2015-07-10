package com.teambr.bookshelf.network;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClientTileUpdate extends AbstractPacket {

    int x, y, z;
    protected NBTTagCompound tag;

    public ClientTileUpdate() {}

    public ClientTileUpdate(int xCoord, int yCoord, int zCoord, NBTTagCompound nbt) {
        x = xCoord;
        y = yCoord;
        z = zCoord;
        tag = nbt;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) throws IOException {
        ByteBufInputStream stream = new ByteBufInputStream(buffer);
        int length = readVLI(stream);
        if (length > 0) {
            tag = CompressedStreamTools.readCompressed(ByteStreams.limit(stream, length));
        } else {
            tag = null;
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) throws IOException {
        ByteBufOutputStream stream = new ByteBufOutputStream(buffer);
        if (tag != null) {
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            CompressedStreamTools.writeCompressed(tag, buff);

            byte[] bytes = buff.toByteArray();
            writeVLI(stream, bytes.length);
            stream.write(bytes);
        } else {
            stream.writeByte(0);
        }
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayer player) {
        if(tag != null) {
            World world = player.getEntityWorld();
            if(world.getTileEntity(x, y, z) != null) {
                world.getTileEntity(x, y, z).readFromNBT(tag);
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
