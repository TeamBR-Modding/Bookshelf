package com.teambr.bookshelf.common.container;

import net.minecraftforge.items.IItemHandler;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public interface IInventoryCallback {

    /**
     * Called when an inventory has a change in state
     * @param inventory The inventory changed
     * @param slotNumber The slot modified
     */
    void onInventoryChanged(IItemHandler inventory, int slotNumber);
}
