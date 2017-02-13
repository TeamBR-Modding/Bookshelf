package com.teambr.bookshelf.client.gui.component.listeners

import com.teambr.bookshelf.client.gui.component.BaseComponent

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
abstract class IMouseEventListener {
    /**
     * Called when the mouse clicks on the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    def onMouseDown(component: BaseComponent, mouseX: Int, mouseY: Int, button: Int)

    /**
     * Called when the mouse releases the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    def onMouseUp(component: BaseComponent, mouseX: Int, mouseY: Int, button: Int)

    /**
     * Called when the mouse drags an item
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     * @param time How long its been clicked
     */
    def onMouseDrag(component: BaseComponent, mouseX: Int, mouseY: Int, button: Int, time: Long)
}
