package se.itu.game.test;

import se.itu.game.cave.Room;
import se.itu.game.cave.Player;
import se.itu.game.cave.Thing;
import se.itu.game.cave.init.CaveInitializer;
import static se.itu.game.cave.Room.Direction.NORTH;
import static se.itu.game.cave.Room.Direction.EAST;
import static se.itu.game.cave.Room.Direction.SOUTH;
import static se.itu.game.cave.Room.Direction.WEST;

import static se.itu.game.test.TestUtils.START_ROOM_DESCR;
import static se.itu.game.test.TestUtils.EAST_ROOM_DESCR;
import static se.itu.game.test.TestUtils.WEST_ROOM_DESCR; 
import static se.itu.game.test.TestUtils.NORTH_ROOM_DESCR;

public class NavigationTests {

  private void testN1() {
        
    /*
      Verify that the description begins with “You are standing at the end of”
    */
    CaveInitializer.getInstance().initAll();
    Player player = Player.getInstance();
    assert(player.currentRoom().description().startsWith(START_ROOM_DESCR))
      : "Wrong initial room. Expected You are standing... Got: " +
      player.currentRoom().description();
  }

  private void testN2() {

    /*
      The player moves west.
      Verify that the description begins with “You have walked up a hill”
    */
    CaveInitializer.getInstance().initAll();
    Player player = Player.getInstance();
    player.go(WEST);
    assert(player.currentRoom().description().startsWith(WEST_ROOM_DESCR))
      : "Wrong room after going West. Expected: You have walked up... Got: " +
      player.currentRoom().description();
  }

  public static void main(String [] args) {

    System.out.println("==Running tests for navigation==");

    System.out.print("Test case N1: ");
    new NavigationTests().testN1();
    System.out.println("OK");

    System.out.print("Test case N2: ");
    new NavigationTests().testN2();
    System.out.println("OK");        
  }
    
}
