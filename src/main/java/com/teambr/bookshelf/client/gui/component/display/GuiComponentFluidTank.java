package com.teambr.bookshelf.client.gui.component.display;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.NinePatchRenderer;
import com.teambr.bookshelf.helpers.GuiHelper;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public class GuiComponentFluidTank extends BaseComponent {
    protected static final int u = 0;
    protected static final int v = 80;

    protected int width;
    protected int height;

    protected FluidTank tank;

    NinePatchRenderer renderer = new NinePatchRenderer(u, v, 3);

    public GuiComponentFluidTank(int x, int y, int w, int h, FluidTank fluidTank) {
        super(x, y);
        this.width = w;
        this.height = h;
        this.tank = fluidTank;
    }


    @Override
    public void initialize() {}

    @Override
    public void render(int guiLeft, int guiTop) {
        GL11.glPushMatrix();

        GL11.glTranslated(xPos, yPos, 0);
        RenderUtils.bindGuiComponentsSheet();

        renderer.render(this, 0, 0, width, height);
        GuiHelper.renderFluid(tank, 1, height - 1, height - 2, width - 2);


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
