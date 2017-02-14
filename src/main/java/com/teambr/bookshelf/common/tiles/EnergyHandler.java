package com.teambr.bookshelf.common.tiles;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import com.teambr.bookshelf.energy.implementations.EnergyBank;
import com.teambr.bookshelf.manager.ConfigManager;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Massive energy handler class to manage interfacing with all energy systems. This handler will manage the conversion
 * between systems by using our own internal implementation
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "ic2.api.tile.IEnergyStorage", modid = "IC2"),
        @Optional.Interface(iface = "ic2.api.energy.tile.IEnergySource", modid = "IC2"),
        @Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2"),
        @Optional.Interface(iface = "net.darkax.tesla.api.ITeslaConsumer", modid = "tesla"),
        @Optional.Interface(iface = "net.darkax.tesla.api.ITeslaProducer", modid = "tesla")
})
public abstract class EnergyHandler extends Syncable implements
        IEnergyHandler, IEnergyReceiver, IEnergyProvider,
        net.minecraftforge.energy.IEnergyStorage,
        IEnergyStorage, IEnergySource, IEnergySink,
        ITeslaConsumer, ITeslaProducer {

    // Sync Values
    public static final int UPDATE_ENERGY_ID     = 1000;
    public static final int UPDATE_DIFFERENCE_ID = 1001;

    // Energy Storage
    public EnergyBank energyStorage;

    // IC2 Update Variable
    protected boolean firstRun = true;

    // Energy Change Values
    public int lastEnergy, lastDifference, currentDifference = 0;

    /**
     * Main Constructor
     */
    public EnergyHandler() {
        energyStorage = new EnergyBank(getDefaultEnergyStorageSize());
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to define the default size of this energy bank
     * @return The default size of the energy bank
     */
    protected abstract int getDefaultEnergyStorageSize();

    /**
     * Is this tile an energy provider
     * @return True to allow energy out
     */
    protected abstract boolean isProvider();

    /**
     * Is this tile an energy reciever
     * @return True to accept energy
     */
    protected abstract boolean isReceiver();

    /*******************************************************************************************************************
     * Tile Methods                                                                                                    *
     *******************************************************************************************************************/

    @Override
    protected void onServerTick() {
        super.onServerTick();

        // Check if needed add on load IC2
        if(firstRun) {
            if(Loader.isModLoaded("IC2"))
                energyLoad(true);
            firstRun = false;
        }

        // Handle Energy Difference
        currentDifference = energyStorage.getCurrentStored() - lastEnergy;

        // Update client
        if(currentDifference != lastDifference)
            sendValueToClient(UPDATE_DIFFERENCE_ID, currentDifference);

        // Store for next round
        lastDifference = currentDifference;
        lastEnergy     = energyStorage.getCurrentStored();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        energyStorage.readFromNBT(compound);

        // Check for bad tags
        if(energyStorage.getMaxStored() == 0)
            energyStorage.setMaxStored(getDefaultEnergyStorageSize());
        if(energyStorage.getMaxInsert() == 0)
            energyStorage.setMaxInsert(getDefaultEnergyStorageSize());
        if(energyStorage.getMaxExtract() == 0)
            energyStorage.setMaxExtract(getDefaultEnergyStorageSize());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_PRODUCER && isProvider())
            return true;
        else if(capability == TeslaCapabilities.CAPABILITY_CONSUMER && isReceiver())
            return true;
        else if(capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == CapabilityEnergy.ENERGY)
            return true;
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == TeslaCapabilities.CAPABILITY_PRODUCER && isProvider())
            return (T) this;
        else if(capability == TeslaCapabilities.CAPABILITY_CONSUMER && isReceiver())
            return (T) this;
        else if(capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == CapabilityEnergy.ENERGY)
            return (T) this;
        return null;
    }

    /**
     * Used to set the value of a field
     * @param id The field id
     * @param value The value of the field
     */
    @Override
    public void setVariable(int id, double value) {
        switch (id) {
            case UPDATE_ENERGY_ID :
                energyStorage.setCurrentStored((int) value);
                break;
            case UPDATE_DIFFERENCE_ID :
                currentDifference = (int) value;
                break;
            default :
        }
    }

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    @Override
    public Double getVariable(int id) {
        switch (id) {
            case UPDATE_ENERGY_ID :
                return (double) energyStorage.getCurrentStored();
            case UPDATE_DIFFERENCE_ID :
                return (double) currentDifference;
            default :
                return 0.0;
        }
    }

    /*******************************************************************************************************************
     * ForgeEnergy                                                                                                     *
     *******************************************************************************************************************/

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return isReceiver();
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return isProvider();
    }

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored() {
        return energyStorage.getCurrentStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of energy to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(isReceiver()) {
            int returnValue = energyStorage.receivePower(maxReceive, !simulate);
            if(!simulate)
                sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
            return returnValue;
        }
        return 0;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract
     *            Maximum amount of energy to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(isProvider()) {
            int returnValue = energyStorage.providePower(maxExtract, !simulate);
            if(!simulate)
                sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
            return returnValue;
        }
        return 0;
    }

    /*******************************************************************************************************************
     * Tesla Compatibility                                                                                             *
     *******************************************************************************************************************/

    /**
     * Offers power to the Tesla Consumer.
     *
     * @param power     The amount of power to offer.
     * @param simulated Whether or not this is being called as part of a simulation.
     *                  Simulations are used to get information without affecting the Tesla Producer.
     * @return The amount of power that the consumer accepts.
     */
    @Override
    public long givePower(long power, boolean simulated) {
        long returnValue = energyStorage.providePower((int) power, !simulated);
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
        return returnValue;
    }

    /**
     * Requests an amount of power from the Tesla Producer.
     *
     * @param power     The amount of power to request.
     * @param simulated Whether or not this is being called as part of a simulation.
     *                  Simulations are used to get information without affecting the Tesla Producer.
     * @return The amount of power that the Tesla Producer will give.
     */
    @Override
    public long takePower(long power, boolean simulated) {
        long returnValue = energyStorage.receivePower((int) power, !simulated);
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
        return returnValue;
    }

    /*******************************************************************************************************************
     * RF Compatibility                                                                                                *
     *******************************************************************************************************************/

    /**
     * Returns the amount of energy currently stored.
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Returns TRUE if the TileEntity can connect on a given side.
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    /**
     * Add energy to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
     *
     * @param from
     *            Orientation the energy is received from.
     * @param maxReceive
     *            Maximum amount of energy to receive.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return receiveEnergy(maxReceive, simulate);
    }

    /**
     * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
     *
     * @param from
     *            Orientation the energy is extracted from.
     * @param maxExtract
     *            Maximum amount of energy to extract.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return extractEnergy(maxExtract, simulate);
    }

    /*******************************************************************************************************************
     * IC2                                                                                                             *
     *******************************************************************************************************************/

    /***
     * Used to send the load event for the IC2 EnergyNet
     * @param load is this load or unload
     */
    @Optional.Method(modid = "IC2")
    protected void energyLoad(boolean load) {
        if(load)
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        else
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    }

    /**
     * Unload from the EnergyNet
     */
    @Override
    public void onChunkUnload() {
        if(!getWorld().isRemote && Loader.isModLoaded("IC2"))
            energyLoad(false);
        super.onChunkUnload();
    }

    /**
     * Unload from the EnergyNet
     */
    @Override
    public void invalidate() {
        if(!getWorld().isRemote && Loader.isModLoaded("IC2"))
            energyLoad(false);
        super.invalidate();
    }

    /**
     * Get the amount of energy currently stored in the block.
     *
     * @return Energy stored in the block
     */
    @Override
    public int getStored() {
        return energyStorage.getCurrentStored() / ConfigManager.euMultiplier;
    }

    /**
     * Set the amount of energy currently stored in the block.
     *
     * @param energy stored energy
     */
    @Override
    public void setStored(int energy) {
        energyStorage.setCurrentStored(energy * ConfigManager.euMultiplier);
    }

    /**
     * Add the specified amount of energy.
     *
     * Use negative values to decrease.
     *
     * @param amount of energy to add
     * @return Energy stored in the block after adding the specified amount
     */
    @Override
    public int addEnergy(int amount) {
        energyStorage.setCurrentStored(energyStorage.getCurrentStored() + (amount * ConfigManager.euMultiplier));
        if(energyStorage.getCurrentStored() < 0)
            energyStorage.setCurrentStored(0);
        else if(energyStorage.getCurrentStored() > energyStorage.getMaxStored())
            energyStorage.setCurrentStored(energyStorage.getMaxStored());
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
        return energyStorage.getCurrentStored();
    }

    /**
     * Get the maximum amount of energy the block can store.
     *
     * @return Maximum energy stored
     */
    @Override
    public int getCapacity() {
        return energyStorage.getMaxStored() / ConfigManager.euMultiplier;
    }

    /**
     * Get the block's energy output.
     *
     * @return Energy output in EU/t
     */
    @Override
    public int getOutput() {
        if(isProvider())
            return Math.min(energyStorage.getMaxExtract() / ConfigManager.euMultiplier, lookupMaxByTier(ConfigManager.ic2Tier));
        return 0;
    }

    /**
     * Get the block's energy output.
     *
     * @return Energy output in EU/t
     */
    @Override
    public double getOutputEnergyUnitsPerTick() {
        if(isProvider())
            return Math.min(energyStorage.getMaxExtract() / ConfigManager.euMultiplier, lookupMaxByTier(ConfigManager.ic2Tier));
        return 0;
    }

    /**
     * Get whether this block can have its energy used by an adjacent teleporter.
     *
     * @param enumFacing side the teleporter is draining energy from
     * @return Whether the block is teleporter compatible
     */
    @Override
    public boolean isTeleporterCompatible(EnumFacing enumFacing) {
        return true;
    }

    /**
     * Used to get how much power maxed by tier
     *
     * Keeping separate and not just doing the math as things may change
     *
     * @param tier Tier
     * @return Max output
     */
    protected int lookupMaxByTier(int tier) {
        switch (tier) {
            case 1  : return 32;
            case 2  : return 128;
            case 3  : return 512;
            case 4  : return 2048;
            case 5  : return 8192;
            default : return 32;
        }
    }

    /**
     * Determine if this emitter can emit energy to an adjacent receiver.
     *
     * The TileEntity in the receiver parameter is what was originally added to the energy net,
     * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
     *
     * @param iEnergyAcceptor receiver, may also be null or an IMetaDelegate
     * @param enumFacing     side the energy is to be sent to
     * @return Whether energy should be emitted
     */
    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, EnumFacing enumFacing) {
        return isProvider();
    }

    /**
     * Energy output provided by the source this tick.
     * This is typically Math.min(stored energy, max output/tick).
     *
     * Modifying the energy net from this method is disallowed.
     * @return Energy offered this tick
     */
    @Override
    public double getOfferedEnergy() {
        if(isProvider()) {
            return Math.min(
                    Math.min(energyStorage.getCurrentStored() / ConfigManager.euMultiplier, energyStorage.getMaxExtract() / ConfigManager.euMultiplier),
                    lookupMaxByTier(ConfigManager.ic2Tier));
        }
        return 0;
    }

    /**
     * Draw energy from this source's buffer.
     *
     * If the source doesn't have a buffer, this is a no-op.
     *
     * @param amount amount of EU to draw, may be negative
     */
    @Override
    public void drawEnergy(double amount) {
        energyStorage.providePower((int) (amount * ConfigManager.euMultiplier), true);
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
    }

    /**
     * Determine the tier of this energy source.
     * 1 = LV, 2 = MV, 3 = MHV, 4 = HV, 5 = EV etc.
     *
     * Modifying the energy net from this method is disallowed.
     * @return tier of this energy source
     */
    @Override
    public int getSourceTier() {
        return ConfigManager.ic2Tier;
    }

    /**
     * Determine how much energy the sink accepts.
     *
     * Make sure that injectEnergy() does accepts energy if demandsEnergy() returns anything > 0.
     *
     * Modifying the energy net from this method is disallowed.
     * @return max accepted input in eu
     */
    @Override
    public double getDemandedEnergy() {
        if(isReceiver())
            return (energyStorage.getMaxEnergyStored() - energyStorage.getCurrentStored()) / ConfigManager.euMultiplier;
        return 0;
    }

    /**
     * Determine if this acceptor can accept current from an adjacent emitter in a direction.
     *
     * The TileEntity in the emitter parameter is what was originally added to the energy net,
     * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
     *
     * @param iEnergyEmitter energy emitter, may also be null or an IMetaDelegate
     * @param enumFacing    side the energy is being received from
     */
    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing enumFacing) {
        return isReceiver();
    }

    /**
     * Determine the tier of this energy sink.
     * 1 = LV, 2 = MV, 3 = HV, 4 = EV etc.
     *
     * Modifying the energy net from this method is disallowed.
     * Return Integer.MAX_VALUE to allow any voltage.
     * @return tier of this energy sink
     */
    @Override
    public int getSinkTier() {
        return Integer.MAX_VALUE;
    }

    /**
     * Transfer energy to the sink.
     *
     * It's highly recommended to accept all energy by letting the internal buffer overflow to
     * increase the performance and accuracy of the distribution simulation.
     *
     * @param enumFacing direction from which the energy comes from
     * @param amount        energy to be transferred
     * @return Energy not consumed (leftover)
     */
    @Override
    public double injectEnergy(EnumFacing enumFacing, double amount, double voltage) {
        energyStorage.receivePower((int) (amount * ConfigManager.euMultiplier), true);
        sendValueToClient(UPDATE_ENERGY_ID, energyStorage.getCurrentStored());
        return 0; // As per documentation, kinda strange but oh well
    }
}
