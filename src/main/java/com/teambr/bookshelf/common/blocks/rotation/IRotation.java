package com.teambr.bookshelf.common.blocks.rotation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRotation {

    /**
     * Get the direction that the block is facing from a metadata value
     * @param meta The metadata of the block
     * @return What direction the block is facing
     */
    public ForgeDirection convertMetaToDirection(int meta);

    /**
     * Get the metadata for the given direction
     * @param dir What direction the block is facing
     * @return The metadata for that direction
     */
    public int convertDirectionToMeta(ForgeDirection dir);

    /**
     * Used when the block is placed, it will calculate what meta should be put onto the block
     * @return The meta to write to the block
     */
    public int getMetaFromEntity(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack itemStack);
}
