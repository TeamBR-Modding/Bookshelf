package com.teambr.bookshelf.common.events;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.common.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistrationEvents {

    @SubscribeEvent
    public void addItems(RegistryEvent.Register<Item> event) {
        CommonProxy.BLOCKS.forEach(registrable -> registrable.registerItem(event.getRegistry()));
        CommonProxy.ITEMS.forEach(registrable -> registrable.registerItem(event.getRegistry()));
        if (FMLCommonHandler.instance().getSide().isClient()) {
            CommonProxy.BLOCKS.forEach(IRegistrable::registerRender);
            CommonProxy.ITEMS.forEach(IRegistrable::registerRender);
        }
    }

    @SubscribeEvent
    public void addBlocks(RegistryEvent.Register<Block> event) {
        CommonProxy.BLOCKS.forEach(registrable -> registrable.registerBlock(event.getRegistry()));
    }

}
