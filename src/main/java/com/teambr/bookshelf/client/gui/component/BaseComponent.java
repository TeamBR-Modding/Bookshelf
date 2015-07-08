package com.teambr.bookshelf.client.gui.component;

import com.teambr.bookshelf.client.gui.component.listeners.IMouseEventListener;
import net.minecraft.client.gui.Gui;

public abstract class BaseComponent extends Gui {

    protected int xPos;
    protected int yPos;

    protected IMouseEventListener mouseEventListener;

    public BaseComponent(int x, int y) {
        xPos = x;
        yPos = y;

        initialize();
    }

    /**
     * Called from constructor. Set up anything needed here
     */
    public abstract void initialize();

    /**
     * Called to render the component
     */
    public abstract void render(int guiLeft, int guiTop);

    /**
     * Called after base render
     */
    public abstract void renderOverlay(int guiLeft, int guiTop);

    /**
     * Used to find how wide this is
     * @return How wide the component is
     */
    public abstract int getWidth();

    /**
     * Used to find how tall this is
     * @return How tall the comonent is
     */
    public abstract int getHeight();

    /**
     * Set the handler for the mouse input
     * @param listener The listener to set
     */
    public void setMouseEventListener(IMouseEventListener listener) {
        mouseEventListener = listener;
    }

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {
        if(mouseEventListener != null) mouseEventListener.onMouseDown(this, x, y, button);
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        if(mouseEventListener != null) mouseEventListener.onMouseUp(this, x, y, button);
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    public void mouseDrag(int x, int y, int button, long time) {
        if(mouseEventListener != null) mouseEventListener.onMouseDrag(this, x, y, button, time);
    }

    /**
     * Used to check if the mouse if currently over the component
     *
     * You must have the getWidth() and getHeight() functions defined for this to work properly
     *
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @return True if mouse if over the component
     */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight();
    }
}