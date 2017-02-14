package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.ClientUtils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

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
public abstract class GuiComponentTextBox extends BaseComponent {
    // Variables
    protected int width, height;
    protected GuiTextField textField;

    /**
     * Creates the text box
     * @param parent The parent gui
     * @param x The x pos
     * @param y The y pos
     * @param boxWidth The text box width
     * @param boxHeight The text box height, usually 16
     * @param defaultLabel The default label, will translate, can be null
     */
    public GuiComponentTextBox(GuiBase<?> parent, int x, int y, int boxWidth, int boxHeight, @Nullable String defaultLabel) {
        super(parent, x, y);
        this.width = boxWidth;
        this.height = boxHeight;

        textField = new GuiTextField(0, fontRenderer, x, y, width, height);
        if(defaultLabel != null)
            textField.setText(ClientUtils.translate(defaultLabel));
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when the value in the text box changes
     * @param value The current value
     */
    protected abstract void fieldUpdated(String value);

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
        textField.mouseClicked(x, y, button);
        if(button == 1  && textField.isFocused()) {
            textField.setText("");
            fieldUpdated(textField.getText());
        }
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    @Override
    public void keyTyped(char letter, int keyCode) {
        if(textField.isFocused()) {
            textField.textboxKeyTyped(letter, keyCode);
            fieldUpdated(textField.getText());
        }
    }


    /**
     * Called to render the component
     */
    @Override
    public void render(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        textField.drawTextBox();
        GlStateManager.disableAlpha();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GlStateManager.popMatrix();
    }

    /**
     * Called after base render, is already translated to guiLeft and guiTop, just move offset
     */
    @Override
    public void renderOverlay(int guiLeft, int guiTop, int mouseX, int mouseY) {
        // NO OP
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

    public GuiTextField getTextField() {
        return textField;
    }

    public void setTextField(GuiTextField textField) {
        this.textField = textField;
    }
}
