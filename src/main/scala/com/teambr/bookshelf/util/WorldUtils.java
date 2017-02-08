package com.teambr.bookshelf.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/7/2017
 */
public class WorldUtils {

    /**
     * Returns the direction to the left of this. This is the direction it is facing turned left
     *
     * @param toTurn Starting point
     * @return The direction turned 90 left
     */
    public static EnumFacing rotateLeft(EnumFacing toTurn) {
        switch (toTurn) {
            case NORTH :
                return EnumFacing.WEST;
            case EAST:
                return EnumFacing.NORTH;
            case SOUTH:
                return EnumFacing.EAST;
            case WEST:
                return EnumFacing.SOUTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /**
     * Returns the direction to the right of this. This is the direction it is facing turned right
     *
     * @param toTurn Starting point
     * @return The direction turned 90 right
     */
    public static EnumFacing rotateRight(EnumFacing toTurn) {
        switch (toTurn) {
            case NORTH :
                return EnumFacing.EAST;
            case EAST:
                return EnumFacing.SOUTH;
            case SOUTH:
                return EnumFacing.WEST;
            case WEST:
                return EnumFacing.NORTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /**
     * Drops and Array of ItemStacks into the world
     *
     * @param world Instance of ``World
     * @param stacks ItemStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStacks(World world, List<ItemStack> stacks, BlockPos pos) {
        stacks.forEach((ItemStack stack) -> {
            dropStack(world, stack, pos);
        });
    }

    /**
     * Drops a ItemStack into the world
     *
     * @param world Instance of ``World
     * @param stack temStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStack(World world, ItemStack stack, BlockPos pos) {
        if(stack != null && stack.stackSize > 0) {
            float rx = world.rand.nextFloat() * 0.8F + 1.0F;
            float ry = world.rand.nextFloat() * 0.8F + 1.0F;
            float rz = world.rand.nextFloat() * 0.8F + 1.0F;

            EntityItem itemEntity = new EntityItem(world,
                    pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                    stack.copy());

            float factor = 0.05F;

            itemEntity.motionX = world.rand.nextGaussian() * factor;
            itemEntity.motionY = world.rand.nextGaussian() * factor + 0.2F;
            itemEntity.motionZ = world.rand.nextGaussian() * factor;
            world.spawnEntityInWorld(itemEntity);

            stack.stackSize = 0;
        }
    }
}
