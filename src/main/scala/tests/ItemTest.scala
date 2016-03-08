package tests

import com.teambr.bookshelf.annotations.ModItem
import com.teambr.bookshelf.common.items.traits.SimpleItemModelProvider
import net.minecraft.creativetab.CreativeTabs

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
class ItemTest extends SimpleItemModelProvider {
    setUnlocalizedName("bookshelfapi:itemTest")
    setCreativeTab(CreativeTabs.tabBlock)
    /**
      * Creates a list of strings to register and render
      *
      * @return An ArrayBuffer of strings, order matters index == layer
      */
    override def getTextures: ArrayBuffer[String] = ArrayBuffer("bookshelfapi:blocks/testBlock")
}
