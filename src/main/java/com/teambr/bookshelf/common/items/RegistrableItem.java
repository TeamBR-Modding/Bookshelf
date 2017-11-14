package com.teambr.bookshelf.common.items;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.client.ModelHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistrableItem extends Item implements IRegistrable {


    @Override
    public void registerBlock(IForgeRegistry<Block> block) {

    }

    @Override
    public void registerItem(IForgeRegistry<Item> item) {
        item.register(this);
    }

    @Override
    public void registerRender() {
        ModelHelper.registerSimpleRenderItem(this);
    }
}
