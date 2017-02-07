package com.teambr.bookshelf.util;

import java.awt.*;

/**
 * This file was created for Bookshelf - Java
 * <p>
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/6/2017
 */
public class ColorUtils {

    /**
     * Used to get the color between the two colors based on how far the progress is between them
     * @param x The first color
     * @param y The second color
     * @param blending How far between the two (0-1)
     * @return The new color that represents the position in the spectrum between the two colors
     */
    public static Color getColorBetween(Color x, Color y, float blending) {
        float inverseBlending = 1 - blending;

        // Interpolate Values
        int red   = (int) (y.getRed()   * blending + x.getRed()   * inverseBlending);
        int green = (int) (y.getGreen() * blending + x.getGreen() * inverseBlending);
        int blue  = (int) (y.getBlue()  * blending + x.getBlue()  * inverseBlending);
        int alpha = (int) (y.getAlpha() * blending + x.getAlpha() * inverseBlending);

        return new Color(red / 255, green / 255, blue / 255, alpha / 255);
    }
}
