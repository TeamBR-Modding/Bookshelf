package com.teambr.bookshelf.client.gui.component.control;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.helpers.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class GuiComponentButton extends BaseComponent {

    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    private String text;
    private int width, height;

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
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        //RenderUtils.bindGuiComponentsSheet();
        Minecraft.getMinecraft().getTextureManager().bindTexture(buttonTextures);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        this.drawTexturedModalRect(0, 0, 0, 46 + 1 * 20, this.width / 2, this.height);
        //this.drawTexturedModalRect(0, this.width / 2, 0, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public void renderOverlay(int guiLeft, int guiTop) {
        GL11.glPushMatrix();
        int size = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
        GL11.glTranslated(xPos + (width / 2 - size / 2), yPos + 8, 0);
        Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, 0x000000);
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

    public void setText(String text) {
        this.text = text;
    }
}
