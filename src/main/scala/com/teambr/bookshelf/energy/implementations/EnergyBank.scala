package com.teambr.bookshelf.energy.implementations

import com.teambr.bookshelf.common.tiles.traits.Syncable
import com.teambr.bookshelf.energy.IEnergyHolder
import com.teambr.bookshelf.energy.IEnergyProvider
import com.teambr.bookshelf.energy.IEnergyReceiver
import com.teambr.bookshelf.traits.NBTSavable
import net.minecraft.nbt.NBTTagCompound

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
abstract class EnergyBank extends Syncable with IEnergyHolder with IEnergyProvider with IEnergyReceiver with NBTSavable {

    var currentStored : Int
    var maxStored : Int

    var maxInsert : Int
    var maxExtract : Int

    /**
     * Simplest constructor of EnergyBank
     * @param size The max stored, also sets max in and out
     */
    def EnergyBank(size : Int) {
        EnergyBank(0, size, size, size)
    }

    /**
     * Constructor for EnergyBank
     * @param size The max energy stored
     * @param maxIn The max energy in rate
     * @param maxOut The max energy out rate
     */
    def EnergyBank(size : Int, maxIn : Int, maxOut : Int) {
        EnergyBank(0, size, maxIn, maxOut)
    }

    /**
     * The main constructor for EnergyBank
     * @param initialStored The initial value of the stored energy
     * @param maxStorage The max amount of energy stored
     * @param maxIn The max insert rate
     * @param maxOut The max extract rate
     */
    def EnergyBank(initialStored : Int, maxStorage : Int, maxIn : Int, maxOut : Int) {
        currentStored = initialStored
        maxStored     = maxStorage
        maxInsert     = maxIn
        maxExtract    = maxOut
    }

    /*******************************************************************************************************************
     * Getters and Setters                                                                                             *
     *******************************************************************************************************************/

    /**
     * Get the current energy stored
     * @return The current energy stored
     */
    def getCurrentStored: Int =
        currentStored

    /**
     * Set the current energy stored
     * @param currentStored The new current stored
     */
    def setCurrentStored(currentStored : Int) =
        this.currentStored = currentStored

    /**
     * Get the max energy stored
     * @return The max energy stored
     */
    def getMaxStored: Int =
         maxStored

    /**
     * Set the max energy stored
     * @param maxStored The max energy stored
     */
    def setMaxStored(maxStored : Int) =
        this.maxStored = maxStored

    /**
     * Get the max receive rate
     * @return The max receive rate
     */
    def getMaxInsert: Int = {
        maxInsert
    }

    /**
     * Set the max insert rate
     * @param maxInsert The max insert rate
     */
    def setMaxInsert(maxInsert : Int) {
        this.maxInsert = maxInsert
    }

    /**
     * Get the max extract rate
     * @return The max extract rate
     */
    def getMaxExtract: Int = {
        maxExtract
    }

    /**
     * Set the max extract
     * @param maxExtract The max extract
     */
    def setMaxExtract(maxExtract : Int) =
        this.maxExtract = maxExtract

    /*******************************************************************************************************************
     * Energy Methods                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Get current energy stored
     * @return The current energy stored
     */
    override def getEnergyStored: Int =
        currentStored


    /**
     * The max amount of energy this object can hold
     * @return The max energy stored, must be positive
     */
    override def getMaxEnergyStored: Int =
        maxStored


    /**
     * Allow the object to receive power
     * @param incomingPower The amount of power sent
     * @param doFill True to fill, false to simulate
     * @return The amount taken
     */
    override def receivePower(incomingPower : Int, doFill : Boolean) : Int = {
        // Find most can accept
        var possibleInserted = Math.min(Math.min(maxInsert, incomingPower), maxStored - currentStored)

        // If actually filling...
        if(doFill)
            currentStored += possibleInserted

        // Return what could/was filled
        possibleInserted
    }

    /**
     * The amount of power this object can provide
     * @param maxOut The max amount to extract
     * @param doDrain True to drain, false to simulate
     * @return The amount drained from internal storage (successful sent)
     */
    override def providePower(maxOut : Int, doDrain : Boolean) : Int = {
        // Find most can extract
        var possibleExtract = Math.min(Math.min(this.maxExtract, maxOut), currentStored)

        // If actually draining...
        if(doDrain)
            currentStored -= possibleExtract

        // Return what could/was drained
        possibleExtract
    }



    /*******************************************************************************************************************
     * NBTSavable                                                                                                      *
     *******************************************************************************************************************/

    lazy val  CURRENT_ENERGY = "CurrentEnergy"
    lazy val  MAX_ENERGY     = "MaxEnergy"
    lazy val  MAX_INSERT     = "MaxInsert"
    lazy val  MAX_EXTRACT    = "MaxExtract"

    /**
     * Save to tag
     * @param tag The incoming tag
     * @return The written tag
     */
    override def writeToNBT(tag : NBTTagCompound) : NBTTagCompound = {
        // Write storage values
        tag.setInteger(CURRENT_ENERGY, currentStored)
        tag.setInteger(MAX_ENERGY,     maxStored)

        // IO values
        tag.setInteger(MAX_INSERT,     maxInsert)
        tag.setInteger(MAX_EXTRACT,    maxExtract)
        tag
    }

    /**
     * Read from the tag
     * @param tag The written tag
     */
   override def readFromNBT(tag : NBTTagCompound) : Unit =  {
        // Read Storage Values
        currentStored = tag.getInteger(CURRENT_ENERGY)
        maxStored     = tag.getInteger(MAX_ENERGY)

        // Read IO values
        maxInsert     = tag.getInteger(MAX_INSERT)
        maxExtract   = tag.getInteger(MAX_EXTRACT)
    }
}
