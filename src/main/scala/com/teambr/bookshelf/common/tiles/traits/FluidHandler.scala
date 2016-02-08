package com.teambr.bookshelf.common.tiles.traits

import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import net.minecraft.util.EnumFacing
import net.minecraftforge.fluids._

import scala.collection.mutable.ArrayBuffer

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * This trait allows you to implement a FluidHandler with one or more tanks
  *
  * @author Paul Davis <pauljoda>
  * @since 2/4/2016
  */
trait FluidHandler extends IFluidHandler {

    /**
      * The tanks themselves
      */
    var tanks : ArrayBuffer[FluidTank] = new ArrayBuffer[FluidTank]()
    setupTanks()

    /**
      * Used to set up the tanks needed. You can insert any number of tanks
      */
    def setupTanks() : Unit

    /**
      * Called when something happens to the tank, you should mark the block for update here if a tile
      */
    def onTankChanged(tank : FluidTank) : Unit

    /**
      * Used to convert a number of buckets into MB
      *
      * @param buckets How many buckets
      * @return The amount of buckets in MB
      */
    def bucketsToMB(buckets : Int) : Int = FluidContainerRegistry.BUCKET_VOLUME * buckets

    /**
      * Fills fluid into internal tanks, distribution is left entirely to the IFluidHandler.
      *
      * @param from
      * Orientation the Fluid is pumped in from.
      * @param resource
      * FluidStack representing the Fluid and maximum amount of fluid to be filled.
      * @param doFill
      * If false, fill will only be simulated.
      * @return Amount of resource that was (or would have been, if simulated) filled.
      */
    def fill(from: EnumFacing, resource: FluidStack, doFill: Boolean): Int = {
        if(resource != null && resource.getFluid != null && canFill(from, resource.getFluid)) {
            for(tank <- tanks) {
                if (tank.fill(resource, false) > 0) {
                    val actual = tank.fill(resource, doFill)
                    if (doFill) onTankChanged(tank)
                    return actual
                }
            }
        }
        0
    }

    /**
      * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
      *
      * @param from
      * Orientation the Fluid is drained to.
      * @param resource
      * FluidStack representing the Fluid and maximum amount of fluid to be drained.
      * @param doDrain
      * If false, drain will only be simulated.
      * @return FluidStack representing the Fluid and amount that was (or would have been, if
      *         simulated) drained.
      */
    def drain(from: EnumFacing, resource: FluidStack, doDrain: Boolean): FluidStack =
        drain(from, resource.amount, doDrain)

    /**
      * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
      *
      * This method is not Fluid-sensitive.
      *
      * @param from
      * Orientation the fluid is drained to.
      * @param maxDrain
      * Maximum amount of fluid to drain.
      * @param doDrain
      * If false, drain will only be simulated.
      * @return FluidStack representing the Fluid and amount that was (or would have been, if
      *         simulated) drained.
      */
    def drain(from: EnumFacing, maxDrain: Int, doDrain: Boolean): FluidStack = {
        var fluidAmount :FluidStack = null
        for(tank <- tanks) {
            fluidAmount = tank.drain(maxDrain, false)
            if (doDrain && fluidAmount != null)
                tank.drain(maxDrain, true); onTankChanged(tank)
        }
        fluidAmount
    }

    /**
      * Returns true if the given fluid can be inserted into the given direction.
      *
      * More formally, this should return true if fluid is able to enter from the given direction.
      */
    def canFill(from: EnumFacing, fluid: Fluid): Boolean = {
        for(tank <- tanks) {
            if((tank.getFluid == null || tank.getFluid.getFluid == null) ||
                    (tank.getFluid != null && tank.getFluid.getFluid == fluid)) return true
        }
        false
    }

    /**
      * Returns true if the given fluid can be extracted from the given direction.
      *
      * More formally, this should return true if fluid is able to leave from the given direction.
      */
    def canDrain(from: EnumFacing, fluid: Fluid): Boolean = {
        for(tank <- tanks) {
            if(tank.getFluid != null || tank.getFluid.getFluid != null) return true
        }
        false
    }

    /**
      * Returns an array of objects which represent the internal tanks. These objects cannot be used
      * to manipulate the internal tanks.
      *
      * @param from
      * Orientation determining which tanks should be queried.
      * @return Info for the relevant internal tanks.
      */
    def getTankInfo(from: EnumFacing): Array[FluidTankInfo] ={
        val tankInfo = new Array[FluidTankInfo](tanks.size)
        var i = 0
        for(tank <- tanks) {
            tankInfo(i)
            i += 1
        }
        tankInfo
    }

    /**
      * Used to save the object to an NBT tag
      *
      * @param tag The tag to save to
      */
    def writeToNBT(tag : NBTTagCompound) = {
        var id = 0
        tag.setInteger("Size", tanks.size)
        val tagList = new NBTTagList
        for(tank <- tanks) {
            if (tank != null) {
                val tankCompound = new NBTTagCompound
                tankCompound.setByte("TankID", id.asInstanceOf[Byte])
                id += 1
                tank.writeToNBT(tankCompound)
                tagList.appendTag(tankCompound)
            }
        }
        tag.setTag("Tanks", tagList)
    }

    /**
      * Used to read from an NBT tag
      *
      * @param tag The tag to read from
      */
    def readFromNBT(tag : NBTTagCompound) = {
        val tagList = tag.getTagList("Tanks", 10)
        val size = tag.getInteger("Size")
        if(size != tanks.size) tanks = new ArrayBuffer[FluidTank](size)
        for(x <- 0 until tagList.tagCount()) {
            val tankCompound = tagList.getCompoundTagAt(x)
            val position = tankCompound.getByte("TankID")
            if(position < tanks.size)
                tanks(position).readFromNBT(tankCompound)
        }
    }
}
