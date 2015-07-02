package com.dyonovan.brlib.client.gui.component.listeners;

import com.dyonovan.brlib.client.gui.component.BaseComponent;

public interface IMouseEventListener {

    /**
     * Called when the mouse clicks on the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    public void onMouseDown(BaseComponent component, int mouseX, int mouseY, int button);

    /**
     * Called when the mouse releases the component
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     */
    public void onMouseUp(BaseComponent component, int mouseX, int mouseY, int button);

    /**
     * Called when the mouse drags an item
     * @param component The component to be clicked
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param button Which button was clicked
     * @param time How long its been clicked
     */
    public void onMouseDrag(BaseComponent component, int mouseX, int mouseY, int button, long time);
}