package com.teambr.bookshelf.collections

import com.teambr.bookshelf.common.blocks.traits.{FourWayRotation, SixWayRotation}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite

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
class CubeTextures {
    val textures = Minecraft.getMinecraft.getTextureMapBlocks

    //Since you didn't tell me what to be, I'm going to set all my textures to stone. Just so you don't crash
    var north = textures.getAtlasSprite("minecraft:blocks/stone")
    var south  = textures.getAtlasSprite("minecraft:blocks/stone")
    var east  = textures.getAtlasSprite("minecraft:blocks/stone")
    var west = textures.getAtlasSprite("minecraft:blocks/stone")
    var up    = textures.getAtlasSprite("minecraft:blocks/stone")
    var down  = textures.getAtlasSprite("minecraft:blocks/stone")

    def this(f : TextureAtlasSprite, b : TextureAtlasSprite, l : TextureAtlasSprite, r : TextureAtlasSprite, u : TextureAtlasSprite, d : TextureAtlasSprite) = {
        this()
        north = f
        south = b
        east = l
        west = r
        up = u
        down = d
    }

    /**
     * Used to get the textures, rotated by the current state
     * @param state The state of the block
     * @param block The block itself
     * @return
     */
    def getRotatedTextures(state : IBlockState, block : Block) : CubeTextures = {
        val rotated = new CubeTextures()
        block match {
            case four : FourWayRotation => //This will rotate four ways
                four.getRotatedTextures(this, state, block)
            case six : SixWayRotation => //This will rotate size ways
                six.getRotatedTextures(this, state, block)
            case _ => rotated
        }
    }

    def copy(other : CubeTextures) = {
        this.north = other.north
        this.south = other.south
        this.east = other.east
        this.west = other.west
        this.up = other.up
        this.down = other.down
    }
}
