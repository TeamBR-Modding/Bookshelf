package com.teambr.bookshelf.common.container

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
abstract class ICustomSlot {
    def getSlotSize: SLOT_SIZE.Value

    def getPoint: (Integer, Integer)
}

object SLOT_SIZE extends Enumeration {
    val STANDARD, LARGE = Value
}
