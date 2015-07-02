package com.dyonovan.brlib.util;

import com.dyonovan.brlib.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {
    public static final ResourceLocation GUI_COMPONENTS = new ResourceLocation(Constants.MODID, "textures/gui/guiComponents.png");
    public static final ResourceLocation MC_BLOCKS = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation MC_ITEMS = new ResourceLocation("textures/atlas/items.png");

    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    public static void bindMinecraftItemSheet() {
        bindTexture(MC_ITEMS);
    }

    public static void bindMinecraftBlockSheet() {
        bindTexture(MC_BLOCKS);
    }

    public static void bindGuiComponentsSheet() {
        bindTexture(GUI_COMPONENTS);
    }
}