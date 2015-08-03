package com.teambr.bookshelf.common.container

import net.minecraft.inventory.IInventory

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
trait IInventoryCallback {
       def onInventoryChanged(inventory : IInventory, slotNumber : Int)
}
