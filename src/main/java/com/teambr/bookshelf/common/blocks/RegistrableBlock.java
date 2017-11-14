package com.teambr.bookshelf.common.blocks;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.client.ModelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;


public class RegistrableBlock extends Block implements IRegistrable {

    public static RegistrableBlock INSTANCE;

    public RegistrableBlock(Material materialIn) {
        super(materialIn);
        INSTANCE = this;
    }

    @Override
    public void registerBlock(IForgeRegistry<Block> block) {
        block.register(this);
    }

    @Override
    public void registerItem(IForgeRegistry<Item> item) {
        item.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerRender() {
        ModelHelper.registerSimpleRenderBlock(this);
    }
}
