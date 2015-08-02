package com.teambr.bookshelf.common.blocks

import com.teambr.bookshelf.Bookshelf
import com.teambr.bookshelf.client.ClientProxy
import com.teambr.bookshelf.client.renderer.BasicBlockRenderer
import com.teambr.bookshelf.collections.BlockTextures
import com.teambr.bookshelf.common.blocks.rotation.{IRotation, NoRotation}
import com.teambr.bookshelf.common.tiles.IOpensGui
import com.teambr.bookshelf.scala.traits.DropsItems
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World, WorldServer}
import net.minecraftforge.common.util.ForgeDirection

/**
 * Used as a common class for all blocks. Makes things a bit easier
 * @param material What material the block should be
 * @param name The unlocalized name of the block : Must be format "MODID:name"
 * @param tile Should the block have a tile, pass the class
 */
abstract class BlockBase(val material: Material, val name: String, val tile: Class[_ <: TileEntity]) extends BlockContainer(material) with DropsItems {
    @SideOnly(Side.CLIENT)
    var textures : BlockTextures = _

    //Set up the block
    setBlockName(name)
    setCreativeTab(getCreativeTab match {
        case Some(i) => i
        case _ => null
    })
    setHardness(getHardness)

    /**
     * Used to define the creative tab
     *
     * If you plan to override this, follow convention and return Some(TAB)
     * I'm sure most know that but this is really just a note to myself
     *
     * @return The tab to display on. None by default
     */
    def getCreativeTab : Option[CreativeTabs] = None

    /**
     * Used to return the hardness
     * @return How hard the block is. 2.0F by default
     */
    def getHardness: Float = 2.0F

    /**
     * Called when the block is activated
     * @param world The world
     * @param x The X position
     * @param y The Y position
     * @param z The Z position
     * @param player The player
     */
    override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float) : Boolean = {
        world match {
            case _: WorldServer =>
                world.getTileEntity(x, y, z) match {
                    case _: IOpensGui =>
                        player.openGui(Bookshelf.instance, 0, world, x, y, z)
                        true
                    case _ => false
                }
            case _ => true
        }
    }

    /**
     * Called by Minecraft to generate the default Icons
     * @param iconRegistry The registry
     */
    override def registerBlockIcons(iconRegistry: IIconRegister) {
        generateDefaultTextures(iconRegistry)
    }

    /**
     * Our call to register icons. Define textures here
     * @param iIconRegister The registry
     */
    def generateDefaultTextures(iIconRegister: IIconRegister) {
        this.blockIcon = iIconRegister.registerIcon(name)
        this.textures = new BlockTextures(iIconRegister, name)
    }

    /**
     * Gets the default rotation
     * @return The default rotation, NoRotation by default
     */
    def getDefaultRotation : IRotation = new NoRotation

    override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, livingBase: EntityLivingBase, itemStack: ItemStack) {
        //Calls upon the default rotation to set the meta
        world.setBlockMetadataWithNotify(x, y, z, getDefaultRotation.getMetaFromEntity(world, x, y, z, livingBase, itemStack), 2)
    }

    @SideOnly(Side.CLIENT)
    override def getIcon (side: Int, metadata: Int) : IIcon =  {
        side match {
            case 0 =>
                textures.getDown(metadata, getDefaultRotation)
            case 1 =>
                textures.getUp(metadata, getDefaultRotation)
            case 2 =>
                textures.getNorth(metadata, getDefaultRotation)
            case 3 =>
                textures.getSouth(metadata, getDefaultRotation)
            case 4 =>
                textures.getWest(metadata, getDefaultRotation)
            case 5 =>
                textures.getEast(metadata, getDefaultRotation)
            case _ =>
                this.blockIcon
        }
    }

    override def renderAsNormalBlock: Boolean = {
        false
    }

    override def isOpaqueCube: Boolean = {
        false
    }

    override def getRenderType: Int = {
        BasicBlockRenderer.renderID
    }

    override def canRenderInPass(pass: Int): Boolean = {
        ClientProxy.renderPass = pass
        true
    }

    override def getRenderBlockPass: Int = {
        if (textures.getOverlay != null) 1 else 0
    }

    override def isSideSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean = {
        true
    }

    /**
     * Create a new tile
     */
    override def createNewTileEntity(world : World, meta : Int): TileEntity = {
        if(tile != null) tile.newInstance else null
    }
}
