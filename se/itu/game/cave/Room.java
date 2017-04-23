package se.itu.game.cave;

import java.util.List;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a room in the cave.
 */
public class Room {

  private String description;
  private Room north;        
  private Room east;        
  private Room south;        
  private Room west;        
  private List<Thing> things;

  public static enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;
  } 
    
  /**
   * Returns a reference to an umodifiable version of the list of Things.
   * @return an umodifiable version of the list of Things in this room.
   */
  public List<Thing> things() {
    return Collections.unmodifiableList(things);
  }

  /**
   * Constructor for Room. A direction (north, east, south or west)
   * with the value null signifies that the room in that direction
   * is either not present or accessible.
   * @param description a String decribing this Room
   * @param north the Room to the north
   * @param east the Room to the east
   * @param south the Room to the south
   * @param west the Room to the west     
   * @param things a List&lt;Thing&gt; of Things in the Room
   * @throws NullPointerException - if things or description is null
   */
  public Room(String description,
              Room north,        
              Room east,
              Room south,        
              Room west,
              List<Thing> things) {
    if ( description == null || things == null) {
      throw new NullPointerException("things or description can't be null.");
    }
    this.description=description;
    this.north=north;
    this.east=east;
    this.south=south;
    this.west=west;
    this.things=things;
  }
    
  /**
   * Remove a Thing from the Room. For convenience reasons this
   * method returns the Thing to remove.
   * @param thing the Thing to remove.
   * @return the Thing to remove.
   * @throws IllegalArgumentException if the Thing to remove is not present in the Room.
   * @throws NullPointerException - if thing is null.
   */
  public Thing removeThing(Thing thing) {
    if (thing == null) {
      throw new NullPointerException("thing can't be null.");
    }
    if (things.remove(thing)) {
      return thing;
    }
    throw new IllegalArgumentException("Can't remove: " + thing);
  }
    
  /**
   * @param direction The direction to Room
   * @param room The Room in direction
   */
  public void setConnectingRoom(Direction direction, Room room) {
    switch (direction) {
      case NORTH:
        north = room;
        break;
      case EAST:
        east = room;
        break;
      case SOUTH:
        south = room;
        break;
      case WEST:
        west = room;
        break;
      default: // This is actually a case where we can skip the default label!
        throw new IllegalArgumentException("Direction not corect, can't happen ;)");
    }
  }
    
  /**
   * Add a Thing to the Room. For convenience reasons this method
   * returns the Thing added.
   * @param thing the Thing to add.
   * @throws NullPointerException - if thing is null.
   * @throws IllegalArgumentException - if thing already exists in list of things
   */
  public void putThing(Thing thing) {
    if (thing == null) {
      throw new NullPointerException("Thing (thing) can't be null.");
    }
    if (things.contains(thing)) {
      throw new IllegalArgumentException("Can't add the same Thing twice");
    }
    things.add(thing);
  }
    
  /**
   * Returns a String representatio of the Room
   * @return a String representatio of the Room
   */
  public String toString() {
    return description + " things: " + things;
  }
    
  /**
   * Returns the Room's description
   * @return the Room's description
   */
  public String description() {
    return description;
  }    
        
  /**
   * Returns the connecting Room in the given direction 
   * @param direction the direction of the room we want.
   * @return connecting Room in the given direction.
   */
  public Room getRoom(Direction direction) {
    switch (direction) {
      case NORTH:
        return north;
      case EAST:
        return east;
      case SOUTH:
        return south;
      case WEST:
        return west;
      default: // This is actually a case where we can skip the default label!
        throw new IllegalArgumentException("Direction not corect, can't happen ;)");
    }
  }
  
}
