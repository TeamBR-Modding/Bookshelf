package com.teambr.bookshelf.collections

import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraftforge.fml.relauncher.{SideOnly, Side}

/**
  * This file was created for NeoTech
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 2/26/2016
  */
@SideOnly(Side.CLIENT)
class ConnectedTextures(val noConnections : TextureAtlasSprite, val anti_corners : TextureAtlasSprite,
                        val corners : TextureAtlasSprite, val horizontal : TextureAtlasSprite, val vertical : TextureAtlasSprite) {

    /**
      * This is used to get what part should be rendered for each corner
      *
      * The corners are represented by integers:
      * 0 & 4 - Top Left
      * 1 & 5 - Top Right
      * 2 & 6 - Bottom Left
      * 3 & 7 - Bottom Right
      *
      * The boolean array is the connections around the block. This should be size 16. 0 is top left and it goes left to right,
      * top to bottom. Exclude the block itself as we don't need to check that
      *
      * Boolean Array
      * 0 1 2
      * 3   4
      * 5 6 7
      *
      * One Level Up
      *  8  9 10
      * 11    12
      * 13 14 15
      *
      */
    def getTextureForCorner(corner : Int, connections : Array[Boolean]) : TextureAtlasSprite = {
        corner match {
            case 0 =>
                if(connections(0) && connections(1) && connections(3))
                    return noConnections
                else if(connections(0) && connections(1))
                    return vertical
                else if(connections(0) && connections(3))
                    return horizontal
                else if(connections(1) && connections(3))
                    return anti_corners
                else if(connections(1))
                    return vertical
                else if(connections(3))
                    return horizontal
                corners
            case 1 =>
                if(connections(2) && connections(1) && connections(4))
                    return noConnections
                else if(connections(2) && connections(1))
                    return vertical
                else if(connections(2) && connections(4))
                    return horizontal
                else if(connections(1) && connections(4))
                    return anti_corners
                else if(connections(1))
                    return vertical
                else if(connections(4))
                    return horizontal
                corners
            case 2 =>
                if(connections(5) && connections(6) && connections(3))
                    return noConnections
                else if(connections(5) && connections(6))
                    return vertical
                else if(connections(5) && connections(3))
                    return horizontal
                else if(connections(6) && connections(3))
                    return anti_corners
                else if(connections(6))
                    return vertical
                else if(connections(3))
                    return horizontal
                corners
            case 3 =>
                if(connections(7) && connections(6) && connections(4))
                    return noConnections
                else if(connections(7) && connections(6))
                    return vertical
                else if(connections(7) && connections(4))
                    return horizontal
                else if(connections(6) && connections(4))
                    return anti_corners
                else if(connections(6))
                    return vertical
                else if(connections(4))
                    return horizontal
                corners
            case _ =>
                noConnections
        }
    }
}
