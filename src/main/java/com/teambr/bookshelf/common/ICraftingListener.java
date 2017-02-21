package com.teambr.bookshelf.common;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * This file was created for NeoTech
 * <p>
 * NeoTech is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/20/2017
 */
public interface ICraftingListener {

    /**
     * Called when this item is crafted, handle NBT moving or other stuff here
     * @param craftingList  The list of items that were used, can have null at locations,
     *                      you should already know where important items are and read from this, be sure to check size
     *                      to find out if what kind of grid it is
     *
     *                      Format Full Crafting:
     *                      0  1  2
     *                      3  4  5
     *                      6  7  8
     *
     *                      Format Player Crafting:
     *                      0  1
     *                      2  3
     *
     *
     * @param craftingStack The output stack, modify this to modify what the player gets on crafting
     * @return The stack to give the player, should probably @param craftingStack but can be something different
     */
    ItemStack onCrafted(ItemStack[] craftingList, ItemStack craftingStack);
}
