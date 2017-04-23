package se.itu.game.test;

import se.itu.game.cave.Room;
import se.itu.game.cave.Player;
import se.itu.game.cave.Thing;
import se.itu.game.cave.init.CaveInitializer;
import se.itu.game.cave.init.Things;

import static se.itu.game.test.TestUtils.START_ROOM_DESCR;
import static se.itu.game.test.TestUtils.EAST_ROOM_DESCR;
import static se.itu.game.test.TestUtils.WEST_ROOM_DESCR; 
import static se.itu.game.test.TestUtils.NORTH_ROOM_DESCR;

import static se.itu.game.cave.Room.Direction.NORTH;
import static se.itu.game.cave.Room.Direction.EAST;
import static se.itu.game.cave.Room.Direction.SOUTH;
import static se.itu.game.cave.Room.Direction.WEST;

public class ThingTests {

  private Player player = Player.getInstance();
  private Thing key = Things.get("Skeleton Key");

  private void testT1() {
        
    /*
      The player moves east.
      Verify that the description begins with “You are inside a building,”
      Verify that there is a key (Skeleton key”) here.
      The player takes (picks up) the key.
      Verify that the key is missing in the room’s list of things.
      Verify that the key is present in the player’s inventory.
    */
    player.go(EAST);
    assert(player.currentRoom().description().startsWith(EAST_ROOM_DESCR));
    assert(player.currentRoom().things().size() == 1)
      : "Room should only have one thing, had: " + player.currentRoom().things();
      
    assert(player.currentRoom().things().contains(key)) : "Could not find Skeleton key in east room ";

    player.takeThing(key);        
    assert(!player.currentRoom().things().contains(key)) : "Could wrongly find Skeleton key in east room ";
    assert(player.inventory().contains(key)) : "Could not find Skeleton key in player's inventory ";
  }

  private void testT2() {
        
    /*
      Player is still in the east room and now has the key.
      The player drops the key.
      Verify that the key is present in the room’s list of things.
      Verify that the key is missing in the player’s inventory.
    */
    player.dropThing(key);
    assert(player.currentRoom().things().contains(key)) : "Could not find Skeleton key in east room ";
    assert(!player.inventory().contains(key)) : "Could not find Skeleton key in player's inventory ";
  }

  public static void main(String [] args) {

    System.out.println("==Running tests for various operations on Thing==");
    CaveInitializer.getInstance().initAll();
    System.out.print("Test case T1 - Go East, pick up key");
    ThingTests test = new ThingTests();
    test.testT1();
    System.out.println("OK");

    System.out.print("Test case T2 - Drop key");
    test.testT2();
    System.out.println("OK");
  }    
}
