package com.teambr.bookshelf.common.container.slots

import java.awt.Color

/**
  * This file was created for com.teambr.bookshelf.Bookshelf
 *
 * com.teambr.bookshelf.Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
trait ICustomSlot {

    /**
      * Used to get what size this slot should be rendered as
 *
      * @return
      */
    def getSlotSize: SLOT_SIZE.Value

    /**
      * Get the top left point of this slot as offset from the orginal place
 *
      * @return
      */
    def getPoint: (Integer, Integer)

    /**
      * Override this if you want your slot to display as a different color
 *
      * @return
      */
    def hasColor : Boolean = false

    /***
      * Used to get the color of the slot, this won't be called unless you set hasColor to true
      * @return
      */
    def getColor : Color = new Color(0, 0, 0, 0)
}

object SLOT_SIZE extends Enumeration {
    val STANDARD, LARGE = Value
}
