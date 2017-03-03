package com.teambr.bookshelf.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

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
public class ClientOverridePacket implements IMessage, IMessageHandler<ClientOverridePacket, IMessage> {

    // Variables
    public BlockPos blockPosition;
    private NBTTagCompound tag;

    /**
     * Stub to allow registration
     */
    public ClientOverridePacket() {}

    /**
     * Creates a packet with the given info
     * @param pos The position to write the tag
     * @param nbt The tag to write
     */
    public ClientOverridePacket(BlockPos pos, NBTTagCompound nbt) {
        super();
        blockPosition = pos;
        tag = nbt;
    }

    /*******************************************************************************************************************
     * IMessage                                                                                                        *
     *******************************************************************************************************************/

    @Override
    public void fromBytes(ByteBuf buf) {
        blockPosition = BlockPos.fromLong(buf.readLong());
        try {
            tag = new PacketBuffer(buf).readCompoundTag();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(blockPosition.toLong());
        new PacketBuffer(buf).writeCompoundTag(tag);
    }

    /*******************************************************************************************************************
     * IMessageHandler                                                                                                 *
     *******************************************************************************************************************/

    @Override
    public IMessage onMessage(ClientOverridePacket message, MessageContext ctx) {
        if(ctx.side.isServer()) {
            if(message.tag != null) {
                World world = ctx.getServerHandler().playerEntity.world;
                if(world.getTileEntity(message.blockPosition) != null) {
                    world.getTileEntity(message.blockPosition).setPos(message.blockPosition);
                    world.getTileEntity(message.blockPosition).readFromNBT(message.tag);
                    world.notifyBlockUpdate(message.blockPosition,
                            world.getBlockState(message.blockPosition), world.getBlockState(message.blockPosition),
                            3);
                }
            }
        }
        return null;
    }
}
