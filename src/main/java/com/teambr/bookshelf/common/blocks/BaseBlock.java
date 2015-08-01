package com.teambr.bookshelf.common.blocks;

import com.teambr.bookshelf.Bookshelf;
import com.teambr.bookshelf.client.ClientProxy;
import com.teambr.bookshelf.client.renderer.BasicBlockRenderer;
import com.teambr.bookshelf.collections.BlockTextures;
import com.teambr.bookshelf.common.blocks.rotation.IRotation;
import com.teambr.bookshelf.common.blocks.rotation.NoRotation;
import com.teambr.bookshelf.common.tiles.IOpensGui;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BaseBlock extends BlockContainer {
    protected String blockName;
    protected Class<? extends TileEntity> tileEntity;

    @SideOnly(Side.CLIENT)
    protected BlockTextures textures;

    /**
     * Used as a common class for all blocks. Makes things a bit easier
     * @param mat What material the block should be
     * @param name The unlocalized name of the block : Must be format "MODID:name"
     * @param tile Should the block have a tile, pass the class
     */
    protected BaseBlock(Material mat, String name, Class<? extends TileEntity> tile) {
        super(mat);
        blockName = name;
        tileEntity = tile;

        this.setBlockName(blockName);
        this.setCreativeTab(getCreativeTab());
        this.setHardness(getHardness());
    }

    /**
     * Used to change the hardness of a block, but will default to 2.0F if not overwritten
     * @return The hardness value, default 2.0F
     */
    protected float getHardness() {
        return 2.0F;
    }

    /**
     * Used to tell if this should be in a creative tab, and if so which one
     * @return Null if none, defaults to the main Modular Systems Tab
     */
    protected CreativeTabs getCreativeTab() {
        return null;
    }

    /**
     * Called when the block is activated. We are using it to open our GUI
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if(!world.isRemote && world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IOpensGui) {
            player.openGui(Bookshelf.instance, 0, world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        generateDefaultTextures(iconRegister);
    }

    /**
     * Used to get the block textures object
     * @return {@link BlockTextures} object for this block
     */
    @SideOnly(Side.CLIENT)
    public BlockTextures getBlockTextures() {
        return textures;
    }

    /**
     * Used to add the textures for the block. Uses block name by default
     *
     * Initialize the {@link BlockTextures} object here
     * @param iconRegister Icon Registry
     */
    public void generateDefaultTextures(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(blockName);
        textures = new BlockTextures(iconRegister, blockName);
    }

    /**
     * Used to set the values needed for the block's rotation on placement
     * @return The rotation class needed, none by default
     */
    public IRotation getDefaultRotation() {
        return new NoRotation();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack itemStack) {
        //Calls upon the default rotation to set the meta
        world.setBlockMetadataWithNotify(x, y, z, getDefaultRotation().getMetaFromEntity(world, x, y, z, livingBase, itemStack), 2);
    }

    //The sides are: Bottom (0), Top (1), North (2), South (3), West (4), East (5).
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        switch(side) {
            case 0 :
                return textures.getDown(metadata, getDefaultRotation());
            case 1 :
                return textures.getUp(metadata, getDefaultRotation());
            case 2 :
                return textures.getNorth(metadata, getDefaultRotation());
            case 3 :
                return textures.getSouth(metadata, getDefaultRotation());
            case 4 :
                return textures.getWest(metadata, getDefaultRotation());
            case 5 :
                return textures.getEast(metadata, getDefaultRotation());
            default :
                return this.blockIcon;
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return BasicBlockRenderer.renderID;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        //Set the static var in the client proxy
        ClientProxy.renderPass = pass;
        //the block can render in both passes, so return true always
        return true;
    }

    @Override
    public int getRenderBlockPass() {
        return getBlockTextures().getOverlay() != null ? 1 : 0;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if(tileEntity != null) {
            try{
                return tileEntity.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
        dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    /**
     * Empty Contents of an Inventory into world on Block Break
     * @param world
     * @param x X-Coord in world
     * @param y Y-Coord in world
     * @param z Z-Coord in world
     */
    public void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) world.getTileEntity(x, y, z);
        if (inventory == null) return;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world,
                        x + rx, y + ry, z + rz,
                        new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);

                itemStack.stackSize = 0;
            }
        }
    }
}
