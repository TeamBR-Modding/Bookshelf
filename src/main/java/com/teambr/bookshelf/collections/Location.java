package com.teambr.bookshelf.collections;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to navigate location in a better way (hopefully)
 */
public class Location {
    public int x, y, z;
    private static final int INVALID = Integer.MIN_VALUE;

    /**
     * Default constructor, sets all to INVALID
     */
    public Location() {
        this(INVALID, INVALID, INVALID);
    }

    /**
     * Main constructor for the location
     * @param xPos X Coord
     * @param yPos Y Coord
     * @param zPos Z Coord
     */
    public Location(int xPos, int yPos, int zPos) {
        x = xPos;
        y = yPos;
        z = zPos;
    }

    /**
     * Used to generate a location from a {@link TileEntity}
     * @param tile The {@link TileEntity} to extract from
     */
    public Location(TileEntity tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    /**
     * Used to generate a location from double values
     * @param xPos X Coord
     * @param yPos Y Coord
     * @param zPos Z Coord
     */
    public Location(double xPos, double yPos, double zPos) {
        x = MathHelper.floor_double(xPos);
        y = MathHelper.floor_double(yPos);
        z = MathHelper.floor_double(zPos);
    }

    /**
     * Used to generate a location from a ChunkPosition
     * @param pos {@link ChunkPosition} to extract from
     */
    public Location(ChunkPosition pos) {
        this.x = pos.chunkPosX;
        this.y = pos.chunkPosY;
        this.z = pos.chunkPosZ;
    }


    /**
     * Used to shallow copy from another location
     * @param loc Location to copy
     */
    public void copyLocation(Location loc) {
        this.x = loc.x;
        this.y = loc.y;
        this.z = loc.z;
    }

    /**
     * Used to move the location in a vector
     * @param xOffset X offset (can be negative)
     * @param yOffset Y offset (can be negative)
     * @param zOffset Z offset (can be negative)
     */
    public void travel(int xOffset, int yOffset, int zOffset) {
        this.x += xOffset;
        this.y += yOffset;
        this.z += zOffset;
    }

    /**
     * Used to move the location in a direction
     * @param dir {@link ForgeDirection} to travel
     */
    public void travel(ForgeDirection dir) {
        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;
    }

    /**
     * Add another Location to this one
     * @param loc The location to add
     */
    public void add(Location loc) {
        x += loc.x;
        y += loc.y;
        z += loc.z;
    }

    /**
     * Gets the location that is the sum of the object and another
     * @param loc The location to add
     * @return A new instance of a location that is the sum of the two
     */
    public Location getSum(Location loc) {
        return new Location(x + loc.x, y + loc.y, z + loc.z);
    }

    /**
     * Subtract another location from this one
     * @param loc The Location to subtract
     */
    public void subtract(Location loc) {
        x -= loc.x;
        y -= loc.y;
        z -= loc.z;
    }

    /**
     * Gets the location that is the difference of the object and another
     * @param loc The location to subtract
     * @return A new instance of a location that is the difference of the two
     */
    public Location getDifference(Location loc) {
        return new Location(x - loc.x, y - loc.y, z - loc.z);
    }

    /**
     * Shorthand to get location adjacent
     * @param dir Which direction to go
     * @return The adjacent location
     */
    public Location getAdjacentLocation(ForgeDirection dir) {
        return getLocationInDirection(dir, 1);
    }

    /**
     * Get the location in the direction for the given distance
     * @param dir What direction to travel
     * @param distance How many units to travel
     * @return A new instance that is the location at that point
     */
    public Location getLocationInDirection(ForgeDirection dir, int distance) {
        return new Location(x + (dir.offsetX * distance), y + (dir.offsetY * distance), z + (dir.offsetZ * distance));
    }

    public List<Location> getTouchingLocations() {
        return getAllAdjacentLocations(false);
    }

    public List<Location> getAllAdjacentLocations(boolean includeCorners) {
        List<Location> locations = new ArrayList<>();

        //Get all touching
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            locations.add(getAdjacentLocation(dir));

        //Leave if that is all
        if(!includeCorners)
            return locations;

        //Top layer
        //XXX
        //X X
        //XXX
        locations.add(new Location(x - 1, y + 1, z - 1));
        locations.add(new Location(x - 1, y + 1, z + 1));
        locations.add(new Location(x + 1, y + 1, z + 1));
        locations.add(new Location(x + 1, y + 1, z - 1));
        locations.add(new Location(    x, y + 1, z - 1));
        locations.add(new Location(    x, y + 1, z + 1));
        locations.add(new Location(x + 1, y + 1,     z));
        locations.add(new Location(x - 1, y + 1,     z));

        //Middle Layer
        //X X
        // L   ('L' is us)
        //X X
        locations.add(new Location(x - 1, y, z - 1));
        locations.add(new Location(x - 1, y, z + 1));
        locations.add(new Location(x + 1, y, z + 1));
        locations.add(new Location(x + 1, y, z - 1));

        //Lower Layer
        //XXX
        //X X
        //XXX
        locations.add(new Location(x - 1, y - 1, z - 1));
        locations.add(new Location(x - 1, y - 1, z + 1));
        locations.add(new Location(x + 1, y - 1, z + 1));
        locations.add(new Location(x + 1, y - 1, z - 1));
        locations.add(new Location(    x, y - 1, z - 1));
        locations.add(new Location(    x, y - 1, z + 1));
        locations.add(new Location(x + 1, y - 1,     z));
        locations.add(new Location(x - 1, y - 1,     z));

        return locations;
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
    public List<Location> getAllWithinBounds(Location loc, boolean includeInner, boolean includeOuter) {
        List<Location> locations = new ArrayList<>();

        int xDirMultiplier = Integer.signum(loc.x - x);
        int yDirMultiplier = Integer.signum(loc.y - y);
        int zDirMultiplier = Integer.signum(loc.z - z);

        int horizMax = Math.abs(loc.x - x);
        int vertMax  = Math.abs(loc.y - y);
        int depthMax = Math.abs(loc.z - z);

        for(int horiz = 0; horiz <= horizMax; horiz++) {
            for(int vert = 0; vert <= vertMax; vert++) {
                for(int depth = 0; depth <= depthMax; depth++) {
                    int xa = this.x + (xDirMultiplier * horiz);
                    int ya = this.y + (yDirMultiplier * vert);
                    int za = this.z + (zDirMultiplier * depth);

                    if (horiz == 0 || horiz == horizMax ||
                            vert == 0 || vert == vertMax ||
                            depth == 0 || depth == depthMax) {
                        if(includeOuter)
                            locations.add(new Location(xa, ya, za));
                    }
                    else if(includeInner)
                        locations.add(new Location(xa, ya, za));
                }
            }
        }
        return locations;
    }

    /**
     * Are we above this location
     * @param pos The position to check if this is above
     * @return True if is above
     */
    public boolean isAbove(Location pos) {
        return pos != null && y > pos.y;
    }

    /**
     * Is this location below the passed
     * @param pos The position to check if we are below
     * @return True if this is below
     */
    public boolean isBelow(Location pos) {
        return pos != null && y < pos.y;
    }

    /**
     * Is this location North of the passed
     * @param pos The position to check against
     * @return True if this is north of pos
     */
    public boolean isNorthOf(Location pos) {
        return pos != null && z < pos.z;
    }

    /**
     * Is this location South of the passed
     * @param pos The position to check against
     * @return True if this is south of pos
     */
    public boolean isSouthOf(Location pos) {
        return pos != null && z > pos.z;
    }

    /**
     * Is this location East of the passed
     * @param pos The position to check against
     * @return True if this is east of pos
     */
    public boolean isEastOf(Location pos) {
        return pos != null && x > pos.x;
    }

    /**
     * Is this location West of the passed
     * @param pos The position to check against
     * @return True if this is west of pos
     */
    public boolean isWestOf(Location pos) {
        return pos != null && x < pos.x;
    }

    /**
     * Are the two on the same X Axis
     * @param pos The other Location
     * @return True if on the same X axis
     */
    public boolean isXAligned(Location pos) {
        return pos != null && x == pos.x;
    }

    /**
     * Are the two locations on the same Y axis
     * @param pos The other location
     * @return True if on the same Y axis
     */
    public boolean isYAligned(Location pos) {
        return pos != null && y == pos.y;
    }

    /**
     * Are the two locations on the same Z axis
     * @param pos The other location
     * @return True is on the same Z axis
     */
    public boolean isZAligned(Location pos) {
        return pos != null && z == pos.z;
    }

    /**
     * Used to return a new instance of this with same values
     * @return A shallow copy of this
     */
    public Location createNew() {
        return new Location(x, y, z);
    }

    /**
     * Checks if the location is a valid location
     * @return True if valid
     */
    public boolean isValid() {
        return x != INVALID && y != INVALID && z != INVALID;
    }

    /**
     * Sets the location to an unreachable location
     */
    public void reset() {
        x = y = z = INVALID;
    }

    /**
     * Returns the distance between two points
     * @param loc Location to calculate from
     * @return How far away the location is
     */
    public double findDistance(Location loc) {
        return Math.sqrt((Math.pow(loc.x - x, 2)) + (Math.pow(loc.y - y, 2)) + (Math.pow(loc.z - z, 2)));
    }

    /**
     * Writes this to the tag
     * @param tag The tag to write to
     */
    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("Location X", x);
        tag.setInteger("Location Y", y);
        tag.setInteger("Location Z", z);
    }

    /**
     * Read the values for this from the tag
     * @param tag The tag, must have this written to it
     */
    public void readFromNBT(NBTTagCompound tag) {
        this.x = tag.getInteger("Location X");
        this.y = tag.getInteger("Location Y");
        this.z = tag.getInteger("Location Z");
    }

    /**
     * Writes this to the tag
     * @param tag The tag to write to
     */
    public void writeToNBT(NBTTagCompound tag, String id) {
        tag.setInteger(id + "Location X", this.x);
        tag.setInteger(id + "Location Y", this.y);
        tag.setInteger(id + "Location Z", this.z);
    }

    /**
     * Read the values for this from the tag
     * @param tag The tag, must have this written to it
     */
    public void readFromNBT(NBTTagCompound tag, String id) {
        this.x = tag.getInteger(id + "Location X");
        this.y = tag.getInteger(id + "Location Y");
        this.z = tag.getInteger(id + "Location Z");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        if (y != location.y) return false;
        return z == location.z;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}