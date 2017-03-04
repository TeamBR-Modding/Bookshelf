package com.teambr.bookshelf.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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
public interface IToolTipProvider {

    /**
     * Used to get the tip to display
     * @return The tip to display
     */
    @Nullable
    @SideOnly(Side.CLIENT)
    List<String> getToolTip(@Nonnull ItemStack stack) ;
}
