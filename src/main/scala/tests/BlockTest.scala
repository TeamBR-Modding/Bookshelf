package tests

import com.teambr.bookshelf.common.blocks.traits.SixWayRotation
import com.teambr.bookshelf.common.tiles.traits.{Inventory, OpensGui}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 03, 2015
 */
class BlockTest extends Block(Material.rock) with SixWayRotation with OpensGui {

    setUnlocalizedName("bookshelf" + ":" + "test")

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
    override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        new ContainerTest(player.inventory, new Inventory {
            override def initialSize: Int = 4
        })
    }

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
    override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        new GuiTest(player)
    }
}
