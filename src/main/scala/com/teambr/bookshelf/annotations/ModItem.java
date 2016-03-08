package com.teambr.bookshelf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This file was created for Bookshelf
 * <p/>
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Annotation to pick up items to be registered. You must pull them from Bookshelf.itemsToRegister and register
 * all that you have added. DON'T REGISTER OTHER MOD'S ITEMS!
 *
 * @author Paul Davis "pauljoda"
 * @since 3/8/2016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModItem {

    /**
     * Define the mod id, useful when only wanting to register certain mod items, you should only register your own
     * items
     * @return MODID
     */
    String modid();
}
