package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.helpers.GuiHelper;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentSetNumber extends BaseComponent {
    private int width;
    private int height;
    private int value;
    private int floor;
    private int ceiling;

    private GuiTextField textField;
    private boolean upSelected = false;
    private boolean downSelected = false;

    public GuiComponentSetNumber(int x, int y, int w, int startingValue, int lower, int higher) {
        super(x, y);
        width = w;
        height = 16;
        value = startingValue;
        floor = lower;
        ceiling = higher;
        textField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, x, y, width - 10, height);
        textField.setText(String.valueOf(startingValue));
    }

    public abstract void setValue(int i);

    @Override
    public void initialize() {}

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int mouseX, int y, int button) {
        if(GuiHelper.isInBounds(mouseX, y, xPos + width - 8, yPos - 1, xPos + width + 2, yPos + 7)) {
            upSelected = true;
            if(value < ceiling)
                value++;
            GuiHelper.playButtonSound();
            setValue(value);
            textField.setText(String.valueOf(value));
        }
        else if(GuiHelper.isInBounds(mouseX, y, xPos + width - 8, yPos + 9, xPos + width + 2, yPos + 17)) {
            downSelected = true;
            if(value > floor)
                value--;
            GuiHelper.playButtonSound();
            setValue(value);
            textField.setText(String.valueOf(value));
        } else if(GuiHelper.isInBounds(mouseX, y, xPos, yPos, xPos + width - 8, yPos + 16)) {
            textField.mouseClicked(mouseX, y, button);
        } else
            textField.setFocused(false);
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        upSelected = downSelected = false;
    }

    /**
     * Used when a key is pressed
     * @param letter The letter
     * @param keyCode The code
     */
    public void keyTyped(char letter, int keyCode) {
        if(Character.isLetter(letter) && (keyCode != 8 && keyCode != 109)) return;
        textField.textboxKeyTyped(letter, keyCode);
        if(textField.getText() == null || textField.getText().equals("") || !isNumeric(textField.getText())) {
            textField.setTextColor(0xE62E00);
            return;
        }
        if(keyCode == 13)
            textField.setFocused(false);
        textField.setTextColor(0xFFFFFF);
        if(Integer.valueOf(textField.getText()) > ceiling)
            textField.setText(String.valueOf(ceiling));
        else if(Integer.valueOf(textField.getText()) < floor)
            textField.setText(String.valueOf(floor));
        value = Integer.valueOf(textField.getText());
        setValue(value);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        drawTexturedModalRect(width - 8, -1, upSelected ? 67 : 56, 0, 11, 8);
        drawTexturedModalRect(width - 8, 9, downSelected ? 67 : 56, 8, 11, 8);

        GL11.glPopMatrix();

        GL11.glPushMatrix();

        textField.drawTextBox();

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {}

    @Override
    public int getWidth() {
        return width + 2;
    }

    @Override
    public int getHeight() {
        return height + 1;
    }
}
