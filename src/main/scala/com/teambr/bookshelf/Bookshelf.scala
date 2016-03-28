package com.teambr.bookshelf

import java.io.File

import com.teambr.bookshelf.annotations.ModItem
import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.helper.LogHelper
import com.teambr.bookshelf.lib.Reference
import com.teambr.bookshelf.manager.{ConfigManager, EventManager, GuiManager}
import com.teambr.bookshelf.network.PacketManager
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import tests.BlockTest

import scala.collection.JavaConversions._

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

    /**
      * Used to get an instance of the mod, useful when calling from Java
      * @return
      */
    def getInstance = this

    @SidedProxy(clientSide = "com.teambr.bookshelf.client.ClientProxy",
                serverSide = "com.teambr.bookshelf.common.CommonProxy")
    var proxy : CommonProxy = _

    var configFolderLocation : String = _

    var notificationXPos : Int = 0
    var notificationConfig : Configuration = null

    var testItem : Item = null
    var blockTest : Block = null

    var itemsToRegister : java.util.Set[ASMData] = null

    @EventHandler def preInit(event : FMLPreInitializationEvent) = {
        configFolderLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + Reference.MODNAME

        itemsToRegister = event.getAsmData.getAll(classOf[ModItem].getCanonicalName)

        ConfigManager.init(configFolderLocation)

        if (ConfigManager.debug) {
            blockTest = new BlockTest
            GameRegistry.registerBlock(blockTest, "blockTest")
            for (data <- itemsToRegister) {
                if (data.getAnnotationInfo.get("modid") != null &&
                        data.getAnnotationInfo.get("modid").equals("bookshelfapi")) {
                    try {
                        val asmClass = Class.forName(data.getClassName)
                        val itemClass = asmClass.asSubclass(classOf[Item])

                        val modItem = itemClass.newInstance()
                        testItem = modItem

                        GameRegistry.registerItem(modItem, modItem.getUnlocalizedName.split(":")(1))
                    } catch {
                        case e: Exception =>
                            LogHelper.severe(String.format("Could not register item class %s", data.getClassName))
                    }
                }
            }
        }

        proxy.preInit(event)

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager)
    }

    @EventHandler def init(event : FMLInitializationEvent) = {
        PacketManager.initPackets()
        EventManager.init()
        proxy.init(event)
    }

    @EventHandler def postInit(event : FMLPostInitializationEvent) = {
        proxy.postInit(event)
    }
}
