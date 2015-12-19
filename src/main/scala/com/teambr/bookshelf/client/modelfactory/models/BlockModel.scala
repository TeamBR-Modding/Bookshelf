package com.teambr.bookshelf.client.modelfactory.models

import java.util
import org.lwjgl.util.vector.Vector3f
import com.teambr.bookshelf.collections.CubeTextures
import com.teambr.bookshelf.common.blocks.traits.{BlockBakeable, FourWayRotation, SixWayRotation}
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model._
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.{IBakedModel, ModelResourceLocation, ModelRotation}
import net.minecraft.item.ItemStack
import net.minecraft.util.{EnumFacing, ResourceLocation}
import net.minecraftforge.client.model.{ISmartBlockModel, ISmartItemModel}

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

object BlockModel {
    // Get the default model resource location for a block state
    // Used to put an entry into the model registry
    def getModelResourceLocation(state: IBlockState): ModelResourceLocation = {
        new ModelResourceLocation(Block.blockRegistry.getNameForObject(state.getBlock).asInstanceOf[ResourceLocation], new DefaultStateMapper().getPropertyString(state.getProperties))
    }
}

class BlockModel extends ISmartBlockModel with ISmartItemModel {
    var textures = new CubeTextures
    val faceBakery = new FaceBakery
    var block: Block = _
    var modelRot = ModelRotation.X0_Y0

    /**
     * We are building based on state here, so lets rotate as needed
     * @param state The current state
     */
    def this(state: IBlockState) {
        this()
        block = state.getBlock

        //Check for rotation
        block match {
            case property: FourWayRotation =>
                modelRot = property.getModelRotation(state)
            case property: SixWayRotation =>
                modelRot = property.getModelRotation(state)
            case _ => //No spin for you
        }

        //Get our textures
        block match {
            case block : BlockBakeable =>
                textures = block.getDisplayTextures(state)
            case _ => println("Someone isn't using the renderer right. Stop that")
        }
    }

    def this(bakeable: BlockBakeable) {
        this()
        textures = bakeable.asInstanceOf[BlockBakeable].getDisplayTextures(bakeable.getDefaultState)
    }

    override def getFaceQuads(facing: EnumFacing): util.List[BakedQuad] = {
        if(modelRot == ModelRotation.X0_Y0)
            getFaceForSide(facing)
        else getRotatedQuads
    }

    override def getGeneralQuads : util.ArrayList[BakedQuad] = { new util.ArrayList[BakedQuad] }

    def getRotatedQuads : util.List[BakedQuad] = {
        val bakedQuads = new util.ArrayList[BakedQuad]()
        val uv = new BlockFaceUV(Array[Float](0.0F, 0.0F, 16.0F, 16.0F), 0)
        val face = new BlockPartFace(null, 0, "", uv)

        val scale = true

        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, textures.down, EnumFacing.DOWN, modelRot, null, scale, true))
        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.up, EnumFacing.UP, modelRot, null, scale, true))
        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, textures.north, EnumFacing.NORTH, modelRot, null, scale, true))
        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.south, EnumFacing.SOUTH, modelRot, null, scale, true))
        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.east, EnumFacing.EAST, modelRot, null, scale, true))
        bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, textures.west, EnumFacing.WEST, modelRot, null, scale, true))

        bakedQuads
    }

    //This will help those without model rotation, but if it does rotate, then just used the general quads. Its just easier
    private def getFaceForSide(facing : EnumFacing) : util.List[BakedQuad] = {
        val bakedQuads = new util.ArrayList[BakedQuad]()
        val uv = new BlockFaceUV(Array[Float](0.0F, 0.0F, 16.0F, 16.0F), 0)
        val face = new BlockPartFace(null, 0, "", uv)

        val scale = true

        facing match {
            case EnumFacing.UP =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.up, EnumFacing.UP, ModelRotation.X0_Y0, null, scale, true))
            case EnumFacing.DOWN =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, textures.down, EnumFacing.DOWN, ModelRotation.X0_Y0, null, scale, true))
            case EnumFacing.NORTH =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, textures.north, EnumFacing.NORTH, ModelRotation.X0_Y0, null, scale, true))
            case EnumFacing.SOUTH =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.south, EnumFacing.SOUTH, ModelRotation.X0_Y0, null, scale, true))
            case EnumFacing.EAST =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, textures.east, EnumFacing.EAST, ModelRotation.X0_Y0, null, scale, true))
            case EnumFacing.WEST =>
                bakedQuads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, textures.west, EnumFacing.WEST, ModelRotation.X0_Y0, null, scale, true))
            case _ => //What are you doing here. This should never be called but we will make this for safety
        }
        bakedQuads
    }

    override def isAmbientOcclusion: Boolean = true

    override def isGui3d: Boolean = true

    override def isBuiltInRenderer: Boolean = false

    /**
     * Now, forge is going to get mad at us and say don't use these. But you know what, I'm not writing a whole new model
     * just for this. So deal with it
     */
    val MovedUp = new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.05F, 0.05F, -0.15F), new Vector3f(-0.5F, -0.5F, -0.5F))

    override def getItemCameraTransforms: ItemCameraTransforms = {
        new ItemCameraTransforms(MovedUp, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT)
    }

    override def handleBlockState(state: IBlockState): IBakedModel = {
        new BlockModel(state)
    }

    override def handleItemState(stack: ItemStack): IBakedModel = {
        new BlockModel(Block.getBlockFromItem(stack.getItem).asInstanceOf[BlockBakeable])
    }

    override def getParticleTexture: TextureAtlasSprite = textures.north
}

