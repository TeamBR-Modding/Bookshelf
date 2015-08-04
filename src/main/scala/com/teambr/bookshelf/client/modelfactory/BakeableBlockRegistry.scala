package com.teambr.bookshelf.client.modelfactory

import com.teambr.bookshelf.common.blocks.traits.BlockBakeable

import scala.collection.mutable.ArrayBuffer

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 */
object BakeableBlockRegistry {
    val blocks = new ArrayBuffer[BlockBakeable]

    /**
     * Used to add a block to the registry
     */
    def addBlock(block : BlockBakeable) = {
        blocks += block
    }
}
