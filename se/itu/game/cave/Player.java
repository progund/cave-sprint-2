package se.itu.game.cave;

import se.itu.game.cave.Room.Direction;
import se.itu.game.cave.init.CaveInitializer;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a Player in the game
 */
public class Player /* does not extend People */ {

  private static Player player;
  private List<Thing> inventory;
  private Room currentRoom;
    
  /**
   * private constructor to prevent instantiation.
   */
  private Player(Room room) {
    inventory = new ArrayList<Thing>();
    currentRoom = room;
  }

  /**
   * Takes a thing (i e in a room) and puts in the inventory.
   * @param thing The Thing to take (pick up)
   */
  public void takeThing(Thing thing) {
    currentRoom.removeThing(thing);
    inventory.add(thing);
  }

  /**
   * Drop down a thing in the current room and remove it from the inventory.
   * @param thing the Thing to drop down.
   * @throws IllegalArgumentException if the Thing to remove is not present in the inventory.
   */
  public void dropThing(Thing thing) {
    if (!inventory.remove(thing)) {
      throw new IllegalArgumentException("Can't remove: " + thing);
    }
    currentRoom.putThing(thing);
  }
    
  /**
'   * Return the player's inventory
   * @return the inventory
   */
  public List<Thing> inventory() {
    return inventory;
  }
    

  /**
   * @return the one and only instance of Player.
   */
  public static Player getInstance() {
    if (player == null) {
      player = new Player(CaveInitializer.getInstance().getFirstRoom());
    }
    return player;
  }

  /**
   * Returns the current Room.
   * @return the current room.
   */
  public Room currentRoom() {
    return currentRoom;
  }
        
  /**
   * Moves the player in given direction.
   * @param direction the direction in which to move the player.
   * @throws IllegalArgumentException - if the room in direction does not exist.
   */
  public void go(Direction direction) {
    Room newRoom = currentRoom.getRoom(direction);
    if (newRoom == null) {
      // TODO: write new exception: IllegalMoveException
      throw new IllegalArgumentException("Room not existing in direction:  " + direction);
    }
    currentRoom = newRoom;
  }
        
  /**
   * Returns a String representation of the player<br>
   * on the form currentRoom: [the room to String] inventory: [the inventory]
   * @return a String representation of the player
   */
  public String toString() {
    return "currentRoom: " + currentRoom + 
      " inventory: " + inventory ;
  }
}
