package com.teambr.bookshelf.util;

import net.minecraftforge.common.util.ForgeDirection;

public class WorldUtils {

    /**
     * Returns the direction to the left of this. This is the direction it is facing turned left
     * @param toTurn Starting point
     * @return The direction turned 90 left
     */
    public static ForgeDirection rotateLeft(ForgeDirection toTurn) {
        switch(toTurn) {
            case NORTH :
                return ForgeDirection.WEST;
            case EAST :
                return ForgeDirection.NORTH;
            case SOUTH :
                return ForgeDirection.EAST;
            case WEST :
                return ForgeDirection.SOUTH;
            case UP :
            case DOWN :
            default :
                return toTurn;
        }
    }

    /**
     * Returns the direction to the right of this. This is the direction it is facing turned right
     * @param toTurn Starting point
     * @return The direction turned 90 right
     */
    public static ForgeDirection rotateRight(ForgeDirection toTurn) {
        switch(toTurn) {
            case NORTH :
                return ForgeDirection.EAST;
            case EAST :
                return ForgeDirection.SOUTH;
            case SOUTH :
                return ForgeDirection.WEST;
            case WEST :
                return ForgeDirection.NORTH;
            case UP :
            case DOWN :
            default :
                return toTurn;
        }
    }
}
