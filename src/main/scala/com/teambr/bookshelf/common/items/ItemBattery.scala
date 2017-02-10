package com.teambr.bookshelf.common.items

import cofh.api.energy.IEnergyContainerItem
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
  * This file was created for NeoTech
  *
  * NeoTech is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 2/17/2016
  */
trait ItemBattery extends Item with IEnergyContainerItem {
    setMaxDamage(16)
    setNoRepair()

    /**
      * Must set the following tags:
      * 'EnergyCapacity'
      * 'MaxReceive'
      * 'MaxExtract'
      */
    def setDefaultTags(stack: ItemStack): Unit

    override def onCreated(stack: ItemStack, worldIn: World, playerIn: EntityPlayer): Unit = {
        setDefaultTags(stack)
        updateDamage(stack)
    }

    /**
      * Called on tick, allows us to make sure things are installed
      */
    override def onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean): Unit = {
        if(!stack.hasTagCompound)
            setDefaultTags(stack)
    }

    /**
      * Adds energy to a container item. Returns the quantity of energy that was accepted.
      * This should always return 0 if the item cannot be externally charged.
      *
      * @param stack ItemStack to be charged.
      * @param maxReceive Maximum amount of energy to be sent into the item.
      * @param simulate If TRUE, the charge will only be simulated.
      * @return Amount of energy that was (or would have been, if simulated) received by the item.
      */
    override def receiveEnergy(stack: ItemStack, maxReceive: Int, simulate: Boolean): Int = {
        if (!stack.hasTagCompound) {
            stack.setTagCompound(new NBTTagCompound)
        }
        var energy: Int = stack.getTagCompound.getInteger("Energy")
        val energyReceived: Int = Math.min(stack.getTagCompound.getInteger("EnergyCapacity") - energy,
            Math.min(stack.getTagCompound.getInteger("MaxReceive"), maxReceive))
        if (!simulate) {
            energy += energyReceived
            stack.getTagCompound.setInteger("Energy", energy)
            updateDamage(stack)
        }
        energyReceived
    }

    /**
      * Removes energy from a container item. Returns the quantity of energy that was removed.
      * This should always return 0 if the item cannot be externally discharged.
      *
      * @param stack ItemStack to be discharged.
      * @param maxExtract Maximum amount of energy to be extracted from the item.
      * @param simulate If TRUE, the discharge will only be simulated.
      * @return Amount of energy that was (or would have been, if simulated) extracted from the item.
      */
    override def extractEnergy(stack: ItemStack, maxExtract: Int, simulate: Boolean): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("Energy")) {
            return 0
        }
        var energy = stack.getTagCompound.getInteger("Energy")
        val energyExtracted = Math.min(energy, Math.min(stack.getTagCompound.getInteger("MaxExtract"), maxExtract))

        if (!simulate) {
            energy -= energyExtracted
            stack.getTagCompound.setInteger("Energy", energy)
            updateDamage(stack)
        }
        energyExtracted
    }

    /**
      * Get the amount of energy currently stored in the container item.
      */
    override def getEnergyStored(stack: ItemStack): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("Energy"))
            return 0
        stack.getTagCompound.getInteger("Energy")
    }

    /**
      * Get the max amount of energy that can be stored in the container item.
      */
    override def getMaxEnergyStored(stack: ItemStack): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("EnergyCapacity"))
            return 0
        stack.getTagCompound.getInteger("EnergyCapacity")
    }

    /**
      * Sets the Damage Bar on the item to correspond to amount of energy stored
      */
    def updateDamage(stack: ItemStack): Unit = {
        val r = getEnergyStored(stack).toFloat / getMaxEnergyStored(stack)
        var res = 16 - Math.round(r * 16)
        if (r < 1 && res == 0)
            res = 1
        stack.setItemDamage(res)
    }
}
