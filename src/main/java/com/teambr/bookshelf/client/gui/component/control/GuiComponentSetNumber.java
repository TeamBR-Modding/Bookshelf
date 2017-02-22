package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.helper.GuiHelper;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/13/2017
 */
public abstract class GuiComponentSetNumber extends BaseComponent {
    // Variables
    protected int width, u, v, value, floor, ceiling;
    protected int height = 16;
    protected GuiTextField textField;
    protected boolean upSelected, downSelected = false;

    /**
     * Creates the set number object
     *
     * IMPORTANT: You must create the up and down arrow and pass the u and v or the top left corner
     * It should look like the following in the texture sheet:
     * UN|US
     * DN|DS
     *
     * With UN and DN being the normal up and down and US and DS being the selected versions (when clicked)
     * The arrow buttons should be 11x8 pixels and all touching to form one big rectangle
     *
     * @param parent The parent GUI
     * @param x The x pos
     * @param y The y pos
     * @param texU The texture u location
     * @param texV The texture v location
     * @param value The starting value
     * @param lowestValue The lowest value
     * @param highestValue The highest value
     */
    public GuiComponentSetNumber(GuiBase<?> parent, int x, int y, int texU, int texV, int value, int lowestValue, int highestValue) {
        super(parent, x, y);
        this.u = texU;
        this.v = texV;
        this.value = value;
        this.floor = lowestValue;
        this.ceiling = highestValue;

        textField = new GuiTextField(0, fontRenderer, x, y, width - 10, height);
        textField.setText(String.valueOf(value));
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when the user sets the value or when the value is changed
     * @param value The value set by the user
     */
    protected abstract void setValue(int value);

    /*******************************************************************************************************************
     * BaseComponent                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(int x, int y, int button) {
        if(GuiHelper.isInBounds(x, y, xPos + width - 8, yPos - 1, xPos + width + 2, yPos + 7)) {
            upSelected = true;

            if(value < ceiling)
                value += 1;

            GuiHelper.playButtonSound();
            setValue(value);
            textField.setText(String.valueOf(value));
        } else if(GuiHelper.isInBounds(x, y, xPos + width - 8, yPos + 9, xPos + width + 2, yPos + 17)) {
            downSelected = true;

            if(value > floor)
                value -= 1;

            GuiHelper.playButtonSound();
            setValue(value);
            textField.setText(String.valueOf(value));
        }
        textField.mouseClicked(x, y, button);
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseUp(int x, int y, int button) {
        upSelected = downSelected = false;
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    @Override
    public void keyTyped(char letter, int keyCode) {
        if (Character.isLetter(letter) && (keyCode != 8 && keyCode != 109)) return;
        if (textField.isFocused())
            textField.textboxKeyTyped(letter, keyCode);
        if (textField.getText() == null || (textField.getText() == "") || !isNumeric(textField.getText())) {
            textField.setTextColor(0xE62E00);
            return;
        }
        if (keyCode == 13)
            textField.setFocused(false);
        textField.setTextColor(0xFFFFFF);
        if (Integer.valueOf(textField.getText()) > ceiling)
            textField.setText(String.valueOf(ceiling));
        else if (Integer.valueOf(textField.getText()) < floor)
            textField.setText(String.valueOf(floor));
        value = Integer.valueOf(textField.getText());
        setValue(value);
    }

    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 0);
        drawTexturedModalRect(width - 1, -1, upSelected  ? u + 11 : u, v, 11, 8);
        drawTexturedModalRect(width - 8, 9, downSelected ? u + 11 : u, v + 8, 11, 9);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        textField.drawTextBox();
        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // No Op
    }

    /**
     * Used to find how wide this is
     *
     * @return How wide the component is
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Used to find how tall this is
     *
     * @return How tall the component is
     */
    @Override
    public int getHeight() {
        return height;
    }

    /*******************************************************************************************************************
     * Accessors/Mutators                                                                                              *
     *******************************************************************************************************************/

    public void setWidth(int width) {
        this.width = width;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getValue() {
        return value;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCeiling() {
        return ceiling;
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public GuiTextField getTextField() {
        return textField;
    }

    public void setTextField(GuiTextField textField) {
        this.textField = textField;
    }
}
