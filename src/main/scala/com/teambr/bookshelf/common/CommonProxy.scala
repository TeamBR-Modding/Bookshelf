package com.teambr.bookshelf.common

import com.teambr.bookshelf.loadables.ILoadActionProvider
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
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaDataProvider.callBackRegisterServer")

        val itemIterator = Item.itemRegistry.iterator()
        while(itemIterator.hasNext) {
            val item = itemIterator.next()
            item match {
                case provider: ILoadActionProvider =>
                    provider.performLoadAction(event)
                case _ =>
            }
        }

        val blockIterator = Block.blockRegistry.iterator()
        while(blockIterator.hasNext) {
            val block = blockIterator.next()
            block match {
                case provider: ILoadActionProvider =>
                    provider.performLoadAction(event)
                case _ =>
            }
        }
    }

    /**
     * Called on postInit
     */
    def postInit(event : FMLPostInitializationEvent) = {}
}
