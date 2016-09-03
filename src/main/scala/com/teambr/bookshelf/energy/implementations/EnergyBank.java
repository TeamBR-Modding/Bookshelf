package com.teambr.bookshelf.energy.implementations;

import com.teambr.bookshelf.energy.IEnergyHolder;
import com.teambr.bookshelf.energy.IEnergyProvider;
import com.teambr.bookshelf.energy.IEnergyReceiver;
import com.teambr.bookshelf.traits.NBTSavable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This file was created for Lux et Umbra
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since 9/2/2016
 */
public class EnergyBank implements IEnergyHolder, IEnergyProvider, IEnergyReceiver, NBTSavable {

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
     * NBTSavable                                                                                                      *
     *******************************************************************************************************************/

    public static final String CURRENT_ENERGY = "CurrentEnergy";
    public static final String MAX_ENERGY     = "MaxEnergy";
    public static final String MAX_INSERT     = "MaxInsert";
    public static final String MAX_EXTRACT    = "MaxExtract";

    /**
     * Save to tag
     * @param tag The incoming tag
     * @return The written tag
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        // Write storage values
        tag.setInteger(CURRENT_ENERGY, currentStored);
        tag.setInteger(MAX_ENERGY,     maxStored);

        // IO values
        tag.setInteger(MAX_INSERT,     maxInsert);
        tag.setInteger(MAX_EXTRACT,    maxExtract);
        return tag;
    }

    /**
     * Read from the tag
     * @param tag The written tag
     */
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        // Read Storage Values
        currentStored = tag.getInteger(CURRENT_ENERGY);
        maxStored     = tag.getInteger(MAX_ENERGY);

        // Read IO values
        maxInsert     = tag.getInteger(MAX_INSERT);
        maxExtract   = tag.getInteger(MAX_EXTRACT);
    }
}
