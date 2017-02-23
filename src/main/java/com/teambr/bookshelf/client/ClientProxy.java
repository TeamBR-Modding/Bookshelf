package com.teambr.bookshelf.client;

import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.common.tiles.Syncable;
import com.teambr.bookshelf.network.PacketManager;
import com.teambr.bookshelf.network.SyncableFieldPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ClientProxy extends CommonProxy {

    /**
     * Called on preInit
     */
    @Override
    public void preInit(FMLPreInitializationEvent event) {}

    /**
     * Called on init
     */
    @Override
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerClientCallback");
    }

    /**
     * Called on postInit
     */
    @Override
    public void postInit(FMLPostInitializationEvent event) {}

    @Override
    public IMessage doSyncableMessage(boolean returnValue, int id, double value, BlockPos blockPosition, World world) {
        world = Minecraft.getMinecraft().theWorld;

        if(world == null || blockPosition == null)
            return null;

        if(world.getTileEntity(blockPosition) == null)
            return null;
        else if(!(world.getTileEntity(blockPosition) instanceof Syncable))
            return null;

        if(returnValue)
            PacketManager.net.sendToServer(new SyncableFieldPacket(false, id,
                    ((Syncable)world.getTileEntity(blockPosition)).getVariable(id), blockPosition));
        else
            ((Syncable)world.getTileEntity(blockPosition)).setVariable(id, value);
        return null;
    }
}
