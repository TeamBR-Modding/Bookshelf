package com.teambr.bookshelf.common.blocks.traits

import com.teambr.bookshelf.client.modelfactory.BakeableBlockRegistry
import com.teambr.bookshelf.collections.CubeTextures
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.util.{BlockPos, ResourceLocation}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.property.IExtendedBlockState
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * This file was created for the Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 03, 2015
 *
 * This trait allows you to create models outside of the .json files.
 * If you do this, make sure you implement all that you need to. This block has to have valid textures
 */
trait BlockBakeable extends Block {
    /**
     * You have to define this in your class. Without this, nothing will load correctly.
     */
    def MODID : String
    def blockName : String

    BakeableBlockRegistry.addBlock(this)

    /**
     * This is used to get the model location for the normal model
     * @return The model location for this
     */
    @SideOnly(Side.CLIENT)
    def getNormalModelLocation : ModelResourceLocation = new ModelResourceLocation(MODID + ":" + blockName, "normal")

    /**
     * This is used to get the model location for the inventory model
     * @return The model location for this
     */
    @SideOnly(Side.CLIENT)
    def getInventoryModelLocation : ModelResourceLocation = new ModelResourceLocation(MODID + ":" + blockName, "inventory")

    /**
     * Used to get the default textures for the block.
     * If you want to specify specific textures for each side, you must override this
     *
     * By default, this will just make a texture map using the block name. THAT MUST EXIST OR IT WILL CRASH
     */
    def getDefaultCubeTextures: CubeTextures = {
        val map = Minecraft.getMinecraft.getTextureMapBlocks
        val textures = new CubeTextures(
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName),
            map.getTextureExtry(MODID + ":blocks/" + blockName)
        )
        textures
    }

    /**
     * Used to register the icons for this block
     *
     * Be default, it will use the block name and mod id given.
     *
     * I'll be very upset with you if you don't either override this or make sure that the default texture exists
     * @return An array of resource locations, used later to actually register the things (we are kinda working around 1.8)
     */
    def registerIcons() : Array[ResourceLocation] = Array[ResourceLocation](new ResourceLocation(MODID + ":blocks/" + blockName))

    /**
     * Used to specify which layer to render on. You should be using 3 for normal stuff
     */
    override def getRenderType: Int = 3

    /**
     * Use this to pass along relevant info with the block state
     * @param state The state incoming
     * @param world The world (Client side)
     * @param pos The position
     * @return The new state (or the passed if no changes)
     */
    def buildExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : Option[IBlockState] = None

    /**
     * Use this to define every state. Since you want to have a model for each state, make sure all possible values are
     * added here. Without all of them, the model won't be baked for each type
     * @return
     */
    def getAllPossibleStates: Array[IBlockState] = Array[IBlockState](getDefaultState)

    /**
     * Used to get more info. Cause no way should we have to use default stuff
     */
    override def getExtendedState(state : IBlockState, world : IBlockAccess, pos : BlockPos) : IBlockState = {
        if(state.isInstanceOf[IExtendedBlockState]) { //Needs to have an extended state
            buildExtendedState(state, world, pos) match {
                case Some(prop) => return prop
                case None => return state
            }
        }
        state
    }
}
