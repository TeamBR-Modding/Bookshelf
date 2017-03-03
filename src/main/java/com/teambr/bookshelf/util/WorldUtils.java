package com.teambr.bookshelf.util;

import com.teambr.bookshelf.common.blocks.IToolable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/7/2017
 */
public class WorldUtils {

    /*******************************************************************************************************************
     * World Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Returns the direction to the left of this. This is the direction it is facing turned left
     *
     * @param toTurn Starting point
     * @return The direction turned 90 left
     */
    public static EnumFacing rotateLeft(EnumFacing toTurn) {
        switch (toTurn) {
            case NORTH :
                return EnumFacing.WEST;
            case EAST:
                return EnumFacing.NORTH;
            case SOUTH:
                return EnumFacing.EAST;
            case WEST:
                return EnumFacing.SOUTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /**
     * Returns the direction to the right of this. This is the direction it is facing turned right
     *
     * @param toTurn Starting point
     * @return The direction turned 90 right
     */
    public static EnumFacing rotateRight(EnumFacing toTurn) {
        switch (toTurn) {
            case NORTH :
                return EnumFacing.EAST;
            case EAST:
                return EnumFacing.SOUTH;
            case SOUTH:
                return EnumFacing.WEST;
            case WEST:
                return EnumFacing.NORTH;
            case UP: // No rotation on y axis
            case DOWN:
            default :
                return toTurn;
        }
    }

    /*******************************************************************************************************************
     * Spawning Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Drops and Array of ItemStacks into the world
     *
     * @param world Instance of ``World
     * @param stacks ItemStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStacks(World world, List<ItemStack> stacks, BlockPos pos) {
        stacks.forEach((ItemStack stack) -> {
            dropStack(world, stack, pos);
        });
    }

    /**
     * Drops a ItemStack into the world
     *
     * @param world Instance of ``World
     * @param stack temStack Array to drop into the world
     * @param pos BlockPos to drop them from
     */
    public static void dropStack(World world, ItemStack stack, BlockPos pos) {
        if(stack != null && stack.getCount() > 0) {
            float rx = world.rand.nextFloat() * 0.8F;
            float ry = world.rand.nextFloat() * 0.8F;
            float rz = world.rand.nextFloat() * 0.8F;

            EntityItem itemEntity = new EntityItem(world,
                    pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                    stack.copy());

            float factor = 0.05F;

            itemEntity.motionX = world.rand.nextGaussian() * factor;
            itemEntity.motionY = world.rand.nextGaussian() * factor + 0.2F;
            itemEntity.motionZ = world.rand.nextGaussian() * factor;
            world.spawnEntity(itemEntity);

            stack.setCount(0);
        }
    }

    /**
     * Helper method to drop items in an inventory, used on break mostly
     * @param itemHandler The itemhandler
     * @param world The world
     * @param pos The block pos
     */
    public static void dropStacksInInventory(IItemHandler itemHandler, World world, BlockPos pos) {
        for(int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack stack = itemHandler.getStackInSlot(slot);
            if(stack != null)
                dropStack(world, stack, pos);
        }
    }

    /*******************************************************************************************************************
     * IToolable Helpers                                                                                               *
     *******************************************************************************************************************/

    /**
     * Breaks the block and saves the NBT to the tag, calls getStackDropped to drop (just item)
     * @param world The world
     * @param pos The block pos
     * @param block The block object
     * @return True if successful
     */
    public static boolean breakBlockSavingNBT(World world, BlockPos pos, @Nonnull IToolable block) {
        if(world.isRemote) return false;
        if(world.getTileEntity(pos) != null) {
            TileEntity savableTile = world.getTileEntity(pos);
            NBTTagCompound tag = savableTile.writeToNBT(new NBTTagCompound());
            ItemStack stack = block.getStackDroppedByWrench(world, pos);
            stack.setTagCompound(tag);
            dropStack(world, stack, pos);
            world.removeTileEntity(pos); // Cancel drop logic
            world.setBlockToAir(pos);
            return true;
        }
        return false;
    }

    /**
     * Call this after onBlockPlacedBy to write saved data to the stack if present
     * @param world The world
     * @param pos The block position
     * @param stack The stack that had the tag
     */
    public static void writeStackNBTToBlock(World world, BlockPos pos, ItemStack stack) {
        if(stack.hasTagCompound()) {
            if(world.getTileEntity(pos) != null) {
                TileEntity tile = world.getTileEntity(pos);
                NBTTagCompound tag = stack.getTagCompound();
                tag.setInteger("x", pos.getX()); // Add back MC tags
                tag.setInteger("y", pos.getY());
                tag.setInteger("z", pos.getZ());
                tile.readFromNBT(tag);
            }
        }
    }
}
