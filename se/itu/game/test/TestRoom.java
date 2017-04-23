package se.itu.game.test;

import se.itu.game.cave.Room;
import se.itu.game.cave.Thing;
import se.itu.game.cave.init.CaveInitializer;
import se.itu.game.cave.init.Things;

import java.util.Arrays;
import java.util.ArrayList;

public class TestRoom {

  private void r1() {
    /* Test that we can connect two Room:s.
     * Test that the getRoom returns the
     * connected Room.
     * Test that we get null for the other directions.
     */
    Room r = new Room("This is a test",
                      null,
                      null,
                      null,
                      null,
                      Arrays.asList(Things.get("Bird"),
                                    Things.get("Cage")));
    Room north = new Room("This is the North",
                          null,
                          null,
                          r,
                          null,
                          Arrays.asList(Things.get("Rod")));
    r.setConnectingRoom(Room.Direction.NORTH, north);
    assert r.getRoom(Room.Direction.NORTH) == north
      : "Expected north room to be the room we connected to the north";
    assert r.getRoom(Room.Direction.SOUTH) == null
      && r.getRoom(Room.Direction.EAST) == null
      && r.getRoom(Room.Direction.WEST) == null
      : "Expected the rest of the directions to be null";
    assert north.getRoom(Room.Direction.SOUTH) == r
      : "Expected the north Room to have r as its South connection";
  }
  
  private void r2() {
    /* Test that we have the right Thing:s in the Room
     * Test that we can't put null in a room
     * Test that we can't put the same thing twice in a room
     */
    Room r = new Room("This is a test",
                      null,
                      null,
                      null,
                      null,
                      /* Can't use Arrays.asList alone, since its a fixed-length list
                       * So we need to wrap it in an ArrayList in order to be able
                       * to remove Things from it. */
                      new ArrayList<Thing>(Arrays.asList(Things.get("Bird"),
                                                  Things.get("Cage"))));
    assert r.things().size() == 2
      : "Expected two things, got: "
      + r.things().size();
    assert r.things().contains(Things.get("Bird"))
      : "Expected r to contain Bird - things: "
      + r.things();
    assert r.things().contains(Things.get("Cage"))
      : "Expected r to contain Cage - things: "
      + r.things();
    try {
      r.putThing(null);
      assert false : "Shouldn't be possible to add a null thing";
    } catch (NullPointerException expected) {}
    try {
      r.putThing(Things.get("Bird"));
      assert false : "Shouldn't be possible to add the same thing twice";
    } catch (IllegalArgumentException expected) {}
    try {
      r.removeThing(Things.get("Rod"));
      assert false : "Shouldn't be able to remove a non-existing thing";
    } catch (IllegalArgumentException expected) {}
    try {
      r.removeThing(null);
      assert false : "Shouldn't be possible to remove null";
    } catch (NullPointerException expected) {}
    
    Thing thing = r.removeThing(Things.get("Bird"));
    assert thing == Things.get("Bird")
      : "Removal of Bird should return the Bird";
    
    assert ! r.things().contains(Things.get("Bird"))
      : "r shouldn't have the Bird after removing it";
    
  }

  public static void main(String[] args) {
    CaveInitializer.getInstance().initAll();
    TestRoom test = new TestRoom();
    System.out.println("==Running test for the Room class==");
    System.out.println("Running test R1 - connecting two rooms");
    test.r1();
    System.out.println("R1 passed OK.");
    System.out.println("Running test R2 - checking things");
    test.r2();
    System.out.println("R2 passed OK");
  }
  
}
