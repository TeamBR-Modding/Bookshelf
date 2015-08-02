package test.com.teambr.bookshelf.common.blocks

import com.teambr.bookshelf.collections.BlockTextures
import com.teambr.bookshelf.common.blocks.BlockBase
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import test.com.teambr.bookshelf.common.tiles.TileTestBlock

/**
 * This file was created for the Bookshelf
 * API. The source is available at: 
 * https://github.com/TeamBR-Modding/Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
class TestBlock extends BlockBase(Material.rock, "Bookshelf:testing", classOf[TileTestBlock]) {
    override def generateDefaultTextures(iconRegistry : IIconRegister) = {
        this.blockIcon = iconRegistry.registerIcon("minecraft:stone")
        textures = new BlockTextures(iconRegistry, "minecraft:stone")
    }
}
