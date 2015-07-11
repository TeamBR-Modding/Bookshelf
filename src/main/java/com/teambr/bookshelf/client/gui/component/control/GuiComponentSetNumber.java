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
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {
        if(GuiHelper.isInBounds(x, y, xPos + width - 10, yPos, xPos + width, yPos + 8)) {
            upSelected = true;
            if(value <= ceiling)
                value++;
            setValue(value);
        }
        else if(GuiHelper.isInBounds(x, y, x + width - 10, yPos + 8, xPos + width, yPos + 16)) {
            downSelected = true;
            if(value >= floor)
                value--;
            setValue(value);
        } else
            textField.mouseClicked(x, y, button);
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
        if(Character.getNumericValue(letter) == -1) return;
        if(!textField.getText().equals("") && keyCode == 109) return;
        textField.textboxKeyTyped(letter, keyCode);
        setValue(Integer.valueOf(textField.getText()));
    }

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos + guiLeft, yPos + guiTop, 0);
        RenderUtils.bindGuiComponentsSheet();

        drawTexturedModalRect(width - 10, 0, upSelected ? 67 : 56, 0, 10, 8);
        drawTexturedModalRect(width - 10, 8, downSelected ? 67 : 56, 8, 10, 8);

        textField.drawTextBox();

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {}

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
