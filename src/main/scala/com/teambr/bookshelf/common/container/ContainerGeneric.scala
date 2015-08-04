package com.teambr.bookshelf.common.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 04, 2015
 */
class ContainerGeneric extends Container {
    override def canInteractWith(playerIn: EntityPlayer): Boolean = true
}
