package com.teambr.bookshelf.client.models

import javax.vecmath.Matrix4f

import com.google.common.base.Function
import com.google.common.collect.{ImmutableList, ImmutableMap, Maps}
import com.teambr.bookshelf.client.ModelHelper
import com.teambr.bookshelf.common.items.traits.SimpleItemModelProvider
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.IPerspectiveAwareModel.MapWrapper
import net.minecraftforge.client.model._
import org.apache.commons.lang3.tuple.Pair

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 3/8/2016
  */
class SimpleItemModel(parent : IFlexibleBakedModel) extends
        ItemLayerModel.BakedModel(parent.getGeneralQuads.asInstanceOf[ImmutableList[BakedQuad]], parent.getParticleTexture,
            parent.getFormat) with ISmartItemModel with IPerspectiveAwareModel {

    lazy val builder = Maps.newHashMap[TransformType, TRSRTransformation]()
    builder.putAll(IPerspectiveAwareModel.MapWrapper.getTransforms(ModelHelper.DEFAULT_TOOL_STATE))

    lazy val transform = ImmutableMap.copyOf[TransformType, TRSRTransformation](builder)

    lazy  val function = new Function[ResourceLocation, TextureAtlasSprite] {
        override def apply(input: ResourceLocation): TextureAtlasSprite =
            Minecraft.getMinecraft.getTextureMapBlocks.getAtlasSprite(input.toString)
    }

    override def handleItemState(stack: ItemStack): IBakedModel = {
        if(stack == null || !stack.getItem.isInstanceOf[SimpleItemModelProvider]) {
            parent
        } else {
            val textureBuilder = ImmutableMap.builder[String, String]()
            val textures = stack.getItem.asInstanceOf[SimpleItemModelProvider].getTextures(stack)
            for(i <- textures.indices) {
                textureBuilder.put("layer" + i, textures(i))
            }

            val model = ItemLayerModel.instance.retexture(textureBuilder.build())
            model.bake(ModelHelper.DEFAULT_ITEM_STATE, parent.getFormat, function)
        }

    }

    override def handlePerspective(cameraTransformType: TransformType): Pair[_ <: IFlexibleBakedModel, Matrix4f] =
        MapWrapper.handlePerspective(this, transform, cameraTransformType)}
