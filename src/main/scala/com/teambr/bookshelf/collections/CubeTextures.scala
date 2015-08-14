package com.teambr.bookshelf.collections

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
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

    def copy(other : CubeTextures) = {
        this.north = other.north
        this.south = other.south
        this.east = other.east
        this.west = other.west
        this.up = other.up
        this.down = other.down
    }
}
