package com.teambr.bookshelf.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/12/2017
 */
public class ClientUtils {

    /**
     * Used to translate the text to a given language
     *
     * @param text The text to translate
     * @return The translated text
     */
    public static String translate(String text) {
        return I18n.format(text);
    }

    /**
     * Used to translate a number to a standard format based on Locale
     *
     * @param number Number to format
     * @return A formated number string, eg 1,000,000
     */
    public static String formatNumber(double number) {
        return NumberFormat.getNumberInstance(Locale.forLanguageTag(Minecraft.getMinecraft().gameSettings.language)).format(number);
    }

    /**
     * Checks for CTRL key, Macs use Command so this will enable that key as well
     *
     * @return True if CTRL is pressed
     */
    public static boolean isCtrlPressed() {
        boolean standardControl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        // Check for Macs
        if(!standardControl && Minecraft.IS_RUNNING_ON_MAC)
            standardControl = Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);
        return standardControl;
    }

    /**
     * Checks for the Shift key pressed
     *
     * @return True if pressed
     */
    public static boolean isShiftPressed() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
