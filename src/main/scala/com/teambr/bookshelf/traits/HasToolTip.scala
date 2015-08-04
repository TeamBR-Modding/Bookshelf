package com.teambr.bookshelf.traits

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 01, 2015
 */
trait HasToolTip {
    /**
     * Used to get the tool tip for this object
     * @return The list of strings
     */
    def getToolTip() : List[String]
}
