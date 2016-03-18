package com.teambr.bookshelf.client


import com.teambr.bookshelf.loadables.ILoadActionProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 2/27/2016
  */
@SideOnly(Side.CLIENT)
class ModelFactory {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    def bakeModels(event: ModelBakeEvent): Unit = {
        val blockIterator = Block.blockRegistry.iterator()
        while(blockIterator.hasNext) {
            val blockLocal = blockIterator.next()
            blockLocal match {
                case actionProvider : ILoadActionProvider =>
                    actionProvider.performLoadAction(event, isClient = true)
                case _ =>
            }
        }

        val itemIterator = Item.itemRegistry.iterator()
        while(itemIterator.hasNext) {
            val itemLocal = itemIterator.next()
            itemLocal match {
                case actionProvider : ILoadActionProvider =>
                    actionProvider.performLoadAction(event, isClient = true)
                case _ =>            }
        }
    }
}
