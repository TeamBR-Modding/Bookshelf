package com.teambr.bookshelf.common.items;

import com.teambr.bookshelf.common.blocks.IToolable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class ToolWrench extends RegistrableItem {

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.getBlockState(pos).getBlock() instanceof IToolable) {
            IToolable toolableBlock = (IToolable) worldIn.getBlockState(pos).getBlock();
            return toolableBlock.onWrench(player.getHeldItem(hand), player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        } else
            return EnumActionResult.PASS;
    }
}
