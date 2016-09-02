package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.traits.NBTSavable
import com.teambr.bookshelf.util.WorldUtils
import net.minecraft.block.Block
import net.minecraft.block.state.{BlockStateContainer, IBlockState}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.AttachCapabilitiesEvent.TileEntity

/**
  * This file was created for Lux et Umbra
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * Trait for blocks that respond to a tool like a wrench, IToolWrench
  *
  * Can be attached to tiles as well
  *
  * @author Paul Davis <pauljoda>
  * @since 9/1/2016
  */
trait IToolable extends Block {

    /**
      * Used to get what itemstack to drop, should be a stack of one of the block usually
      * @param worldIn The world
      * @param pos The pos of the block to break
      * @return The stack to be spawned on wrench
      */
    def getStackDroppedByWrench(worldIn: World, pos: BlockPos) : ItemStack = new ItemStack(getDefaultState.getBlock, 1)

    /**
      * Called when a wrench clicks on this block
      * @param stack The stack clicking on this block, an instance of IToolWrench
      * @param playerIn The player clicking
      * @param worldIn The world
      * @param pos The block pos (us)
      * @param hand The player's hand
      * @param facing Which face was clicked
      * @param hitX X hit
      * @param hitY Y hit
      * @param hitZ Z hit
      * @return The result, pass to send to next, success to end
      */
    def onWrench(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand,
                 facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) : EnumActionResult = {
        if(playerIn.isSneaking && breakSavingNBT(worldIn, pos))
            EnumActionResult.SUCCESS
        else
            EnumActionResult.PASS
    }

    /**
      * Breaks the block and saves the NBT to the tag, calls getStackDropped to drop (just item)
      * @param world The world
      * @param pos The block pos
      * @return True if successful
      */
    def breakSavingNBT(world : World, pos : BlockPos): Boolean = {
        if(world.isRemote) return false
        world.getTileEntity(pos) match {
            case savable =>
                val tag = savable.writeToNBT(new NBTTagCompound)
                val stack = getStackDroppedByWrench(world, pos)
                stack.setTagCompound(tag)
                WorldUtils.dropStack(world, stack, pos)
                world.setBlockToAir(pos)
                return true
            case _ =>
        }
        false
    }

    /**
      * Called by ItemBlocks after a block is set in the world, to allow post-place logic
      */
    override def onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack): Unit = {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack)
        if(stack.hasTagCompound) {
            worldIn.getTileEntity(pos) match {
                case savable =>
                    val tag = stack.getTagCompound
                    tag.setInteger("x", pos.getX)
                    tag.setInteger("y", pos.getY)
                    tag.setInteger("z", pos.getZ)
                    savable.readFromNBT(tag)
                case _ =>
            }
        }
    }
}
