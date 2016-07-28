package com.teambr.bookshelf.client.models;

import com.google.common.collect.ImmutableList;
import com.teambr.bookshelf.client.ModelHelper;
import com.teambr.bookshelf.common.items.traits.ItemModelProvider;
import com.teambr.bookshelf.lib.Reference;
import gnu.trove.map.hash.THashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ItemLayerModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis "pauljoda"
 * @since 3/18/2016
 */
public class BakedDynItem implements IBakedModel, IPerspectiveAwareModel {

    // The model resource location, reflect items to this if you want it to use this model, no need to register the model itself
    public static final ModelResourceLocation MODEL_RESOURCE_LOCATION =
            new ModelResourceLocation(new ResourceLocation(Reference.MODID(), "dynItem"), "inventory");

    // Holds a list of cached models, prevents the need to keep making them over and over
    protected static final THashMap<List<String>, BakedDynItem> modelCache = new THashMap<>();

    // Holds the list of textures, order is important
    protected List<String> textureLocations;

    protected List<BakedQuad> quads = null;

    /**
     * Used to define if this is a tool
     */
    protected boolean isTool = false;

    /**
     * Stub method, for reference only
     */
    public BakedDynItem() {}

    /**
     * Generates a model with the given texture list
     * @param textures The list of textures
     */
    public BakedDynItem(List<String> textures) {
        this(textures, false);
    }

    /**
     * Generates a model with the given texture list
     * @param textures The list of textures
     */
    public BakedDynItem(List<String> textures, boolean tool) {
        this.textureLocations = textures;
        isTool = tool;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        if(quads == null || (textureLocations != null && quads.size() == 0)) {
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            for (String loc : textureLocations)
                builder.add(new ResourceLocation(loc));
            quads = (new ItemLayerModel(builder.build())).bake(ModelHelper.DEFAULT_ITEM_STATE(),
                    DefaultVertexFormats.ITEM, ModelHelper.textureGetter()).getQuads(state, side, rand);
        }
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(textureLocations.get(0));
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return BakedDynItemOverride.INSTANCE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {

        // Wrap the base and have it handle the movement
        return IPerspectiveAwareModel.MapWrapper
                .handlePerspective(
                        this,
                        isTool ? ModelHelper.DEFAULT_TOOL_STATE() : ModelHelper.DEFAULT_ITEM_STATE(),
                        cameraTransformType);
    }

    /**
     * Allows us to base things on the stack itself, kinda nice
     */
    public static final class BakedDynItemOverride extends ItemOverrideList {

        // The instance of this override, you should never need to make a new one
        public static final BakedDynItemOverride INSTANCE = new BakedDynItemOverride();

        /**
         * Constructor, resolved to private because you should never need to make a new one, use INSTANCE
         */
        private BakedDynItemOverride()
        {
            super(ImmutableList.<ItemOverride>of());
        }

        /**
         * Allows us to change the item model base on the stack data
         * @param originalModel The base model, probably won't be very useful to you
         * @param stack The stack
         * @param world The world
         * @param entity The entity
         * @return The new model to render, try to cache if at all possible
         */
        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {

            // Must be a model provider
            if(stack.getItem() instanceof ItemModelProvider) {

                // Get the list of textures to add
                final List<String> textureList = ((ItemModelProvider)stack.getItem()).getTextures(stack);

                // Check if already in the cache, don't need to remake it otherwise
                if(BakedDynItem.modelCache.containsKey(textureList))
                    return BakedDynItem.modelCache.get(textureList);
                else { // Not in cache, add and build new model
                    BakedDynItem.modelCache.put(textureList, new BakedDynItem(textureList, ((ItemModelProvider)stack.getItem()).isTool()));
                    return BakedDynItem.modelCache.getOrDefault(textureList, new BakedDynItem(textureList, ((ItemModelProvider)stack.getItem()).isTool()));
                }
            }

            // Couldn't find it
            return originalModel;
        }
    }
}
