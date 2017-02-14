package com.teambr.bookshelf.client.gui;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 *
 * Used to handle extended formats. Determines what the text will look like
 */
public enum GuiTextFormat {
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKE_THROUGH('m'),
    UNDERLINE('n'),
    ITALICS('o'),
    RESET('r');

    private char formatChar;
    GuiTextFormat(char c) {
        this.formatChar = c;
    }

    @Override
    public String toString() {
        return "\u00a7" + formatChar;
    }
}
