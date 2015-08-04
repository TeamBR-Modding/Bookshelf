package com.teambr.bookshelf.client.gui.component.listeners

import com.teambr.bookshelf.client.gui.component.BaseComponent

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
abstract class IKeyboardListener {
    /**
     * Called when the keyboard is pressed
     * @param component The component getting the input
     * @param character The character pressed
     * @param keyCode The key code
     */
    def keyTyped(component: BaseComponent, character: Char, keyCode: Int)
}
