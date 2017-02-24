package com.teambr.bookshelf.common.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/10/2017
 */
public interface IRedstoneAware {

    // Kinda cheaty
    // Copying the TileEntity methods we need, when implementing this interface that implementation will be used
    World getWorld();
    BlockPos getPos();

    /**
     * Checks if this block is recieving redstone
     *
     * @return True if has power
     */
    default boolean isPowered() {
        return !(getWorld() == null || getPos() == null) &&
                (isPoweringTo(getWorld(), getPos().offset(EnumFacing.UP), EnumFacing.DOWN) ||
                        isPoweringTo(getWorld(), getPos().offset(EnumFacing.DOWN), EnumFacing.UP) ||
                        isPoweringTo(getWorld(), getPos().offset(EnumFacing.NORTH), EnumFacing.SOUTH) ||
                        isPoweringTo(getWorld(), getPos().offset(EnumFacing.SOUTH), EnumFacing.NORTH) ||
                        isPoweringTo(getWorld(), getPos().offset(EnumFacing.EAST), EnumFacing.WEST) ||
                        isPoweringTo(getWorld(), getPos().offset(EnumFacing.WEST), EnumFacing.EAST));
    }

    /**
     * Tests if the block is providing a redstone signal
     *
     * @param world The World
     * @param blockPos The block position
     * @param side Which side of the block
     * @return True if is providing
     */
    default boolean isPoweringTo(World world, BlockPos blockPos, EnumFacing side) {
        return getWorld() != null && world.getBlockState(blockPos).getWeakPower(world, blockPos, side) > 0;
    }
}
