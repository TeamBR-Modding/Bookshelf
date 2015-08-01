package com.teambr.bookshelf.common.blocks.rotation;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Bookshelf
 * Created by Paul Davis on 7/27/2015
 */
public class SixWayRotation implements IRotation {
    @Override
    public ForgeDirection convertMetaToDirection(int meta) {
        switch (meta) {
            case 0 :
                return ForgeDirection.DOWN;
            case 1 :
                return ForgeDirection.UP;
            case 2 :
                return ForgeDirection.NORTH;
            case 3 :
                return ForgeDirection.SOUTH;
            case 4 :
                return ForgeDirection.WEST;
            case 5 :
                return ForgeDirection.EAST;
            default :
                return ForgeDirection.UNKNOWN;
        }
    }

    @Override
    public int convertDirectionToMeta(ForgeDirection dir) {
        switch (dir) {
            case NORTH :
                return 2;
            case EAST :
                return 5;
            case SOUTH :
                return 3;
            case WEST :
                return 4;
            case UP :
                return 1;
            case DOWN :
                return 0;
            default :
                return 0;
        }
    }

    @Override
    public int getMetaFromEntity(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
       /**
        * 0: Down
        * 1: Up
        * 2: north
        * 3: south
        * 4: west
        * 5: east
        */
        return BlockPistonBase.determineOrientation(world, x, y, z, entity);
    }
}
