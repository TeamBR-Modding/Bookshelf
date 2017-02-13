package com.teambr.bookshelf.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

/**
 * This file was created for com.teambr.bookshelf.Bookshelf - Java
 * <p>
 * com.teambr.bookshelf.Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/10/2017
 */
public abstract class FluidHandler extends UpdatingTile implements IFluidHandler {

    // NBT Tags
    protected static final String SIZE_NBT_TAG    = "Size";
    protected static final String TANK_ID_NBT_TAG = "TankID";
    protected static final String TANKS_NBT_TAG   = "Tanks";

    // Tanks
    public FluidTank[] tanks;

    /**
     * Default constructor, calls the setupTanks method to setup the tanks
     */
    public FluidHandler() {
        setupTanks();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to set up the tanks needed. You can insert any number of tanks
     */
    protected abstract void setupTanks();

    /**
     * Which tanks can input
     *
     * @return An array with the indexes of the input tanks
     */
    protected abstract int[] getInputTanks();

    /**
     * Which tanks can output
     *
     * @return An array with the indexes of the output tanks
     */
    protected abstract int[] getOutputTanks();

    /*******************************************************************************************************************
     * FluidHandler                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Called when something happens to the tank, you should mark the block for update here if a tile
     */
    public void onTankChanged(FluidTank tank) {
        markForUpdate(3);
    }

    /**
     * Used to convert a number of buckets into MB
     *
     * @param buckets How many buckets
     * @return The amount of buckets in MB
     */
    public int bucketsToMB(int buckets) {
        return Fluid.BUCKET_VOLUME * buckets;
    }

    /**
     * Returns true if the given fluid can be inserted
     *
     * More formally, this should return true if fluid is able to enter
     */
    protected boolean canFill(Fluid fluid) {
        for(Integer x : getInputTanks()) {
            if(x < tanks.length)
                if((tanks[x].getFluid() == null || tanks[x].getFluid().getFluid() == null) ||
                        (tanks[x].getFluid() != null && tanks[x].getFluid().getFluid() == fluid))
                    return true;
        }
        return false;
    }

    /**
     * Returns true if the given fluid can be extracted
     *
     * More formally, this should return true if fluid is able to leave
     */
    protected boolean canDrain(Fluid fluid) {
        for(Integer x : getOutputTanks()) {
            if(x < tanks.length)
                if(tanks[x].getFluid() != null && tanks[x].getFluid().getFluid() != null)
                    return true;
        }
        return false;
    }

    /*******************************************************************************************************************
     * Tile Methods                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Used to save the object to an NBT tag
     *
     * @param compound The tag to save to
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        int id = 0;
        compound.setInteger(SIZE_NBT_TAG, tanks.length);
        NBTTagList tagList = new NBTTagList();
        for(FluidTank tank : tanks) {
            if(tank != null) {
                NBTTagCompound tankCompound = new NBTTagCompound();
                tankCompound.setByte(TANK_ID_NBT_TAG, (byte) id);
                id += 1;
                tank.writeToNBT(tankCompound);
                tagList.appendTag(tankCompound);
            }
        }
        compound.setTag(TANKS_NBT_TAG, tagList);
        return compound;
    }

    /**
     * Used to read from an NBT tag
     *
     * @param compound The tag to read from
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList(TANKS_NBT_TAG, 10);
        int size = compound.getInteger(SIZE_NBT_TAG);
        if(size != tanks.length && compound.hasKey(SIZE_NBT_TAG)) tanks = new FluidTank[size];
        for(int x = 0; x < tagList.tagCount(); x++) {
            NBTTagCompound tankCompound = tagList.getCompoundTagAt(x);
            byte position = tankCompound.getByte(TANK_ID_NBT_TAG);
            if(position < tanks.length)
                tanks[position].readFromNBT(tankCompound);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) this;
        return super.getCapability(capability, facing);
    }

    /*******************************************************************************************************************
     * IFluidHandler                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Returns an array of objects which represent the internal tanks.
     * These objects cannot be used to manipulate the internal tanks.
     *
     * @return Properties for the relevant internal tanks.
     */
    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] properties = new IFluidTankProperties[tanks.length];
        for(int x = 0; x < tanks.length; x++) {
            FluidTank tank = tanks[x];
            properties[x] = new IFluidTankProperties() {
                @Nullable
                @Override
                public FluidStack getContents() {
                    return tank.getFluid();
                }

                @Override
                public int getCapacity() {
                    return tank.getCapacity();
                }

                @Override
                public boolean canFill() {
                    return tank.canFill();
                }

                @Override
                public boolean canDrain() {
                    return tank.canDrain();
                }

                @Override
                public boolean canFillFluidType(FluidStack fluidStack) {
                    return tank.canFillFluidType(fluidStack);
                }

                @Override
                public boolean canDrainFluidType(FluidStack fluidStack) {
                    return tank.canDrainFluidType(fluidStack);
                }
            };
        }
        return properties;
    }

    /**
     * Fills fluid into internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be filled.
     * @param doFill   If false, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource != null && resource.getFluid() != null && canFill(resource.getFluid())) {
            for(Integer x : getInputTanks()) {
                if(x < tanks.length) {
                    if(tanks[x].fill(resource, false) > 0) {
                        int actual = tanks[x].fill(resource, doFill);
                        if(doFill) onTankChanged(tanks[x]);
                        return actual;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     * <p/>
     * This method is not Fluid-sensitive.
     *
     * @param maxDrain Maximum amount of fluid to drain.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        FluidStack fluidStack = null;
        for(Integer x : getOutputTanks()) {
            if(x < tanks.length) {
                fluidStack = tanks[x].drain(maxDrain, false);
                if(fluidStack != null) {
                    tanks[x].drain(maxDrain, doDrain);
                    if(doDrain) onTankChanged(tanks[x]);
                    return fluidStack;
                }
            }
        }
        return fluidStack;
    }

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be drained.
     * @param doDrain  If false, drain will only be simulated.
     * @return FluidStack representing the Fluid and amount that was (or would have been, if
     * simulated) drained.
     */
    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return drain(resource.amount, doDrain);
    }
}
