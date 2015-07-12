package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentCheckBox extends BaseComponent {

    private String text;
    private boolean isSelected;

    /**
     * Checkbox Constructor
     * @param x X Position
     * @param y Y Position
     * @param label The string to display before
     * @param startValue The starting value
     */
    public GuiComponentCheckBox(int x, int y, String label, boolean startValue) {
        super(x, y);
        text = StatCollector.translateToLocal(label);
        isSelected = startValue;
    }

    /**
     * Called when there is a change in state, use this to set the value on what this controls
     * @param bool The current value of this component
     */
    public abstract void setValue(boolean bool);

    @Override
    public void initialize() {}

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int mouseX, int mouseY, int button) {
        if(mouseX >= xPos + 50 && mouseX < xPos + 60 && mouseY >= yPos && mouseY < yPos + 10) {
            isSelected = !isSelected;
            setValue(isSelected);
        }
    }

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(50, 0, 0);
        drawTexturedModalRect(0, 0, isSelected ? 48 : 40, 0, 8, 8);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {
        GL11.glPushMatrix();
        GL11.glTranslated(xPos, yPos, 0);
        Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, 0x000000);
        GL11.glPopMatrix();
    }

    @Override
    public int getWidth() {
        return 58;
    }

    @Override
    public int getHeight() {
        return 8;
    }
}
