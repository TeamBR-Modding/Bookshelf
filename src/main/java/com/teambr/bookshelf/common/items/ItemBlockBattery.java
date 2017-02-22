package com.teambr.bookshelf.common.items;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/15/2017
 */
public abstract class ItemBlockBattery extends ItemBlock implements IEnergyContainerItem {
    // NBT Tags
    protected static final String ENERGY_NBT_TAG             = "Energy";
    protected static final String ENERGY_CAPACITY_NBT_TAG    = "EnergyCapacity";
    protected static final String ENERGY_MAX_RECIEVE_NBT_TAG = "MaxRecieve";
    protected static final String ENERGY_MAX_EXTRACT_NBT_TAG = "MaxExtract";

    /**
     * Main Constructor
     */
    public ItemBlockBattery(Block block) {
        super(block);
        setMaxDamage(16);
        setNoRepair();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to set the default tags
     *
     * You must define:
     * EnergyCapacity
     * MaxRecieve
     * MaxExtract
     *
     * Use the static strings for nbt tags
     * @param stack The stack
     */
    protected abstract void setDefaultTags(ItemStack stack);

    /*******************************************************************************************************************
     * ItemBattery                                                                                                     *
     *******************************************************************************************************************/

    /**
     * Used to update the item damage based on current energy stored
     * @param stack The stack
     */
    protected void updateDamage(ItemStack stack) {
        int r = getEnergyStored(stack) / getMaxEnergyStored(stack);
        int result = 16 - Math.round(r * 16);
        if(r < 1 && result == 0)
            result = 1;
        stack.setItemDamage(result);
    }

    /*******************************************************************************************************************
     * Item                                                                                                            *
     *******************************************************************************************************************/

    /**
     * Called when the item is created
     * @param stack The stack
     * @param worldIn The world
     * @param playerIn The player
     */
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        setDefaultTags(stack);
        updateDamage(stack);
    }

    /**
     * Called on tick
     * @param stack The stack
     * @param worldIn The world
     * @param entityIn The entity holding
     * @param itemSlot The item slot
     * @param isSelected Is this selected (held)
     */
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!stack.hasTagCompound())
            setDefaultTags(stack);
    }

    /*******************************************************************************************************************
     * IEnergyContainerItem                                                                                            *
     *******************************************************************************************************************/

    /**
     * Adds energy to a container item. Returns the quantity of energy that was accepted.
     * This should always return 0 if the item cannot be externally charged.
     *
     * @param stack
     *            ItemStack to be charged.
     * @param maxReceive
     *            Maximum amount of energy to be sent into the item.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received by the item.
     */
    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        if(!stack.hasTagCompound())
            setDefaultTags(stack);

        int energyStored  = getEnergyStored(stack);
        int energyReceived = Math.min(stack.getTagCompound().getInteger(ENERGY_CAPACITY_NBT_TAG) - energyStored,
                Math.min(stack.getTagCompound().getInteger(ENERGY_MAX_RECIEVE_NBT_TAG), maxReceive));

        if(!simulate) {
            energyStored += energyReceived;
            stack.getTagCompound().setInteger(ENERGY_NBT_TAG, energyStored);
            updateDamage(stack);
        }

        return energyReceived;
    }

    /**
     * Removes energy from a container item. Returns the quantity of energy that was removed.
     * This should always return 0 if the item cannot be externally
     * discharged.
     *
     * @param stack
     *            ItemStack to be discharged.
     * @param maxExtract
     *            Maximum amount of energy to be extracted from the item.
     * @param simulate
     *            If TRUE, the discharge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the item.
     */
    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        if(!stack.hasTagCompound())
            setDefaultTags(stack);

        int energyStored    = getEnergyStored(stack);
        int energyExtracted = Math.min(energyStored,
                Math.min(stack.getTagCompound().getInteger(ENERGY_MAX_EXTRACT_NBT_TAG), maxExtract));

        if(!simulate) {
            energyStored -= energyExtracted;
            stack.getTagCompound().setInteger(ENERGY_NBT_TAG, energyStored);
            updateDamage(stack);
        }

        return energyExtracted;
    }

    /**
     * Get the amount of energy currently stored in the container item.
     */
    @Override
    public int getEnergyStored(ItemStack stack) {
        if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey(ENERGY_NBT_TAG))
            return 0;
        return stack.getTagCompound().getInteger(ENERGY_NBT_TAG);
    }

    /**
     * Get the max amount of energy that can be stored in the container item.
     */
    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey(ENERGY_CAPACITY_NBT_TAG))
            return 0;
        return stack.getTagCompound().getInteger(ENERGY_CAPACITY_NBT_TAG);
    }
}
