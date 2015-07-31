package com.teambr.bookshelf.client.gui;

import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.control.*;
import com.teambr.bookshelf.client.gui.component.display.GuiComponentText;
import com.teambr.bookshelf.client.gui.component.display.GuiTabCollection;
import com.teambr.bookshelf.common.container.ContainerTestBlock;
import com.teambr.bookshelf.common.tiles.TileTestBlock;
import com.teambr.bookshelf.helpers.GuiHelper;
import com.teambr.bookshelf.lib.Reference;
import com.teambr.bookshelf.manager.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuiTestBlock extends GuiBase<ContainerTestBlock> {

    protected TileTestBlock tileEntity;

    public GuiTestBlock(InventoryPlayer player, TileTestBlock tileEntity) {
        super(new ContainerTestBlock(player, tileEntity), 175, 165, "Test Gui");
        this.tileEntity = tileEntity;
    }

    @Override
    public void addComponents() {
        components.add(new GuiComponentSlider<Integer>(15, 50, 80, GuiComponentSlider.generateNumberList(0, 16), 8) {
            @Override
            public void onValueChanged(Integer value) {

            }
        });
    }

    @Override
    public void addRightTabs(GuiTabCollection tabs) {
        //Priority Tab
        List<BaseComponent> priorityTab = new ArrayList<>();
        priorityTab.add(new GuiComponentSetNumber(26, 25, 40, 0, 0, 100) {
            @Override
            public void setValue(int value) {

            }

            @Override
            public List<String> getDynamicToolTip(int x, int y) {
                return Collections.singletonList("HELLO");
            }
        });
        priorityTab.add(new GuiComponentText("Fuel Priority", 22, 7) {
            @Override
            public List<String> getDynamicToolTip(int mouseX, int mouseY) {
                return Collections.singletonList("HELLO");
            }
        });
        tabs.addTab(priorityTab, 95, 100, new Color(255, 68, 51), new ItemStack(Blocks.anvil));

        //Priority Tab
        List<BaseComponent> a = new ArrayList<>();
        a.add(new GuiComponentSetNumber(26, 25, 40, 0, 0, 100) {
            @Override
            public void setValue(int value) {

            }
        });
        a.add(new GuiComponentText("Fuel Priority", 22, 7));
        tabs.addTab(a, 95, 100, new Color(255, 68, 51), new ItemStack(Blocks.anvil));

        //Priority Tab
        List<BaseComponent> v = new ArrayList<>();
        v.add(new GuiComponentSetNumber(26, 25, 40, 0, 0, 100) {
            @Override
            public void setValue(int value) {

            }
        });
        v.add(new GuiComponentText("Fuel Priority", 22, 7));
        tabs.addTab(v, 95, 100, new Color(255, 68, 51), new ItemStack(Blocks.anvil));
        tabs.getTabs().get(1).setToolTip(Arrays.asList("Hello", "Testing"));
    }

    @Override
    public void addLeftTabs(GuiTabCollection tabs) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        if (fr != null) {
            String current = Reference.VERSION;
            String remote = ConfigManager.lastVersion;
            List<BaseComponent> updateTab = new ArrayList<>();
            updateTab.add(new GuiComponentText(GuiHelper.GuiColor.YELLOW + "Version Info", 9, 7));
            updateTab.add(new GuiComponentText(GuiHelper.GuiColor.BLUE + "Current Version", 50 - fr.getStringWidth("Current Version") / 2, 20));
            updateTab.add(new GuiComponentText((current.equalsIgnoreCase(remote) ? GuiHelper.GuiColor.GREEN : GuiHelper.GuiColor.RED) + current, 50 - fr.getStringWidth(current) / 2, 30));
            updateTab.add(new GuiComponentText(GuiHelper.GuiColor.BLUE + "Remote Version", 50 - fr.getStringWidth("Remote Version") / 2, 40));
            updateTab.add(new GuiComponentText(GuiHelper.GuiColor.GREEN + remote, 50 - fr.getStringWidth(remote) / 2, 50));
            updateTab.add(new GuiComponentButton(10, 60, 80, 20, "Get Update") {
                @Override
                public void doAction() {
                    try {
                        Desktop.getDesktop().browse(URI.create(ConfigManager.updateURL));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            updateTab.add(new GuiComponentCheckBox(5, 85, "Hide tab?", false) {
                @Override
                public void setValue(boolean bool) {

                }
            });
            tabs.addReverseTab(updateTab, 100, 100, new Color(255, 168, 86), new ItemStack(Blocks.command_block));
            tabs.getTabs().get(0).setToolTip(Collections.singletonList("Version Status"));
        }

    }
}
