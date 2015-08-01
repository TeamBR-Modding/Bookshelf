package com.teambr.bookshelf.scala.traits

import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.world.{WorldServer, World}

import scala.util.Random

/** Bookshelf
  * Created by Paul Davis on 8/1/2015
  *
  */
trait DropsItems extends Block {
    override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, metaData: Int): Unit = {
        world match {
            case WorldServer => //We are on a server
                world.getTileEntity(x, y, z) match {
                    case tile: IInventory => //This is an inventory
                        val random = new Random
                        for (i <- tile.getSizeInventory) {
                            val stack = tile.getStackInSlot(i)

                            if(stack != null && stack.stackSize > 0) {
                                val rx = random.nextFloat * 0.8F + 0.1F
                                val ry = random.nextFloat * 0.8F + 0.1F
                                val rz = random.nextFloat * 0.8F + 0.1F

                                val itemEntity = new EntityItem(world,
                                    x + rx, y + ry, z + rz,
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
                    case _ => //No-OP, not an inventory
                }
            case _ => //No-0P, only perform on server
        }
        super.breakBlock(world, x, y, z, block, metaData)
    }
}
