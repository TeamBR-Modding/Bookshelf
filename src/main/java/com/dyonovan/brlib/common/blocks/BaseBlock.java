package com.dyonovan.brlib.common.blocks;

import com.dyonovan.brlib.BRLib;
import com.dyonovan.brlib.client.ClientProxy;
import com.dyonovan.brlib.client.renderer.BasicBlockRenderer;
import com.dyonovan.brlib.collections.BlockTextures;
import com.dyonovan.brlib.common.blocks.rotation.IRotation;
import com.dyonovan.brlib.common.blocks.rotation.NoRotation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

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
        if(!player.isSneaking()) {
            player.openGui(BRLib.instance, 0, world, x, y, z);
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
        world.setBlockMetadataWithNotify(x, y, z, getDefaultRotation().getMetaFromEntity(livingBase), 2);
    }

    //The sides are: Bottom (0), Top (1), North (2), South (3), West (4), East (5).
    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon(int side, int metadata) {
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
}
