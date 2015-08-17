package com.teambr.bookshelf

import java.io.File

import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.lib.Reference
import com.teambr.bookshelf.manager.{ConfigManager, EventManager, GuiManager}
import com.teambr.bookshelf.network.PacketManager
import mcp.mobius.waila.api.impl.ConfigHandler
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{ FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent }
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.{ Mod, SidedProxy }
import tests.BlockTest

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
@Mod(name = Reference.MODNAME, modid = Reference.MODID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, modLanguage = "scala")
object Bookshelf {

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
                serverSide = "com.teambr.bookshelf.common.CommonProxy")
    var proxy : CommonProxy = _

    var configFolderLocation : String = _

    @EventHandler def preInit(event : FMLPreInitializationEvent) = {
        configFolderLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + Reference.MODNAME
        ConfigManager.init(configFolderLocation)

        proxy.preInit()

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager)

        if (ConfigManager.debug)
            GameRegistry.registerBlock(new BlockTest, "blockTest")
    }

    @EventHandler def init(event : FMLInitializationEvent) = {
        PacketManager.initPackets()
        EventManager.init()
        proxy.init()
    }

    @EventHandler def postInit(event : FMLPostInitializationEvent) = {
        proxy.postInit()
    }
}
