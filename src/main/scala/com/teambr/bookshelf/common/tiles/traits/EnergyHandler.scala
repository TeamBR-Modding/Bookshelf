package com.teambr.bookshelf.common.tiles.traits

import cofh.api.energy.{IEnergyProvider, EnergyStorage, IEnergyReceiver}
import com.teambr.bookshelf.traits.NBTSavable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing

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
trait EnergyHandler extends UpdatingTile with IEnergyReceiver with IEnergyProvider with NBTSavable {

    val energyStorage = new EnergyStorage(defaultEnergyStorageSize)

    var energyIn  : Int     = 0
    var energyOut : Int     = 0

    /**
      * Used to define the default energy storage for this energy handler
      *
      * @return
      */
    def defaultEnergyStorageSize : Int

    /**
      * Return true if you want this to be able to provide energy
      * @return
      */
    def isProvider : Boolean

    /**
      * Return true if you want this to be able to receive energy
      * @return
      */
    def isReceiver : Boolean

    /** ****************************************************************************************************************
      * *************************************************  Tile Methods  ************************************************
      * *****************************************************************************************************************/

    var ticker = 0
    override def update() : Unit = {
        super.update()
        ticker -= 1
        if(ticker <= 0) {
            ticker = checkDelay
            energyIn  = 0
            energyOut = 0
        }
    }

    override def writeToNBT(tag: NBTTagCompound) : Unit = {
        tag.setInteger("CurrentEnergy", energyStorage.getEnergyStored)
        tag.setInteger("EnergyCapacity", energyStorage.getMaxEnergyStored)
        tag.setInteger("MaxExtract", energyStorage.getMaxExtract)
        tag.setInteger("MaxReceive", energyStorage.getMaxReceive)
        tag.setInteger("EnergyIn", energyIn)
        tag.setInteger("EnergyOut", energyOut)
    }

    override def readFromNBT(tag : NBTTagCompound) : Unit = {
        energyStorage.setCapacity(tag.getInteger("EnergyCapacity"))
        if(energyStorage.getMaxEnergyStored == 0)
            energyStorage.setCapacity(defaultEnergyStorageSize)
        energyStorage.setEnergyStored(tag.getInteger("CurrentEnergy"))
        energyStorage.setMaxExtract(tag.getInteger("MaxExtract"))
        energyStorage.setMaxReceive(tag.getInteger("MaxReceive"))
        energyIn = tag.getInteger("EnergyIn")
        energyOut = tag.getInteger("EnergyOut")
        if(energyStorage.getMaxExtract == 0)
            energyStorage.setMaxExtract(defaultEnergyStorageSize)
        if(energyStorage.getMaxReceive == 0)
            energyStorage.setMaxReceive(defaultEnergyStorageSize)
    }

    /*******************************************************************************************************************
      ************************************************ Energy methods **************************************************
      ******************************************************************************************************************/

    /**
      * Used to get how long before checking levels again
      *
      * @return
      */
    def checkDelay : Int = 20 * 2

    /**
      * Used to get the approximate RF change
      * @return
      */
    def getRFInPerTick: Int = {
        energyIn
    }

    /**
      * Used to get the approximate RF change
      * @return
      */
    def getRFOutPerTick: Int = {
        energyOut
    }

    /**
      * Used to change the max extraction rate of the energy storage
      * @param maxExtract The new max extraction rate
      */
    def setMaxExtract(maxExtract : Int) : Unit = {
        energyStorage.setMaxExtract(maxExtract)
    }

    /**
      * Used to set the maximum energy received
      * @param maxReceive The max energy to receive
      */
    def setMaxReceive(maxReceive : Int) : Unit = {
        energyStorage.setMaxReceive(maxReceive)
    }

    /**
      * Used to set the max energy stored
      * @param maxEnergyStored The maximum amount to store
      */
    def setMaxEnergyStored(maxEnergyStored : Int) : Unit = {
        energyStorage.setCapacity(maxEnergyStored)
    }

    /**
      * Get the current energy stored in the energy tank
      *
      * @param from The side to check (can be used if you have different energy storages)
      * @return
      */
    override def getEnergyStored(from: EnumFacing): Int = energyStorage.getEnergyStored

    /**
      * Get the maximum energy this handler can store, not the current
      *
      * @param from The side to check from (can be used if you have different energy storages)
      * @return The maximum potential energy
      */
    override def getMaxEnergyStored(from: EnumFacing): Int = energyStorage.getMaxEnergyStored

    /**
      * Checks if energy can connect to a given side
      *
      * @param from The face to check
      * @return True if the face allows energy flow
      */
    override def canConnectEnergy(from: EnumFacing): Boolean = true

    /**
      * Add energy to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
      *
      * @param from Orientation the energy is received from.
      * @param maxReceive Maximum amount of energy to receive.
      * @param simulate If TRUE, the charge will only be simulated.
      * @return Amount of energy that was (or would have been, if simulated) received.
      */
    override def receiveEnergy(from: EnumFacing, maxReceive: Int, simulate: Boolean): Int = {
        if(isReceiver) {
            if(energyStorage != null) {
                val actual = energyStorage.receiveEnergy(maxReceive, simulate)
                if(!simulate)
                    energyIn = actual
                if(getWorld != null)
                    getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
                actual
            } else 0
        } else 0
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
        if(isProvider) {
            if(energyStorage != null) {
                val actual = energyStorage.extractEnergy(maxExtract, simulate)
                if(!simulate)
                    energyOut = -actual
                if(getWorld != null)
                    getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
                actual
            } else 0
        } else 0
    }
}
