package com.teambr.bookshelf.client.gui.misc;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;

/**
 * This file was created for Bookshelf
 * <p>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public class TrackballWrapper {
    // Variables
    public int mouseButton, radius;
    public Trackball target;
    public boolean isDragging = true;

    public TrackballWrapper(int mouseButton, int radius) {
        this.mouseButton = mouseButton;
        this.radius = radius;

        target = new Trackball();
    }

    public void update(int mouseX, int mouseY) {
        float mx = (float) mouseX / (float) radius;
        float my = (float) mouseY / (float) radius;

        boolean buttonState = Mouse.isButtonDown(mouseButton);
        if(!isDragging && buttonState) {
            isDragging = true;
            target.startDrag(mx, my);
        } else if(isDragging && !buttonState) {
            isDragging = false;
            target.endDrag(mx, my);
        }

        target.applyTransform(mx, my, isDragging);
    }

    public void setTransform(Matrix4f transform) {
        target.lastTransform = transform;
    }
}
