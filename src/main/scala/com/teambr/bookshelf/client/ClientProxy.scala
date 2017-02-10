package com.teambr.bookshelf.client

import com.teambr.bookshelf.common.CommonProxy
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLInterModComms, FMLPostInitializationEvent, FMLPreInitializationEvent}

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
class ClientProxy extends CommonProxy {

    /**
      * Called on preInit
      */
    override def preInit(event : FMLPreInitializationEvent) = {}

    /**
      * Called on init
      */
    override def init(event : FMLInitializationEvent) = {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerClientCallback")
    }

    /**
      * Called on postInit
      */
    override def postInit(event : FMLPostInitializationEvent) = {}
}
