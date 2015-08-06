package com.teambr.bookshelf.collections

import net.minecraft.block.properties.IProperty
import net.minecraftforge.common.property.Properties.PropertyAdapter

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 06, 2015
 */
class UnlistedAdapter[V <: Comparable[_]](iProperty: IProperty) extends PropertyAdapter[V](iProperty) {
    //Since Forge won't let me use this directly, I'll just get it myself
    def getProperty : IProperty = iProperty
}
