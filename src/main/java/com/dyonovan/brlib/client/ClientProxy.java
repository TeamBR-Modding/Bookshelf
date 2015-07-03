package com.dyonovan.brlib.client;

import com.dyonovan.brlib.client.renderer.BasicBlockRenderer;
import com.dyonovan.brlib.common.CommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    public static int renderPass;

    public void init() {
        BasicBlockRenderer.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BasicBlockRenderer());
    }
}
