package com.teambr.bookshelf.common;

import com.teambr.bookshelf.common.tiles.Syncable;
import com.teambr.bookshelf.network.PacketManager;
import com.teambr.bookshelf.network.SyncableFieldPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
public class CommonProxy {

    /**
     * Called on preInit
     */
    public void preInit(FMLPreInitializationEvent event) {}

    /**
     * Called on init
     */
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerServerCallback");
    }

    /**
     * Called on postInit
     */
    public void postInit(FMLPostInitializationEvent event) {}

    public IMessage doSyncableMessage(boolean returnValue, int id, double value, BlockPos blockPosition, World world) {
        if(world.getTileEntity(blockPosition) == null)
            return null;
        else if(!(world.getTileEntity(blockPosition) instanceof Syncable))
            return null;

        if(returnValue)
            PacketManager.net.sendToAllAround(new SyncableFieldPacket(false, id,
                            ((Syncable)world.getTileEntity(blockPosition)).getVariable(id), blockPosition),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(),
                            blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), 25));
        else {
            ((Syncable)world.getTileEntity(blockPosition)).setVariable(id, value);
        }
        return null;
    }
}
