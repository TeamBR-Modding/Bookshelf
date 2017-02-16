package com.teambr.bookshelf.common.blocks;

import com.teambr.bookshelf.util.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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
public interface IToolable {

    /**
     * Called to get what stack should be dropped on wrench
     * @param world The world
     * @param pos The position of the block
     * @return The stack to drop in the world
     */
    @Nonnull
    ItemStack getStackDroppedByWrench(World world, BlockPos pos);

    /**
     * Called when a wrench clicks on this block
     * @param stack The stack clicking on this block, an INSTANCE of IToolWrench
     * @param player The player clicking
     * @param world The world
     * @param pos The block pos (us)
     * @param hand The player's hand
     * @param facing Which face was clicked
     * @param hitX X hit
     * @param hitY Y hit
     * @param hitZ Z hit
     * @return The result, pass to send to next, success to end
     */
    default EnumActionResult onWrench(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(player.isSneaking() && WorldUtils.breakBlockSavingNBT(world, pos, (IToolable) world.getBlockState(pos).getBlock()))
            return EnumActionResult.SUCCESS;
        else
            return EnumActionResult.FAIL;
    }
}
