package tests

import java.util

import com.teambr.bookshelf.annotations.ModItem
import com.teambr.bookshelf.client.models.BakedDynItem
import com.teambr.bookshelf.common.items.traits.ItemModelProvider
import com.teambr.bookshelf.loadables.CreatesTextures
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.client.model.ModelLoader

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
@ModItem(modid = "bookshelfapi")
class ItemTest extends Item with ItemModelProvider with CreatesTextures {
    setUnlocalizedName("bookshelfapi:itemTest")
    setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
    ModelLoader.setCustomModelResourceLocation(this, 0, BakedDynItem.MODEL_RESOURCE_LOCATION)

    override def getTextures(stack: ItemStack) : util.List[String] = {
        val list = new util.ArrayList[String]()
        list.add("bookshelfapi:blocks/testBlock")
        list.add("bookshelfapi:blocks/testBlockFront")
        list
    }

    /**
      * Used to define the strings needed
      */
    override def getTexturesToStitch: ArrayBuffer[String] =
        ArrayBuffer("bookshelfapi:blocks/testBlock",
        "bookshelfapi:blocks/testBlockFront")

    override def isTool: Boolean = false
}
