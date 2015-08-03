package com.teambr.bookshelf

import java.io.File

import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.lib.Reference
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{SidedProxy, Mod}
import net.minecraftforge.fml.common.Mod.{EventHandler, Instance}

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
@Mod(name = Reference.MODNAME, modid = Reference.MODID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, modLanguage = "scala")
object Bookshelf {

    @Instance
    val INSTANCE = null

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
                serverSide = "com.teambr.bookshelf.common.CommonProxy")
    var proxy : CommonProxy = null

    var configFolderLocation : String = ""

    @EventHandler def preInit(event : FMLPreInitializationEvent) = {
        configFolderLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + Reference.MODNAME

        proxy.preInit()
    }

    @EventHandler def init(event : FMLInitializationEvent) = {
        proxy.init()
    }

    @EventHandler def postInit(event : FMLPostInitializationEvent) = {}
}
