package tests

import com.teambr.bookshelf.common.blocks.BlockBakeable
import net.minecraft.block.Block
import net.minecraft.block.material.Material

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
class BlockTest extends Block(Material.rock) with BlockBakeable {
    override def MODID: String = "bookshelf"
    override def blockName : String = "testBlock"

    setUnlocalizedName(MODID + ":" + blockName)
}
