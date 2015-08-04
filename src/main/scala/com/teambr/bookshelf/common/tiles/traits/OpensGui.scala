package com.teambr.bookshelf.common.tiles.traits

import com.teambr.bookshelf.Bookshelf
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.{World, WorldServer}

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 *
 * This defines the block will open a GUI. You must use this in the Block class
 */
trait OpensGui extends Block {
    /**
     * Return the container for this tile
     * @param ID Id, probably not needed but could be used for multiple guis
     * @param player The player that is opening the gui
     * @param world The world
     * @param x X Pos
     * @param y Y Pos
     * @param z Z Pos
     * @return The container to open
     */
    def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef

    /**
     * Return the gui for this tile
     * @param ID Id, probably not needed but could be used for multiple guis
     * @param player The player that is opening the gui
     * @param world The world
     * @param x X Pos
     * @param y Y Pos
     * @param z Z Pos
     * @return The gui to open
     */
    def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef

    /**
     * Called when the block is activated
     *
     * If you want to override this but still call it, make sure you call
     *      super[OpensGui].onBlockActivated(...)
     */
    override def onBlockActivated(world : World, pos : BlockPos, state : IBlockState, player : EntityPlayer, side : EnumFacing, hitX : Float, hitY : Float, hitZ : Float) : Boolean = {
        super[Block].onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ)

        world match {
            case world : WorldServer =>
                player.openGui(Bookshelf.INSTANCE, 0, world, pos.getX, pos.getY, pos.getZ)
                true
            case _ => true
        }
    }
}
