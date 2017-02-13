package com.teambr.bookshelf.client.gui.misc

import java.lang.IllegalStateException

import com.google.common.base.Preconditions
import com.teambr.bookshelf.util.RenderUtils
import org.lwjgl.input.Mouse
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

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
class Trackball {

    var dragStart : Vector3f = _
    var lastTransform : Matrix4f = new Matrix4f()

    def calculateSpherePoint(x : Float, y : Float) : Vector3f = {
        val result = new Vector3f(x, y, 0)

        val sqrZ = 1 - Vector3f.dot(result, result)

        if(sqrZ > 0) result.z = Math.sqrt(sqrZ).toFloat
        else result.normalise()

        result
    }

    def getTransform(mouseX : Float, mouseY : Float) : Matrix4f = {
        if(dragStart == null) return lastTransform
        val current = calculateSpherePoint(mouseX, mouseY)

        val dot = Vector3f.dot(dragStart, current)
        if(Math.abs(dot - 1) < 0.0001) return lastTransform

        val axis = Vector3f.cross(dragStart, current, null)

        try {
            axis.normalise()
        } catch  { //Zero length vector
            case zeroLength : IllegalStateException => return lastTransform
            case e : Exception =>
        }

        val angle = 2.toFloat * Math.acos(dot).toFloat

        val rotation = new Matrix4f()
        rotation.rotate(angle, axis)
        Matrix4f.mul(rotation, lastTransform, null)
    }

    def applyTransform(mouseX : Float, mouseY : Float, isDragging : Boolean) : Unit = {
        RenderUtils.loadMatrix(if(isDragging) getTransform(mouseX, mouseY) else lastTransform)
    }

    def startDrag(mouseX : Float, mouseY : Float) : Unit = {
        dragStart = calculateSpherePoint(mouseX, mouseY)
    }

    def endDrag(mouseX : Float, mouseY : Float) : Unit = {
        lastTransform = getTransform(mouseX, mouseY)
    }
}
