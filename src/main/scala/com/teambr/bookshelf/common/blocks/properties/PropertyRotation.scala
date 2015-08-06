package com.teambr.bookshelf.common.blocks.properties

import java.util

import com.teambr.bookshelf.collections.UnlistedAdapter
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.util.EnumFacing

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 06, 2015
 */
object PropertyRotation {
    val FOUR_WAY = new UnlistedAdapter[EnumFacing](PropertyDirection.create("facing", util.Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST)))
    val SIX_WAY = new UnlistedAdapter[EnumFacing](PropertyDirection.create("facing", util.Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN)))
}
