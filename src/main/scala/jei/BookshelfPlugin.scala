package jei

import mezz.jei.api._

/**
  * This file was created for Bookshelf API
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 3/23/2016
  */
object BookshelfPlugin {

    var jeiHelpers: IJeiHelpers = _
}

@JEIPlugin
class BookshelfPlugin extends IModPlugin {
    override def register(registry: IModRegistry): Unit = {
        BookshelfPlugin.jeiHelpers = registry.getJeiHelpers
    }

    override def onRuntimeAvailable(jeiRuntime: IJeiRuntime): Unit = { }
}
