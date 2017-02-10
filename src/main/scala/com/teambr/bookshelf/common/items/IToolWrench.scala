package com.teambr.bookshelf.common.items

import com.teambr.bookshelf.common.blocks.IToolable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.world.World

/**
  * This file was created for Lux et Umbra
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * The trait for wrenches
  *
  * @author Paul Davis <pauljoda>
  * @since 9/1/2016
  */
trait IToolWrench extends Item {

    /**
      * Called when a block is clicked on with this tool
      * @param stack The stack
      * @param playerIn The player clicking
      * @param worldIn The world
      * @param pos Block Pos
      * @param hand The hand
      * @param facing Block face
      * @param hitX X hit
      * @param hitY Y hit
      * @param hitZ Z hit
      * @return
      */
    override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand,
                  facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {

        // Catch attached to blocks
        worldIn.getBlockState(pos).getBlock match {
            case toolableBlock : IToolable =>
                return toolableBlock.onWrench(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)
            case _ =>
        }

        EnumActionResult.PASS
    }
}
