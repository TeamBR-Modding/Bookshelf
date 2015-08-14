package com.teambr.bookshelf.traits

import net.minecraft.nbt.NBTTagCompound

/**
 * This file was created for Bookshelf
 *
 * Bookshelf if licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 03, 2015
 *
 * Used to specify that something can be read and written to an NBT tag compound
 */
trait NBTSavable {
    /**
     * Used to save the object to an NBT tag
     * @param tag The tag to save to
     */
    def writeToNBT(tag : NBTTagCompound)

    /**
     * Used to read from an NBT tag
     * @param tag The tag to read from
     */
    def readFromNBT(tag : NBTTagCompound)
}
