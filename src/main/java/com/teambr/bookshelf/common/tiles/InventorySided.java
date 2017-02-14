package com.teambr.bookshelf.common.tiles;

import com.teambr.bookshelf.common.container.SidedInventoryWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public abstract class InventorySided extends InventoryHandler {

    // Side Handlers
    private IItemHandler handlerTop    = new SidedInventoryWrapper(this, EnumFacing.UP);
    private IItemHandler handlerDown   = new SidedInventoryWrapper(this, EnumFacing.DOWN);
    private IItemHandler handlerNorth  = new SidedInventoryWrapper(this, EnumFacing.NORTH);
    private IItemHandler handlerSouth  = new SidedInventoryWrapper(this, EnumFacing.SOUTH);
    private IItemHandler handlerEast   = new SidedInventoryWrapper(this, EnumFacing.EAST);
    private IItemHandler handlerWest   = new SidedInventoryWrapper(this, EnumFacing.WEST);

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Get the slots for the given face
     * @param face The face
     * @return What slots can be accessed
     */
    public abstract int[] getSlotsForFace(EnumFacing face);

    /**
     * Can insert the item into the inventory
     * @param slot The slot
     * @param itemStackIn The stack to insert
     * @param dir The dir
     * @return True if can insert
     */
    public abstract boolean canInsertItem(int slot, ItemStack itemStackIn, EnumFacing dir);

    /**
     * Can this extract the item
     * @param slot The slot
     * @param stack The stack
     * @param dir The dir
     * @return True if can extract
     */
    public abstract boolean canExtractItem(int slot, ItemStack stack, EnumFacing dir);

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (facing) {
                case UP :
                    return (T) handlerTop;
                case DOWN :
                    return (T) handlerDown;
                case NORTH :
                    return (T) handlerNorth;
                case SOUTH :
                    return (T) handlerSouth;
                case EAST :
                    return (T) handlerEast;
                case WEST :
                    return (T) handlerWest;
                default :
            }
        }
        return super.getCapability(capability, facing);
    }
}
