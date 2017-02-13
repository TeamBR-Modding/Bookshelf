package com.teambr.bookshelf.client.gui.misc

import org.lwjgl.input.Mouse
import org.lwjgl.util.vector.Matrix4f

/**
  * This file was created for com.teambr.bookshelf.Bookshelf
  *
  * com.teambr.bookshelf.Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/31/2016
  */
class TrackballWrapper(mouseButton : Int, radius : Int) {
    val target = new Trackball
    var isDragging : Boolean = true

    def update(mouseX : Int, mouseY : Int): Unit = {
        val mx : Float = mouseX.toFloat / radius.toFloat
        val my : Float = mouseY.toFloat / radius.toFloat

        val buttonState = Mouse.isButtonDown(mouseButton)
        if(!isDragging && buttonState) {
            isDragging = true
            target.startDrag(mx, my)
        } else if(isDragging && !buttonState) {
            isDragging = false
            target.endDrag(mx, my)
        }

        target.applyTransform(mx, my, isDragging)
    }

    def setTransform(transform : Matrix4f) : Unit = target.lastTransform = transform
}
