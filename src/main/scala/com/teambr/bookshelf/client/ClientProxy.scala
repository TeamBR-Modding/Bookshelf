package com.teambr.bookshelf.client

import com.teambr.bookshelf.client.modelfactory.{ BakeableBlockRegistry, ModelGenerator }
import com.teambr.bookshelf.common.CommonProxy
import com.teambr.bookshelf.common.blocks.traits.BlockBakeable
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.statemap.StateMapperBase
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
class ClientProxy extends CommonProxy {

    /**
     * Called on preInit
     */
    override def preInit() = {}

    /**
     * Called on init
     */
    override def init() = {
        BakeableBlockRegistry.blocks.foreach((block : BlockBakeable) => {

            //We just want one model
            val ignoreState = new StateMapperBase {
                override def getModelResourceLocation(state : IBlockState) : ModelResourceLocation = block.getNormalModelLocation
            }
            ModelLoader.setCustomStateMapper(block, ignoreState)
        })

        ModelGenerator.register()
    }

    /**
     * Called on postInit
     */
    override def postInit() = {}
}
