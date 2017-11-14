package com.teambr.bookshelf.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Lux-et-Umbra-Redux
 * <p>
 * Lux-et-Umbra-Redux is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 10/8/2016
 */
public class BlockUtils {

    /**
     * Returns a collection of {@link net.minecraft.util.math.BlockPos} that are not Air
     *
     * @param size              the amount of blocks from the center block
     * @param facing            the block the player is looking at {@link net.minecraft.util.math.RayTraceResult}
     * @param world             {@link net.minecraft.world.World}
     * @return                  An {@link ArrayList} of {@link net.minecraft.util.math.BlockPos}
     */
    public static List<BlockPos> getBlockList(int size, EnumFacing facing, BlockPos pos, World world) {

        BlockPos pos1;
        BlockPos pos2;
        List<BlockPos> actualList = new ArrayList<>();

        if (facing.getAxis().isHorizontal()) {
            if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                pos1 = pos.offset(EnumFacing.UP, size).offset(EnumFacing.EAST, size);
                pos2 = pos.offset(EnumFacing.DOWN, size).offset(EnumFacing.WEST, size);
            } else {
                pos1 = pos.offset(EnumFacing.UP, size).offset(EnumFacing.SOUTH, size);
                pos2 = pos.offset(EnumFacing.DOWN, size).offset(EnumFacing.NORTH, size);
            }

            while(pos2.getY() < pos.getY() - 1) {
                pos1 = pos1.offset(EnumFacing.UP);
                pos2 = pos2.offset(EnumFacing.UP);
            }
        } else {
            pos1 = pos.offset(EnumFacing.NORTH, size).offset(EnumFacing.WEST, size);
            pos2 = pos.offset(EnumFacing.SOUTH, size).offset(EnumFacing.EAST, size);
        }

        for (BlockPos blockPos : BlockPos.getAllInBox(pos1, pos2)) {
            if (!world.isAirBlock(blockPos))
                actualList.add(blockPos);
        }

        return actualList;
    }
}
