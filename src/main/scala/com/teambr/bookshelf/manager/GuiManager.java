package com.teambr.bookshelf.manager;

import com.teambr.bookshelf.common.OpensGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

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
public class GuiManager implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof OpensGui)
            return ((OpensGui)world.getBlockState(new BlockPos(x, y, z)).getBlock()).getServerGuiElement(ID, player, world, x, y, z);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof OpensGui)
            return ((OpensGui)world.getBlockState(new BlockPos(x, y, z)).getBlock()).getClientGuiElement(ID, player, world, x, y, z);
        return null;    }
}
