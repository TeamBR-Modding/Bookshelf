package com.teambr.bookshelf.energy.implementations;

import com.teambr.bookshelf.energy.IEnergyHolder;
import com.teambr.bookshelf.energy.IEnergyProvider;
import com.teambr.bookshelf.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This file was created for Lux et Umbra
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since 9/2/2016
 */
public class EnergyBank implements IEnergyHolder, IEnergyProvider, IEnergyReceiver {

    protected int currentStored;
    protected int maxStored;

    protected int maxInsert;
    protected int maxExtract;

    /**
     * Simplest constructor of EnergyBank
     * @param size The max stored, also sets max in and out
     */
    public EnergyBank(int size) {
        this(0, size, size, size);
    }

    /**
     * Constructor for EnergyBank
     * @param size The max energy stored
     * @param maxIn The max energy in rate
     * @param maxOut The max energy out rate
     */
    public EnergyBank(int size, int maxIn, int maxOut) {
        this(0, size, maxIn, maxOut);
    }

    /**
     * The main constructor for EnergyBank
     * @param initialStored The initial value of the stored energy
     * @param maxStorage The max amount of energy stored
     * @param maxIn The max insert rate
     * @param maxOut The max extract rate
     */
    public EnergyBank(int initialStored, int maxStorage, int maxIn, int maxOut) {
        currentStored = initialStored;
        maxStored     = maxStorage;
        maxInsert     = maxIn;
        maxExtract    = maxOut;
    }

    /*******************************************************************************************************************
     * Getters and Setters                                                                                             *
     *******************************************************************************************************************/

    /**
     * Get the current energy stored
     * @return The current energy stored
     */
    public int getCurrentStored() {
        return currentStored;
    }

    /**
     * Set the current energy stored
     * @param currentStored The new current stored
     */
    public void setCurrentStored(int currentStored) {
        this.currentStored = currentStored;
    }

    /**
     * Get the max energy stored
     * @return The max energy stored
     */
    public int getMaxStored() {
        return maxStored;
    }

    /**
     * Set the max energy stored
     * @param maxStored The max energy stored
     */
    public void setMaxStored(int maxStored) {
        this.maxStored = maxStored;
    }

    /**
     * Get the max receive rate
     * @return The max receive rate
     */
    public int getMaxInsert() {
        return maxInsert;
    }

    /**
     * Set the max insert rate
     * @param maxInsert The max insert rate
     */
    public void setMaxInsert(int maxInsert) {
        this.maxInsert = maxInsert;
    }

    /**
     * Get the max extract rate
     * @return The max extract rate
     */
    public int getMaxExtract() {
        return maxExtract;
    }

    /**
     * Set the max extract
     * @param maxExtract The max extract
     */
    public void setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
    }

    /*******************************************************************************************************************
     * Energy Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Get current energy stored
     * @return The current energy stored
     */
    @Override
    public int getEnergyStored() {
        return currentStored;
    }

    /**
     * The max amount of energy this object can hold
     * @return The max energy stored, must be positive
     */
    @Override
    public int getMaxEnergyStored() {
        return maxStored;
    }

    /**
     * Allow the object to receive power
     * @param incomingPower The amount of power sent
     * @param doFill True to fill, false to simulate
     * @return The amount taken
     */
    @Override
    public int receivePower(int incomingPower, boolean doFill) {
        // Find most can accept
        int possibleInserted = Math.min(Math.min(maxInsert, incomingPower), maxStored - currentStored);

        // If actually filling...
        if(doFill)
            currentStored += possibleInserted;

        // Return what could/was filled
        return possibleInserted;
    }

    /**
     * The amount of power this object can provide
     * @param maxOut The max amount to extract
     * @param doDrain True to drain, false to simulate
     * @return The amount drained from internal storage (successful sent)
     */
    @Override
    public int providePower(int maxOut, boolean doDrain) {
        // Find most can extract
        int possibleExtract = Math.min(Math.min(this.maxExtract, maxOut), currentStored);

        // If actually draining...
        if(doDrain)
            currentStored -= possibleExtract;

        // Return what could/was drained
        return possibleExtract;
    }



    /*******************************************************************************************************************
     * NBT                                                                                                             *
     *******************************************************************************************************************/

    public static final String ENERGY_NBT_TAG             = "Energy";
    public static final String ENERGY_CAPACITY_NBT_TAG    = "EnergyCapacity";
    public static final String ENERGY_MAX_RECIEVE_NBT_TAG = "MaxRecieve";
    public static final String ENERGY_MAX_EXTRACT_NBT_TAG = "MaxExtract";

    /**
     * Save to tag
     * @param tag The incoming tag
     * @return The written tag
     */
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        // Write storage values
        tag.setInteger(ENERGY_NBT_TAG, currentStored);
        tag.setInteger(ENERGY_CAPACITY_NBT_TAG,     maxStored);

        // IO values
        tag.setInteger(ENERGY_MAX_RECIEVE_NBT_TAG,     maxInsert);
        tag.setInteger(ENERGY_MAX_EXTRACT_NBT_TAG,     maxExtract);
        return tag;
    }

    /**
     * Read from the tag
     * @param tag The written tag
     */
    public void readFromNBT(NBTTagCompound tag) {
        // Read Storage Values
        currentStored = tag.getInteger(ENERGY_NBT_TAG);
        maxStored     = tag.getInteger(ENERGY_CAPACITY_NBT_TAG);

        // Read IO values
        maxInsert     = tag.getInteger(ENERGY_MAX_RECIEVE_NBT_TAG);
        maxExtract    = tag.getInteger(ENERGY_MAX_EXTRACT_NBT_TAG);
    }
}