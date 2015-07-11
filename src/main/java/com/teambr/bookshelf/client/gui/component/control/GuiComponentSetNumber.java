package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public abstract class GuiComponentSetNumber extends BaseComponent {
    private int width;
    private int height;
    private int value;

    private GuiTextField textField;
    private boolean upSelected = false;
    private boolean downSelected = false;

    public GuiComponentSetNumber(int x, int y, int w, int startingValue) {
        super(x, y);
        width = w;
        height = 16;
        value = startingValue;
        textField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, x, y, width - 10, height);
        textField.setText(String.valueOf(startingValue));
    }

    public abstract void setValue(int i);

    @Override
    public void initialize() {}

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {}

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        if(mouseEventListener != null) mouseEventListener.onMouseUp(this, x, y, button);
    }

    @Override
    public void render(int guiLeft, int guiTop) {

    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
