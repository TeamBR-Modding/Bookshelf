package tests

import java.awt.Color

import com.teambr.bookshelf.client.gui.{GuiColor, GuiBase}
import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.display.{GuiComponentLongText, GuiComponentText, GuiTabCollection}
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
  * @author Paul Davis pauljoda
  * @since August 04, 2015
  */
class GuiTest(player : EntityPlayer)
        extends GuiBase[ContainerTest](new ContainerTest(player.inventory, new Inventory {
            override def initialSize: Int = 4
        }), 175, 165, "title.test") {


    /**
      * This will be called after the GUI has been initialized and should be where you add all components.
      */
    override def addComponents(): Unit = {

    }

    /**
      * Adds the tabs to the right. Overwrite this if you want tabs on your GUI
      *
      * @param tabs List of tabs, put GuiTabs in
      */
    override def addRightTabs(tabs: GuiTabCollection) : Unit = {
        tabs.addTab(List[BaseComponent](new GuiComponentText("test", 25, 8)), 100, 100, new Color(150, 150, 150), new ItemStack(Blocks.anvil))
    }

    override def addLeftTabs(tabs : GuiTabCollection) : Unit = {
        tabs.addReverseTab(List[BaseComponent](
            new GuiComponentLongText(10, 25, GuiColor.YELLOW + "The grinder " + GuiColor.WHITE + "\n\n\n\n\n is an early game ore doubling mechanic. To use, place ores in the top three slots. Then, place any vanilla pressure plate on top. The better the plate, the faster it goes. Jump on the pressure plate to make the grinder start grinding ores. The higher you jump, the more it progresses. Ores will move into the center slot once you start the grinding process. You can automate placing items into the inventory, but you can not pull items out of the output slots.",
                80, 60, textScale = 50))
            , 100, 100, new Color(0, 0, 0), new ItemStack(Blocks.anvil))

    }
}
