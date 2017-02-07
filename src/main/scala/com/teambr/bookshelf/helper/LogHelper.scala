package com.teambr.bookshelf.helper

import com.teambr.bookshelf.lib.Reference
import org.apache.logging.log4j.{Level, LogManager}

/**
 * This file was created for Bookshelf
 *
 * Bookshelf is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 04, 2015
 */
object LogHelper {
    val logger = LogManager.getLogger(Reference.MOD_ID)
    
    def log(level: Level, toPrint: AnyRef) : Unit = logger.log(level, toPrint.toString)

    def severe(toPrint: AnyRef) : Unit = log(Level.FATAL, toPrint.toString)
    
    def debug(toPrint: AnyRef) : Unit = log(Level.DEBUG, "[DEBUG] " + toPrint.toString)

    def warning(toPrint: AnyRef) : Unit = log(Level.WARN, toPrint.toString)

    def info(toPrint: AnyRef) : Unit = log(Level.INFO, toPrint.toString)
    
    def config(toPrint: AnyRef) : Unit = log(Level.INFO, toPrint.toString)
}
