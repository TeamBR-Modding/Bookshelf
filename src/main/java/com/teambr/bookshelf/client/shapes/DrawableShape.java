package com.teambr.bookshelf.client.shapes;

import org.lwjgl.util.glu.Quadric;

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis "pauljoda"
  * @since 3/10/2016
  */
public class DrawableShape extends Quadric {
    public enum TEXTURE_MODE {
        PANEL,
        WRAP
    }

    protected TEXTURE_MODE textureMode = TEXTURE_MODE.WRAP;

    /**
      * Used to set the current draw mode
 *
      * @param mode The new mode
      */
    public void setTextureMode(TEXTURE_MODE mode) { textureMode = mode; }

    /**
      * Get the current draw mode
 *
      * @return The current draw mode
      */
    public TEXTURE_MODE getTextureMode() { return textureMode; }
}
