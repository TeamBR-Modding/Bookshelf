package com.teambr.bookshelf.common.blocks.traits

import java.util

import com.teambr.bookshelf.collections.CubeTextures
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.IBlockState
import net.minecraft.block.{Block, BlockPistonBase}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.World

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
trait SixWayRotation extends Block with FourWayRotation {
    override def PROPERTY_ROTATION = PropertyDirection.create("facing", util.Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN))
    
    /**
     * Called when the block is placed, we check which way the player is facing and put our value as the opposite of that
     */
    override def onBlockPlaced(world: World, blockPos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState = {
        this.getDefaultState.withProperty(PROPERTY_ROTATION, BlockPistonBase.getFacingFromEntity(world, blockPos, placer))
    }

    override def getAllPossibleStates: Array[IBlockState] =
        Array[IBlockState](getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.NORTH),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.EAST),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.SOUTH),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.WEST),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.UP),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.DOWN))

    override def getRotatedTextures(that: CubeTextures, state: IBlockState, block: Block): CubeTextures = {
        val rotated = super.getRotatedTextures(that, state, block)
        if(state == block.getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.UP)) {
            rotated.north = that.down
            rotated.south = that.up
            rotated.east = that.east
            rotated.west = that.west
            rotated.up = that.north
            rotated.down = that.south
        } else if(state == block.getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.DOWN)) {
            rotated.north = that.down
            rotated.south = that.up
            rotated.east = that.east
            rotated.west = that.west
            rotated.up = that.south
            rotated.down = that.north
        }
        rotated
    }
}