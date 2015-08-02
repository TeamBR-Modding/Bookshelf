package test.com.teambr.bookshelf.common.tiles

import com.teambr.bookshelf.scala.traits.NBTSavable
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
class TestValue(var thing : Int) extends NBTSavable {

    /**
     * Used to save a value to the tag
     * @param tagCompound The tag to save to
     */
    override def saveToNBT(tagCompound: NBTTagCompound): Unit = {
        tagCompound.setInteger("Testing", thing)
    }

    /**
     * Used to load a value from the tag
     * @param tagCompound The tag to load from
     */
    override def loadFromNBT(tagCompound: NBTTagCompound): Unit = {
        thing = tagCompound.getInteger("Testing")
    }
}
