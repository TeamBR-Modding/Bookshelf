package com.teambr.bookshelf.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * This defines the block will open a GUI. You must use this in the Block class
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public interface IOpensGui {

    /**
     * Return the container for this tile
     *
     * @param id Id, probably not needed but could be used for multiple guis
     * @param player The player that is opening the gui
     * @param world The world
     * @param x X Pos
     * @param y Y Pos
     * @param z Z Pos
     * @return The container to open
     */
    Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z);

    /**
     * Return the gui for this tile
     *
     * @param id Id, probably not needed but could be used for multiple guis
     * @param player The player that is opening the gui
     * @param world The world
     * @param x X Pos
     * @param y Y Pos
     * @param z Z Pos
     * @return The gui to open
     */
    Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z);
}
