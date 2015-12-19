package com.teambr.bookshelf.common.blocks.properties;

import com.google.common.collect.ImmutableMap;
import com.teambr.bookshelf.common.blocks.traits.TileAware;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

import java.util.Collection;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since 12/18/2015
 */
public class TileAwareState implements IBlockState {
    public TileEntity tile;
    public Block block;

    public TileAwareState(TileEntity t, Block b) {
        tile = t;
        block = b;
    }

    @Override
    public Collection<IProperty> getPropertyNames() {
        return null;
    }

    @Override
    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        return null;
    }

    @Override
    public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
        return null;
    }

    @Override
    public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
        return null;
    }

    @Override
    public ImmutableMap<IProperty, Comparable> getProperties() {
        return null;
    }

    @Override
    public Block getBlock() {
        return null;
    }
}
