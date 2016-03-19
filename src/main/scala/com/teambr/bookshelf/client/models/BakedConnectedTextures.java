package com.teambr.bookshelf.client.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.teambr.bookshelf.client.ModelHelper;
import com.teambr.bookshelf.collections.ConnectedTextures;
import com.teambr.bookshelf.common.blocks.BlockConnectedTextures;
import com.teambr.bookshelf.common.blocks.ConnectedTexturesState;
import com.teambr.bookshelf.common.blocks.properties.Properties;
import com.teambr.bookshelf.lib.Reference;
import gnu.trove.map.hash.THashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class BakedConnectedTextures implements IBakedModel, IPerspectiveAwareModel {

    // The model resource location, reflect items to this if you want it to use this model, no need to register the model itself
    public static final ModelResourceLocation MODEL_RESOURCE_LOCATION_NORMAL =
            new ModelResourceLocation(new ResourceLocation(Reference.MODID(), "connectedTextures"), "normal");

    // The face bakery
    protected static final FaceBakery faceBakery = new FaceBakery();

    // Cache for item models
    public static final THashMap<TextureAtlasSprite, BakedConnectedTextures> modelCache = new THashMap<>();

    private static TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return new TRSRTransformation(
                new javax.vecmath.Vector3f(tx / 16, ty / 16, tz / 16),
                TRSRTransformation.quatFromXYZDegrees(new javax.vecmath.Vector3f(ax, ay, az)),
                new javax.vecmath.Vector3f(s, s, s),
                null);
    }

    private static Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ImmutableMap.<ItemCameraTransforms.TransformType, TRSRTransformation>builder()
            .put(ItemCameraTransforms.TransformType.GUI,                         get(0, 0, 0, 30, 45, 0, 0.625f))
            .put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,     get(0, 2.5f, 0, 75, 45, 0, 0.375f))
            .put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND,      get(0, 2.5f, 0, 75, 45, 0, 0.375f))
            .put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND,     get(0, 0, 0, 0, 45, 0, 0.4f))
            .put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND,      get(0, 0, 0, 0, 225, 0, 0.4f))
            .put(ItemCameraTransforms.TransformType.GROUND,                      get(0, 2, 0, 0, 0, 0, 0.25f))
            .put(ItemCameraTransforms.TransformType.HEAD,                        get(0, 0, 0, 0, 0, 0, 1))
            .put(ItemCameraTransforms.TransformType.FIXED,                       get(0, 0, 0, 0, 0, 0, 1))
            .build();

    // Variables needed
    protected BlockConnectedTextures block;
    protected IBlockAccess world;
    protected BlockPos pos;
    public boolean isItem = false;

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return Pair.of(this, transforms.get(cameraTransformType).getMatrix());
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        if(!isItem && side != null && state instanceof ConnectedTexturesState) {

            ConnectedTexturesState newState = (ConnectedTexturesState)state;

            // Get the relevant things we need out
            world = newState.world;
            pos = newState.pos;
            block = newState.block;

            // Check for ourselves
            if(world.getBlockState(pos.offset(side)).getBlock() == block)
                return new ArrayList<>();

            // Create the new list
            ArrayList<BakedQuad> quads = new ArrayList<>();

            // Reference values
            ModelRotation rot = lookUpRotationForFace(side);
            boolean[] connections = block.getConnectionArrayForFace(world, pos, side);

            quads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(8.0F, 8.0F, 16.0F),
                    new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] {0.0F, 8.0F, 8.0F, 0.0F}, 0)),
                    block.getConnectedTextures().getTextureForCorner(2, connections), EnumFacing.SOUTH, rot, null, true, true));

            quads.add(faceBakery.makeBakedQuad(new Vector3f(8.0F, 0.0F, 16.0F), new Vector3f(16.0F, 8.0F, 16.0F),
                    new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] {8.0F, 8.0F, 16.0F, 0.0F}, 0)),
                    block.getConnectedTextures().getTextureForCorner(3, connections), EnumFacing.SOUTH, rot, null, true, true));


            quads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 8.0F, 16.0F), new Vector3f(8.0F, 16.0F, 16.0F),
                    new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] {0.0F, 16.0F, 8.0F, 8.0F}, 0)),
                    block.getConnectedTextures().getTextureForCorner(0, connections), EnumFacing.SOUTH, rot, null, true, true));


            quads.add(faceBakery.makeBakedQuad(new Vector3f(8.0F, 8.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F),
                    new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] {8.0F, 16.0F, 16.0F, 8.0F}, 0)),
                    block.getConnectedTextures().getTextureForCorner(1, connections), EnumFacing.SOUTH, rot, null, true, true));


            return quads;
        } else if(block != null && side != null) {

            // Create the new list
            ArrayList<BakedQuad> quads = new ArrayList<>();

            // Reference values
            BlockPartFace face = new BlockPartFace(null, 0, "",
                    new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0));
            ModelRotation rot = lookUpRotationForFace(EnumFacing.SOUTH);

            for(EnumFacing facing : EnumFacing.values())
                quads.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, block.getConnectedTextures().corners(), facing, rot, null, true, true));

            return quads;
        }

        return new ArrayList<>();
    }

    private ModelRotation lookUpRotationForFace(EnumFacing face) {
        switch(face) {
            case UP :
                return ModelRotation.X90_Y0;
            case DOWN :
                return ModelRotation.X270_Y0;
            case NORTH :
                return ModelRotation.X0_Y180;
            case EAST :
                return ModelRotation.X0_Y270;
            case SOUTH :
                return ModelRotation.X0_Y0;
            case WEST :
                return ModelRotation.X0_Y90;
            default :
                return ModelRotation.X0_Y0;
        }
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return block == null ? Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite() : block.getConnectedTextures().corners();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return BakedConnectedTexturesOverride.INSTANCE;
    }

    /**
     * Allows us to base things on the stack itself, kinda nice
     */
    public static final class BakedConnectedTexturesOverride extends ItemOverrideList {

        // The instance of this override, you should never need to make a new one
        public static final BakedConnectedTexturesOverride INSTANCE = new BakedConnectedTexturesOverride();

        /**
         * Constructor, resolved to private because you should never need to make a new one, use INSTANCE
         */
        private BakedConnectedTexturesOverride()
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
            if(Block.getBlockFromItem(stack.getItem()) instanceof BlockConnectedTextures) {

                // Get the list of textures to add
                final BlockConnectedTextures block = (BlockConnectedTextures)Block.getBlockFromItem(stack.getItem());

                // Check if already in the cache, don't need to remake it otherwise
                if(BakedConnectedTextures.modelCache.containsKey(block.getConnectedTextures().noConnections()))
                    return BakedConnectedTextures.modelCache.get(block.getConnectedTextures().noConnections());
                else { // Not in cache, add and build new model
                    BakedConnectedTextures model = new BakedConnectedTextures();
                    model.isItem = true;
                    model.block = block;
                    BakedConnectedTextures.modelCache.put(block.getConnectedTextures().noConnections(), model);
                    return BakedConnectedTextures.modelCache.getOrDefault(block.getConnectedTextures().noConnections(), model);
                }
            }

            // Couldn't find it
            return originalModel;
        }
    }
}
