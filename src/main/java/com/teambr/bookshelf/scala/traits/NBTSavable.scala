package com.teambr.bookshelf.scala.traits

import net.minecraft.nbt.NBTTagCompound

/**
 * This file was created for the Bookshelf
 * API. The source is available at: 
 * https://github.com/TeamBR-Modding/Bookshelf
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
trait NBTSavable {
    /**
     * Used to save a value to the tag
     * @param tagCompound The tag to save to
     */
    def saveToNBT(tagCompound : NBTTagCompound)

    /**
     * Used to load a value from the tag
     * @param tagCompound The tag to load from
     */
    def loadFromNBT(tagCompound: NBTTagCompound)
}
