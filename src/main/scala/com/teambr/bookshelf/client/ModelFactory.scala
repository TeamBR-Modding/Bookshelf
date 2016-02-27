package com.teambr.bookshelf.client

import com.teambr.bookshelf.client.models.ModelConnectedTextures
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.fml.common.eventhandler.{EventPriority, SubscribeEvent}

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
class ModelFactory {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    def bakeModels(event: ModelBakeEvent): Unit = {
        for(block <- ConnectedTextureBlocks.blocks) {
            event.modelRegistry.putObject(block.getNormal, new ModelConnectedTextures())
            event.modelRegistry.putObject(block.getInventory, new ModelConnectedTextures())
            Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(Item.getItemFromBlock(block), 0, block.getInventory)
        }
    }
}
