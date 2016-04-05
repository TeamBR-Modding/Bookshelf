package com.teambr.bookshelf.helper

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 05, 2015
 */
object BlockHelper {
    /**
     * Get the string for this block
     * @param block Block to check
     * @param meta Block meta data
     * @return 'modid:name:meta' string representation
     */
    def getBlockString(block : Block, meta : Int) : String = {
        block.getRegistryName.toString + ":" + meta
        //block.getUnlocalizedName.substring(5) + ":" + meta
    }

    /**
     * Used to get non-meta specific string
     * @param block Block to check
     * @return "modid:name:-1"
     */
    def getBlockString(block : Block) : String = {
        block.getRegistryName.toString + ":" + String.valueOf(-1)
        //block.getUnlocalizedName.substring(5) + ":" + String.valueOf(-1)
    }

    /**
     * Get the block and meta from the string
     * @param str Block string
     * @return The block and meta
     */
    def getBlockFromString(str : String) : (Block, Integer) = {
        val name = str.split(":")
        name.length match {
            case 3 => (Block.getBlockFromItem(GameRegistry.findItem(name(0), name(1))), if (Integer.valueOf(name(2)) == -1) 0 else Integer.valueOf(name(2)))
            case 2 => (Block.getBlockFromItem(GameRegistry.findItem(name(0), name(1))), 0)
            case _ => (Blocks.air, 0)
        }
    }
}
