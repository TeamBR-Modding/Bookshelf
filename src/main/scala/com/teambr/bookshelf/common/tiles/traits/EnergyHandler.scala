package com.teambr.bookshelf.common.tiles.traits

import cofh.api.energy.{IEnergyHandler, IEnergyProvider, IEnergyReceiver}
import com.teambr.bookshelf.energy.implementations.EnergyBank
import com.teambr.bookshelf.manager.ConfigManager
import ic2.api.tile.IEnergyStorage
import net.darkhax.tesla.api.{ITeslaConsumer, ITeslaHolder, ITeslaProducer}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.common.Optional

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 2/15/2016
  */
@Optional.InterfaceList(Array(
    new Optional.Interface(iface = "ic2.api.tile.IEnergyStorage", modid = "IC2"),
    new Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = "tesla"),
    new Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = "tesla"),
    new Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = "tesla")

))
trait EnergyHandler extends EnergyBank
        with IEnergyHandler with IEnergyReceiver with IEnergyProvider
        with IEnergyStorage
        with ITeslaHolder with ITeslaConsumer with ITeslaProducer {

    /**
      * Used to define the default energy storage for this energy handler
      *
      * @return
      */
    def defaultEnergyStorageSize : Int

    /**
      * Return true if you want this to be able to provide energy
      *
      * @return
      */
    def isProvider : Boolean

    /**
      * Return true if you want this to be able to receive energy
      *
      * @return
      */
    def isReceiver : Boolean

    /*******************************************************************************************************************
      * Tile Methods                                                                                                   *
      ******************************************************************************************************************/

    /**
      * Write to the tag
      * @param tag The tag to write to
      */
    override def writeToNBT(tag: NBTTagCompound) : NBTTagCompound = {
        super[EnergyBank].writeToNBT(tag)
        tag
    }

    /**
      * Read data from tag
      * @param tag The tag to read from
      */
    override def readFromNBT(tag : NBTTagCompound) = {
        super[EnergyBank].readFromNBT(tag)

        // Checks for bad tags
        if(maxStored == 0)
            maxStored = defaultEnergyStorageSize

        if(maxInsert == 0)
            maxInsert = defaultEnergyStorageSize

        if(maxExtract == 0)
            maxExtract = defaultEnergyStorageSize

    }

    /*******************************************************************************************************************
      * RF Compatibility                                                                                               *
      ******************************************************************************************************************/
    /**
      * Returns the amount of energy currently stored.
      */
    override def getEnergyStored(from: EnumFacing): Int = getEnergyStored

    /**
      * Returns the maximum amount of energy that can be stored.
      */
    override def getMaxEnergyStored(from: EnumFacing): Int = getMaxStored

    /**
      * Add energy to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
      *
      * @param from Orientation the energy is received from.
      * @param maxReceive Maximum amount of energy to receive.
      * @param simulate If TRUE, the charge will only be simulated.
      * @return Amount of energy that was (or would have been, if simulated) received.
      */
    override def receiveEnergy(from: EnumFacing, maxReceive: Int, simulate: Boolean): Int = {
        if(isReceiver)
            super.receivePower(maxReceive, !simulate)
        else
            0
    }

    /**
      * Used to extract energy from this tile. You should return zero if you don't want to be able to extract
      *
      * @param from The direction pulling from
      * @param maxExtract The maximum amount to extract
      * @param simulate True to just simulate, not actually drain
      * @return How much energy was/should be drained
      */
    override def extractEnergy(from: EnumFacing, maxExtract: Int, simulate: Boolean): Int = {
        if(isProvider)
            providePower(maxExtract, !simulate)
        else
            0
    }

    /*******************************************************************************************************************
      * IC2 Compatibility                                                                                              *
      ******************************************************************************************************************/

    /**
      * Get the amount of energy currently stored in the block.
      *
      * @return Energy stored in the block
      */
    override def getStored: Int = getStored * ConfigManager.euMultiplier

    /**
      * Set the amount of energy currently stored in the block.
      *
      * @param energy stored energy
      */
    override def setStored(energy: Int) : Unit = setCurrentStored(energy * ConfigManager.euMultiplier)

    /**
      * Add the specified amount of energy.
      *
      * Use negative values to decrease.
      *
      * @param amount of energy to add
      * @return Energy stored in the block after adding the specified amount
      */
    override def addEnergy(amount: Int): Int = {
        currentStored += amount * ConfigManager.euMultiplier
        if(currentStored < 0)
            currentStored = 0
        else if(currentStored > maxStored)
            currentStored = maxStored
        currentStored
    }

    /**
      * Get the maximum amount of energy the block can store.
      *
      * @return Maximum energy stored
      */
    override def getCapacity: Int = getMaxStored * ConfigManager.euMultiplier

    /**
      * Get the block's energy output.
      *
      * @return Energy output in EU/t
            */
    override def getOutput: Int = maxExtract * ConfigManager.euMultiplier

    /**
      * Get the block's energy output.
      *
      * @return Energy output in EU/t
      */
    override def getOutputEnergyUnitsPerTick: Double = maxExtract * ConfigManager.euMultiplier

    /**
      * Get whether this block can have its energy used by an adjacent teleporter.
      *
      * @param side side the teleporter is draining energy from
      * @return Whether the block is teleporter compatible
      */
    override def isTeleporterCompatible(side: EnumFacing): Boolean = true

    /*******************************************************************************************************************
      * Tesla                                                                                                          *
      ******************************************************************************************************************/
    /**
      * Gets the amount of Tesla power stored being stored.
      *
      * @return The amount of Tesla power being stored.
      */
    override def getStoredPower: Long = currentStored

    /**
      * Offers power to the Tesla Consumer.
      *
      * @param power     The amount of power to offer.
      * @param simulated Whether or not this is being called as part of a simulation.
      *                  Simulations are used to get information without affecting the Tesla Producer.
      * @return The amount of power that the consumer accepts.
      */
    def givePower(power: Long, simulated: Boolean): Long = providePower(power.toInt, !simulated)

    /**
      * Requests an amount of power from the Tesla Producer.
      *
      * @param power     The amount of power to request.
      * @param simulated Whether or not this is being called as part of a simulation.
      *                  Simulations are used to get information without affecting the Tesla Producer.
      * @return The amount of power that the Tesla Producer will give.
      */
    def takePower(power: Long, simulated: Boolean): Long = receivePower(power.toInt, !simulated)
}
