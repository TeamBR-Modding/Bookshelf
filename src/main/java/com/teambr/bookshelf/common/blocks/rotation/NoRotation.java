package com.teambr.bookshelf.common.blocks.rotation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NoRotation implements IRotation {
    @Override
    public ForgeDirection convertMetaToDirection(int meta) {
        return ForgeDirection.UNKNOWN;
    }

    @Override
    public int convertDirectionToMeta(ForgeDirection dir) {
        return 0;
    }

    @Override
    public int getMetaFromEntity(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack itemStack) {
        return 0;
    }
}
