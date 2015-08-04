package com.teambr.bookshelf.client

import com.teambr.bookshelf.client.modelfactory.ModelGenerator
import com.teambr.bookshelf.common.CommonProxy

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
class ClientProxy extends CommonProxy {

    /**
     * Called on preInit
     */
    override def preInit() = {}

    /**
     * Called on init
     */
    override def init() = {
        ModelGenerator.register()
    }

    /**
     * Called on postInit
     */
    override def postInit() = {}
}
