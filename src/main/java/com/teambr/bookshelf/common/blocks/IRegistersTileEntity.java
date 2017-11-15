package com.teambr.bookshelf.common.blocks;

import net.minecraft.tileentity.TileEntity;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 11/15/17
 */
public interface IRegistersTileEntity {
    /**
     * Used to get the class for the tile this object registers to
     * @return The class for the tile entity
     */
    Class<? extends TileEntity> getTileEntityClass();
}
