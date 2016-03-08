package com.teambr.bookshelf.common.items.traits

import com.teambr.bookshelf.client.models.SimpleItemModel
import com.teambr.bookshelf.loadables.{CreatesTextures, ILoadActionProvider}
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.model.IFlexibleBakedModel

import scala.collection.mutable.ArrayBuffer

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 3/8/2016
  */
trait SimpleItemModelProvider extends Item with ILoadActionProvider with CreatesTextures {

    /**
      * Creates a list of strings to register and render
      *
      * @return An ArrayBuffer of strings, order matters index == layer
      */
    def getTextures : ArrayBuffer[String]


    /**
      * Creates a list of strings to register and render, ItemStack aware
      *
      * @return An ArrayBuffer of strings, order matters index == layer
      */
    def getTextures(stack : ItemStack) : ArrayBuffer[String]

    /**
      * Used to get the model location for this object, default to unlocalizedName plus inventory
      *
      * @return
      */
    def getModelLocation : ModelResourceLocation = new ModelResourceLocation(getUnlocalizedName, "inventory")

    /**
      * Used to define the strings needed
      */
    override def getTexturesToStitch: ArrayBuffer[String] = getTextures

    /**
      * Performs the action at the given event
      *
      * @param event    The event being called from
      * @param isClient True if only on client side, false (default) for server side
      */
    override def performLoadAction(event: AnyRef, isClient: Boolean): Unit = {
        event match {
            case modelEvent : ModelBakeEvent =>
                Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(this, 0, getModelLocation)
                // We are using the default formats for the forge bucket. Sue me
                val holdModel = modelEvent.modelRegistry.getObject(new ModelResourceLocation("minecraft:apple", "inventory"))
                modelEvent.modelRegistry.putObject(getModelLocation, new SimpleItemModel(holdModel.asInstanceOf[IFlexibleBakedModel]))
            case _ =>
        }
    }
}
