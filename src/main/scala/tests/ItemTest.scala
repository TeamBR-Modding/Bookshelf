package tests

import com.teambr.bookshelf.annotations.ModItem
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item

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
class ItemTest extends Item {
    setUnlocalizedName("bookshelfapi:itemTest")
    setCreativeTab(CreativeTabs.tabBlock)
}
