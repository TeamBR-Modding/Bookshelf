package com.teambr.bookshelf.client;

import com.teambr.bookshelf.client.renderer.BasicBlockRenderer;
import com.teambr.bookshelf.common.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    public static int renderPass;

    public void init() {
        BasicBlockRenderer.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BasicBlockRenderer());
    }
}
