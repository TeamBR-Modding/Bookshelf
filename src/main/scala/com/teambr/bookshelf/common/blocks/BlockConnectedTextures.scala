package com.teambr.bookshelf.common.blocks

import javax.annotation.Nonnull

import com.teambr.bookshelf.client.TextureManager
import com.teambr.bookshelf.client.models.BakedConnectedTextures
import com.teambr.bookshelf.collections.ConnectedTextures
import com.teambr.bookshelf.loadables.{CreatesTextures, ILoadActionProvider}
import net.minecraft.block.Block
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.{BlockStateContainer, IBlockState}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType, EnumFacing}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.common.property.{ExtendedBlockState, IUnlistedProperty}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.collection.mutable.ArrayBuffer

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
trait BlockConnectedTextures extends Block with CreatesTextures with ILoadActionProvider {

    // Methods to move textures to lower class, handle others here
    def NoCornersTextureLocation   : String
    def AntiCornersTextureLocation : String
    def CornersTextureLocation     : String
    def HorizontalTextureLocation  : String
    def VerticalTextureLocation    : String

    /**
      * The name of this block, eg neotech:phantomGlass
      */
    def name : String = getUnlocalizedName

    /**
      * Define true if you are a clear texture
      *
      * @return
      */
    def isClear : Boolean

    def getNormal : ModelResourceLocation = new ModelResourceLocation(name.split("tile.")(1), "normal")
    def getInventory : ModelResourceLocation = new ModelResourceLocation(name.split("tile.")(1), "inventory")

    @SideOnly(Side.CLIENT)
    lazy val connectedTextures = new ConnectedTextures(TextureManager.getTexture(NoCornersTextureLocation),
        TextureManager.getTexture(AntiCornersTextureLocation), TextureManager.getTexture(CornersTextureLocation),
        TextureManager.getTexture(HorizontalTextureLocation), TextureManager.getTexture(VerticalTextureLocation))

    /**
      * Used to define the strings needed
      */
    override def getTexturesToStitch: ArrayBuffer[String] = ArrayBuffer(NoCornersTextureLocation, AntiCornersTextureLocation,
        CornersTextureLocation, HorizontalTextureLocation, VerticalTextureLocation)

    /**
      * Used to get the connected textures object for this block
      *
      * @return
      */
    @SideOnly(Side.CLIENT)
    def getConnectedTextures : ConnectedTextures = if(connectedTextures != null) connectedTextures else {
        new ConnectedTextures(TextureManager.getTexture(NoCornersTextureLocation),
            TextureManager.getTexture(AntiCornersTextureLocation), TextureManager.getTexture(CornersTextureLocation),
            TextureManager.getTexture(HorizontalTextureLocation), TextureManager.getTexture(VerticalTextureLocation))
    }

    /**
      * Used to check if we are able to connect textures with the block
      *
      * @return True if can connect
      */
    def canTextureConnect(world : IBlockAccess, pos : BlockPos, otherPos : BlockPos) : Boolean =
        world.getBlockState(otherPos).getBlock == this

    /**
      * Kinds long, but the way to get the connection array for the face
      */
    def getConnectionArrayForFace(world: IBlockAccess, pos: BlockPos, facing: EnumFacing): Array[Boolean] = {
        val connections = new Array[Boolean](16)
        if (world.isAirBlock(pos.offset(facing)) || (!world.getBlockState(pos.offset(facing)).getBlock.isOpaqueCube(world.getBlockState(pos.offset(facing))) &&
                !canTextureConnect(world, pos, pos.offset(facing)))) {
            facing match {
                case EnumFacing.UP =>
                    connections(0) = canTextureConnect(world, pos, pos.add(-1, 0, -1))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 0, -1))
                    connections(2) = canTextureConnect(world, pos, pos.add(1, 0, -1))
                    connections(3) = canTextureConnect(world, pos, pos.add(-1, 0, 0))
                    connections(4) = canTextureConnect(world, pos, pos.add(1, 0, 0))
                    connections(5) = canTextureConnect(world, pos, pos.add(-1, 0, 1))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, 0, 1))
                    connections(7) = canTextureConnect(world, pos, pos.add(1, 0, 1))
                   return connections
                case EnumFacing.DOWN =>
                    connections(0) = canTextureConnect(world, pos, pos.add(-1, 0, 1))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 0, 1))
                    connections(2) = canTextureConnect(world, pos, pos.add(1, 0, 1))
                    connections(3) = canTextureConnect(world, pos, pos.add(-1, 0, 0))
                    connections(4) = canTextureConnect(world, pos, pos.add(1, 0, 0))
                    connections(5) = canTextureConnect(world, pos, pos.add(-1, 0, -1))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, 0, -1))
                    connections(7) = canTextureConnect(world, pos, pos.add(1, 0, -1))
                   return connections
                case EnumFacing.NORTH =>
                    connections(0) = canTextureConnect(world, pos, pos.add(1, 1, 0))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 1, 0))
                    connections(2) = canTextureConnect(world, pos, pos.add(-1, 1, 0))
                    connections(3) = canTextureConnect(world, pos, pos.add(1, 0, 0))
                    connections(4) = canTextureConnect(world, pos, pos.add(-1, 0, 0))
                    connections(5) = canTextureConnect(world, pos, pos.add(1, -1, 0))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, -1, 0))
                    connections(7) = canTextureConnect(world, pos, pos.add(-1, -1, 0))
                   return connections
                case EnumFacing.SOUTH =>
                    connections(0) = canTextureConnect(world, pos, pos.add(-1, 1, 0))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 1, 0))
                    connections(2) = canTextureConnect(world, pos, pos.add(1, 1, 0))
                    connections(3) = canTextureConnect(world, pos, pos.add(-1, 0, 0))
                    connections(4) = canTextureConnect(world, pos, pos.add(1, 0, 0))
                    connections(5) = canTextureConnect(world, pos, pos.add(-1, -1, 0))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, -1, 0))
                    connections(7) = canTextureConnect(world, pos, pos.add(1, -1, 0))
                   return connections
                case EnumFacing.WEST =>
                    connections(0) = canTextureConnect(world, pos, pos.add(0, 1, -1))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 1, 0))
                    connections(2) = canTextureConnect(world, pos, pos.add(0, 1, 1))
                    connections(3) = canTextureConnect(world, pos, pos.add(0, 0, -1))
                    connections(4) = canTextureConnect(world, pos, pos.add(0, 0, 1))
                    connections(5) = canTextureConnect(world, pos, pos.add(0, -1, -1))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, -1, 0))
                    connections(7) = canTextureConnect(world, pos, pos.add(0, -1, 1))
                   return connections
                case EnumFacing.EAST =>
                    connections(0) = canTextureConnect(world, pos, pos.add(0, 1, 1))
                    connections(1) = canTextureConnect(world, pos, pos.add(0, 1, 0))
                    connections(2) = canTextureConnect(world, pos, pos.add(0, 1, -1))
                    connections(3) = canTextureConnect(world, pos, pos.add(0, 0, 1))
                    connections(4) = canTextureConnect(world, pos, pos.add(0, 0, -1))
                    connections(5) = canTextureConnect(world, pos, pos.add(0, -1, 1))
                    connections(6) = canTextureConnect(world, pos, pos.add(0, -1, 0))
                    connections(7) = canTextureConnect(world, pos, pos.add(0, -1, -1))
                  return connections
                case _ => return connections
            }
        }
        connections
    }

    /**
      * Used to say what our block state is
      */
    override def createBlockState(): BlockStateContainer = {
        val listed: Array[IProperty[_]] = new Array(0)
        val unlisted = new Array[IUnlistedProperty[_]](0)
        new ExtendedBlockState(this, listed, unlisted)
    }

    override def getExtendedState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState = {
        new ConnectedTexturesState(pos, Minecraft.getMinecraft.theWorld,
            state.getBlock.asInstanceOf[BlockConnectedTextures], state.getBlock)
    }

    override def getRenderType(state: IBlockState) = EnumBlockRenderType.MODEL
    override def isOpaqueCube(state: IBlockState) = !isClear
    override def isTranslucent(state: IBlockState) = isClear
    override def isFullCube(state: IBlockState) = !isClear

    /**
      * Used to define what layer to render in
      */
    override def canRenderInLayer(layer: BlockRenderLayer) : Boolean =
        if(isClear)
            layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.TRANSLUCENT
        else
            layer == BlockRenderLayer.SOLID

    /**
      * Performs the action at the given event
      *
      * @param event The event being called from
      * @param isClient True if only on client side, false (default) for server side
      */
    def performLoadAction(@Nonnull event: AnyRef, isClient : Boolean = false) : Unit = {
        event match  {
            case modelBake : ModelBakeEvent =>
                modelBake.getModelRegistry.putObject(getNormal, new BakedConnectedTextures)
            case _ =>
        }
    }
}
