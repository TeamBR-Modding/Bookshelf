package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.common.blocks.properties.Properties
import net.minecraft.block.Block
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.{BlockStateContainer, IBlockState}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.{BlockPos, MathHelper}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.property.{ExtendedBlockState, IExtendedBlockState, IUnlistedProperty}

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
trait FourWayRotation extends Block {

    /**
     * Called when the block is placed, we check which way the player is facing and put our value as the opposite of that
     */
    override def onBlockPlacedBy(world: World, pos : BlockPos, state : IBlockState, placer : EntityLivingBase, stack : ItemStack) : Unit = {
        val playerFacingDirection = if (placer == null) 0 else MathHelper.floor((placer.rotationYaw / 90.0F) + 0.5D) & 3
        val enumFacing = EnumFacing.getHorizontal(playerFacingDirection).getOpposite
        world.setBlockState(pos, getDefaultState.withProperty(Properties.FOUR_WAY, enumFacing))
    }

    /**
     * Used to say what our block state is
     */
    override def createBlockState() : BlockStateContainer = {
        val listed = new Array[IProperty[_]](1)
        listed(0) = Properties.FOUR_WAY
        val unlisted = new Array[IUnlistedProperty[_]](0)
        new ExtendedBlockState(this, listed, unlisted)
    }

    override def getExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : IBlockState = {
        state match {
            case returnValue : IExtendedBlockState =>
                returnValue.withProperty(Properties.FOUR_WAY, world.getBlockState(pos).getValue(Properties.FOUR_WAY))
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
    override def getStateFromMeta(meta : Int) : IBlockState = getDefaultState.withProperty(Properties.FOUR_WAY, EnumFacing.getFront(meta))

    /**
     * Called to convert state from meta
 *
     * @param state The state
     * @return
     */
    override def getMetaFromState(state : IBlockState) = state.getValue(Properties.FOUR_WAY).getIndex
}
