package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.helpers.GuiHelper;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentButton extends BaseComponent {

    private String text;
    private int width, height;
    private boolean isOver;

    protected static final int u = 0;
    protected static final int v = 100;

    NinePatchRenderer renderer = new NinePatchRenderer(u, v, 4);
    NinePatchRenderer rendererOver = new NinePatchRenderer(u, v + 9, 4);

    /**
     * Button Constructor
     * @param x X Position
     * @param y Y Position
     * @param width Button Width
     * @param height Button Height
     * @param label String to display in button
     */
    public GuiComponentButton(int x, int y, int width, int height, String label) {
        super(x, y);
        text = StatCollector.translateToLocal(label);
        this.width = width;
        this.height = height;
        this.isOver = false;
    }

    /**
     * Called when button is pressed
     */
    public abstract void doAction();

    @Override
    public void initialize() {

    }

    /**
     * Called when the mouse is pressed
     * @param mouseX Mouse X Position
     * @param mouseY Mouse Y Position
     * @param button Mouse Button
     */
    @Override
    public void mouseDown(int mouseX, int mouseY, int button) {
        if(mouseX >= xPos  && mouseX < xPos + width && mouseY >= yPos && mouseY < yPos + height) {
            GuiHelper.playButtonSound();
            doAction();
        }
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        if (mouseX >= xPos && mouseX < xPos + getWidth() && mouseY >= yPos && mouseY < yPos + getHeight()) {
            isOver = true;
            return true;
        }
        isOver = false;
        return false;
    }

    @Override
    public void render(int guiLeft, int guiTop) {

        GL11.glPushMatrix();
        RenderUtils.prepareRenderState();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();
        if (isOver)
            rendererOver.render(this, 0, 0, width, height);
        else
            renderer.render(this, 0, 0, width, height);

        RenderUtils.restoreRenderState();
        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {
        GL11.glPushMatrix();
        int size = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        GL11.glTranslated(xPos + (width / 2 - size / 2), yPos + 6, 0);
        Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
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
