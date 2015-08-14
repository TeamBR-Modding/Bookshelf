package com.teambr.bookshelf.util

import net.minecraft.util.EnumFacing

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
}
