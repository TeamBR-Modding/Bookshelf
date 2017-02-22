package com.teambr.bookshelf.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/14/2017
 */
public class PlayerUtils {

    /**
     * Checks if the player is holding the given item in either hand
     * @param player The player entity
     * @param item The item to check
     * @return True if either hand contains the item
     */
    public static boolean isPlayerHoldingEither(EntityPlayer player, Item item) {
        return !(player == null || item == null || player.getHeldEquipment() == null) &&
                ((player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == item) ||
                        (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() == item));
    }

    /**
     * Gets what hand this item is in, this does an object match so you must send the object, not match by values
     * @param stack The object
     * @return What hand its in
     */
    public static EnumHand getHandStackIsIn(EntityPlayer player, ItemStack stack) {
        if(player == null || stack == null)
            return EnumHand.MAIN_HAND;

        if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().equals(stack))
            return EnumHand.MAIN_HAND;
        else
            return EnumHand.OFF_HAND;
    }
}
