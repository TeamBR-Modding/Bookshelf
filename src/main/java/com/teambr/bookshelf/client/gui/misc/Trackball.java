package com.teambr.bookshelf.client.gui.misc;

import com.teambr.bookshelf.util.RenderUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
public class Trackball {
    // Variables
    public Vector3f dragStart;
    public Matrix4f lastTransform = new Matrix4f();

    public Vector3f calculateSpherePoint(float x, float y) {
        Vector3f result = new Vector3f(x, y, 0);

        float sqrZ = 1 - Vector3f.dot(result, result);

        if(sqrZ > 0)
            result.z = (float) Math.sqrt(sqrZ);
        else
            result.normalise();

        return result;
    }

    public Matrix4f getTransform(float mouseX, float mouseY) {
        if(dragStart == null)
            return  lastTransform;

        Vector3f current = calculateSpherePoint(mouseX, mouseY);

        float dot = Vector3f.dot(dragStart, current);

        if(Math.abs(dot- 1) < 0.0001)
            return lastTransform;

        Vector3f axis = Vector3f.cross(dragStart, current, null);

        try {
            axis.normalise();
        } catch (IllegalStateException e){
            return lastTransform;
        } catch (Exception ignored) {}

        float angle = (float) (2 * Math.acos(dot));

        Matrix4f rotation = new Matrix4f();
        rotation.rotate(angle, axis);
        return Matrix4f.mul(rotation, lastTransform, null);
    }

    public void applyTransform(float mouseX, float mouseY, boolean isDragging) {
        RenderUtils.loadMatrix(isDragging ? getTransform(mouseX, mouseY) : lastTransform);
    }

    public void startDrag(float mouseX, float mouseY) {
        dragStart = calculateSpherePoint(mouseX, mouseY);
    }

    public void endDrag(float mouseX, float mouseY) {
        lastTransform = getTransform(mouseX, mouseY);
    }
}
