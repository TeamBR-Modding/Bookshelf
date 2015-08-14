package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.common.blocks.properties.{ TileAwareState, TileAwareProperty }
import net.minecraft.block.BlockContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.property.IExtendedBlockState

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
trait TileAware extends BlockContainer {
    override def getExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : IBlockState = {
        state match {
            case extendedState: IExtendedBlockState => //Needs to have an extended state
                world.getTileEntity(pos) match {
                    case tile : TileEntity => new TileAwareState(tile, world.getBlockState(pos).getBlock)
                    case _ => state
                }
            case _ => state
        }
    }
}
