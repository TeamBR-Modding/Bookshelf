package com.teambr.bookshelf.client

import java.io.File

import com.teambr.bookshelf.Bookshelf
import com.teambr.bookshelf.client.models.{BakedConnectedTextures, BakedDynItem}
import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.helper.KeyInputHelper
import com.teambr.bookshelf.loadables.ILoadActionProvider
import com.teambr.bookshelf.manager.ConfigManager
import com.teambr.bookshelf.notification.{NotificationKeyBinding, NotificationTickHandler}
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
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
class ClientProxy extends CommonProxy {

    /**
      * Called on preInit
      */
    override def preInit(event : FMLPreInitializationEvent) = {
        MinecraftForge.EVENT_BUS.register(new NotificationTickHandler())

        if(ConfigManager.debug) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Bookshelf.blockTest), 0,
                BakedConnectedTextures.MODEL_RESOURCE_LOCATION_NORMAL)

            ModelLoader.setCustomModelResourceLocation(Bookshelf.testItem, 0, BakedDynItem.MODEL_RESOURCE_LOCATION)
        }

        Bookshelf.notificationConfig = new Configuration(new File(Bookshelf.configFolderLocation + "/NotificationsSettings" + ".cfg"))
        Bookshelf.notificationXPos = Bookshelf.notificationConfig.getInt("notification xpos", "notifications", 1, 0, 2, "0: Left\n1: Center\n2: Right")
        Bookshelf.notificationConfig.save()
    }

    /**
      * Called on init
      */
    override def init(event : FMLInitializationEvent) = {
        NotificationKeyBinding.init()
        MinecraftForge.EVENT_BUS.register(new KeyInputHelper())

        MinecraftForge.EVENT_BUS.register(new ModelFactory)
        MinecraftForge.EVENT_BUS.register(TextureManager)

        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaDataProvider.callBackRegisterClient")

        val itemIterator = Item.itemRegistry.iterator()
        while(itemIterator.hasNext) {
            val item = itemIterator.next()
            item match {
                case provider: ILoadActionProvider =>
                    provider.performLoadAction(event, isClient = true)
                case _ =>
            }
        }

        val blockIterator = Block.blockRegistry.iterator()
        while(blockIterator.hasNext) {
            val block = blockIterator.next()
            block match {
                case provider: ILoadActionProvider =>
                    provider.performLoadAction(event, isClient = true)
                case _ =>
            }
        }
    }

    /**
      * Called on postInit
      */
    override def postInit(event : FMLPostInitializationEvent) = {}
}
