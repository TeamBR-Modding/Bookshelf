package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.common.blocks.properties.PropertyRotation
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.{ BlockState, IBlockState }
import net.minecraft.client.resources.model.ModelRotation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.{ BlockPos, EnumFacing, MathHelper }
import net.minecraft.world.{ IBlockAccess, World }
import net.minecraftforge.common.property.{ ExtendedBlockState, IExtendedBlockState, IUnlistedProperty }

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
trait FourWayRotation extends BlockBakeable {


    /**
     * Called when the block is placed, we check which way the player is facing and put our value as the opposite of that
     */
    override def onBlockPlaced(world : World, blockPos : BlockPos, facing : EnumFacing, hitX : Float, hitY : Float, hitZ : Float, meta : Int, placer : EntityLivingBase) : IBlockState = {
        val playerFacingDirection = if (placer == null) 0 else MathHelper.floor_double((placer.rotationYaw / 90.0F) + 0.5D) & 3
        val enumFacing = EnumFacing.getHorizontal(playerFacingDirection).getOpposite
        this.getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, enumFacing)
    }

    /**
     * Used to say what our block state is
     */
    override def createBlockState() : BlockState = {
        val listed = new Array[IProperty](1)
        listed(0) = PropertyRotation.FOUR_WAY.getProperty
        val unlisted = new Array[IUnlistedProperty[_]](0)
        new ExtendedBlockState(this, listed, unlisted)
    }

    override def getExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : IBlockState = {
        state match {
            case returnValue : IExtendedBlockState =>
                returnValue.withProperty(PropertyRotation.FOUR_WAY, world.getBlockState(pos).getValue(PropertyRotation.FOUR_WAY.getProperty).asInstanceOf[EnumFacing])
                returnValue
            case _ =>state
        }
    }

    /**
     * Used to convert the meta to state
     * @param meta The meta
     * @return
     */
    override def getStateFromMeta(meta : Int) : IBlockState = getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.getFront(meta))

    /**
     * Called to convert state from meta
     * @param state The state
     * @return
     */
    override def getMetaFromState(state : IBlockState) = state.getValue(PropertyRotation.FOUR_WAY.getProperty).asInstanceOf[EnumFacing].getIndex


    def getModelRotation(state : IBlockState) : ModelRotation = {
        if(state == getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.EAST))
            return ModelRotation.X0_Y90
        else if(state == getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.SOUTH))
            return ModelRotation.X0_Y180
        else if(state == getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.WEST))
            return ModelRotation.X0_Y270
        ModelRotation.X0_Y0
    }

    override def getAllPossibleStates: Array[IBlockState] =
        Array[IBlockState](getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.NORTH),
            getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.EAST),
            getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.SOUTH),
            getDefaultState.withProperty(PropertyRotation.FOUR_WAY.getProperty, EnumFacing.WEST))
}
