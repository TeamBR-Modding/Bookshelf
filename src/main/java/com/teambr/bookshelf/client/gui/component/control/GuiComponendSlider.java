package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiComponendSlider<V> extends BaseComponent {
    protected int width;
    protected int boxX;
    protected boolean isDragging;
    protected V currentSelected;
    protected List<V> selectables;

    //Locations on texture sheet
    private static final int BAR_U = 32;
    private static final int BAR_V = 0;
    private static final int BOX_U = 32;
    private static final int BOX_V = 3;

    public GuiComponendSlider(int x, int y, int length, List<V> data, int index) {
        super(x, y);
        width = length;
        boxX = xPos + 1;
        isDragging = false;
        selectables = data;
        currentSelected = data.get(index);
    }

    @Override
    public void initialize() {}

    /**
     * Called when something changes
     * @param value The current value
     */
    public abstract void onValueChanged(V value);

    /**
     * Called when the mouse is pressed
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseDown(int x, int y, int button) {
        isDragging = true;
        boxX = x;
        updateCurrentSelection();
    }

    /**
     * Called when the mouse button is over the component and released
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     */
    public void mouseUp(int x, int y, int button) {
        isDragging = false;
    }

    /**
     * Called when the user drags the component
     * @param x Mouse X Position
     * @param y Mouse Y Position
     * @param button Mouse Button
     * @param time How long
     */
    public void mouseDrag(int x, int y, int button, long time) {
        boxX = x;
        updateCurrentSelection();
    }

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        //Start the bar
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 4, 0);
        drawTexturedModalRect(0, 0, BAR_U, BAR_V + 1, 1, 1);

        //Render middle out to edge
        GL11.glPushMatrix();
        GL11.glTranslatef(1, -1, 0); //Move over and up
        GL11.glScaled(width - 2, 1, 0); //Scale to other end
        drawTexturedModalRect(0, 0, BAR_U + 1, BAR_V, 1, 3);
        GL11.glPopMatrix();

        //End the bar
        GL11.glTranslatef(width - 1, 0, 0);
        drawTexturedModalRect(0, 0, BAR_U + 2, BAR_V + 1, 1, 1);

        GL11.glPopMatrix();

        //Render the box
        GL11.glTranslatef(boxX - xPos, 0, 0);
        drawTexturedModalRect(0, 0, BOX_U, BOX_V, 4, 9);

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);

        //Render Current Selection
        GL11.glPushMatrix();
        String currentSelect = currentSelected.toString();
        GL11.glTranslated((width / 2) - (fontRenderer.getStringWidth(currentSelect) / 2), -8, 0);
        fontRenderer.drawString(currentSelect, 0, 0, 4210752);
        GL11.glPopMatrix();

        //Draw End points smaller
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0); //All movement from here on out must be X2 for scale

        GL11.glPushMatrix();
        String lower = selectables.get(0).toString();
        GL11.glTranslated(5 - (fontRenderer.getStringWidth(lower) / 2), -9, 0);
        fontRenderer.drawString(lower, 0, 0, 4210752);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        String upper = selectables.get(selectables.size() - 1).toString();
        GL11.glTranslated(((width * 2) - 8) - (fontRenderer.getStringWidth(upper) / 2), -9, 0);
        fontRenderer.drawString(upper, 0, 0, 4210752);
        GL11.glPopMatrix();

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    public void updateCurrentSelection() {
        if(boxX <= xPos)
            boxX = xPos + 1;
        else if(boxX >= xPos + width - 5)
            boxX = xPos + width - 5;

        int newPosition = (int) Math.floor(((boxX - (xPos + 1)) * (selectables.size() - 1)) / ((xPos + width - 5) - (xPos + 1)));
        currentSelected = selectables.get(newPosition);
        onValueChanged(currentSelected);
    }

    public V getCurrentSelected() {
        return currentSelected;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return (mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight()) || isDragging;
    }

    public static List<Integer> generateNumberList(int start, int end) {
        List<Integer> list = new ArrayList<>();
        for(int i = start; i <= end; i++)
            list.add(i);
        return list;
    }
}
