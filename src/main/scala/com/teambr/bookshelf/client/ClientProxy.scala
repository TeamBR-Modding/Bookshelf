package com.teambr.bookshelf.client

import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.loadables.ILoadActionProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.common.MinecraftForge
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
        MinecraftForge.EVENT_BUS.register(new ModelFactory)
        MinecraftForge.EVENT_BUS.register(TextureManager)

        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerClientCallback")

        val itemIterator = Item.REGISTRY.iterator()
        while(itemIterator.hasNext) {
            val item = itemIterator.next()
            item match {
                case provider: ILoadActionProvider =>
                    provider.performLoadAction(event, isClient = true)
                case _ =>
            }
        }

        val blockIterator = Block.REGISTRY.iterator()
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
