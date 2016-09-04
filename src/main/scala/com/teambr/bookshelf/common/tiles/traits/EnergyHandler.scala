package com.teambr.bookshelf.common.tiles.traits

import cofh.api.energy.{IEnergyHandler, IEnergyProvider, IEnergyReceiver}
import com.teambr.bookshelf.energy.implementations.EnergyBank
import com.teambr.bookshelf.manager.ConfigManager
import ic2.api.energy.event.{EnergyTileLoadEvent, EnergyTileUnloadEvent}
import ic2.api.energy.tile.{IEnergyAcceptor, IEnergyEmitter, IEnergySink, IEnergySource}
import ic2.api.tile.IEnergyStorage
import net.darkhax.tesla.api.{ITeslaConsumer, ITeslaHolder, ITeslaProducer}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.MinecraftForge
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
    new Optional.Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2"),
    new Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2"),
    new Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = "tesla"),
    new Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = "tesla")

))
trait EnergyHandler extends Syncable
        with IEnergyHandler with IEnergyReceiver with IEnergyProvider
        with IEnergyStorage with IEnergySource with IEnergySink
        with ITeslaConsumer with ITeslaProducer {

    lazy val UPDATE_ENERGY_ID = 0

    // Energy Storage
    lazy val energyStorage = new EnergyBank(10000)

    protected var firstRun = true

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

    /** *****************************************************************************************************************
      * Tile Methods                                                                                                   *
      * *****************************************************************************************************************/

    /**
      *  Called on the server side only
      */
    override def onServerTick(): Unit = {
        super.onServerTick()
        if (firstRun) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this))
            firstRun = false
        }
    }

    override def onChunkUnload() {
        if (!getWorld.isRemote)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
        super.onChunkUnload()
    }

    override def invalidate() {
        if (!getWorld.isRemote)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this))
        super.invalidate()
    }

    /**
      * Write to the tag
      * @param tag The tag to write to
      */
    override def writeToNBT(tag: NBTTagCompound) : NBTTagCompound = {
        super.writeToNBT(tag)
        energyStorage.writeToNBT(tag)
        tag
    }

    /**
      * Read data from tag
      * @param tag The tag to read from
      */
    override def readFromNBT(tag : NBTTagCompound) = {
        super.readFromNBT(tag)

        energyStorage.readFromNBT(tag)

        // Checks for bad tags
        if(energyStorage.getMaxStored == 0)
            energyStorage.setMaxStored(defaultEnergyStorageSize)

        if(energyStorage.getMaxInsert == 0)
            energyStorage.setMaxInsert(defaultEnergyStorageSize)

        if(energyStorage.getMaxExtract == 0)
            energyStorage.setMaxExtract(defaultEnergyStorageSize)

    }

    /**
      * Used to set the value of a field
      * @param id The field id
      * @param value The value of the field
      */
    override def setVariable(id : Int, value : Double): Unit = {
        id match {
            case UPDATE_ENERGY_ID =>
                energyStorage.setCurrentStored(value.toInt)
            case _ =>
        }
    }

    /**
      * Used to get the field on the server, this will fetch the server value and overwrite the current
      * @param id The field id
      * @return The value on the server, now set to ourselves
      */
    override def getVariable(id : Int) : Double = {
        id match {
            case UPDATE_ENERGY_ID => energyStorage.getCurrentStored
            case _ => 0.0
        }
    }

    /*******************************************************************************************************************
      * RF Compatibility                                                                                               *
      ******************************************************************************************************************/
    /**
      * Returns the amount of energy currently stored.
      */
    override def getEnergyStored(from: EnumFacing): Int = energyStorage.getEnergyStored

    /**
      * Returns the maximum amount of energy that can be stored.
      */
    override def getMaxEnergyStored(from: EnumFacing): Int = energyStorage.getMaxStored

    /**
      * Returns TRUE if the TileEntity can connect on a given side.
      */
    def canConnectEnergy(from: EnumFacing): Boolean = true

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
            val returnValue = energyStorage.receivePower(maxReceive, !simulate)
            sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
            returnValue
        }
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
        if(isProvider) {
            val returnValue = energyStorage.providePower(maxExtract, !simulate)
            sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
            returnValue
        }
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
    override def getStored: Int = energyStorage.getCurrentStored * ConfigManager.euMultiplier

    /**
      * Set the amount of energy currently stored in the block.
      *
      * @param energy stored energy
      */
    override def setStored(energy: Int) : Unit = energyStorage.setCurrentStored(energy * ConfigManager.euMultiplier)

    /**
      * Add the specified amount of energy.
      *
      * Use negative values to decrease.
      *
      * @param amount of energy to add
      * @return Energy stored in the block after adding the specified amount
      */
    override def addEnergy(amount: Int): Int = {
        energyStorage.setCurrentStored(energyStorage.getCurrentStored + (amount * ConfigManager.euMultiplier))
        if(energyStorage.getCurrentStored < 0)
            energyStorage.setCurrentStored(0)
        else if(energyStorage.getCurrentStored > energyStorage.getMaxStored)
            energyStorage.setCurrentStored(energyStorage.getMaxStored)
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
        energyStorage.getCurrentStored
    }

    /**
      * Get the maximum amount of energy the block can store.
      *
      * @return Maximum energy stored
      */
    override def getCapacity : Int = energyStorage.getMaxStored * ConfigManager.euMultiplier

    /**
      * Get the block's energy output.
      *
      * @return Energy output in EU/t
      */
    override def getOutput: Int = energyStorage.getMaxExtract / ConfigManager.euMultiplier

    /**
      * Get the block's energy output.
      *
      * @return Energy output in EU/t
      */
    override def getOutputEnergyUnitsPerTick: Double = energyStorage.getMaxExtract / ConfigManager.euMultiplier

    /**
      * Get whether this block can have its energy used by an adjacent teleporter.
      *
      * @param side side the teleporter is draining energy from
      * @return Whether the block is teleporter compatible
      */
    override def isTeleporterCompatible(side: EnumFacing): Boolean = true

    /**
      * Determine if this emitter can emit energy to an adjacent receiver.
      *
      * The TileEntity in the receiver parameter is what was originally added to the energy net,
      * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
      *
      * @param receiver receiver, may also be null or an IMetaDelegate
      * @param side     side the energy is to be sent to
      * @return Whether energy should be emitted
      */
    override def emitsEnergyTo(receiver: IEnergyAcceptor, side: EnumFacing): Boolean = true

    /**
      * Energy output provided by the source this tick.
      * This is typically Math.min(stored energy, max output/tick).
      *
      * @note Modifying the energy net from this method is disallowed.
      * @return Energy offered this tick
      */
    override def getOfferedEnergy: Double = energyStorage.getMaxExtract / ConfigManager.euMultiplier

    /**
      * Draw energy from this source's buffer.
      *
      * If the source doesn't have a buffer, this is a no-op.
      *
      * @param amount amount of EU to draw, may be negative
      */
    override def drawEnergy(amount: Double) : Unit = {
        energyStorage.providePower(amount.toInt * ConfigManager.euMultiplier, true)
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
    }

    /**
      * Determine the tier of this energy source.
      * 1 = LV, 2 = MV, 3 = HV, 4 = EV etc.
      *
      * @note Modifying the energy net from this method is disallowed.
      * @return tier of this energy source
      */
    override def getSourceTier: Int = ConfigManager.ic2Tier

    /**
      * Determine how much energy the sink accepts.
      *
      * Make sure that injectEnergy() does accepts energy if demandsEnergy() returns anything > 0.
      *
      * @note Modifying the energy net from this method is disallowed.
      * @return max accepted input in eu
      */
    override def getDemandedEnergy: Double = (energyStorage.getMaxEnergyStored - energyStorage.getCurrentStored) / ConfigManager.euMultiplier

    /**
      * Determine if this acceptor can accept current from an adjacent emitter in a direction.
      *
      * The TileEntity in the emitter parameter is what was originally added to the energy net,
      * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
      *
      * @param emitter energy emitter, may also be null or an IMetaDelegate
      * @param side    side the energy is being received from
      */
    override def acceptsEnergyFrom(emitter: IEnergyEmitter, side: EnumFacing): Boolean = true

    /**
      * Determine the tier of this energy sink.
      * 1 = LV, 2 = MV, 3 = HV, 4 = EV etc.
      *
      * @note Modifying the energy net from this method is disallowed.
      * @note Return Integer.MAX_VALUE to allow any voltage.
      * @return tier of this energy sink
      */
    override def getSinkTier: Int = Integer.MAX_VALUE

    /**
      * Transfer energy to the sink.
      *
      * It's highly recommended to accept all energy by letting the internal buffer overflow to
      * increase the performance and accuracy of the distribution simulation.
      *
      * @param directionFrom direction from which the energy comes from
      * @param amount        energy to be transferred
      * @return Energy not consumed (leftover)
      */
    override def injectEnergy(directionFrom: EnumFacing, amount: Double, voltage: Double): Double = {
        val returnValue = energyStorage.receivePower(amount.toInt * ConfigManager.euMultiplier, true)
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
        amount - returnValue
    }

    /*******************************************************************************************************************
      * Tesla                                                                                                          *
      ******************************************************************************************************************/

    /**
      * Offers power to the Tesla Consumer.
      *
      * @param power     The amount of power to offer.
      * @param simulated Whether or not this is being called as part of a simulation.
      *                  Simulations are used to get information without affecting the Tesla Producer.
      * @return The amount of power that the consumer accepts.
      */
    override def givePower(power: Long, simulated: Boolean): Long = {
        val returnValue = energyStorage.providePower(power.toInt, !simulated)
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
        returnValue
    }

    /**
      * Requests an amount of power from the Tesla Producer.
      *
      * @param power     The amount of power to request.
      * @param simulated Whether or not this is being called as part of a simulation.
      *                  Simulations are used to get information without affecting the Tesla Producer.
      * @return The amount of power that the Tesla Producer will give.
      */
    override def takePower(power: Long, simulated: Boolean): Long = {
        val returnValue = energyStorage.receivePower(power.toInt, !simulated)
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored)
        returnValue
    }
}
