package com.teambr.bookshelf.client;

import com.teambr.bookshelf.client.renderer.BasicBlockRenderer;
import com.teambr.bookshelf.common.CommonProxy;
import com.teambr.bookshelf.manager.ConfigManager;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;

public class ClientProxy extends CommonProxy {
    public static int renderPass;

    public void preInit() {
        BasicBlockRenderer.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BasicBlockRenderer());
    }

    public void init() {
            FMLInterModComms.sendMessage("Waila", "register",
                    "com.teambr.bookshelf.api.waila.WailaDataProvider.callbackRegister");
    }
}
