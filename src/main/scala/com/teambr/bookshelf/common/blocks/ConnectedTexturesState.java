package com.teambr.bookshelf.common.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collection;
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
public class ConnectedTexturesState implements IBlockState {
    public BlockPos pos;
    public IBlockAccess world;
    public BlockConnectedTextures block;
    public Block holder;

    public ConnectedTexturesState(BlockPos p, IBlockAccess w, BlockConnectedTextures b, Block h) {
        pos = p;
        world = w;
        block = b;
        holder = h;
    }

    @Override
    public Collection<IProperty<?>> getPropertyKeys() {
        return null;
    }

    @Override
    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        return null;
    }

    @Override
    public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
        return null;
    }

    @Override
    public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property) {
        return null;
    }

    @Override
    public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
        return null;
    }

    @Override
    public Block getBlock() {
        return holder;
    }

    @Override
    public Material getMaterial() {
        return holder.getMaterial(this);
    }

    @Override
    public boolean isFullBlock() {
        return holder.isFullBlock(this);
    }

    @Override
    public boolean canEntitySpawn(Entity entityIn) {
        return false;
    }

    @Override
    public int getLightOpacity() {
        return holder.getLightOpacity(this);
    }

    @Override
    public int getLightOpacity(IBlockAccess world, BlockPos pos) {
        return holder.getLightOpacity(this, world, pos);
    }

    @Override
    public int getLightValue() {
        return holder.getLightValue(this);
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos) {
        return holder.getLightValue(this, world, pos);
    }

    @Override
    public boolean isTranslucent() {
        return holder.isTranslucent(this);
    }

    @Override
    public boolean useNeighborBrightness() {
        return holder.getUseNeighborBrightness(this);
    }

    @Override
    public MapColor getMapColor() {
        return holder.getMapColor(this);
    }

    @Override
    public IBlockState withRotation(Rotation rot) {
        return holder.withRotation(this, rot);
    }

    @Override
    public IBlockState withMirror(Mirror mirrorIn) {
        return holder.withMirror(this, mirrorIn);
    }

    @Override
    public boolean isFullCube() {
        return holder.isFullCube(this);
    }

    @Override
    public EnumBlockRenderType getRenderType() {
        return holder.getRenderType(this);
    }

    @Override
    public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
        return holder.getPackedLightmapCoords(this, source, pos);
    }

    @Override
    public float getAmbientOcclusionLightValue() {
        return holder.getAmbientOcclusionLightValue(this);
    }

    @Override
    public boolean isBlockNormalCube() {
        return holder.isBlockNormalCube(this);
    }

    @Override
    public boolean isNormalCube() {
        return holder.isNormalCube(this);
    }

    @Override
    public boolean canProvidePower() {
        return holder.canProvidePower(this);
    }

    @Override
    public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return holder.getWeakPower(this, blockAccess, pos, side);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return holder.hasComparatorInputOverride(this);
    }

    @Override
    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
        return holder.getComparatorInputOverride(this, worldIn, pos);
    }

    @Override
    public float getBlockHardness(World worldIn, BlockPos pos) {
        return holder.getBlockHardness(this, worldIn, pos);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos) {
        return holder.getPlayerRelativeBlockHardness(this, player, worldIn, pos);
    }

    @Override
    public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return holder.getStrongPower(this, blockAccess, pos, side);
    }

    @Override
    public EnumPushReaction getMobilityFlag() {
        return holder.getMobilityFlag(this);
    }

    @Override
    public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos) {
        return holder.getActualState(this, blockAccess, pos);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos) {
        return holder.getCollisionBoundingBox(this, worldIn, pos);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
        return holder.shouldSideBeRendered(this, blockAccess, pos, facing);
    }

    @Override
    public boolean isOpaqueCube() {
        return holder.isOpaqueCube(this);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
        return holder.getSelectedBoundingBox(this, worldIn, pos);
    }

    @Override
    public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List<AxisAlignedBB> p_185908_4_, Entity p_185908_5_) {
        holder.addCollisionBoxToList(this, worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
        return holder.getBoundingBox(this, blockAccess, pos);
    }

    @Override
    public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return holder.collisionRayTrace(this, worldIn, pos, start, end);
    }

    @Override
    public boolean isFullyOpaque() {
        return holder.isFullyOpaque(this);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return holder.doesSideBlockRendering(this, world, pos, side);
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return holder.isSideSolid(this, world, pos, side);
    }

    @Override
    public boolean onBlockEventReceived(World world, BlockPos blockPos, int i, int i1) {
        return false;
    }

    @Override
    public void neighborChanged(World world, BlockPos blockPos, Block block) {

    }
}
