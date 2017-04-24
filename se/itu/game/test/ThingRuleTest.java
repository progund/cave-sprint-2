package se.itu.game.test;

import se.itu.game.cave.IllegalMoveException;
import se.itu.game.cave.Room;
import se.itu.game.cave.RuleBook;
import se.itu.game.cave.RuleViolationException;
import se.itu.game.cave.Player;
import se.itu.game.cave.Thing;
import se.itu.game.cave.ThingRule;
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

public class ThingRuleTest {

  private Player player = Player.getInstance();
  private Thing key = Things.get("Skeleton Key");

  private void testT1() {
        
    /*
      The player moves east.
      There is a key there.
      Verify that the player can pick it up - there should be no rule against it.
      Verify that the player has it in its inventory and that the room doesn't
      have it any more.
    */
    try {
      player.go(EAST);
    } catch (IllegalMoveException e) {
      System.err.println("Couldn't go! " + e.getMessage());
      assert false : "Couldn't go EAST from first room";
    }
    try {
      player.takeThing(key);
    } catch (RuleViolationException e) {
      assert false : "Player couldn't take the key!";
    }
    assert(!player.currentRoom().things().contains(key)) : "Could wrongly find Skeleton key in east room ";
    assert(player.inventory().contains(key)) : "Could not find Skeleton key in player's inventory ";
  }

  private void testT2() {
    /* 1. Create a fake rule that the Player must have the Cage in order to pick up the
     * skeletonKey and verify that the rule doesn't allow it 
     * 2. Pick up the cage and try again - verify that the player was allowed this time.
     */
    player.dropThing(key);
    RuleBook
      .addThingRule(key, () ->
                    {
                      if( !Player.getInstance()
                          .inventory()
                          .contains(Things.get("Cage")) ) {
                        throw new RuleViolationException("Must have cage to pick up key");
                      } else {
                        return true;
                      }
                    });
    try {
      player.takeThing(key);
      assert false : "Shouldn't been allowed to pick up key";
    } catch (RuleViolationException e) {
      System.err.println("Can't pick thing up: " + e.getMessage());
    }
    try {
      System.out.println("Picking up cage. Now we should be allowed to pick up key.");
      player.currentRoom().putThing(Things.get("Cage"));
      player.takeThing(Things.get("Cage"));
      player.takeThing(key);
    } catch (RuleViolationException e) {
      System.err.println("Can't pick thing up: " + e.getMessage());
      assert false : "Should have been allowed to pick up key with cage in inventory";
    }    
  }

  public void testT3() {
    /* Test the rules for Bird and Pirate Chest
     * Pick up chest without gold, silver, diamonds and jewels,
     * verify that you couldn't pick up chest.
     *
     * Pick up chest with gold, silver, diamonds and jewels
     * verify that you could.
     */
    Thing diamonds = Things.get("Diamonds");
    assert diamonds != null : "No diamonds";
    Thing gold = Things.get("Gold");
    assert gold != null : "No gold";
    Thing silver = Things.get("Silver");
    assert silver != null : "No silver";
    Thing jewels = Things.get("Jewelry");
    assert diamonds != null : "No jewels";
    Thing chest = Things.get("Pirate Chest");
    assert chest != null : "No pirate chest";
    Player player = Player.getInstance();
    try {
      player.currentRoom().putThing(diamonds);
      player.currentRoom().putThing(gold);
      player.currentRoom().putThing(silver);
      player.currentRoom().putThing(jewels);
      player.currentRoom().putThing(chest);
      player.takeThing(chest);
      assert false : "Could take chest without goods";
    } catch (RuleViolationException expected) {}
    try {
      player.takeThing(diamonds);
      player.takeThing(gold);
      player.takeThing(silver);
      player.takeThing(jewels);
      player.takeThing(chest);
    } catch (RuleViolationException e) {
      assert false : "Couldn't pick up chest with all the goods";      
    }
  }
  
  public static void main(String [] args) {

    System.out.println("==Running tests for the ThingRules==");
    CaveInitializer.getInstance().initAll();
    ThingRuleTest test = new ThingRuleTest();
    System.out.print("Test case TR1 - Go East, pick up key");
    test.testT1();
    System.out.println("OK");
    System.out.println("Test case TR2 - Try to pick up key with rule for cage");
    test.testT2();
    System.out.println("TR2 OK\n");
    System.out.println("\nTest case TR3 - Try to pick up the chest with and without the gold etc");
    test.testT3();
    System.out.println("OK");
  }    
}
