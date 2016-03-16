package tests

import java.awt.Color

import com.teambr.bookshelf.client.gui.GuiBase
import com.teambr.bookshelf.client.gui.component.BaseComponent
import com.teambr.bookshelf.client.gui.component.display._
import com.teambr.bookshelf.common.tiles.traits.Inventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.{FluidStack, FluidRegistry, FluidTank}

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
        components += new GuiComponentColoredZone(8, 48, 20, 20, new Color(255, 0, 0, 150))

        val tank = new FluidTank(1000)
        tank.fill(new FluidStack(FluidRegistry.LAVA, 1000), true)
        components += new GuiComponentFluidTank(130, 5, 16, 50, tank)
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
            new GuiComponentLongText(10, 25, "§6Electric Furnace \n §r§fThe electric furnace is simply a furnace that runs on RF. It will smelt anything a vanilla furnace would. \n\n §r§n§2Upgrades:§r \n\n §6Processors: §r \n §fEach processor removes 24 ticks (a little over a second from the cook time§r \n\n §6Hard Drives: §r \n §fEach hard drive multiplies the max energy stored by 10 times the number of hard drives§r \n\n §6Control Upgrade: §r \n §fThe control upgrade gives you access to Redstone controls. High means you need a redstone signal, Low means you need no redstone signal, and Disabled ignores redstone signals§r\n\n§6Expansion Upgrade: §r \n §fThe expansion upgrade allows you to configure the automatic input and output of the machine§r",
                80, 60, textScale = 50))
            , 100, 100, new Color(0, 0, 0), new ItemStack(Blocks.anvil))
    }
}
