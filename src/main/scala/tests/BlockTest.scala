package tests

import com.teambr.bookshelf.collections.CubeTextures
import com.teambr.bookshelf.common.blocks.traits.{BlockBakeable, SixWayRotation}
import com.teambr.bookshelf.common.tiles.traits.{ Inventory, OpensGui }
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
class BlockTest extends Block(Material.rock) with BlockBakeable with SixWayRotation with OpensGui {
    override def MODID : String = "bookshelf"
    override def blockName : String = "testBlock"

    setUnlocalizedName(MODID + ":" + blockName)

    override def getDefaultCubeTextures: CubeTextures = {
        val map = Minecraft.getMinecraft.getTextureMapBlocks
        val textures = new CubeTextures(
            map.getTextureExtry(MODID + ":blocks/" + blockName + "Front"),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName)
        )
        textures
    }

    override def registerIcons() : Array[ResourceLocation] = Array[ResourceLocation](new ResourceLocation(MODID + ":blocks/" + blockName),
                                                                            new ResourceLocation(MODID + ":blocks/" + blockName + "Front"))

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
            override var inventoryName: String = "test"

            /**
             * Does this inventory has a custom name
             * @return True if there is a name (localized)
             */
            override def hasCustomName: Boolean = false

            override def initialSize: Int = 3
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
