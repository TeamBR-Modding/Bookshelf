package com.dyonovan.brlib.manager;

import com.dyonovan.brlib.common.tiles.IOpensGui;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiManager implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof IOpensGui ? (((IOpensGui) world.getTileEntity(x, y, z)).getServerGuiElement(ID, player, world, x, y, z)) : null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof IOpensGui ? (((IOpensGui) world.getTileEntity(x, y, z)).getClientGuiElement(ID, player, world, x, y, z)) : null;
    }
}
