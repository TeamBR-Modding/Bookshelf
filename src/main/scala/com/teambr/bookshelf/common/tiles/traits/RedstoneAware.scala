package com.teambr.bookshelf.common.tiles.traits

import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, BlockPos}
import net.minecraft.world.World

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 *
 * This is used to make sure the tile can see if there is redstone powering it
 */
trait RedstoneAware extends TileEntity {
    /**
     * Checks if this block is recieving redstone
     * @return True if has power
     */
    def isPowered: Boolean = {
        isPoweringTo(getWorld,  getPos.offset(EnumFacing.UP),    EnumFacing.DOWN)  ||
                isPoweringTo(getWorld, getPos.offset(EnumFacing.DOWN),  EnumFacing.UP)    ||
                isPoweringTo(getWorld, getPos.offset(EnumFacing.NORTH), EnumFacing.SOUTH) ||
                isPoweringTo(getWorld, getPos.offset(EnumFacing.SOUTH), EnumFacing.NORTH) ||
                isPoweringTo(getWorld, getPos.offset(EnumFacing.EAST),  EnumFacing.WEST)  ||
                isPoweringTo(getWorld, getPos.offset(EnumFacing.WEST),  EnumFacing.EAST)
    }

    /**
     * Tests if the block is providing a redstone signal
     * @param world The World
     * @param blockPos The block position
     * @param side Which side of the block
     * @return True if is providing
     */
    def isPoweringTo(world: World, blockPos: BlockPos, side: EnumFacing): Boolean = {
         world.getBlockState(blockPos).getBlock.isProvidingWeakPower(world, blockPos, world.getBlockState(blockPos), side) > 0
    }
}
