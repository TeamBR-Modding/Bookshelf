package com.teambr.bookshelf.common

import net.minecraftforge.fml.common.event.FMLInterModComms

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
    def preInit() = {}

    /**
     * Called on init
     */
    def init() = {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaDataProvider.callBackRegisterServer")
    }

    /**
     * Called on postInit
     */
    def postInit() = {}
}
