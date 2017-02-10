package com.teambr.bookshelf.common

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent, FMLInterModComms}

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 02, 2015
 */
class CommonProxy {

    /**
     * Called on preInit
     */
    def preInit(event : FMLPreInitializationEvent) = {}

    /**
     * Called on init
     */
    def init(event : FMLInitializationEvent) = {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerServerCallback")
    }

    /**
     * Called on postInit
     */
    def postInit(event : FMLPostInitializationEvent) = {}
}
