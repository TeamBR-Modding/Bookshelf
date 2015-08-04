package com.teambr.bookshelf.client.gui;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
public enum GuiTextFormat {
    ITALICS(0);

    private int number;
    GuiTextFormat(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String text = "\u00a7";
        switch (number) {
            case 0:
                return text + "o";
        }
        return "";
    }
}
