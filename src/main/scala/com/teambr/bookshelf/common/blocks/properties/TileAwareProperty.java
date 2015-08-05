package com.teambr.bookshelf.common.blocks.properties;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0
 * International License: http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
public class TileAwareProperty implements IUnlistedProperty<TileEntity> {
    @Override
    public String getName() {
        return "tileAware";
    }

    @Override
    public boolean isValid(TileEntity value) {
        return true;
    }

    @Override
    public Class<TileEntity> getType() {
        return TileEntity.class;
    }

    @Override
    public String valueToString(TileEntity value) {
        return value.toString();
    }
}