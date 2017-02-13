package com.teambr.bookshelf.common.container.slots

/**
  * This file was created for com.teambr.bookshelf.Bookshelf
  *
  * com.teambr.bookshelf.Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/21/2016
  */
trait IPhantomSlot {
    //Can this slot change?
    def canAdjust: Boolean
}
