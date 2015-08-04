package tests

import java.awt.Color

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.display.{GuiComponentText, GuiTabCollection}
import com.teambr.bookshelf.common.container.Inventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

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
class GuiTest(player : EntityPlayer)
        extends GuiBase[ContainerTest](new ContainerTest(player.inventory, new Inventory("test", false, 3)), 175, 165, "title.test") {


    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    override def addComponents(): Unit = {
    }

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiTabs in
     */
    override def addRightTabs(tabs: GuiTabCollection) : Unit = {
        tabs.addTab(List[BaseComponent](new GuiComponentText("test", 25, 8)), 100, 100, new Color(150, 150, 150), new ItemStack(Blocks.anvil))
    }
}
