package com.teambr.bookshelf.common.items.traits;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis "pauljoda"
 * @since 3/18/2016
 */
public interface ItemModelProvider {

    /**
     * Used to get a list of textures to render, order is important
     * @return
     */
    List<String> getTextures(ItemStack stack);

    /**
     * Defines if this should be rendered as a tool
     * @return Is a tool
     */
    boolean isTool();
}
