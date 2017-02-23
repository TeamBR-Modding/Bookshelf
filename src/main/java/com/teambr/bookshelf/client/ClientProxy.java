package com.teambr.bookshelf.client;

import com.teambr.bookshelf.common.CommonProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
}
