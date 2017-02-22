package com.teambr.bookshelf.common;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public interface IHasToolTip {

    /**
     * Used to get the tip to display
     * @return The tip to display
     */
    List<String> getToolTip(ItemStack stack) ;
}
