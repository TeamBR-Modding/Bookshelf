package com.teambr.bookshelf.client.gui;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.control.GuiComponentCheckBox;
import com.teambr.bookshelf.client.gui.component.control.GuiComponentSetNumber;
import com.teambr.bookshelf.client.gui.component.display.GuiComponentText;
import com.teambr.bookshelf.client.gui.component.display.GuiTabCollection;
import com.teambr.bookshelf.common.container.ContainerTestBlock;
import com.teambr.bookshelf.common.tiles.TileTestBlock;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiTestBlock extends GuiBase<ContainerTestBlock> {

    protected TileTestBlock tileEntity;
    public GuiTestBlock(InventoryPlayer player, TileTestBlock tileEntity) {
        super(new ContainerTestBlock(player, tileEntity), 175, 165, "Test Gui");

    }

    @Override
    public void addComponents() {

    }

    @Override
    public void addRightTabs(GuiTabCollection tabs) {
        //Priority Tab
        List<BaseComponent> priorityTab = new ArrayList<>();
        priorityTab.add(new GuiComponentSetNumber(26, 25, 40, 0, 0, 100) {
            @Override
            public void setValue(int value) {

            }
        });
        priorityTab.add(new GuiComponentText("Fuel Priority", 22, 7));
        tabs.addTab(priorityTab, 95, 100, new Color(255, 68, 51), new ItemStack(Blocks.anvil));
    }

    @Override
    public void addLeftTabs(GuiTabCollection tabs) {
        List<BaseComponent> testTab = new ArrayList<>();
        testTab.add(new GuiComponentCheckBox(10, 10, "Test", false) {
            @Override
            public void setValue(boolean bool) {

            }
        });
        tabs.addReverseTab(testTab, 95, 100, new Color(255, 0, 0), new ItemStack(Blocks.furnace));
    }
}
