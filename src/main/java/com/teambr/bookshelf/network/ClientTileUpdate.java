package com.teambr.bookshelf.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class ClientTileUpdate implements IMessageHandler<ClientTileUpdate.Message, IMessage> {

    @Override
    public IMessage onMessage(Message message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            if(message.tag != null) {
                World world = ctx.getServerHandler().playerEntity.worldObj;
                if(world.getTileEntity(message.x, message.y, message.z) != null) {
                    world.getTileEntity(message.x, message.y, message.z).readFromNBT(message.tag);
                    world.markBlockForUpdate(message.x, message.y, message.z);
                }
            }
        }
        return null;
    }

    public static class Message implements IMessage {

        int x, y, z;
        protected NBTTagCompound tag;

        public Message() {}

        public Message(int xCoord, int yCoord, int zCoord, NBTTagCompound nbt) {
            x = xCoord;
            y = yCoord;
            z = zCoord;
            tag = nbt;
        }

        @Override
        public void fromBytes(ByteBuf buffer) {
            try {
                tag = new PacketBuffer(buffer).readNBTTagCompoundFromBuffer();
                x = buffer.readInt();
                y = buffer.readInt();
                z = buffer.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void toBytes(ByteBuf buffer) {
            try {
                new PacketBuffer(buffer).writeNBTTagCompoundToBuffer(tag);
                buffer.writeInt(x);
                buffer.writeInt(y);
                buffer.writeInt(z);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
