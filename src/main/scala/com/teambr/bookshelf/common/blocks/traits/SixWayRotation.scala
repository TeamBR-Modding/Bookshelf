package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.common.blocks.properties.Properties
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.{BlockStateContainer, IBlockState}
import net.minecraft.block.{ Block, BlockPistonBase }
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{ EnumFacing }
import net.minecraft.world.{ IBlockAccess, World }
import net.minecraftforge.common.property.{ IExtendedBlockState, ExtendedBlockState, IUnlistedProperty }

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 03, 2015
 */
trait SixWayRotation extends Block with FourWayRotation {

    /**
     * Called when the block is placed, we check which way the player is facing and put our value as the opposite of that
     */
    override def onBlockPlaced(world: World, blockPos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState = {
        this.getDefaultState.withProperty(Properties.SIX_WAY, BlockPistonBase.getFacingFromEntity(blockPos, placer))
    }

    /**
     * Used to say what our block state is
     */
    override def createBlockState() : BlockStateContainer = {
        val listed = new Array[IProperty[_]](1)
        listed(0) = Properties.SIX_WAY
        val unlisted = new Array[IUnlistedProperty[_]](0)
        new ExtendedBlockState(this, listed, unlisted)
    }

    override def getExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : IBlockState = {
        state match {
            case returnValue : IExtendedBlockState =>
                returnValue.withProperty(Properties.SIX_WAY, world.getBlockState(pos).getValue(Properties.SIX_WAY).asInstanceOf[EnumFacing])
                returnValue
            case _ =>state
        }
    }

    /**
     * Used to convert the meta to state
 *
     * @param meta The meta
     * @return
     */
    override def getStateFromMeta(meta : Int) : IBlockState = getDefaultState.withProperty(Properties.SIX_WAY, EnumFacing.getFront(meta))

    /**
     * Called to convert state from meta
 *
     * @param state The state
     * @return
     */
    override def getMetaFromState(state : IBlockState) = state.getValue(Properties.SIX_WAY).getIndex
}