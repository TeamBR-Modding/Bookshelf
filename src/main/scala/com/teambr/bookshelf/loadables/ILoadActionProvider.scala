package com.teambr.bookshelf.loadables

import javax.annotation.Nonnull

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * Defines that this trait will provide an action while the game is loading, extend this trait to have the action
  * called on load automatically if it is an item or block. Others need special loading ability
  *
  * @author Paul Davis "pauljoda"
  * @since 3/6/2016
  */
trait ILoadActionProvider {

    /**
      * Performs the action at the given event
      *
      * @param event The event being called from
      * @param isClient True if only on client side, false (default) for server side
      */
    def performLoadAction(@Nonnull event: AnyRef, isClient : Boolean = false) : Unit = {}
}
