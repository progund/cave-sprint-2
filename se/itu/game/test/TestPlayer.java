package se.itu.game.test;

import se.itu.game.cave.Room;
import se.itu.game.cave.Thing;
import se.itu.game.cave.Player;
import se.itu.game.cave.init.CaveInitializer;
import se.itu.game.cave.init.Things;

import java.util.Arrays;
import java.util.ArrayList;

public class TestPlayer {

  private void p1() {
    /* Go EAST from the start room and
     * pick up the Skeleton key
     * Player should now have the key.
     * Go WEST back to the start room and
     * drop the Skeleton key.
     * The player should now not have the key anymore.
     */
    Player player = Player.getInstance();
    player.go(Room.Direction.EAST);
    player.takeThing(Things.get("Skeleton Key"));
    assert player.inventory().contains(Things.get("Skeleton Key"))
      : "Player should have the key after picking it up!";
    player.dropThing(Things.get("Skeleton Key"));
    assert player.inventory().size() == 0
      : "Player's inventory should be size 0 after dropping the key";
    assert ! player.inventory().contains(Things.get("Skeleton Key"))
      : "Player's inventory shouldn't contain the key after dropping it";
    assert player.currentRoom().things().contains(Things.get("Skeleton Key"))
      : "Skeleton key is not in room after dropping it there: "
      + player;
  }

  private void p2() {
    /* Pick up the Key again.
     * Test that you can't drop a thing you don't have
     */
    Player player = Player.getInstance();
    player.takeThing(Things.get("Skeleton Key"));
    try {
      player.dropThing(Things.get("Rod"));
      assert false : "Shouldn't be possible to drop a thing the player doesn't have";
    } catch (IllegalArgumentException expected) {}
    // Go back to the first room
    player.go(Room.Direction.WEST);
  }

  private void p3() {
    /* Go SOUTH 4 times and then it shouldn't be possible to go SOUTH again
     * We're testing that we can't go in an illegal direction.
     */
    // First make sure we're in the right room
    Player player = Player.getInstance();
    assert player.currentRoom().description().startsWith(TestUtils.START_ROOM_DESCR)
      : "We're in the wrong room. Run the tests for navigation or "
      + "review the previous test cases.";
    try {
      for(int i = 0; i < 4; i++) {
        player.go(Room.Direction.SOUTH);
      }
      assert player.currentRoom().description().startsWith(TestUtils.SOUTH_4_ROOM_DESCR)
        : "We're in the wrong room. Run the tests for navigation or "
        + "review the previous test cases.";
      assert player.currentRoom().getRoom(Room.Direction.SOUTH) == null
        : "There should be no room to the South from here.";
      player.go(Room.Direction.SOUTH);
      assert false : "Shouldn't be able to go in a direction with no connecting Room";
    } catch (IllegalArgumentException expected) {}
  }
  
  public static void main(String[] args) {
    CaveInitializer.getInstance().initAll();
    TestPlayer test = new TestPlayer();
    System.out.println("==Running test for the Player class==");
    System.out.println("Running test P1 (Picking up and dropping down things)");
    test.p1();
    System.out.println("P1 passed OK");
    System.out.println("Running test P2 (Dropping things you already have etc)");
    test.p2();
    System.out.println("P3 passed OK");
    System.out.println("Running test P3 (Moving in illegal direction)");
    test.p3();
    System.out.println("P3 passed OK");
  }
}
