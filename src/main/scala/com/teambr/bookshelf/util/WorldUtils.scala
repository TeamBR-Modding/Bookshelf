package com.teambr.bookshelf.util

import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.World

import scala.util.Random

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
object WorldUtils {
    /**
     * Returns the direction to the left of this. This is the direction it is facing turned left
      *
      * @param toTurn Starting point
     * @return The direction turned 90 left
     */
    def rotateLeft(toTurn : EnumFacing) : EnumFacing = {
        toTurn match {
            case EnumFacing.NORTH => EnumFacing.WEST
            case EnumFacing.EAST  => EnumFacing.NORTH
            case EnumFacing.SOUTH => EnumFacing.EAST
            case EnumFacing.WEST  => EnumFacing.SOUTH
            case EnumFacing.UP    => EnumFacing.UP
            case EnumFacing.DOWN  => EnumFacing.DOWN
        }
    }

    /**
     * Returns the direction to the right of this. This is the direction it is facing turned right
      *
      * @param toTurn Starting point
     * @return The direction turned 90 right
     */
    def rotateRight(toTurn : EnumFacing) : EnumFacing = {
        toTurn match {
            case EnumFacing.NORTH => EnumFacing.EAST
            case EnumFacing.EAST  => EnumFacing.SOUTH
            case EnumFacing.SOUTH => EnumFacing.WEST
            case EnumFacing.WEST  => EnumFacing.NORTH
            case EnumFacing.UP    => EnumFacing.UP
            case EnumFacing.DOWN  => EnumFacing.DOWN
        }
    }

    /**
      * Drops and Array of ``ItemStacks into the world
      *
      * @param world Instance of ``World
      * @param stacks ``ItemStack Array to drop into the world
      * @param pos ``BlockPos to drop them from
      */
    def dropStack(world: World, stacks: java.util.List[ItemStack], pos: BlockPos): Unit = {
        for(stack <- stacks.toArray())
            dropStack(world, stack.asInstanceOf[ItemStack], pos)
    }

    /**
      * Drops a ItemStack into the world
      *
      * @param world Instance of ``World
      * @param stack ``ItemStack Array to drop into the world
      * @param pos ``BlockPos to drop them from
      */
    def dropStack(world: World, stack: ItemStack, pos: BlockPos): Unit = {
        val random = new Random
        if (stack != null && stack.stackSize > 0) {
            val rx = random.nextFloat * 0.8F + 0.1F
            val ry = random.nextFloat * 0.8F + 0.1F
            val rz = random.nextFloat * 0.8F + 0.1F

            val itemEntity = new EntityItem(world,
                pos.getX + rx, pos.getY + ry, pos.getZ + rz,
                new ItemStack(stack.getItem, stack.stackSize, stack.getItemDamage))

            if (stack.hasTagCompound)
                itemEntity.getEntityItem.setTagCompound(stack.getTagCompound)

            val factor = 0.05F

            itemEntity.motionX = random.nextGaussian * factor
            itemEntity.motionY = random.nextGaussian * factor + 0.2F
            itemEntity.motionZ = random.nextGaussian * factor
            world.spawnEntityInWorld(itemEntity)

            stack.stackSize = 0
        }
    }
}
