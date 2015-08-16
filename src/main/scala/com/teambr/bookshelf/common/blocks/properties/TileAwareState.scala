package com.teambr.bookshelf.common.blocks.properties

import java.util

import com.google.common.collect.ImmutableMap
import net.minecraft.block.Block
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 14, 2015
 *
 * Used only for the model, don't try to use this as a full block state
 */
class TileAwareState(val tile : TileEntity, block: Block) extends IBlockState {
    override def getBlock : Block = block
    override def withProperty(property : IProperty, value : Comparable[_]) : IBlockState = null
    override def getValue(property : IProperty) : Comparable[_] = null
    override def cycleProperty(property : IProperty) : IBlockState = null
    override def getPropertyNames : util.Collection[_] = null
    override def getProperties : ImmutableMap[_, _] = null
}
