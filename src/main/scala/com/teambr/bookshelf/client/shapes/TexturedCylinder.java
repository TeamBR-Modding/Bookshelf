package com.teambr.bookshelf.client.shapes;

import net.minecraft.util.MathHelper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.GLU_INSIDE;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis "pauljoda"
 * @since 3/10/2016
 */
public class TexturedCylinder extends DrawableShape {

    @Override
    protected float cos(float r) {
        return MathHelper.cos(r);
    }

    @Override
    protected float sin(float r) {
        return MathHelper.sin(r);
    }

    /**
     * Draws the cylinder
     * @param radius The radius of the ball
     * @param slices How many slices, this is from pole to pole and around (like an orange)
     * @param stacks How many stack, this is how many panels to add to each slice, you need at least 2
     * @param minU Texture MinU
     * @param maxU Texture MaxU
     * @param minV Texture MinV
     * @param maxV Texture MaxV
     */
    public void draw(float radius, int slices, int stacks, float minU, float maxU, float minV, float maxV) {
        float rho, drho, theta, dtheta;
        float x, y, z;
        float nsign;
        float xTexCoord, yTexCoord;

        if (super.orientation == GLU_INSIDE) {
            nsign = -1.0f;
        } else {
            nsign = 1.0f;
        }

        float PI = (float) Math.PI;
        drho = PI / stacks;
        dtheta = 2.0f * PI / slices;

        float stackUSize = (maxU - minU) / (slices + 1);
        float stackVSize = (maxV - minV) / (stacks + 1);

        // draw intermediate stacks as quad strips
        for (int i = 0; i < stacks; i++) {
            rho = i * drho;
            glBegin(GL_QUAD_STRIP);
            for (int j = 0; j <= slices; j++) {
                theta = (j == slices) ? 0.0f : j * dtheta;
                x = -sin(theta);
                y = sin(rho);
                z = nsign * cos(rho);

                switch (textureMode) {
                    case PANEL:
                        xTexCoord = j % 2 == 0 ? minU  : maxU;
                        yTexCoord = minV;
                        break;
                    case WRAP :
                    default :
                        xTexCoord = (j % 2 == 0 ? minU + (j * stackUSize) + stackUSize: minU + (j * stackUSize) + stackUSize);
                        yTexCoord = minV + (i * stackVSize);
                }

                glTexCoord2f(xTexCoord, yTexCoord);
                glNormal3f(x * nsign, y * nsign, z * nsign);
                glVertex3f(x * radius, y * radius, z * radius);

                x = -sin(theta);
                y = sin(rho + drho);
                z = nsign * cos(rho + drho);

                switch (textureMode) {
                    case PANEL:
                        xTexCoord = j % 2 == 0 ? minU  : maxU;
                        yTexCoord = minV;
                        break;
                    case WRAP :
                    default :
                        xTexCoord = (j % 2 == 0 ? minU + (j * stackUSize) + stackUSize : minU + (j * stackUSize) + stackUSize);
                        yTexCoord =  minV + (i * stackVSize) + stackVSize;
                }

                glTexCoord2f(xTexCoord, yTexCoord);
                glNormal3f(x * nsign, y * nsign, z * nsign);
                glVertex3f(x * radius, y * radius, z * radius);
            }
            glEnd();
        }
    }
}