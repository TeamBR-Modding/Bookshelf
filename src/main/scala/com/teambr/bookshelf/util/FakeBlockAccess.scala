package com.teambr.bookshelf.util

import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, BlockPos}
import net.minecraft.world.{WorldType, IBlockAccess}
import net.minecraft.world.biome.BiomeGenBase

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 1/31/2016
  */
class FakeBlockAccess(state : IBlockState) extends IBlockAccess {

    var tileEntity : TileEntity = null

    def this(state : IBlockState, tileEntity: TileEntity) = {
        this(state)
        this.tileEntity = tileEntity
    }

    override def getBlockState(blockPos : BlockPos) = if(FakeBlockAccess.isOrigin(blockPos)) state else Blocks.air.getDefaultState

    override def getTileEntity(blockPos: BlockPos) = if(FakeBlockAccess.isOrigin(blockPos)) tileEntity else null

    override def getCombinedLight(blockPos: BlockPos, light : Int) = 0xF000F0

    override def getStrongPower(blockPos: BlockPos, side : EnumFacing) = 0

    override def isAirBlock(blockPos: BlockPos) = !FakeBlockAccess.isOrigin(blockPos)

    override def getBiomeGenForCoords(blockPos: BlockPos) = BiomeGenBase.desert

    override def extendedLevelsInChunkCache = false

    override def isSideSolid(blockPos: BlockPos, side : EnumFacing, default : Boolean) =
        if(FakeBlockAccess.isOrigin(blockPos)) state.getBlock.isSideSolid(this, blockPos, side) else default

    override def getWorldType = WorldType.DEFAULT
}

object FakeBlockAccess {
    val ORGIN : BlockPos = new BlockPos(0, 0, 0)

    def isOrigin(blockPos: BlockPos) =  ORGIN.equals(blockPos)
}
