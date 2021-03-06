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
 * This gives us the ability to add color to all vanilla text. Simply concat one of these enums into your string and
 * it will color all text after the concatenation
 */
public enum GuiColor {
    BLACK(0),
    BLUE(1),
    GREEN(2),
    CYAN(3),
    RED(4),
    PURPLE(5),
    ORANGE(6),
    LIGHTGRAY(7),
    GRAY(8),
    LIGHTBLUE(9),
    LIME(10),
    TURQUISE(11),
    PINK(12),
    MAGENTA(13),
    YELLOW(14),
    WHITE(15);

    private int number;
    GuiColor(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "\u00a7" + Integer.toHexString(number);
    }
}