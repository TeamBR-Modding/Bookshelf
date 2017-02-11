package com.teambr.bookshelf.common.items;

import com.teambr.bookshelf.common.blocks.IToolable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ToolWrench extends Item {

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
     * @return Result
     */
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.getBlockState(pos).getBlock() instanceof IToolable) {
            IToolable toolableBlock = (IToolable) worldIn.getBlockState(pos).getBlock();
            return toolableBlock.onWrench(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        } else
            return EnumActionResult.PASS;
    }
}
