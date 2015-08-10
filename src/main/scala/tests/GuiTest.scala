package tests

import java.awt.Color

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.control.GuiComponentCheckBox
import com.teambr.bookshelf.client.gui.component.display.{ GuiComponentText, GuiTabCollection }
import com.teambr.bookshelf.common.tiles.traits.Inventory
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
        extends GuiBase[ContainerTest](new ContainerTest(player.inventory, new Inventory {
            override var inventoryName: String = "test"

            /**
             * Does this inventory has a custom name
             * @return True if there is a name (localized)
             */
            override def hasCustomName: Boolean = false

            override def initialSize: Int = 3
        }), 175, 165, "title.test") {


    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    override def addComponents(): Unit = {
        components += new GuiComponentCheckBox(20, 20, "import", true) {
            override def setValue(bool : Boolean) = {

            }
        }

        components += new GuiComponentCheckBox(20, 35, "export", true) {
            override def setValue(bool : Boolean) = {

            }
        }
        components += new GuiComponentCheckBox(20, 50, "auto", true) {
            override def setValue(bool : Boolean) = {
            }
        }
    }

    /**
     * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
     * @param tabs List of tabs, put GuiTabs in
     */
    override def addRightTabs(tabs: GuiTabCollection) : Unit = {
        tabs.addTab(List[BaseComponent](new GuiComponentText("test", 25, 8)), 100, 100, new Color(150, 150, 150), new ItemStack(Blocks.anvil))
    }
}
