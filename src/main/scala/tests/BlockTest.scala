package tests

import com.teambr.bookshelf.collections.CubeTextures
import com.teambr.bookshelf.common.blocks.traits.{BlockBakeable, FourWayRotation}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

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
class BlockTest extends Block(Material.rock) with BlockBakeable with FourWayRotation {
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
}
