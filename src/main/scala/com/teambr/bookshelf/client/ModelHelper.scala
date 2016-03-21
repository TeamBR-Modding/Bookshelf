package com.teambr.bookshelf.client

import javax.vecmath.Vector3f

import com.google.common.collect.ImmutableMap
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.SimpleModelState
import net.minecraftforge.common.model.{IModelState, TRSRTransformation}

/**
  * This file was created for NeoTech
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * Adapted from Tinker's Construct Model Helper https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/library/client/model/ModelHelper.java
  *
  * @author Paul Davis <pauljoda>
  * @since 2/22/2016
  */
object ModelHelper {

    lazy val DEFAULT_ITEM_STATE : IModelState = { //Normal items (not held as a tool)
    val thirdPerson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 3 / 16F, 1 / 16F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.55F, 0.55F, 0.55F),
            null))
        val firstPerson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(1.13F / 16F, 3.2F / 16F, 1.13F / 16F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 25)),
            new Vector3f(0.68F, 0.68F, 0.68F),
            null))
        val thirdPersonLeft = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 3 / 16F, 1 / 16F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.55F, 0.55F, 0.55F),
            null))
        val firstPersonLeft = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(1.13F / 16F, 3.2F / 16F, 1.13F / 16F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 25)),
            new Vector3f(0.68F, 0.68F, 0.68F),
            null))
        val ground = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 2F / 16F, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.5F, 0.5F, 0.5F),
            null))
        val head = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 13F / 16F, 7F / 16F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 180, 0)),
            new Vector3f(1,1, 1),
            null))
        new SimpleModelState(ImmutableMap.builder()
                .put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, thirdPerson)
                .put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, firstPerson)
                .put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, thirdPersonLeft)
                .put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, firstPersonLeft)
                .put(ItemCameraTransforms.TransformType.GROUND, ground)
                .put(ItemCameraTransforms.TransformType.HEAD, head).build())
    }

    lazy val DEFAULT_TOOL_STATE : IModelState = { //Tool items, held in hand
    val thirdPersonRight = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 4F / 16, 0.5F / 16),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 55)),
            new Vector3f(0.85F, 0.85F, 0.85F),
            null))
        val thirdPersonLeft = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 4F / 16, 0.5F / 16),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 90, -55)),
            new Vector3f(0.85F, 0.85F, 0.85F),
            null))
        val firstPersonRight = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(1.13F / 16F, 3.2F / 16, 1.13F / 16),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 25)),
            new Vector3f(0.68F, 0.68F, 0.68F),
            null))
        val firstPersonLeft = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(1.13F / 16F, 3.2F / 16, 1.13F / 16),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 90, -25)),
            new Vector3f(0.68F, 0.68F, 0.68F),
            null))
        val ground = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
            new Vector3f(0, 2F / 16F, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.5F, 0.5F, 0.5F),
            null))
        new SimpleModelState(ImmutableMap.of(
            ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, thirdPersonRight,
            ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, thirdPersonLeft,
            ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, firstPersonRight,
            ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, firstPersonLeft,
            ItemCameraTransforms.TransformType.GROUND, ground))
    }

    lazy val DEFAULT_BLOCK_STATE : IModelState = { //Normal block
    val gui = new TRSRTransformation(
            new Vector3f(0, 0, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(30, 225, 0)),
            new Vector3f(0.625F, 0.625F, 0.625F),
            null)
        val ground = new TRSRTransformation(
            new Vector3f(0, 3F / 16F, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.25F, 0.25F, 0.25F),
            null)
        val fixed = new TRSRTransformation(
            new Vector3f(0, 0, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 0, 0)),
            new Vector3f(0.5F, 0.5F, 0.5F),
            null)
        val thirdPersonRight = new TRSRTransformation(
            new Vector3f(0, 2.5F / 16F, 0F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(75, 45, 0)),
            new Vector3f(0.375F, 0.375F, 0.375F),
            null)
        val firstPersonRight = new TRSRTransformation(
            new Vector3f(0, 0, 0F),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 45, 0)),
            new Vector3f(0.4F, 0.4F, 0.4F),
            null)
        val firstPersonLeft = new TRSRTransformation(
            new Vector3f(0, 0, 0),
            TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 225, 0)),
            new Vector3f(0.4F, 0.4F, 0.4F),
            null)
        new SimpleModelState(ImmutableMap.builder()
                .put(ItemCameraTransforms.TransformType.GUI, gui)
                .put(ItemCameraTransforms.TransformType.GROUND, ground)
                .put(ItemCameraTransforms.TransformType.FIXED, fixed)
                .put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, thirdPersonRight)
                .put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, firstPersonRight)
                .put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, firstPersonLeft).build())
    }

    val textureGetter: com.google.common.base.Function[ResourceLocation, TextureAtlasSprite] =
        new com.google.common.base.Function[ResourceLocation, TextureAtlasSprite]() {
            def apply(location: ResourceLocation): TextureAtlasSprite = {
                Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(location.toString)
            }
        }

    /**
      * Gets the texture for the block with meta
      *
      * @param block The block
      * @param meta The state
      * @return The texture
      */
    def getTextureFromBlock(block : Block, meta : Int) : TextureAtlasSprite =
        getTextureFromBlockState(block.getStateFromMeta(meta))

    /**
      * Gets the texture by state
      *
      * @param state The state
      * @return The texture for the block
      */
    def getTextureFromBlockState(state : IBlockState) : TextureAtlasSprite =
        Minecraft.getMinecraft.getBlockRendererDispatcher.getBlockModelShapes.getTexture(state)
}