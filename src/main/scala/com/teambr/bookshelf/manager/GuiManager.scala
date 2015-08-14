package com.teambr.bookshelf.manager

import com.teambr.bookshelf.common.tiles.traits.OpensGui
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

/**
 * This file was created for Bookshelf
 *
 * Bookshelf if licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 02, 2015
 */
class GuiManager extends IGuiHandler {
    override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        world.getBlockState(new BlockPos(x, y, z)).getBlock match {
            case block : OpensGui => block.getClientGuiElement(ID, player, world, x, y, z)
            case _ => null
        }
    }

    override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        world.getBlockState(new BlockPos(x, y, z)).getBlock match {
            case block : OpensGui => block.getServerGuiElement(ID, player, world, x, y, z)
            case _ => null
        }
    }
}
