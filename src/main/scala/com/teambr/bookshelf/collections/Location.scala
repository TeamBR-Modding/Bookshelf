package com.teambr.bookshelf.collections

import java.util

import com.teambr.bookshelf.traits.NBTSavable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.{BlockPos, MathHelper}

/**
  * This file was created for Bookshelf
  *
  * Bookshelf is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis pauljoda
 * @since August 03, 2015
 */
class Location extends NBTSavable {
    private val INVALID: Int = Integer.MIN_VALUE
    var x = INVALID
    var y = INVALID
    var z = INVALID

    /**
     * Main constructor for the location
      *
      * @param xPos X Coord
     * @param yPos Y Coord
     * @param zPos Z Coord
     */
    def this(xPos: Int, yPos: Int, zPos: Int) {
        this()
        x = xPos
        y = yPos
        z = zPos
    }

    /**
     * Used to generate a location from a TileEntity
      *
      * @param tile The { @link TileEntity} to extract from
     */
    def this(tile: TileEntity) {
        this()
        x = tile.getPos.getX
        y = tile.getPos.getY
        z = tile.getPos.getZ
    }

    /**
     * Used to generate a location from double values
      *
      * @param xPos X Coord
     * @param yPos Y Coord
     * @param zPos Z Coord
     */
    def this(xPos: Double, yPos: Double, zPos: Double) {
        this()
        x = MathHelper.floor_double(xPos)
        y = MathHelper.floor_double(yPos)
        z = MathHelper.floor_double(zPos)
    }

    /**
     * Used to create a location with this block pos
      *
      * @param blockPos The block pos
     */
    def this(blockPos : BlockPos) = {
        this()
        x = blockPos.getX
        y = blockPos.getY
        z = blockPos.getZ
    }

    /**
     * Used to use a location as a block position
      *
      * @return A block position for this location
     */
    def asBlockPos : BlockPos = new BlockPos(x, y, z)

    /**
     * Used to shallow copy from another location
      *
      * @param loc Location to copy
     */
    def copyLocation(loc: Location) {
        this.x = loc.x
        this.y = loc.y
        this.z = loc.z
    }

    /**
     * Used to move the location in a vector
      *
      * @param xOffset X offset (can be negative)
     * @param yOffset Y offset (can be negative)
     * @param zOffset Z offset (can be negative)
     */
    def travel(xOffset: Int, yOffset: Int, zOffset: Int) {
        this.x += xOffset
        this.y += yOffset
        this.z += zOffset
    }

    /**
     * Used to move the location in a direction
      *
      * @param dir { @link ForgeDirection} to travel
     */
    def travel(dir: EnumFacing) {
        x += dir.getFrontOffsetX
        y += dir.getFrontOffsetY
        z += dir.getFrontOffsetZ
    }

    /**
     * Add another Location to this one
      *
      * @param loc The location to add
     */
    def add(loc: Location) {
        x += loc.x
        y += loc.y
        z += loc.z
    }

    /**
     * Gets the location that is the sum of the object and another
      *
      * @param loc The location to add
     * @return A new instance of a location that is the sum of the two
     */
    def getSum(loc: Location): Location = {
        new Location(x + loc.x, y + loc.y, z + loc.z)
    }

    /**
     * Subtract another location from this one
      *
      * @param loc The Location to subtract
     */
    def subtract(loc: Location) {
        x -= loc.x
        y -= loc.y
        z -= loc.z
    }

    /**
     * Gets the location that is the difference of the object and another
      *
      * @param loc The location to subtract
     * @return A new instance of a location that is the difference of the two
     */
    def getDifference(loc: Location): Location = {
        new Location(x - loc.x, y - loc.y, z - loc.z)
    }

    /**
     * Shorthand to get location adjacent
      *
      * @param dir Which direction to go
     * @return The adjacent location
     */
    def getAdjacentLocation(dir: EnumFacing): Location = {
        getLocationInDirection(dir, 1)
    }

    /**
     * Get the location in the direction for the given distance
      *
      * @param dir What direction to travel
     * @param distance How many units to travel
     * @return A new instance that is the location at that point
     */
    def getLocationInDirection(dir: EnumFacing, distance: Int): Location = {
        new Location(x + (dir.getFrontOffsetX * distance), y + (dir.getFrontOffsetY * distance), z + (dir.getFrontOffsetZ * distance))
    }

    def getTouchingLocations: util.ArrayList[Location] = {
        getAllAdjacentLocations(false)
    }

    def getAllAdjacentLocations(includeCorners: Boolean): util.ArrayList[Location] = {
        val locations: util.ArrayList[Location] = new util.ArrayList[Location]
        for (dir <- EnumFacing.values()) locations.add(getAdjacentLocation(dir))
        if (!includeCorners) return locations

        //Top layer
        //XXX
        //X X
        //XXX
        locations.add(new Location(x - 1, y + 1, z - 1))
        locations.add(new Location(x - 1, y + 1, z + 1))
        locations.add(new Location(x + 1, y + 1, z + 1))
        locations.add(new Location(x + 1, y + 1, z - 1))
        locations.add(new Location(x, y + 1, z - 1))
        locations.add(new Location(x, y + 1, z + 1))
        locations.add(new Location(x + 1, y + 1, z))
        locations.add(new Location(x - 1, y + 1, z))

        //Middle Layer
        //X X
        // L   ('L' is us)
        //X X
        locations.add(new Location(x - 1, y, z - 1))
        locations.add(new Location(x - 1, y, z + 1))
        locations.add(new Location(x + 1, y, z + 1))
        locations.add(new Location(x + 1, y, z - 1))

        //Lower Layer
        //XXX
        //X X
        //XXX
        locations.add(new Location(x - 1, y - 1, z - 1))
        locations.add(new Location(x - 1, y - 1, z + 1))
        locations.add(new Location(x + 1, y - 1, z + 1))
        locations.add(new Location(x + 1, y - 1, z - 1))
        locations.add(new Location(x, y - 1, z - 1))
        locations.add(new Location(x, y - 1, z + 1))
        locations.add(new Location(x + 1, y - 1, z))
        locations.add(new Location(x - 1, y - 1, z))
        locations
    }

    /**
     * Returns a list off all blocks in between these two
     *
     * Creates a cube with these two being opposite corners
     *
     * @param loc The opposite corner of the cube
     * @param includeInner Set to true to include inside, otherwise it will be hollow
     * @param includeOuter Set to true to include the outside shell
     * @return A list that contains all Locations in between inclusive
     */
    def getAllWithinBounds(loc: Location, includeInner: Boolean, includeOuter: Boolean): util.ArrayList[Location] = {
        val locations: util.ArrayList[Location] = new util.ArrayList[Location]

        val xDirMultiplier: Int = Integer.signum(loc.x - x)
        val yDirMultiplier: Int = Integer.signum(loc.y - y)
        val zDirMultiplier: Int = Integer.signum(loc.z - z)

        val horizMax: Int = Math.abs(loc.x - x)
        val vertMax: Int = Math.abs(loc.y - y)
        val depthMax: Int = Math.abs(loc.z - z)

        for (horiz <- 0 to horizMax) {
            for (vert <- 0 to vertMax) {
                for (depth <- 0 to depthMax) {

                    val xa: Int = this.x + (xDirMultiplier * horiz)
                    val ya: Int = this.y + (yDirMultiplier * vert)
                    val za: Int = this.z + (zDirMultiplier * depth)

                    if (horiz == 0 || horiz == horizMax ||
                            vert == 0 || vert == vertMax ||
                            depth == 0 || depth == depthMax) {
                        if (includeOuter) //We are on the outside
                            locations.add(new Location(xa, ya, za))
                    }
                    else if (includeInner) //We are on the inside
                        locations.add(new Location(xa, ya, za))
                }
            }
        }
        locations
    }

    /**
     * Are we above this location
      *
      * @param pos The position to check if this is above
     * @return True if is above
     */
    def isAbove(pos: Location): Boolean = {
         pos != null && y > pos.y
    }

    /**
     * Is this location below the passed
      *
      * @param pos The position to check if we are below
     * @return True if this is below
     */
    def isBelow(pos: Location): Boolean = {
         pos != null && y < pos.y
    }

    /**
     * Is this location North of the passed
      *
      * @param pos The position to check against
     * @return True if this is north of pos
     */
    def isNorthOf(pos: Location): Boolean = {
         pos != null && z < pos.z
    }

    /**
     * Is this location South of the passed
      *
      * @param pos The position to check against
     * @return True if this is south of pos
     */
    def isSouthOf(pos: Location): Boolean = {
         pos != null && z > pos.z
    }

    /**
     * Is this location East of the passed
      *
      * @param pos The position to check against
     * @return True if this is east of pos
     */
    def isEastOf(pos: Location): Boolean = {
         pos != null && x > pos.x
    }

    /**
     * Is this location West of the passed
      *
      * @param pos The position to check against
     * @return True if this is west of pos
     */
    def isWestOf(pos: Location): Boolean = {
         pos != null && x < pos.x
    }

    /**
     * Are the two on the same X Axis
      *
      * @param pos The other Location
     * @return True if on the same X axis
     */
    def isXAligned(pos: Location): Boolean = {
         pos != null && x == pos.x
    }

    /**
     * Are the two locations on the same Y axis
      *
      * @param pos The other location
     * @return True if on the same Y axis
     */
    def isYAligned(pos: Location): Boolean = {
         pos != null && y == pos.y
    }

    /**
     * Are the two locations on the same Z axis
      *
      * @param pos The other location
     * @return True is on the same Z axis
     */
    def isZAligned(pos: Location): Boolean = {
         pos != null && z == pos.z
    }

    /**
     * Used to return a new instance of this with same values
      *
      * @return A shallow copy of this
     */
    def createNew: Location = {
         new Location(x, y, z)
    }

    /**
     * Checks if the location is a valid location
      *
      * @return True if valid
     */
    def isValid: Boolean = {
         x != INVALID && y != INVALID && z != INVALID
    }

    /**
     * Sets the location to an unreachable location
     */
    def reset() {
        x = INVALID
        y = INVALID
        z = INVALID
    }

    /**
     * Returns the distance between two points
      *
      * @param loc Location to calculate from
     * @return How far away the location is
     */
    def findDistance(loc: Location): Double = {
         Math.sqrt(Math.pow(loc.x - x, 2) + Math.pow(loc.y - y, 2) + Math.pow(loc.z - z, 2))
    }

    /**
     * Writes this to the tag
      *
      * @param tag The tag to write to
     */
    override def writeToNBT(tag: NBTTagCompound) {
        tag.setInteger("Location X", x)
        tag.setInteger("Location Y", y)
        tag.setInteger("Location Z", z)
    }

    /**
     * Read the values for this from the tag
      *
      * @param tag The tag, must have this written to it
     */
    override def readFromNBT(tag: NBTTagCompound) {
        this.x = tag.getInteger("Location X")
        this.y = tag.getInteger("Location Y")
        this.z = tag.getInteger("Location Z")
    }

    /**
     * Writes this to the tag
      *
      * @param tag The tag to write to
     */
    def writeToNBT(tag: NBTTagCompound, id: String) {
        tag.setInteger(id + "Location X", this.x)
        tag.setInteger(id + "Location Y", this.y)
        tag.setInteger(id + "Location Z", this.z)
    }

    /**
     * Read the values for this from the tag
      *
      * @param tag The tag, must have this written to it
     */
    def readFromNBT(tag: NBTTagCompound, id: String) {
        this.x = tag.getInteger(id + "Location X")
        this.y = tag.getInteger(id + "Location Y")
        this.z = tag.getInteger(id + "Location Z")
    }

    def isEqual(o: AnyRef): Boolean = {
        if (this eq o) return true
        if (o == null || getClass != o.getClass) return false
        val location: Location = o.asInstanceOf[Location]
        if (x != location.x) return false
        if (y != location.y) return false
        z == location.z
    }

    override def hashCode: Int = {
        var result: Int = x
        result = 31 * result + y
        result = 31 * result + z
        result
    }

    override def toString: String = {
        "Location{" + "x=" + x + ", y=" + y + ", z=" + z + '}'
    }
}
