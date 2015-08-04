package com.teambr.bookshelf.common.blocks.properties;

import net.minecraft.world.IBlockAccess;
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
public class WorldAwareProperty<V> implements IUnlistedProperty<IBlockAccess> {
    @Override
    public String getName() {
        return "worldAware";
    }

    @Override
    public boolean isValid(IBlockAccess value) {
        return true;
    }

    @Override
    public Class<IBlockAccess> getType() {
        return IBlockAccess.class;
    }

    @Override
    public String valueToString(IBlockAccess value) {
        return value.toString();
    }
}
