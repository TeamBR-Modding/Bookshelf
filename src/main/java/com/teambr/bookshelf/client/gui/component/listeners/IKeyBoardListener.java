package com.teambr.bookshelf.client.gui.component.listeners;

import com.teambr.bookshelf.client.gui.component.BaseComponent;

public interface IKeyBoardListener {

    /**
     * Called when the keyboard is pressed
     * @param component The component getting the input
     * @param character The character pressed
     * @param keyCode The key code
     */
    void keyTyped(BaseComponent component, char character, int keyCode);
}
