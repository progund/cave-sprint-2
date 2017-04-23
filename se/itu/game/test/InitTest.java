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
import static se.itu.game.test.TestUtils.SOUTH_4_ROOM_DESCR;

import static se.itu.game.cave.Room.Direction.NORTH;
import static se.itu.game.cave.Room.Direction.EAST;
import static se.itu.game.cave.Room.Direction.SOUTH;
import static se.itu.game.cave.Room.Direction.WEST;

public class InitTest {

  static {
    CaveInitializer.getInstance().initAll();
  }
  
  private static Player player;
  private static final Thing skeletonKey = Things.get("Skeleton Key");
  private static final Thing cage = Things.get("Cage");

  /*

  */
  public InitTest(){
    player = Player.getInstance();
  }

  public void testI2() {
    /*
      Go in an illegal direction. See what happens.
      The player now is in the Cage room with no NORTH exit
     */
    try {
      player.go(NORTH);
      assert false : "Should throw illegal argument exception!"
        + " player's state: " + player;
    } catch (IllegalArgumentException iae) {
      ; // Expected!
    }
  }
  
  public void testI1() {
    /*
      Go east, verify room desc
      pick up the Skeleton Key, verify inventory
      go west, verify room desc
      go south * 4, verify room desc
      go west, verify room has Cage
      pick up the Cage, verify inventory + room things
     */
    player.go(EAST);
    assert player.currentRoom().description().startsWith(EAST_ROOM_DESCR)
      : "East from first room failed. Player's room: "
      + player.currentRoom();
    player.takeThing(skeletonKey);
    assert player.inventory().contains(skeletonKey)
      : "Player couldn't take " + skeletonKey + " room: "
      + player.currentRoom();
    player.go(WEST);
    assert player.currentRoom().description().startsWith(START_ROOM_DESCR)
      : "West from east room failed. Player's room: "
      + player.currentRoom();
    for (int i = 0; i < 4; i++) {
      player.go(SOUTH);
    }
    assert player.currentRoom().description().startsWith(SOUTH_4_ROOM_DESCR)
      : "South x 4 from first room failed. Player's room: "
      + player.currentRoom();
    player.go(WEST);
    assert player.currentRoom().things().contains(cage)
      : "Cage room didn't have cage. Player's room: "
      + player.currentRoom();
    player.takeThing(cage);
    assert player.inventory().contains(cage)
      : "Player didn't have cage after picking it up."
      + " Player: " + player;
    assert !player.currentRoom().things().contains(cage)
      : "Room still has cage after player picking it up."
      + " Player's room: " + player.currentRoom();
  }
  
  public static void main(String[] args) {
    System.out.println("==Running test for the CaveInitializer class==");
    System.out.println("Running I1 (move around, pick up key):");
    InitTest test = new InitTest();
    test.testI1();
    System.out.println("I1 Passed.");
    System.out.println("Running I2 (try an illegal move - go to room which doesn't exist)");
    test.testI2();
    System.out.println("I2 passed, illegal argument caught.");
  }
  
}
