package com.teambr.bookshelf.common;

import com.teambr.bookshelf.annotation.IRegistrable;
import com.teambr.bookshelf.annotation.RegisteringBlock;
import com.teambr.bookshelf.annotation.RegisteringItem;
import com.teambr.bookshelf.events.RegistrationEvents;
import com.teambr.bookshelf.util.AnnotationUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Bookshelf - Java
 *
 * Bookshelf - Java is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 2/9/2017
 */
public class CommonProxy {

    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Item>  ITEMS  = new ArrayList<>();

    /**
     * Called on preInit
     */
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RegistrationEvents());
        findBookBlocks(event);
        findBookItems(event);
    }

    /**
     * Called on init
     */
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "com.teambr.bookshelf.api.waila.WailaModPlugin.registerServerCallback");
    }

    /**
     * Called on postInit
     */
    public void postInit(FMLPostInitializationEvent event) {}


    private void findBookBlocks(FMLPreInitializationEvent event) {
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringBlock.class).stream().filter(IRegistrable.class::isAssignableFrom).forEach(aClass -> {
            try {
                BLOCKS.add((Block) aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        event.getModLog().info("Found " + BLOCKS.size() + " RegisteringBlocks");
    }

    private void findBookItems(FMLPreInitializationEvent event) {
        AnnotationUtils.getAnnotatedClasses(event.getAsmData(), RegisteringItem.class).stream().filter(IRegistrable.class::isAssignableFrom).forEach(aClass -> {
            try {
                ITEMS.add((Item) aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        event.getModLog().info("Found " + ITEMS.size() + " RegisteringItems");
    }
}
