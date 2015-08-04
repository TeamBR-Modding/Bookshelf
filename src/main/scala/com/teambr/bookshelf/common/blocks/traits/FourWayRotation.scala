package com.teambr.bookshelf.common.blocks.traits

import java.util

import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.client.resources.model.ModelRotation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.{BlockPos, EnumFacing, MathHelper}
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
trait FourWayRotation extends BlockBakeable {
    def PROPERTY_ROTATION = PropertyDirection.create("facing", util.Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST))

    /**
     * Called when the block is placed, we check which way the player is facing and put our value as the opposite of that
     */
    override def onBlockPlaced(world : World, blockPos : BlockPos, facing : EnumFacing, hitX : Float, hitY : Float, hitZ : Float, meta : Int, placer : EntityLivingBase) : IBlockState = {
        val playerFacingDirection = if (placer == null) 0 else MathHelper.floor_double((placer.rotationYaw / 90.0F) + 0.5D) & 3
        val enumFacing = EnumFacing.getHorizontal(playerFacingDirection).getOpposite
        this.getDefaultState.withProperty(PROPERTY_ROTATION, enumFacing)
    }

    /**
     * Used to say what our block state is
     */
    override def createBlockState() : BlockState = new BlockState(this, PROPERTY_ROTATION)

    /**
     * Used to convert the meta to state
     * @param meta The meta
     * @return
     */
    override def getStateFromMeta(meta : Int) : IBlockState = getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.getFront(meta))

    /**
     * Called to convert state from meta
     * @param state The state
     * @return
     */
    override def getMetaFromState(state : IBlockState) = state.getValue(PROPERTY_ROTATION).asInstanceOf[EnumFacing].getIndex

    override def getAllPossibleStates: Array[IBlockState] =
        Array[IBlockState](getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.NORTH),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.EAST),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.SOUTH),
            getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.WEST))

    def getModelRotation(state : IBlockState) : ModelRotation = {
        if(state == getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.EAST))
            return ModelRotation.X0_Y90
        else if(state == getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.SOUTH))
            return ModelRotation.X0_Y180
        else if(state == getDefaultState.withProperty(PROPERTY_ROTATION, EnumFacing.WEST))
            return ModelRotation.X0_Y270
        ModelRotation.X0_Y0
    }
}
