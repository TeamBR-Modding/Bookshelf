package com.teambr.bookshelf.common.blocks.traits

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.world.{World, WorldServer}

import scala.util.Random

/** Bookshelf
  * Created by Paul Davis on 8/1/2015
  *
  */
trait DropsItems extends Block {
    override def breakBlock(world: World, pos: BlockPos, state : IBlockState): Unit = {
        world match {
            case _: WorldServer => //We are on a server
                world.getTileEntity(pos) match {
                    case tile: IInventory => //This is an inventory
                        val random = new Random
                        for (i <- 0 until tile.getSizeInventory) {
                            val stack = tile.getStackInSlot(i)

                            if(stack != null && stack.stackSize > 0) {
                                val rx = random.nextFloat * 0.8F + 0.1F
                                val ry = random.nextFloat * 0.8F + 0.1F
                                val rz = random.nextFloat * 0.8F + 0.1F

                                val itemEntity = new EntityItem(world,
                                    pos.getX + rx, pos.getY + ry, pos.getZ + rz,
                                    new ItemStack(stack.getItem, stack.stackSize, stack.getItemDamage))

                                if(stack.hasTagCompound)
                                    itemEntity.getEntityItem.setTagCompound(stack.getTagCompound)

                                val factor = 0.05F

                                itemEntity.motionX = random.nextGaussian * factor
                                itemEntity.motionY = random.nextGaussian * factor + 0.2F
                                itemEntity.motionZ = random.nextGaussian * factor
                                world.spawnEntityInWorld(itemEntity)

                                stack.stackSize = 0
                            }
                        }
                    case _ => //Not an inventory
                }
            case _ => //Not on the server
        }
        super.breakBlock(world, pos, state)
    }
}
