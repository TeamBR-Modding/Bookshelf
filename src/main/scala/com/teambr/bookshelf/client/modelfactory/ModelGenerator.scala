package com.teambr.bookshelf.client.modelfactory

import com.teambr.bookshelf.client.modelfactory.models.BlockModel
import com.teambr.bookshelf.common.blocks.traits.BlockBakeable
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.statemap.StateMapperBase
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.{ ModelBakeEvent, TextureStitchEvent }
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.{ EventPriority, SubscribeEvent }

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
object ModelGenerator {
    //Our live instance, since we are an object
    val INSTANCE = new ModelGenerator

    def register(): Unit = {
        MinecraftForge.EVENT_BUS.register(INSTANCE)
    }
}

class ModelGenerator {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    def textureStitch(event : TextureStitchEvent.Pre): Unit = {

        //Register Textures
        BakeableBlockRegistry.blocks.foreach((block : BlockBakeable) => {
            val icons = block.registerIcons()
            if(icons != null) {
                icons.foreach((location : ResourceLocation) =>
                    event.map.registerSprite(location))
            }})
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    def bakeModels(event : ModelBakeEvent): Unit = {
        val itemModelMesher = Minecraft.getMinecraft.getRenderItem.getItemModelMesher


        BakeableBlockRegistry.blocks.foreach((block : BlockBakeable) => {
            val ignoreStates = new StateMapperBase {
                override def getModelResourceLocation(state : IBlockState) : ModelResourceLocation = block.getNormalModelLocation
            }
            ModelLoader.setCustomStateMapper(block, ignoreStates)
            //Bake all world block models
            block.getAllPossibleStates.foreach((state : IBlockState) => event.modelRegistry.putObject(BlockModel.getModelResourceLocation(state), new BlockModel(block)))
            event.modelRegistry.putObject(block.getNormalModelLocation, new BlockModel(block))

            //Bake the item models
            event.modelRegistry.putObject(block.getInventoryModelLocation, new BlockModel(block))

            //Register the item model
            itemModelMesher.register(Item.getItemFromBlock(block), 0, block.getInventoryModelLocation)
        })
    }
}
